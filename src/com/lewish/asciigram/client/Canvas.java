//Copyright Lewis Hemens 2011
package com.lewish.asciigram.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.inject.Singleton;

@Singleton
public class Canvas extends Composite {

	private static final int DEFAULT_WIDTH = 100;
	private static final int DEFAULT_HEIGHT = 30;

	private final FlowPanel panel = new FlowPanel();
	private final FocusPanel focusPanel = new FocusPanel(panel);

	private List<List<Cell>> rows;
	private int width = DEFAULT_WIDTH;
	private int height = DEFAULT_HEIGHT;

	public Canvas() {
		focusPanel.setStyleName(CssStyles.CanvasFocus);
		panel.setStyleName(CssStyles.Canvas);
		initRows();
		initPanel();
		initWidget(focusPanel);
	}

	public void addListener(Controller controller) {
		focusPanel.addKeyDownHandler(controller);
		focusPanel.addKeyPressHandler(controller);
		for (List<Cell> row : rows) {
			for (Cell cell : row) {
				cell.addListener(controller);
			}
		}
	}

	private void initRows() {
		rows = new ArrayList<List<Cell>>();
		for (int i = 0; i < height; i++) {
			List<Cell> row = new ArrayList<Cell>();
			for (int j = 0; j < width; j++) {
				row.add(new Cell(j, i));
			}
			rows.add(row);
		}
	}

	private void initPanel() {
		panel.clear();
		for (List<Cell> row : rows) {
			FlowPanel rowPanel = new FlowPanel();
			for (Cell cell : row) {
				rowPanel.add(cell);
			}
			panel.add(rowPanel);
		}
	}

	public void clearCells() {
		for (List<Cell> row : rows) {
			for (Cell cell : row) {
				cell.setDrawValue(" ");
				cell.refreshDraw();
				cell.commitDraw();
			}
		}
	}

	public void refreshDraw() {
		for (List<Cell> row : rows) {
			for (Cell cell : row) {
				cell.refreshDraw();
			}
		}
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

	public String getText(boolean html) {
		String text = "";
		for (List<Cell> row : rows) {
			String rowText = "";
			for (Cell cell : row) {
				rowText += html ? cell.getHTML() : cell.getText();
			}
			text += rowText + (html ? "<br>" : "") + "\n";
		}
		return text;
	}

	public void focus() {
		focusPanel.setFocus(true);
	}
}
