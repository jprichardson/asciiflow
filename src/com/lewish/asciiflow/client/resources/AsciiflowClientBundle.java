package com.lewish.asciiflow.client.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface AsciiflowClientBundle extends ClientBundle {

	@Source("icons/box.png")
	ImageResource boxToolImage();

	@Source("icons/arrow.png")
	ImageResource arrowToolImage();

	@Source("icons/eraser.png")
	ImageResource eraseToolImage();

	@Source("icons/line.png")
	ImageResource lineToolImage();

	@Source("icons/select.png")
	ImageResource selectToolImage();

	@Source("icons/text.png")
	ImageResource textToolImage();

	@Source("icons/titled_box.png")
	ImageResource titledBoxToolImage();

	@Source("icons/freeform.png")
	ImageResource freeformToolImage();

	@Source("loading.gif")
	ImageResource loadingImage();
}
