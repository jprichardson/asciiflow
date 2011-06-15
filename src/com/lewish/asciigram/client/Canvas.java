//Copyright Lewis Hemens 2011
package com.lewish.asciigram.client;

import java.util.ArrayList;
import java.util.List;

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

	private int width = DEFAULT_WIDTH;
	private int height = DEFAULT_HEIGHT;

	private CellFactory cellFactory;
	private Cell[][] model = new Cell[width][height];

	@Inject
	public Canvas() {
		focusPanel.setStyleName(CssStyles.CanvasFocus);
		panel.setStyleName(CssStyles.Canvas);
		initWidget(focusPanel);
	}

	public void setListener(Controller controller) {
		focusPanel.addKeyPressHandler(controller);
		focusPanel.addKeyDownHandler(controller);
		cellFactory = new CellFactory(controller);
		initRows();
	}

	private void initRows() {
		for (int i = 0; i < height; i++) {
			FlowPanel rowPanel = new FlowPanel();
			for (int j = 0; j < width; j++) {
				Cell cell = cellFactory.getCell(j, i);
				model[j][i]  = cell;
				rowPanel.add(cell);
			}
			panel.add(rowPanel);
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
		for (List<Cell> row : rows) {
			for (Cell cell : row) {
				cell.refreshDraw();
			}
		}
	}

	public void draw(int x, int y, String value) {
		getCell(x ,y).setDrawValue(value);
	}

	public void clearDraw() {
		for (List<Cell> row : rows) {
			for (Cell cell : row) {
				cell.setDrawValue(null, false);
				cell.refreshDraw();
			}
		}
	}

	public void commitDraw(boolean save) {
		for (List<Cell> row : rows) {
			for (Cell cell : row) {
				cell.commitDraw();
			}
		}
		if (save) {
			HistoryManager.get().save();
		}
	}

	public void commitDraw() {
		commitDraw(true);
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
				getCell(drag.topLeftX() + x, drag.topLeftY() + y).setDrawValue(state[y][x], false);
			}
		}
		refreshDraw();
		commitDraw(false);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Cell getCell(int x, int y) {
		x = x < width ? x : width - 1;
		y = y < height ? y : height - 1;
		return rows.get(y).get(x);
	}

	public void addRow(Controller controller) {
		List<Cell> row = new ArrayList<Cell>();
		FlowPanel rowPanel = new FlowPanel();
		for (int j = 0; j < width; j++) {
			Cell cell = new Cell(j, height);
			cell.addListener(controller);
			row.add(cell);
			rowPanel.add(cell);
		}
		rows.add(row);
		panel.add(rowPanel);
		height++;

	}

	public void addColumn(Controller controller) {
		for (int i = 0; i < height; i++) {
			Cell cell = new Cell(width, i);
			cell.addListener(controller);
			rows.get(i).add(cell);
			((FlowPanel) panel.getWidget(i)).add(cell);
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
			cell.addListener(controller);
			return cell;
		}
	}
}
