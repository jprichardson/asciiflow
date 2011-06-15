//Copyright Lewis Hemens 2011
package com.lewish.asciigram.client.tools;

import javax.inject.Inject;

import com.lewish.asciigram.client.Canvas;
import com.lewish.asciigram.client.Cell;
import com.lewish.asciigram.client.Drag;


public abstract class DragTool extends Tool {

	private final Canvas canvas;
	private Drag currentBox;

	@Inject
	public DragTool(Canvas canvas) {
		this.canvas = canvas;
	}

	private void stopDragging(Cell cell) {
		currentBox.setFinish(cell);
		currentBox = null;
		canvas.commitDraw();
	}

	private void startDragging(Cell cell) {
		currentBox = new Drag();
		currentBox.setStart(cell);
		currentBox.setFinish(cell);
	}
	
	@Override
	public void mouseOver(Cell cell) {
		if(currentBox != null) {
			currentBox.setFinish(cell);
			draw(currentBox, canvas);
		}
	}

	@Override
	public void mouseDown(Cell cell) {
		if(currentBox == null) {
			startDragging(cell);
			draw(currentBox, canvas);
		}
	}

	@Override
	public void mouseUp(Cell cell) {
		if(currentBox != null) {
			stopDragging(cell);
		}
	}

	@Override
	public void cleanup() {
		if(currentBox != null) {
			canvas.clearDraw();
			currentBox = null;
		}
	}

	public void draw() {
		draw(currentBox, canvas);
	}

	public abstract void draw(Drag box, Canvas canvas);
}
