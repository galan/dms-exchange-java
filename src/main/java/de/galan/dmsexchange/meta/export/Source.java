package de.galan.dmsexchange.meta.export;

/**
 * Information about the exporting dmns.
 *
 * @author daniel
 */
public class Source {

	private String name;
	private String version;
	private String url;
	private String email;


	public Source(String name, String version, String url, String email) {
		this.name = name;
		this.version = version;
		this.url = url;
		this.email = email;
	}


	public String getName() {
		return name;
	}


	public String getVersion() {
		return version;
	}


	public String getUrl() {
		return url;
	}


	public String getEmail() {
		return email;
	}

}
