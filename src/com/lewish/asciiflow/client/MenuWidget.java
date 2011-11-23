package com.lewish.asciiflow.client;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.lewish.asciiflow.client.resources.AsciiflowCss;

/**
 * Base class for the content of drop down menu items.
 * Applies necessary css styles and convenient show/hide functions.
 * 
 * @author lewis
 */
public abstract class MenuWidget extends Composite {

	private final FlowPanel resizePanel = new FlowPanel();
	private final FlowPanel hidePanel = new FlowPanel();
	private final int height;

	protected final AsciiflowCss css;

	public MenuWidget(int height, AsciiflowCss css) {
		this.height = height;
		this.css = css;
		resizePanel.addStyleName(css.menuWidget());
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
