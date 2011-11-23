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
import com.lewish.asciiflow.client.Uri.UriChangeEvent;
import com.lewish.asciiflow.client.Uri.UriChangeHandler;
import com.lewish.asciiflow.client.common.Layout;
import com.lewish.asciiflow.client.resources.AsciiflowCss;

/**
 * A widget that lists the pages within the app, and allows you to move between them.
 * 
 * @author lewis
 */
@Singleton
public class ActivityMenu extends Composite {

	private final AsciiflowCss css;
	private final ActivityController activityController;
	private final Uri uri;

	private final FlowPanel panel = new FlowPanel();

	private FocusPanel currentLink;

	@Inject
	public ActivityMenu(AsciiflowCss css, ActivityController activityController,
			Map<Activity, Layout> activityMap, Uri uri) {
		this.css = css;
		this.activityController = activityController;
		this.uri = uri;

		panel.setStyleName(css.activityMenu());

		for (Activity activity : activityMap.keySet()) {
			addLink(activity);
		}
		initWidget(panel);
	}

	private void addLink(final Activity activity) {
		final FocusPanel link = new FocusPanel();
		link.add(new Label(activity.getLabel()));
		final ClickHandler clickHandler = new ClickHandler() {
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
		// TODO: This is all fucked up, but whatever.
		uri.addUriChangeHandler(new UriChangeHandler() {
			@Override
			public void onModelChange(UriChangeEvent event) {
				if (activity.name().equals(uri.getActivity())) {
					clickHandler.onClick(null);
				}
			}
		});
		if (activity.name().equals(uri.getActivity())) {
			clickHandler.onClick(null);
		}
		panel.add(link);
	}
}
