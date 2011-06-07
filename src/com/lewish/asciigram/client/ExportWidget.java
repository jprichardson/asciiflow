package com.lewish.asciigram.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ExportWidget extends Composite {

	private ExportPopup popup = new ExportPopup();

	public ExportWidget(final Canvas canvas) {
		Button button = new Button("Export", new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				popup.setText(canvas.getText());
				popup.center();
			}
		});
		initWidget(button);
	}

	private class ExportPopup extends PopupPanel {
		VerticalPanel panel = new VerticalPanel();
		TextArea textArea = new TextArea();

		public ExportPopup() {
			textArea.addStyleName(CssStyles.ExportTextArea);
			panel.add(textArea);
			Button close = new Button("Close", new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					hide();
				}
				
			});
			close.addStyleName(CssStyles.ExportClose);
			panel.add(close);
			add(panel);
		}

		@Override
		public void center() {
			super.center();
			textArea.setFocus(true);
			textArea.selectAll();
		}

		public void setText(String text) {
			textArea.setText(text);
		}
	}
}
