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

	private final Controller controller;
	private final FlowPanel panel = new FlowPanel();
	private final FocusPanel focusPanel = new FocusPanel(panel);

	private List<List<Cell>> rows;
	private int width = DEFAULT_WIDTH;
	private int height = DEFAULT_HEIGHT;

	@Inject
	public Canvas(Controller controller) {
		this.controller = controller;
		focusPanel.addKeyDownHandler(controller);
		focusPanel.addKeyPressHandler(controller);
		focusPanel.setStyleName(CssStyles.CanvasFocus);
		panel.setStyleName(CssStyles.Canvas);
		initRows();
		initPanel();
		initWidget(focusPanel);
	}

	private void initRows() {
		rows = new ArrayList<List<Cell>>();
		for (int i = 0; i < height; i++) {
			List<Cell> row = new ArrayList<Cell>();
			for (int j = 0; j < width; j++) {
				row.add(new Cell(j, i, controller));
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
				cell.setDrawValue(null);
				cell.commitDraw();
			}
		}
	}

	public void commitDraw() {
		for (List<Cell> row : rows) {
			for (Cell cell : row) {
				cell.commitDraw();
			}
		}
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getWidth() {
		return width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getHeight() {
		return height;
	}

	public Cell getCell(int x, int y) {
		x = x < width ? x : width - 1;
		y = y < height ? y : height - 1;
		return rows.get(y).get(x);
	}

	public void addRow() {
		List<Cell> row = new ArrayList<Cell>();
		FlowPanel rowPanel = new FlowPanel();
		for (int j = 0; j < width; j++) {
			Cell cell = new Cell(j, height, controller);
			row.add(cell);
			rowPanel.add(cell);
		}
		rows.add(row);
		panel.add(rowPanel);
		height++;

	}

	public void addColumn() {
		for (int i = 0; i < height; i++) {
			Cell cell = new Cell(width, i, controller);
			rows.get(i).add(cell);
			((FlowPanel) panel.getWidget(i)).add(cell);
		}
		width++;
	}

	public String getText() {
		String text = "";
		for (List<Cell> row : rows) {
			for (Cell cell : row) {
				text += cell.getText();
			}
			text += "\n";
		}
		return text;
	}
}
