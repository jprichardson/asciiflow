package com.lewish.asciigram.client;

import com.google.gwt.user.client.ui.HTML;

public class Cell extends HTML {
	public final int x;
	public final int y;

	String value;
	String commitValue;
	String drawValue;

	boolean highlight;
	boolean drawHighlight;

	Cell(int x, int y) {
		this.x = x;
		this.y = y;
		setHTML("&nbsp;");
	}

	void pushValue(String value) {
		this.value = value;
		if(value == null || value.equals(" ")) {
			value = null;
			setHTML("&nbsp;");
		} else {
			setText(value);
		}
	}

	void pushHighlight() {
		highlight = drawHighlight;
		if(highlight) {
			addStyleName(CssStyles.Drawing);
		} else {
			removeStyleName(CssStyles.Drawing);
		}
	}

	public String getValue() {
		return value;
	}
}
