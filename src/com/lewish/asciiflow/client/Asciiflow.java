//Copyright Lewis Hemens 2011
package com.lewish.asciiflow.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AttachDetachException;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.lewish.asciiflow.client.resources.AsciiflowCss;
import com.lewish.asciiflow.client.social.FacebookWidget;
import com.lewish.asciiflow.client.social.PlusOneWidget;
import com.lewish.asciiflow.client.social.TwitterWidget;

public class Asciiflow implements EntryPoint {

	AsciiflowGinjector injector = GWT.create(AsciiflowGinjector.class);

	FlowPanel frame = new FlowPanel();
	FlowPanel header = new FlowPanel();

	public void onModuleLoad() {
		AsciiflowCss css = injector.getCss();
		ContentFrame contentFrame = injector.getContentFrame();
		ActivityMenu activityMenu = injector.getActivityMenu();

		// This starts everything in the constructor.
		@SuppressWarnings("unused")
		ActivityController activityController = injector.getActivityController();

		css.ensureInjected();

		//TODO: Pull out remianing classes to HTML :(
		frame.setStyleName(css.frame());
		header.setStyleName(css.header());

		frame.add(header);
			header.add(new Logo(css));
			header.add(activityMenu);
			header.add(new FacebookWidget(css));
			header.add(new TwitterWidget(css));
			header.add(new PlusOneWidget(css));
		frame.add(contentFrame);

		try {
		RootPanel.get("main").add(frame);
		} catch (AttachDetachException e) {
			System.out.println(e.getCauses());
		}
	}
}
