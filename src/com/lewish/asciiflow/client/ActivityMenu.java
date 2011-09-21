package com.lewish.asciiflow.client;

import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.lewish.asciiflow.client.resources.AsciiflowCss;

@Singleton
public class ActivityMenu extends Composite {

	private final ActivityController activityController;

	private final FlowPanel panel = new FlowPanel();

	@Inject
	public ActivityMenu(AsciiflowCss css, ActivityController activityController, Map<Activity, Layout> activityMap) {
		this.activityController = activityController;

		panel.setStyleName(css.activityMenu());

		for(Activity activity : activityMap.keySet()) {
			addLink(activity);
		}
		initWidget(panel);
	}

	private void addLink(final Activity activity) {
		FocusPanel link = new FocusPanel();
		link.add(new Label(activity.getLabel()));
		link.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				activityController.startActivity(activity);
			}
		});
		panel.add(link);
	}
}
