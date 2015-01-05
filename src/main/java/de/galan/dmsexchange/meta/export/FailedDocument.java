package de.galan.dmsexchange.meta.export;

/**
 * Potential information about a document that failed during the export.
 *
 * @author daniel
 */
public class FailedDocument {

	private String message;
	private String idUser;
	private String idSystem;


	public FailedDocument(String message) {
		this(message, null, null);
	}


	public FailedDocument(String message, String idSystem, String idUser) {
		this.message = message;
		this.idSystem = idSystem;
		this.idUser = idUser;
	}


	public String getMessage() {
		return message;
	}


	public String getIdUser() {
		return idUser;
	}


	public String getIdSystem() {
		return idSystem;
	}

}
