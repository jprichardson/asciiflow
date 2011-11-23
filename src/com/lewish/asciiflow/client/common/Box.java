package com.lewish.asciiflow.client.common;

public class Box {
	public int startX;
	public int startY;
	public  int finishX;
	public  int finishY;

	public Box(int x, int y) {
		setStart(x, y);
		setFinish(x, y);
	}

	public void setStart(int x, int y) {
		this.startX = x;
		this.startY = y;
	}

	public void setFinish(int x, int y) {
		this.finishX = x;
		this.finishY = y;
	}

	public int topLeftX() {
		return Math.min(startX, finishX);
	}

	public int topLeftY() {
		return Math.min(startY, finishY);
	}

	public int bottomRightX() {
		return Math.max(startX, finishX);
	}

	public int bottomRightY() {
		return Math.max(startY, finishY);
	}
}
