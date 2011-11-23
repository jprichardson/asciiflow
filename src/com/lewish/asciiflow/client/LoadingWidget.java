package com.lewish.asciiflow.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.lewish.asciiflow.client.resources.AsciiflowClientBundle;

/**
 * Absolutely positioned loading overlay with an animated gif.
 * 
 * @author lewis
 */
@Singleton
public class LoadingWidget extends Composite {

	private final FlowPanel panel = new FlowPanel();
	private final Image loadingImage;

	@Inject
	public LoadingWidget(AsciiflowClientBundle clientBundle) {
		loadingImage = new Image(clientBundle.loadingImage());
		panel.add(loadingImage);
		loadingImage.setStyleName(clientBundle.css().loadingImage());
		panel.setStyleName(clientBundle.css().loadingBackground());

		initWidget(panel);
		hide();
	}

	public void show() {
		panel.setVisible(true);
	}

	public void hide() {
		panel.setVisible(false);
	}
}
