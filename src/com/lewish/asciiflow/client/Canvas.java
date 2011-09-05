//Copyright Lewis Hemens 2011
package com.lewish.asciiflow.client;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.lewish.asciiflow.shared.CellStateMap;
import com.lewish.asciiflow.shared.CellState;

@Singleton
public class Canvas extends Composite {

	public static final int MAX_WIDTH = 200;
	public static final int MAX_HEIGHT = 100;

	private static final int DEFAULT_WIDTH = 100;
	private static final int DEFAULT_HEIGHT = 30;

	private final FlowPanel panel = new FlowPanel();
	private final FocusPanel focusPanel = new FocusPanel(panel);
	private final CellImpl[][] model = new CellImpl[MAX_WIDTH][MAX_HEIGHT];

	private int width;
	private int height;

	private CellFactory cellFactory;

	private Set<CellImpl> currentDraw = new HashSet<CellImpl>();
	private Set<CellImpl> nextDraw = new HashSet<CellImpl>();

	@Inject
	public Canvas() {
		focusPanel.setStyleName(CssStyles.CanvasFocus);
		panel.setStyleName(CssStyles.Canvas);
		setWidth(DEFAULT_WIDTH);
		setHeight(DEFAULT_HEIGHT);
		initWidget(focusPanel);
	}

	private void setHeight(int height) {
		panel.setHeight(height * CellImpl.HEIGHT + "px");
		this.height = height;
	}

	private void setWidth(int width) {
		panel.setWidth(width * CellImpl.WIDTH + "px");
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
				CellImpl cell = cellFactory.getCell(x, y);
				model[x][y] = cell;
				panel.add(cell);
			}
		}
	}

	public void refreshDraw() {
		refreshDraw(false);
	}

	public void refreshDraw(boolean sticky) {
		currentDraw.addAll(nextDraw);
		nextDraw.clear();
		for (CellImpl cell : currentDraw) {
			if (cell.drawValue == null && cell.drawHighlight == false) {
				// Using as a temporary store, for cells to remove from draw.
				nextDraw.add(cell);
			}
			String value = cell.drawValue != null ? cell.drawValue : cell.commitValue;
			if (cell.value == null || !cell.value.equals(value)) {
				pushValue(cell, value);
			}
			if (cell.highlight != cell.drawHighlight) {
				pushHighlight(cell);
			}
			if (!sticky) {
				cell.drawValue = null;
				cell.drawHighlight = false;
			}
		}
		currentDraw.removeAll(nextDraw);
		nextDraw.clear();
	}

	public void draw(int x, int y, String value) {
		if (x < 0 || y < 0 || x >= width || y >= height)
			return;
		draw(model[x][y], value);
	}

	private void draw(CellImpl cell, String value) {
		if (cell == null)
			return;
		cell.drawValue = value;
		cell.drawHighlight = (value != null);
		nextDraw.add(cell);
	}

	public void highlight(int x, int y, boolean value) {
		if (x < 0 || y < 0 || x >= width || y >= height)
			return;
		highlight(model[x][y], value);
	}

	private void highlight(CellImpl cell, boolean value) {
		if (cell == null)
			return;
		cell.drawHighlight = value;
		nextDraw.add(cell);
	}

	public void clearDraw() {
		for (CellImpl cell : currentDraw) {
			cell.drawValue = null;
			cell.drawHighlight = false;
		}
		refreshDraw();
	}

	public CellStateMap commitDraw() {
		// This is effectively a diff, the state that should be drawn to undo.
		CellStateMap oldState = new CellStateMap();
		for (CellImpl cell : currentDraw) {
			if (cell.value != null) {
				oldState.add(new CellState(cell.x, cell.y, cell.commitValue == null ? " "
						: cell.commitValue));
				if (cell.value.equals(" ")) {
					cell.commitValue = null;
				} else {
					cell.commitValue = cell.value;
				}
				cell.value = cell.commitValue;
			}
			if (cell.highlight == true) {
				cell.drawHighlight = false;
				pushHighlight(cell);
			}
		}
		currentDraw.clear();
		return oldState;
	}

	private void pushHighlight(CellImpl cell) {
		cell.highlight = cell.drawHighlight;
		if (cell.highlight) {
			cell.addStyleName(CssStyles.Drawing);
		} else {
			cell.removeStyleName(CssStyles.Drawing);
		}
	}

	private void pushValue(CellImpl cell, String value) {
		cell.value = value;
		if (value == null || value.equals(" ")) {
			cell.setHTML("&nbsp;");
		} else {
			cell.setText(value);
		}
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

	public void addRow() {
		for (int j = 0; j < width; j++) {
			CellImpl cell = cellFactory.getCell(j, height);
			model[j][height] = cell;
			panel.add(cell);
		}
		setHeight(height + 1);
	}

	public void addColumn() {
		for (int i = 0; i < height; i++) {
			CellImpl cell = cellFactory.getCell(width, i);
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

		public CellImpl getCell(int x, int y) {
			CellImpl cell = new CellImpl(x, y);
			cell.addMouseDownHandler(controller);
			cell.addMouseOverHandler(controller);
			cell.addMouseUpHandler(controller);
			return cell;
		}
	}

	public void drawCellStates(CellStateMap state) {
		for (CellState cellState : state.getCellStates()) {
			draw(cellState.x, cellState.y, cellState.value);
		}
	}

	public CellStateMap getCellStates() {
		// TODO: Optimise this, maintain current state through commit/refresh
		// draw.
		CellStateMap state = new CellStateMap();
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				CellImpl cell = model[i][j];
				if (cell.value == null || cell.value.equals(" "))
					continue;
				state.add(new CellState(i, j, cell.value));
			}
		}
		return state;
	}

	private static class CellImpl extends HTML implements Cell {
		public static final int HEIGHT = 14;
		public static final int WIDTH = 8;

		public final int x;
		public final int y;

		String value;
		public String commitValue;
		String drawValue;

		boolean highlight;
		boolean drawHighlight;

		CellImpl(int x, int y) {
			this.x = x;
			this.y = y;
			DOM.setStyleAttribute(getElement(), "top", HEIGHT * y + "px");
			DOM.setStyleAttribute(getElement(), "left", WIDTH * x + "px");
			setHTML("&nbsp;");
		}

		@Override
		public int getX() {
			return x;
		}

		@Override
		public int getY() {
			return y;
		}
	}

}
