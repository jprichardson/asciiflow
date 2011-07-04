//Copyright Lewis Hemens 2011
package com.lewish.asciiflow.client;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.lewish.asciiflow.shared.State;
import com.lewish.asciiflow.shared.State.CellState;

@Singleton
public class Canvas extends Composite {

	public static final int MAX_WIDTH = 200;
	public static final int MAX_HEIGHT = 100;

	private static final int DEFAULT_WIDTH = 100;
	private static final int DEFAULT_HEIGHT = 30;

	private final FlowPanel panel = new FlowPanel();
	private final FocusPanel focusPanel = new FocusPanel(panel);
	private final Cell[][] model = new Cell[MAX_WIDTH][MAX_HEIGHT];

	private int width;
	private int height;

	private CellFactory cellFactory;

	private Set<Cell> currentDraw = new HashSet<Cell>();
	private Set<Cell> nextDraw = new HashSet<Cell>();

	@Inject
	public Canvas() {
		focusPanel.setStyleName(CssStyles.CanvasFocus);
		panel.setStyleName(CssStyles.Canvas);
		setWidth(DEFAULT_WIDTH);
		setHeight(DEFAULT_HEIGHT);
		initWidget(focusPanel);
	}

	private void setHeight(int height) {
		panel.setHeight(height * Cell.HEIGHT + "px");
		this.height = height;
	}

	private void setWidth(int width) {
		panel.setWidth(width * Cell.WIDTH + "px");
		this.width = width;
	}

	public void init(Controller controller) {
		if (cellFactory != null) {
			throw new IllegalStateException();
		}
		focusPanel.addKeyPressHandler(controller);
		focusPanel.addKeyDownHandler(controller);
		cellFactory = new CellFactory(controller);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				Cell cell = cellFactory.getCell(x, y);
				model[x][y]  = cell;
				panel.add(cell);
			}
		}
	}

	public void refreshDraw() {
		currentDraw.addAll(nextDraw);
		nextDraw.clear();
		for (Cell cell : currentDraw) {
			if(cell.drawValue == null && cell.drawHighlight == false) {
				//Using as a temporary store, for cells to remove from draw.
				nextDraw.add(cell);
			}
			String value = cell.drawValue != null ? cell.drawValue : cell.commitValue;
			if(cell.value != value) {
				cell.pushValue(value);
			}
			if(cell.highlight != cell.drawHighlight) {
				cell.pushHighlight();
			}
			cell.drawValue = null;
			cell.drawHighlight = false;
		}
		currentDraw.removeAll(nextDraw);
		nextDraw.clear();
	}

	public void draw(int x, int y, String value) {
		if(x < 0 || y < 0 || x > width || y > height) return;
		draw(model[x][y], value);
	}

	public void draw(Cell cell, String value) {
		if (cell == null) return;
		cell.drawValue = value;
		cell.drawHighlight = (value != null);
		nextDraw.add(cell);
	}

	public void highlight(int x, int y, boolean value) {
		highlight(model[x][y], value);
	}

	public void highlight(Cell cell, boolean value) {
		if (cell == null) return;
		cell.drawHighlight = value;
		nextDraw.add(cell);
	}

	public void clearDraw() {
		for(Cell cell : currentDraw) {
			cell.drawValue = null;
			cell.drawHighlight = false;
		}
		refreshDraw();
	}

	public State commitDraw() {
		State oldState = new State();
		for(Cell cell : currentDraw) {
			if (cell.value != null) {
				oldState.add(new CellState(cell.x, cell.y, cell.commitValue == null ? " " : cell.commitValue));
				cell.commitValue = cell.value.equals(" ") ? null : cell.value;
				cell.value = cell.commitValue;
				if (cell.highlight == true) {
					cell.pushHighlight();
				}
			}
		}
		currentDraw.clear();
		return oldState;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public String getValue(int x, int y) {
		return model[x][y].value;
	}

	@Deprecated
	public Cell getCell(int x, int y) {
		return model[x][y];
	}

	public void addRow() {
		for (int j = 0; j < width; j++) {
			Cell cell = cellFactory.getCell(j, height);
			model[j][height] = cell;
			panel.add(cell);
		}
		setHeight(height + 1);
	}

	public void addColumn() {
		for (int i = 0; i < height; i++) {
			Cell cell = cellFactory.getCell(width, i);
			model[width][i] = cell;
			panel.add(cell);
		}
		setWidth(width + 1);
	}

	public void focus() {
		focusPanel.setFocus(true);
	}

	private static class CellFactory {
		private final Controller controller;
		public CellFactory(Controller controller) {
			this.controller = controller;
		}
		public Cell getCell(int x, int y) {
			Cell cell =  new Cell(x,y);
			cell.addMouseDownHandler(controller);
			cell.addMouseOverHandler(controller);
			cell.addMouseUpHandler(controller);
			return cell;
		}
	}

	public void drawState(State state) {
		for(CellState cellState : state.getStates()) {
				draw(cellState.x, cellState.y, cellState.value);
		}
	}
}
