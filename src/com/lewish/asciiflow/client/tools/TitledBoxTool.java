//Copyright Lewis Hemens 2011
package com.lewish.asciiflow.client.tools;

import com.google.gwt.resources.client.ImageResource;
import com.google.inject.Inject;
import com.lewish.asciiflow.client.Canvas;
import com.lewish.asciiflow.client.HistoryManager;
import com.lewish.asciiflow.client.common.Box;
import com.lewish.asciiflow.client.resources.AsciiflowClientBundle;

public class TitledBoxTool extends BaseBoxTool {

	@Inject
	public TitledBoxTool(Canvas canvas, HistoryManager historyManager,
			AsciiflowClientBundle clientBundle) {
		super(canvas, historyManager, clientBundle);
	}

	@Override
	public String getLabel() {
		return "Box";
	}

	@Override
	protected void draw(Box box) {
		draw(box, canvas, true);
	}

	@Override
	public String getDescription() {
		return "Click and drag to draw a box with header";
	}

	@Override
	public ImageResource getImageResource() {
		return clientBundle.titledBoxToolImage();
	}
}
