//Copyright Lewis Hemens 2011
package com.lewish.asciiflow.client;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public abstract class MenuWidget extends Composite {

	private final FlowPanel resizePanel = new FlowPanel();
	private final FlowPanel hidePanel = new FlowPanel();
	private final int height;

	public MenuWidget(int height) {
		this.height = height;
		resizePanel.addStyleName(CssStyles.MenuWidget);
		hidePanel.add(getPanel());
		resizePanel.add(hidePanel);
		initWidget(resizePanel);
		hide();
	}

	public void show() {
		setHeight(height + "px");
		Timer timer = new Timer() {
			@Override
			public void run() {
				hidePanel.setVisible(true);
				onShow();
			}
		};
		timer.schedule(500);
	}

	protected abstract Widget getPanel();
	
	protected abstract void onShow();

	public void hide() {
		setHeight("0px");
		hidePanel.setVisible(false);
	}

	public void toggle() {
		if (hidePanel.isVisible()) {
			hide();
		} else {
			show();
		}
	}
}
