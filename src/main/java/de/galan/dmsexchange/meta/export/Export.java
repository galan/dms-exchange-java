package de.galan.dmsexchange.meta.export;

import java.time.ZonedDateTime;
import java.util.List;

import de.galan.commons.time.ApplicationClock;
import de.galan.dmsexchange.meta.User;
import de.galan.dmsexchange.util.Version;


/**
 * daniel should have written a comment here.
 *
 * @author daniel
 */
public class Export {

	private String version = Version.VERSION;
	private String description;
	private User exportBy;
	private Source source;
	private ZonedDateTime tsExport;
	private int documentsSuccessfulAmount;
	private List<FailedDocument> documentsFailed;


	public Export() {
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


	public ZonedDateTime getTsExport() {
		return tsExport;
	}


	public void setTsExport(ZonedDateTime tsExport) {
		this.tsExport = tsExport;
	}

}
