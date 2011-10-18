package com.lewish.asciiflow.client;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.lewish.asciiflow.client.resources.AsciiflowCss;
import com.lewish.asciiflow.shared.UriConstants;

@Singleton
public class FooterWidget extends Composite {

	FlowPanel footer = new FlowPanel();

	@Inject
	public FooterWidget(AsciiflowCss css, Uri uri) {
		footer.setStyleName(css.footer());

		footer.add(new Anchor("Found a bug?", UriConstants.MAILTO_URI));
		footer.add(new HTML("&nbsp;-&nbsp;"));
		footer.add(new Anchor("Blog", UriConstants.BLOG_URI));
		footer.add(new HTML("&nbsp;-&nbsp;"));
		if (uri.isStable()) {
			footer.add(new Anchor("Nightly build", UriConstants.NIGHTLY_HOST));
		} else {
			footer.add(new Anchor("Stable build", UriConstants.STABLE_HOST));
		}

		initWidget(footer);
	}
}
