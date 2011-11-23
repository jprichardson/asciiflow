package com.lewish.asciiflow.client;

import com.google.gwt.user.client.ui.SimplePanel;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.lewish.asciiflow.client.common.Layout;
import com.lewish.asciiflow.client.resources.AsciiflowCss;

/**
 * A simple panel that holds the content of what ever page is currently being viewed.
 * 
 * @author lewis
 */
@Singleton
public class ContentFrame extends SimplePanel {

	private final AsciiflowCss css;
	private final FooterWidget footerWidget;

	@Inject
	public ContentFrame(AsciiflowCss css, FooterWidget footerWidget) {
		this.css = css;
		this.footerWidget = footerWidget;
	}
	public void setLayout(Layout layout) {
		clear();
		add(layout.buildLayout(css, footerWidget));
	}
}
