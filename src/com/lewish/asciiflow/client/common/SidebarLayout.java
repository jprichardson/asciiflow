package com.lewish.asciiflow.client.common;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.lewish.asciiflow.client.FooterWidget;
import com.lewish.asciiflow.client.resources.AsciiflowCss;

public abstract class SidebarLayout extends Composite implements Layout {

	@Override
	public Panel buildLayout(AsciiflowCss css, FooterWidget footerWidget) {
		VerticalPanel panel = new VerticalPanel();
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		VerticalPanel rightPanel = new VerticalPanel();
		panel.add(getHeaderContent());
		panel.add(horizontalPanel);
			horizontalPanel.add(getSideContent());
			horizontalPanel.add(rightPanel);
				rightPanel.add(getContent());
				rightPanel.add(footerWidget);

		panel.setStyleName(css.body());

		return panel;
	}

	public abstract Widget getContent();
	public abstract Widget getSideContent();
	public abstract Widget getHeaderContent();
}
