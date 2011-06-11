package com.lewish.asciigram.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Singleton;

@Singleton
public class InfoPanel extends Composite {
	
	private static InfoPanel instance;

	private Label label = new Label();

	public InfoPanel() {
		if(instance != null) {
			return;
		}
		instance = this;
		label.addStyleName(CssStyles.InfoPanel);
		initWidget(label);
	}

	public static void setText(String text) {
		instance.label.setText(text);
	}
}
