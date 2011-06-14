//Copyright Lewis Hemens 2011
package com.lewish.asciigram.client;

import com.google.gwt.user.client.ui.HTML;

public class Cell extends HTML {

	private final int x;
	private final int y;

	private String value;
	private String commitValue;
	private String drawValue;

	private boolean highlight;
	private boolean drawHighlight;

	public Cell(int x, int y) {
		this.x = x;
		this.y = y;
		setHTML("&nbsp;");
	}

	public void addListener(Controller controller) {
		addMouseDownHandler(controller);
		addMouseOverHandler(controller);
		addMouseUpHandler(controller);
	}

	public void refreshDraw() {
		String value = drawValue != null ? drawValue : commitValue;
		if(this.value != value) {
			pushValue(value);
		}
		if(highlight != drawHighlight) {
			pushHighlight();
		}
		drawValue = null;
		drawHighlight = false;
	}

	private void pushValue(String value) {
		this.value = value;
		if(value == null || value.equals(" ")) {
			value = null;
			setHTML("&nbsp;");
		} else {
			setText(value);
		}
	}
	
	private void pushHighlight() {
		highlight = drawHighlight;
		if(highlight) {
			addStyleName(CssStyles.Drawing);
		} else {
			removeStyleName(CssStyles.Drawing);
		}
	}

	public void commitDraw() {
		if(value != null) {
			commitValue = value.equals(" ") ? null : value;
			if(highlight == true) {
				pushHighlight();
			}
		}
	}

	public void setDrawHighlight(boolean highlight) {
		drawHighlight = highlight;
	}

	public void setDrawValue(String character) {
		setDrawValue(character, true);
	}

	public void setDrawValue(String value, boolean highlight) {
		drawValue = value;
		setDrawHighlight(highlight);
	}

	public String getValue() {
		return value;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
