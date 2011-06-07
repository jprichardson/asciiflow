package com.lewish.asciigram.client;

public class BoxTool extends DragTool implements Tool {

	public BoxTool(Canvas canvas) {
		super(canvas);
	}

	@Override
	public String getLabel() {
		return "Box";
	}

	@Override
	public void draw(Drag box, Canvas canvas) {
		int x1 = box.getStart().getX();
		int y1 = box.getStart().getY();
		int x2 = box.getFinish().getX();
		int y2 = box.getFinish().getY();
		// change to top left and bottom right
		if (x1 > x2) {
			x1 = x1 ^ x2;
			x2 = x1 ^ x2;
			x1 = x1 ^ x2;
		}
		if (y1 > y2) {
			y1 = y1 ^ y2;
			y2 = y1 ^ y2;
			y1 = y1 ^ y2;
		}
		canvas.getCell(x1, y1).setDrawValue("+");
		canvas.getCell(x1, y2).setDrawValue("+");
		canvas.getCell(x2, y1).setDrawValue("+");
		canvas.getCell(x2, y2).setDrawValue("+");
		for (int x = x1 + 1; x < x2; x++) {
			canvas.getCell(x, y1).setDrawValue("-");
			canvas.getCell(x, y2).setDrawValue("-");
		}
		for (int y = y1 + 1; y < y2; y++) {
			canvas.getCell(x1, y).setDrawValue("|");
			canvas.getCell(x2, y).setDrawValue("|");
		}
		canvas.refreshDraw();
	}

	@Override
	public String getDescription() {
		return "Click and drag to draw a box";
	}

	@Override
	public String getImageUrl() {
		return "images/boxtool.png";
	}
}
