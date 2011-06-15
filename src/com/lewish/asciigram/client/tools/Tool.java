//Copyright Lewis Hemens 2011
package com.lewish.asciigram.client.tools;

import com.google.gwt.event.dom.client.KeyPressEvent;
import com.lewish.asciigram.client.Cell;

public abstract class Tool {

	public abstract String getLabel();

	public abstract String getDescription();

	public abstract String getImageUrl();

	public void mouseOver(Cell cell) {
	}

	public void mouseDown(Cell cell) {
	}

	public void mouseUp(Cell cell) {
	}

	public void cleanup() {
	}

	public void keyPress(KeyPressEvent event) {
	}
}
