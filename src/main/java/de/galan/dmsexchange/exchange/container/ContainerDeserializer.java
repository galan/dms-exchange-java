package de.galan.dmsexchange.exchange.container;

import static de.galan.verjson.util.Transformations.*;
import static java.nio.charset.StandardCharsets.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;

import de.galan.dmsexchange.meta.Document;
import de.galan.dmsexchange.meta.DocumentFile;
import de.galan.dmsexchange.meta.Revision;
import de.galan.dmsexchange.util.InvalidArchiveException;
import de.galan.dmsexchange.util.Version;
import de.galan.verjson.core.IOReadException;
import de.galan.verjson.core.NamespaceMismatchException;
import de.galan.verjson.core.VersionNotSupportedException;
import de.galan.verjson.step.ProcessStepException;


/**
 * Converts container-archives back to {@link Document}s.
 *
 * @author daniel
 */
public class ContainerDeserializer extends AbstractContainer {

	private static final Pattern REVISION_PATTERN = Pattern.compile("^" + REVISIONS_DIRNAME + "/[0-9]{4}[01][0-9][0-3][0-9]T[0-2][0-9]([0-5][0-9]){2}Z_.*");


	protected Document unarchive(byte[] data, boolean standalone) throws InvalidArchiveException, VersionNotSupportedException, NamespaceMismatchException, ProcessStepException, IOReadException, IOException {
		return unarchive(new ByteArrayInputStream(data), standalone);
	}


	/**
	 * Reads a tar/tgz from the given InputStream and extracts the Document containing. The given InputStream is not
	 * closed and must be closed by the caller.
	 */
	public Document unarchive(InputStream is, boolean standalone) throws IOException, InvalidArchiveException, VersionNotSupportedException, NamespaceMismatchException, ProcessStepException, IOReadException {
		Document result = null;
		Map<String, byte[]> revisions = new HashMap<>();
		//@SuppressWarnings("resource")
		TarArchiveInputStream tar = new TarArchiveInputStream(standalone ? new GzipCompressorInputStream(is) : is);
		TarArchiveEntry entry = null;
		while((entry = tar.getNextTarEntry()) != null) {
			if (!entry.isDirectory()) {
				if (isMetadata(entry.getName())) {
					result = readMeta(IOUtils.toByteArray(tar));
				}
				else if (isRevision(entry.getName())) {
					byte[] baos = IOUtils.toByteArray(tar);
					revisions.put(entry.getName(), baos);
				}
				else {
					throw new InvalidArchiveException("Container has invalid files ('" + entry.getName() + "')");
				}
			}
		}
		if (result == null) {
			throw new InvalidArchiveException("Container is empty");
		}
		merge(result, revisions);
		return result;

	}


	protected void merge(Document result, Map<String, byte[]> revisions) throws InvalidArchiveException {
		for (DocumentFile df: result.getDocumentFiles()) {
			for (Revision revision: df.getRevisions()) {
				String generated = generateRevisionName(df, revision);
				byte[] data = revisions.get(generated);
				if (data == null || data.length == 0) {
					throw new InvalidArchiveException("Revision for '" + generated + "' not found");
				}
				revision.setData(data);
			}
		}
	}


	protected Document readMeta(byte[] metaBytes) throws IOException, InvalidArchiveException, VersionNotSupportedException, NamespaceMismatchException, ProcessStepException, IOReadException {
		String metaString = new String(metaBytes, UTF_8);
		JsonNode metaNode = getVerjson().readTree(metaString);
		long version = determineVersion(metaNode);
		return getVerjson().readPlain(metaNode, version);
	}


	protected long determineVersion(JsonNode metaNode) throws InvalidArchiveException {
		// evaluate version string from export json
		String version = null;
		try {
			version = obj(metaNode).get("version").asText();
		}
		catch (Exception ex) {
			throw new InvalidArchiveException("Unable to determine version from meta.json");
		}
		// evaluate mapped version for verjson
		Long result = Version.getVerjson(version);
		if (result == null) {
			throw new InvalidArchiveException("Version '" + version + "' not supported");
		}
		return result;
	}


	protected boolean isMetadata(String name) {
		return StringUtils.equals(name, "meta.json");
	}


	protected boolean isRevision(String name) {
		return REVISION_PATTERN.matcher(name).matches();
	}

}
