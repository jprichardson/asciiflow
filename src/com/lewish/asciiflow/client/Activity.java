package com.lewish.asciiflow.client;

/**
 * Enum of all pages within the app.
 * 
 * @author lewis
 */
public enum Activity {
	Draw("DRAW"),
	About("ABOUT"),
	Gallery("GALLERY"),
	;

	private final String label;

	private Activity(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
