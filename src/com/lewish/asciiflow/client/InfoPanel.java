//Copyright Lewis Hemens 2011
package com.lewish.asciiflow.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Singleton;

@Singleton
public class InfoPanel extends Composite {

	private Label label = new Label();

	public InfoPanel() {
		label.addStyleName(CssStyles.InfoPanel);
		initWidget(label);
	}

	public void setText(String text) {
		label.setText(text);
	}
}
