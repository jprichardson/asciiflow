//Copyright Lewis Hemens 2011
package com.lewish.asciigram.client;

public class Drag {
	private Cell start;
	private Cell finish;

	

	public void setStart(Cell start) {
		this.start = start;
	}

	public Cell getStart() {
		return start;
	}

	public void setFinish(Cell finish) {
		this.finish = finish;
	}

	public Cell getFinish() {
		return finish;
	}

	public int topLeftX() {
		return Math.min(start.x, finish.x);
	}

	public int topLeftY() {
		return Math.min(start.y, finish.y);
	}

	public int bottomRightX() {
		return Math.max(start.x, finish.x);
	}

	public int bottomRightY() {
		return Math.max(start.y, finish.y);
	}
}
