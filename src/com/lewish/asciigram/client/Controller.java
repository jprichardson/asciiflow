package com.lewish.asciigram.client;

//Copyright Lewis Hemens 2011
import com.google.gwt.event.dom.client.DomEvent;
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
import com.lewish.asciigram.client.tools.Tool;

@Singleton
public class Controller implements MouseDownHandler, MouseOverHandler,
		MouseUpHandler, KeyPressHandler {

	private final HistoryManager historyManager;
	private final ExportPanel exportPanel;
	private final Canvas canvas;

	private Tool currentTool;

	private Cell hoverCell;

	@Inject
	public Controller(Canvas canvas, ExportPanel exportPanel,
			HistoryManager historyManager) {
		this.exportPanel = exportPanel;
		this.historyManager = historyManager;
		this.canvas = canvas;
		canvas.addListener(this);
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
		}
	}

	@Override
	public void onMouseDown(MouseDownEvent event) {
		exportPanel.hide();
		if (event.getSource() instanceof Cell) {
			currentTool.mouseDown((Cell) event.getSource());
			canvas.focus();
			// Focus
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
		if (event.isControlKeyDown()
				&& (event.getCharCode() == 'z' || event.getCharCode() == 'Z')) {
			historyManager.undo();
		} else if (event.isControlKeyDown()
				&& (event.getCharCode() == 'y' || event.getCharCode() == 'Y')) {
			historyManager.redo();
		} else {
			currentTool.keyPress(event);
		}
		
		killEvent(event);
	}

	private void killEvent(DomEvent<?> event) {
		event.preventDefault();
		event.stopPropagation();
	}

	/*
	public void onMouseOver(Cell cell) {
		currentTool.mouseOver(cell);
		if (hoverCell != null) {
			hoverCell.removeStyleName(CssStyles.Hover);
		}
		hoverCell = cell;
		hoverCell.addStyleName(CssStyles.Hover);
	}
	 */
	/*
	public void onMouseDown(Cell event) {
		exportPanel.hide();
		currentTool.mouseDown(event);
		canvas.focus();
		// Focus
		// killEvent(event);
	}
	*/
	/*
	public void onMouseUp(Cell cell) {
		currentTool.mouseUp(cell);
	}
	*/
}
