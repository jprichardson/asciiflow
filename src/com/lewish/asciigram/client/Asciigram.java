package com.lewish.asciigram.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;

public class Asciigram implements EntryPoint {

	// AsciigramGinjector injector = GWT.create(AsciigramGinjector.class);
	FlowPanel frame = new FlowPanel();
	FlowPanel header = new FlowPanel();
	HorizontalPanel body = new HorizontalPanel();
	FlowPanel footer = new FlowPanel();

	public void onModuleLoad() {
		Controller controller = new Controller();
		Canvas canvas = new Canvas(controller);
		ToolPanel toolPanel = new ToolPanel(controller, canvas);

		frame.setStyleName(CssStyles.Frame);
		header.setStyleName(CssStyles.Header);
		body.setStyleName(CssStyles.Body);
		footer.setStyleName(CssStyles.Footer);

		frame.add(header);
			header.add(new Image("images/asciigram.png"));
		frame.add(body);
			body.add(toolPanel);
			body.add(canvas);
		frame.add(footer);
		RootPanel.get().add(frame);
	}
}
