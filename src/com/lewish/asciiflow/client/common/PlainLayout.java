package com.lewish.asciiflow.client.common;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.lewish.asciiflow.client.FooterWidget;
import com.lewish.asciiflow.client.resources.AsciiflowCss;

public abstract class PlainLayout extends Composite implements Layout {

	@Override
	public Panel buildLayout(AsciiflowCss css, FooterWidget footerWidget) {
		VerticalPanel panel = new VerticalPanel();
		panel.add(getContent());
		panel.add(footerWidget);

		panel.setStyleName(css.body());

		return panel;
	}

	public abstract Widget getContent();
}
