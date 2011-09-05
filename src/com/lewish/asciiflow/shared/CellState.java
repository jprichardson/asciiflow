package com.lewish.asciiflow.shared;

public class CellState {
	public int x;
	public int y;
	public String value;

	public CellState(int x, int y, String value) {
		this.x = x;
		this.y = y;
		this.value = value;
	}

	public static CellState fromString(String string) {
		String[] split = string.split(":", 3);
		return new CellState(Integer.parseInt(split[0]), Integer.parseInt(split[1]), split[2]);
	}
}
