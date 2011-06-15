//Copyright Lewis Hemens 2011
package com.lewish.asciigram.client;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class Canvas extends Composite {

	private static final int DEFAULT_WIDTH = 100;
	private static final int DEFAULT_HEIGHT = 30;

	private final FlowPanel panel = new FlowPanel();
	private final FocusPanel focusPanel = new FocusPanel(panel);
	private final Cell[][] model;

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
		model = new Cell[width*2][height*2]; //No array resizing please!
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

	public void setListener(Controller controller) {
		focusPanel.addKeyPressHandler(controller);
		focusPanel.addKeyDownHandler(controller);
		cellFactory = new CellFactory(controller);
		initRows();
	}

	private void initRows() {
		for (int i = 0; i < height; i++) {
			//FlowPanel rowPanel = new FlowPanel();
			for (int j = 0; j < width; j++) {
				Cell cell = cellFactory.getCell(j, i);
				model[j][i]  = cell;
				//rowPanel.add(cell);
				panel.add(cell);
			}
			//panel.add(rowPanel);
		}
	}

	public void clearCells() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				draw(x, y, " ");
			}
		}
		refreshDraw();
		commitDraw();
	}

	public void refreshDraw() {
		currentDraw.addAll(nextDraw);
		nextDraw.clear();
		for (Cell cell : currentDraw) {
			/*
			 * CELL LOGIC: REFRESH DRAW
			 */
			if(cell.drawValue == null) {
				//Using as a temporary store
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
		draw(model[x][y], value);
	}

	public void draw(Cell cell, String value) {
		cell.drawValue = value;
		cell.drawHighlight = true;
		nextDraw.add(cell);
	}

	public void highlight(int x, int y, boolean value) {
		highlight(model[x][y], value);
	}

	public void highlight(Cell cell, boolean value) {
		cell.drawHighlight = value;
		nextDraw.add(cell);
	}

	public void clearDraw() {
		for(Cell cell : currentDraw) {
			cell.drawValue = null;
			cell.drawHighlight = false;
		}
		currentDraw.clear();
	}

	public void commitDraw(boolean save) {
		for(Cell cell : currentDraw) {
			/*
			 * CELL LOGIC: COMMIT DRAW
			 */
			if (cell.value != null) {
				cell.commitValue = cell.value.equals(" ") ? null : cell.value;
				if (cell.highlight == true) {
					cell.pushHighlight();
				}
			}
		}
		if (save) {
			HistoryManager.get().save();
		}
		currentDraw.clear();
	}

	public void commitDraw() {
		commitDraw(true);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Cell getCell(int x, int y) {
		if(x >= width) return null;
		if(y >= height) return null;
		return model[x][y];
	}

	public void addRow(Controller controller) {
		for (int j = 0; j < width; j++) {
			Cell cell = cellFactory.getCell(j, height);
			model[j][height] = cell;
			panel.add(cell);
		}
		height++;
	}

	public void addColumn(Controller controller) {
		for (int i = 0; i < height; i++) {
			Cell cell = cellFactory.getCell(width, i);
			model[width][i] = cell;
			panel.add(cell);
		}
		width++;
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

	
	
	
	
	
	public String[][] getState() {
		Drag drag = new Drag();
		drag.setStart(getCell(0, 0));
		drag.setFinish(getCell(width - 1, height - 1));
		return getState(drag);
	}

	public String[][] getState(Drag drag) {
		String[][] state = new String[drag.bottomRightY() - drag.topLeftY() + 1][drag
				.bottomRightX()
				- drag.topLeftX() + 1];
		for (int y = 0; y < state.length
				&& y <= drag.bottomRightY()-drag.topLeftY(); y++) {
			for (int x = 0; x < state[y].length
					&& x <= drag.bottomRightX() - drag.topLeftX(); x++) {
				state[y][x] = getCell(drag.topLeftX() + x, drag.topLeftY() + y).getValue();
			}
		}
		return state;
	}

	public void loadState(String[][] state) {
		Drag drag = new Drag();
		drag.setStart(getCell(0, 0));
		drag.setFinish(getCell(width - 1, height - 1));
		loadState(drag, state);
	}

	public void loadState(Drag drag, String[][] state) {
		for (int y = 0; y < state.length; y++) {
			for (int x = 0; x < state[y].length; x++) {
				draw(drag.topLeftX() + x, drag.topLeftY() + y, state[y][x]);
			}
		}
		refreshDraw();
		commitDraw(false);
	}
}
