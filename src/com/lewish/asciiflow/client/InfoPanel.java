package com.lewish.asciiflow.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.lewish.asciiflow.client.resources.AsciiflowCss;

@Singleton
public class InfoPanel extends Composite {

	private Label label = new Label();

	@Inject
	public InfoPanel(AsciiflowCss css) {
		label.addStyleName(css.infoPanel());
		initWidget(label);
	}

	public void setText(String text) {
		label.setText(text);
	}
}
