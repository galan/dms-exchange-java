package de.galan.dmsexchange.meta.document;

/**
 * Rotation a document-file is stored in.
 *
 * @author daniel
 */
public enum Rotation {

	DEGREE_0(0),
	DEGREE_90(90),
	DEGREE_180(180),
	DEGREE_270(270);

	private int degree;


	private Rotation(int degree) {
		this.degree = degree;
	}


	public int getDegree() {
		return degree;
	}

}
