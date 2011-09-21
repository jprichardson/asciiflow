package com.lewish.asciiflow.client;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.lewish.asciiflow.client.resources.AsciiflowCss;
import com.lewish.asciiflow.shared.Uri;

@Singleton
public class FooterWidget extends Composite {

	FlowPanel footer = new FlowPanel();

	@Inject
	public FooterWidget(AsciiflowCss css) {
		footer.setStyleName(css.footer());

		footer.add(new Anchor("Found a bug?", Uri.getMailtoUri()));
		footer.add(new HTML("&nbsp;-&nbsp;"));
		footer.add(new Anchor("Blog", Uri.getBlogUri()));
		footer.add(new HTML("&nbsp;-&nbsp;"));
		if (Uri.isStable()) {
			footer.add(new Anchor("Nightly build", Uri.getNightlyUri()));
		} else {
			footer.add(new Anchor("Stable build", Uri.getStableUri()));
		}

		initWidget(footer);
	}
}
