package com.lewish.asciiflow.client.gallery;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;
import com.lewish.asciiflow.client.resources.AsciiflowCss;
import com.lewish.asciiflow.shared.State;
import com.lewish.asciiflow.shared.OutputUtils;

public class GalleryPreviewWidget extends Composite {

	private final FlowPanel panel = new FlowPanel();
	private final Label title = new Label();
	private final HTML preview = new HTML();
	@Inject
	public GalleryPreviewWidget(AsciiflowCss css) {
		panel.setStyleName(css.galleryPreviewWidget());
		panel.add(title);
		panel.add(preview);
		initWidget(panel);
	}

	public void setState(State state) {
		title.setText(state.getTitle());
		preview.setHTML(OutputUtils.toText(state.getCellStateMap()));
	}
}
