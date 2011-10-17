package com.lewish.asciiflow.client.gallery;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.inject.Inject;
import com.lewish.asciiflow.client.Activity;
import com.lewish.asciiflow.client.ActivityController;
import com.lewish.asciiflow.client.resources.AsciiflowCss;
import com.lewish.asciiflow.shared.OutputUtils;
import com.lewish.asciiflow.shared.State;

public class GalleryPreviewWidget extends Composite {

	private final ActivityController activityController;

	private final FlowPanel panel = new FlowPanel();
	private final Anchor title = new Anchor();
	private final HTML preview = new HTML();
	private State state;

	@Inject
	public GalleryPreviewWidget(AsciiflowCss css, ActivityController activityController) {
		this.activityController = activityController;
		panel.setStyleName(css.galleryPreviewWidget());
		panel.add(title);
		panel.add(preview);
		initWidget(panel);
	}

	public void setState(final State state) {
		this.state = state;
		title.setText(state.getTitle().isEmpty() ? "Untitled" : state.getTitle());
		title.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				activityController.startActivity(Activity.Draw);
				// TODO: Pass the state to the store model.
				History.newItem(String.valueOf(state.getId()));
			}
		});
		title.setHref("javascript:void(0)");
		if (state.getCellStateMap() != null) {
			preview.setHTML(OutputUtils.toHtml(state.getCellStateMap()));
		}
	}

	public State getState() {
		return state;
	}
}
