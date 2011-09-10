//Copyright Lewis Hemens 2011
package com.lewish.asciiflow.client;

import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.lewish.asciiflow.client.resources.AsciiflowCss;

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

	public String getText(boolean html) {
		String text = html ? "<pre>" : "";
		for (int y = 0; y < canvas.getHeight(); y++) {
			String rowText = "";
			for (int x = canvas.getWidth() - 1; x >= 0; x--) {
				String value = canvas.getValue(x, y);
				if (rowText.equals("") && value == null)
					continue;
				String out = value == null ? (html ? "&nbsp;" : " ") : (html ? SafeHtmlUtils
						.htmlEscape(value) : value);
				rowText = out + rowText;
			}
			text += rowText + "\n";
		}
		return text + (html ? "</pre>" : "");
	}

	public void setHtml(boolean html) {
		this.html  = html;
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
		textArea.setText(getText(html));
		textArea.setFocus(true);
		textArea.selectAll();
	}
}
