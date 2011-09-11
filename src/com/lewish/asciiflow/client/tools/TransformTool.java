package com.lewish.asciiflow.client.tools;

import com.google.gwt.resources.client.ImageResource;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.lewish.asciiflow.client.Canvas;
import com.lewish.asciiflow.client.HistoryManager;
import com.lewish.asciiflow.client.Tool;
import com.lewish.asciiflow.client.common.Box;
import com.lewish.asciiflow.client.resources.AsciiflowClientBundle;

@Singleton
public class TransformTool extends Tool {

	@Inject
	public TransformTool(Canvas canvas, HistoryManager historyManager,
			AsciiflowClientBundle clientBundle) {
		super(canvas, historyManager, clientBundle);
	}

	@Override
	public void mouseOver(int x, int y) {
		String value = canvas.getValue(x, y);
		if ("+".equals(value)) {
			
		}
	};

	@Override
	public String getLabel() {
		return "Transform";
	}

	@Override
	public String getDescription() {
		return "Click on box corners and line ends to resize and move them.";
	}

	@Override
	public ImageResource getImageResource() {
		// TODO 
		return clientBundle.freeformToolImage();
	}

}
