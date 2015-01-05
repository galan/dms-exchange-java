package de.galan.dmsexchange.meta.export;

import static org.apache.commons.lang3.StringUtils.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.galan.commons.time.ApplicationClock;
import de.galan.dmsexchange.meta.User;
import de.galan.dmsexchange.util.Version;


/**
 * Metadata an export-archive defines.
 *
 * @author daniel
 */
public class Export {

	private String version;
	private String description;
	private User exportBy;
	private Source source;
	private ZonedDateTime tsExport;

	private int documentsSuccessfulAmount;
	private List<FailedDocument> documentsFailed;


	public Export() {
		version = Version.SUPPORTED_VERSION;
		tsExport = ZonedDateTime.now(ApplicationClock.getClock());
	}


	public String getVersion() {
		return version;
	}


	public void setVersion(String version) {
		this.version = version;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public User getExportBy() {
		return exportBy;
	}


	public void setExportBy(User exportBy) {
		this.exportBy = exportBy;
	}


	public Source getSource() {
		return source;
	}


	public void setSource(Source source) {
		this.source = source;
	}


	@JsonIgnore
	public String getSourceAsString() {
		return (source == null) ? "unknown" : getSource().toString();
	}


	public ZonedDateTime getTsExport() {
		return tsExport;
	}


	public void setTsExport(ZonedDateTime tsExport) {
		this.tsExport = tsExport;
	}


	public void incrementDocumentsSuccessfulAmount() {
		documentsSuccessfulAmount++;
	}


	public int getDocumentsSuccessfulAmount() {
		return documentsSuccessfulAmount;
	}


	public void addDocumentFailed(String message) {
		if (isNotBlank(message)) {
			addDocumentFailed(new FailedDocument(message));
		}
	}


	public void addDocumentFailed(String message, String idSystem, String idUser) {
		if (isNotBlank(message)) {
			addDocumentFailed(new FailedDocument(message, idSystem, idUser));
		}
	}


	public void addDocumentFailed(FailedDocument failed) {
		if (failed != null) {
			if (documentsFailed == null) {
				documentsFailed = new ArrayList<>();
			}
			documentsFailed.add(failed);
		}
	}

}
