package com.lewish.asciiflow.client;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HTML;

public class Cell extends HTML {
	public static final int HEIGHT = 14;
	public static final int WIDTH = 8;

	public final int x;
	public final int y;

	String value;
	public String commitValue;
	String drawValue;

	boolean highlight;
	boolean drawHighlight;

	Cell(int x, int y) {
		this.x = x;
		this.y = y;
		DOM.setStyleAttribute(getElement(), "top", HEIGHT * y + "px");
		DOM.setStyleAttribute(getElement(), "left", WIDTH * x + "px");
		setHTML("&nbsp;");
	}

	public void pushValue(String value) {
		this.value = value;
		if(value == null || value.equals(" ")) {
			value = null;
			setHTML("&nbsp;");
		} else {
			setText(value);
		}
	}

	public void pushHighlight() {
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
