package com.lewish.asciiflow.client.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface AsciiflowClientBundle extends ClientBundle {

	@Source("boxtool.png")
	ImageResource boxToolImage();

	@Source("arrowtool.png")
	ImageResource arrowToolImage();

	@Source("erasetool.png")
	ImageResource eraseToolImage();

	@Source("linetool.png")
	ImageResource lineToolImage();

	@Source("selecttool.png")
	ImageResource selectToolImage();

	@Source("texttool.png")
	ImageResource textToolImage();

	@Source("titledboxtool.png")
	ImageResource titledBoxToolImage();

	@Source("freeformtool.png")
	ImageResource freeformToolImage();
}
