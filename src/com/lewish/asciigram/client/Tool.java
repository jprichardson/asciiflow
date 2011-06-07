package com.lewish.asciigram.client;

public interface Tool {
	public String getLabel();
	public String getDescription();
	public String getImageUrl();
	public void mouseOver(Cell cell);
	public void mouseDown(Cell cell);
	public void mouseUp(Cell cell);
	public void cleanup();
	public void keyDown(int keyCode);
	public void keyPress(char character);
}
