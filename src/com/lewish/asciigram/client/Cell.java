package com.lewish.asciigram.client;

import com.google.gwt.user.client.ui.HTML;

public class Cell extends HTML {

	private final int x;
	private final int y;
	private String value;
	private String drawValue;
	private boolean drawChanged = false;
	private boolean drawSet = false;
	private boolean drawingStyle = false;

	public Cell(int x, int y) {
		this.x = x;
		this.y = y;
		addStyleName("character");
		setHTML("&nbsp;");
		
	}

	public void addListener(Controller controller) {
		addMouseDownHandler(controller);
		addMouseOverHandler(controller);
		addMouseUpHandler(controller);
	}

	public void refreshDraw() {
		if (!drawChanged && !drawSet && drawValue != null) {
			drawChanged = true;
			drawValue = null;
		}
		if(drawSet) {
			addDrawingStyle();
		} else {
			removeDrawingStyle();
		}
		if(!drawChanged) {
			drawSet = false;
			return;
		}
		String val;
		if (drawValue != null) {
			val = drawValue;
			
		} else {
			val = value != null ? value : " ";
		}
		if (val.equals(" ")) {
			setHTML("&nbsp;");
		} else {
			setText(val);
		}
		drawChanged = false;
		drawSet = false;
	}

	public void commitDraw() {
		if (drawValue != null) {
			value = drawValue == " " ? null : drawValue;
			removeDrawingStyle();
		}
	}

	public void addDrawingStyle() {
		if(!drawingStyle) {
			drawingStyle = true;
			addStyleName(CssStyles.Drawing);
		}
	}

	public void removeDrawingStyle() {
		if(drawingStyle) {
			drawingStyle = false;
			removeStyleName(CssStyles.Drawing);
		}
	}

	public void setDrawValue(String character) {
		//if (character == null && drawValue == null) return;
		drawSet = true;
		if (character != null && character.equals(drawValue))return;
		drawValue = character;
		drawChanged = true;
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
