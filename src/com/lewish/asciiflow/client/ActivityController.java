package com.lewish.asciiflow.client;

import java.util.Map;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.lewish.asciiflow.client.common.Layout;

/**
 * Controls the pages within the app, loads up the default view, etc.
 * 
 * @author lewis
 */
@Singleton
public class ActivityController {

	public static final Activity DEFAULT_ACTIVITY = Activity.Draw;
	
	private final Map<Activity, Layout> activityMap;
	private final ContentFrame contentFrame;
	private final Uri uri;

	private Activity currentActivity;

	@Inject
	public ActivityController(Map<Activity, Layout> activityMap, ContentFrame contentFrame, Uri uri) {
		this.activityMap = activityMap;
		this.contentFrame = contentFrame;
		this.uri = uri;
		if (uri.hasActivity()) {
			startActivity(Activity.valueOf(uri.getActivity()));
		} else {
			startActivity(DEFAULT_ACTIVITY);
		}
	}

	public void startActivity(Activity activity) {
		if (activity != currentActivity && activityMap.containsKey(activity)) {
			if (!activity.name().equals(uri.getActivity())) {
				uri.setActivity(activity.name());
			}
			currentActivity = activity;
			contentFrame.setLayout(activityMap.get(activity));
		}
	}
}
