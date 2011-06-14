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
		return Math.min(start.getX(), finish.getX());
	}

	public int topLeftY() {
		return Math.min(start.getY(), finish.getY());
	}

	public int bottomRightX() {
		return Math.max(start.getX(), finish.getX());
	}

	public int bottomRightY() {
		return Math.max(start.getY(), finish.getY());
	}
}
