//Copyright Lewis Hemens 2011
package com.lewish.asciiflow.client;

import com.google.gwt.event.dom.client.DomEvent;
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
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class Controller implements MouseDownHandler, MouseOverHandler, MouseUpHandler,
		KeyPressHandler, KeyDownHandler {

	private final HistoryManager historyManager;
	private final ExportWidget exportPanel;
	private final ImportWidget importPanel;
	private final SaveWidget savePanel;

	private final Canvas canvas;

	private Tool currentTool;
	private Cell hoverCell;

	@Inject
	public Controller(Canvas canvas,
			ExportWidget exportPanel,
			ImportWidget importPanel,
			SaveWidget savePanel,
			HistoryManager historyManager) {
		this.exportPanel = exportPanel;
		this.importPanel = importPanel;
		this.savePanel = savePanel;
		this.historyManager = historyManager;
		this.canvas = canvas;
		canvas.init(this);
	}

	public void setTool(Tool tool) {
		if (currentTool != null) {
			currentTool.cleanup();
		}
		currentTool = tool;
	}

	@Override
	public void onMouseOver(MouseOverEvent event) {
		if (event.getSource() instanceof Cell) {
			currentTool.mouseOver((Cell) event.getSource());
			if (hoverCell != null) {
				hoverCell.removeStyleName(CssStyles.Hover);
			}
			hoverCell = (Cell) event.getSource();
			hoverCell.addStyleName(CssStyles.Hover);
			canvas.focus();
		}
	}

	@Override
	public void onMouseDown(MouseDownEvent event) {
		exportPanel.hide();
		importPanel.hide();
		savePanel.hide();
		if (event.getSource() instanceof Cell) {
			currentTool.mouseDown((Cell) event.getSource());
			canvas.focus();
			killEvent(event);
		}
	}

	@Override
	public void onMouseUp(MouseUpEvent event) {
		if (event.getSource() instanceof Cell) {
			currentTool.mouseUp((Cell) event.getSource());
		}
	}

	@Override
	public void onKeyPress(KeyPressEvent event) {
		if (!event.isControlKeyDown()) {
			currentTool.keyPress(event);
		}
		killEvent(event);
	}

	@Override
	public void onKeyDown(KeyDownEvent event) {
		if (currentTool == null)
			return;
		int specialKeyCode = event.getNativeKeyCode();
		if (event.isControlKeyDown() || event.isMetaKeyDown()) {
			if (event.getNativeKeyCode() == 67) {
				specialKeyCode = AsciiKeyCodes.COPY;
			}
			if (event.getNativeKeyCode() == 86) {
				specialKeyCode = AsciiKeyCodes.PASTE;
			}
			if (event.getNativeKeyCode() == 90) {
				historyManager.undo();
				specialKeyCode = AsciiKeyCodes.UNDO;
			}
			if (event.getNativeKeyCode() == 89) {
				historyManager.redo();
				specialKeyCode = AsciiKeyCodes.REDO;
			}
			if (event.getNativeKeyCode() == 88) {
				specialKeyCode = AsciiKeyCodes.CUT;
			}
			killEvent(event);
		}
		currentTool.specialKeyPress(specialKeyCode);
		if (specialKeyCode == KeyCodes.KEY_BACKSPACE) {
			killEvent(event);
		}
	}

	private void killEvent(DomEvent<?> event) {
		event.preventDefault();
		event.stopPropagation();
	}
}
