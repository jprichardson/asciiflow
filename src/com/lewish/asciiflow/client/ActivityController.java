package com.lewish.asciiflow.client;

import java.util.Map;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.lewish.asciiflow.client.common.Layout;
import com.lewish.asciiflow.shared.Uri;

@Singleton
public class ActivityController {

	public static final Activity DEFAULT_ACTIVITY = Activity.Draw;
	
	private final Map<Activity, Layout> activityMap;
	private final ContentFrame contentFrame;

	private Activity currentActivity;

	@Inject
	public ActivityController(Map<Activity, Layout> activityMap, ContentFrame contentFrame) {
		this.activityMap = activityMap;
		this.contentFrame = contentFrame;
		if (Uri.isGalleryMode()) {
			startActivity(Activity.Gallery);
		} else {
			startActivity(DEFAULT_ACTIVITY);
		}
	}

	public void startActivity(Activity activity) {
		if (activity != currentActivity && activityMap.containsKey(activity)) {
			currentActivity = activity;
			contentFrame.setLayout(activityMap.get(activity));
		}
	}
}
