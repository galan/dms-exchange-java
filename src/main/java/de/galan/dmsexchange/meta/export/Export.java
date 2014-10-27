package de.galan.dmsexchange.meta.export;

import java.time.ZonedDateTime;
import java.util.List;

import de.galan.dmsexchange.util.Version;


/**
 * daniel should have written a comment here.
 *
 * @author daniel
 */
public class Export {

	private String version = Version.VERSION;
	private String description;
	private String exportBy;
	private Source source;
	private ZonedDateTime tsExport;
	private int documentsSuccessfulAmount;
	private List<FailedDocument> documentsFailed;


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


	public String getExportBy() {
		return exportBy;
	}


	public void setExportBy(String exportBy) {
		this.exportBy = exportBy;
	}


	public Source getSource() {
		return source;
	}


	public void setSource(Source source) {
		this.source = source;
	}


	public ZonedDateTime getTsExport() {
		return tsExport;
	}


	public void setTsExport(ZonedDateTime tsExport) {
		this.tsExport = tsExport;
	}

}
