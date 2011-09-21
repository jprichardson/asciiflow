package com.lewish.asciiflow.client;

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
