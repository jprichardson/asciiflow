package com.lewish.asciigram.client;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.inject.Singleton;
import com.lewish.asciigram.client.tools.Tool;

@Singleton
public class Controller implements MouseDownHandler, MouseOverHandler,
		MouseUpHandler, KeyDownHandler, KeyPressHandler {

	private Tool currentTool;
	private Cell hoverCell;

	public void setTool(Tool tool) {
		if(currentTool != null) {
			currentTool.cleanup();
		}
		currentTool = tool;
	}

	@Override
	public void onMouseOver(MouseOverEvent event) {
		if (event.getSource() instanceof Cell) {
			if (hoverCell != null) {
				hoverCell.removeStyleName(CssStyles.Hover);
			}
			hoverCell = (Cell) event.getSource();
			hoverCell.addStyleName(CssStyles.Hover);
			currentTool.mouseOver((Cell) event.getSource());
		}
	}

	@Override
	public void onMouseDown(MouseDownEvent event) {
		if (event.getSource() instanceof Cell) {
			currentTool.mouseDown((Cell) event.getSource());
		}
	}

	@Override
	public void onMouseUp(MouseUpEvent event) {
		if (event.getSource() instanceof Cell) {
			currentTool.mouseUp((Cell) event.getSource());
		}
	}

	@Override
	public void onKeyDown(KeyDownEvent event) {
		currentTool.keyDown(event.getNativeKeyCode());
		if (event.getNativeKeyCode() == KeyCodes.KEY_BACKSPACE) {
			event.preventDefault();
			event.stopPropagation();
		}
	}

	@Override
	public void onKeyPress(KeyPressEvent event) {
		currentTool.keyPress(event.getCharCode());
	}
}
