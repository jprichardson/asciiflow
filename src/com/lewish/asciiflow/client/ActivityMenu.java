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
import com.lewish.asciiflow.client.common.Layout;
import com.lewish.asciiflow.client.resources.AsciiflowCss;

@Singleton
public class ActivityMenu extends Composite {

	private final AsciiflowCss css;
	private final ActivityController activityController;

	private final FlowPanel panel = new FlowPanel();

	private FocusPanel currentLink;

	@Inject
	public ActivityMenu(AsciiflowCss css, ActivityController activityController,
			Map<Activity, Layout> activityMap) {
		this.css = css;
		this.activityController = activityController;

		panel.setStyleName(css.activityMenu());

		for (Activity activity : activityMap.keySet()) {
			addLink(activity);
		}
		initWidget(panel);
	}

	private void addLink(final Activity activity) {
		final FocusPanel link = new FocusPanel();
		link.add(new Label(activity.getLabel()));
		ClickHandler clickHandler = new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (currentLink != null) {
					currentLink.removeStyleName(css.selectedActivity());
				}
				currentLink = link;
				link.addStyleName(css.selectedActivity());
				activityController.startActivity(activity);
			}
		};
		link.addClickHandler(clickHandler);
		// TODO: Hack to highlight the right link at startup. Will break soon,
		// turn activity controller to a model.
		if (activity == ActivityController.DEFAULT_ACTIVITY) {
			clickHandler.onClick(null);
		}
		panel.add(link);
	}
}
