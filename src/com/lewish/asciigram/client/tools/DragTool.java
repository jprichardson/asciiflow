//Copyright Lewis Hemens 2011
package com.lewish.asciigram.client.tools;

import javax.inject.Inject;

import com.google.gwt.event.dom.client.KeyDownEvent;
import com.lewish.asciigram.client.Canvas;
import com.lewish.asciigram.client.Cell;
import com.lewish.asciigram.client.Drag;


public abstract class DragTool implements Tool {

	public static enum State {
		Dragging,
		Nothing;
	}

	private final Canvas canvas;
	private State state = State.Nothing;
	private Drag currentBox;

	@Inject
	public DragTool(Canvas canvas) {
		this.canvas = canvas;
	}

	private void stopDragging(Cell cell) {
		state = State.Nothing;
		currentBox.setFinish(cell);
		currentBox = null;
		canvas.commitDraw();
	}

	private void startDragging(Cell cell) {
		state = State.Dragging;
		currentBox = new Drag();
		currentBox.setStart(cell);
		currentBox.setFinish(cell);
	}
	
	@Override
	public void mouseOver(Cell cell) {
		if(state == State.Dragging) {
			currentBox.setFinish(cell);
			draw(currentBox, canvas);
		}
	}

	@Override
	public void mouseDown(Cell cell) {
		if(state == State.Nothing) {
			startDragging(cell);
			draw(currentBox, canvas);
		}
	}

	@Override
	public void mouseUp(Cell cell) {
		if(state == State.Dragging) {
			stopDragging(cell);
		}
	}

	@Override
	public void cleanup() {
		state = State.Nothing;
		if(currentBox != null) {
			canvas.clearDraw();
			currentBox = null;
		}
	}

	@Override
	public void keyDown(KeyDownEvent event) {
	}

	@Override
	public void keyPress(char character) {
	}

	@Override
	public String getLabel() {
		return "+box";
	}

	public void draw() {
		draw(currentBox, canvas);
	}

	public abstract void draw(Drag box, Canvas canvas);
}
