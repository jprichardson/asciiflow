package com.lewish.asciigram.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;

public class Asciigram implements EntryPoint {

	AsciigramGinjector injector = GWT.create(AsciigramGinjector.class);

	FlowPanel frame = new FlowPanel();
	FlowPanel header = new FlowPanel();
	HorizontalPanel body = new HorizontalPanel();
	FlowPanel footer = new FlowPanel();

	public void onModuleLoad() {
		InfoPanel infoPanel = injector.getInfoPanel();
		Canvas canvas = injector.getCanvas();
		ToolPanel toolPanel = injector.getToolPanel();
		MenuPanel menuPanel = injector.getMenuPanel();
		ExportPanel exportPanel = injector.getExportPanel();

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
			body.add(canvas);
		frame.add(footer);
			footer.add(infoPanel);
			footer.add(new Anchor("Found a bug?", "mailto:lewis@asciiflow.com"));
		RootPanel.get().add(frame);
	}
}
