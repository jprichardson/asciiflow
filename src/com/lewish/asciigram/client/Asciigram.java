//Copyright Lewis Hemens 2011
package com.lewish.asciigram.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Asciigram implements EntryPoint {

	AsciigramGinjector injector = GWT.create(AsciigramGinjector.class);

	FlowPanel frame = new FlowPanel();
	FlowPanel header = new FlowPanel();
	HorizontalPanel body = new HorizontalPanel();
	VerticalPanel rightBody = new VerticalPanel();
	FlowPanel footer = new FlowPanel();

	public void onModuleLoad() {
		InfoPanel infoPanel = injector.getInfoPanel();
		Canvas canvas = injector.getCanvas();
		ToolPanel toolPanel = injector.getToolPanel();
		MenuPanel menuPanel = injector.getMenuPanel();
		ExportPanel exportPanel = injector.getExportPanel();

		//TODO: UiBinder main page!
		frame.setStyleName(CssStyles.Frame);
		header.setStyleName(CssStyles.Header);
		body.setStyleName(CssStyles.Body);
		footer.setStyleName(CssStyles.Footer);

		frame.add(header);
			header.add(new Logo());
			header.add(menuPanel);
			header.add(exportPanel);
		frame.add(body);
			body.add(toolPanel);
			body.add(rightBody);
				rightBody.add(canvas);
				rightBody.add(infoPanel);
		frame.add(footer);
			footer.add(new Anchor("Found a bug?", "mailto:lewis@asciiflow.com"));
			footer.add(new HTML("&nbsp;-&nbsp;"));
			footer.add(new Anchor("Blog", "http://blog.asciiflow.com"));
		RootPanel.get().add(frame);

		if (Window.Navigator.getUserAgent().toLowerCase().contains("msie")) {
			Window.alert("This site has serious performance issues with IE. Please use Chrome, Firefox or Safari for the best possible experience.");
		}
	}
}
