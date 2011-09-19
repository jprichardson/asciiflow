//Copyright Lewis Hemens 2011
package com.lewish.asciiflow.client;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.lewish.asciiflow.client.resources.AsciiflowCss;
import com.lewish.asciiflow.shared.CellStateMap;
import com.lewish.asciiflow.shared.OutputUtils;

@Singleton
public class ExportWidget extends MenuWidget {

	private final Canvas canvas;

	private TextArea textArea;

	private boolean html;

	@Inject
	public ExportWidget(Canvas canvas, AsciiflowCss css) {
		super(100, css);
		this.canvas = canvas;
	}

	public void setHtml(boolean html) {
		this.html = html;
	}

	@Override
	protected Widget getPanel() {
		textArea = new TextArea();
		textArea.addStyleName(css.exportTextArea());
		FlowPanel panel = new FlowPanel();
		panel.add(textArea);
		panel.add(new HTML("Copy the text above and use it in a <b>monospace</b> font"));
		return panel;
	}

	@Override
	protected void onShow() {
		CellStateMap cellStateMap = canvas.getCellStates();
		textArea
				.setText(html ? OutputUtils.toHtml(cellStateMap) : OutputUtils.toText(cellStateMap));
		textArea.setFocus(true);
		textArea.selectAll();
	}
}
