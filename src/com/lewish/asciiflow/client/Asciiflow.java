//Copyright Lewis Hemens 2011
package com.lewish.asciiflow.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.lewish.asciiflow.client.social.FacebookWidget;
import com.lewish.asciiflow.client.social.PlusOneWidget;
import com.lewish.asciiflow.client.social.TwitterWidget;
import com.lewish.asciiflow.shared.Uri;

public class Asciiflow implements EntryPoint {

	AsciiflowGinjector injector = GWT.create(AsciiflowGinjector.class);

	FlowPanel frame = new FlowPanel();
	FlowPanel header = new FlowPanel();
	HorizontalPanel body = new HorizontalPanel();
	VerticalPanel rightBody = new VerticalPanel();
	FlowPanel footer = new FlowPanel();

	public void onModuleLoad() {
		InfoPanel infoPanel = injector.getInfoPanel();
		final Canvas canvas = injector.getCanvas();
		ToolPanel toolPanel = injector.getToolPanel();
		MenuPanel menuPanel = injector.getMenuPanel();
		MenuWidget exportPanel = injector.getExportPanel();
		MenuWidget importPanel = injector.getImportPanel();
		MenuWidget savePanel = injector.getSavePanel();
		StoreWidget storeWidget = injector.getStoreWidget();
		StoreHelper storeHelper = injector.getStorageHelper();

		//TODO: UiBinder main page!
		frame.setStyleName(CssStyles.Frame);
		header.setStyleName(CssStyles.Header);
		body.setStyleName(CssStyles.Body);
		footer.setStyleName(CssStyles.Footer);

		frame.add(header);
			header.add(new Logo());
			header.add(new FacebookWidget());
			header.add(new TwitterWidget());
			header.add(new PlusOneWidget());
			header.add(storeWidget);
			header.add(menuPanel);
			header.add(exportPanel);
			header.add(importPanel);
			header.add(savePanel);
		frame.add(body);
			body.add(toolPanel);
			body.add(rightBody);
				rightBody.add(canvas);
				rightBody.add(infoPanel);
				rightBody.add(footer);
					footer.add(new Anchor("Found a bug?", Uri.getMailtoUri()));
					footer.add(new HTML("&nbsp;-&nbsp;"));
					footer.add(new Anchor("Blog", Uri.getBlogUri()));
					footer.add(new HTML("&nbsp;-&nbsp;"));
					if (Uri.isStable()) {
						footer.add(new Anchor("Nightly build", Uri.getNightlyUri()));
					} else {
						footer.add(new Anchor("Stable build", Uri.getStableUri()));
					}
		RootPanel.get("main").add(frame);
		storeHelper.parseFragmentLoadAndDraw();
	}
}
