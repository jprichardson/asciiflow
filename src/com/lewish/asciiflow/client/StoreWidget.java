package com.lewish.asciiflow.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.lewish.asciiflow.client.StoreModel.ModelChangeEvent;
import com.lewish.asciiflow.client.StoreModel.ModelChangeEvent.ModelChangeState;
import com.lewish.asciiflow.client.StoreModel.ModelChangeHandler;
import com.lewish.asciiflow.client.resources.AsciiflowCss;
import com.lewish.asciiflow.client.tools.EraseTool;
import com.lewish.asciiflow.shared.State;
import com.lewish.asciiflow.shared.Uri;

@Singleton
public class StoreWidget extends Composite implements ModelChangeHandler {

	private final StoreModel storeModel;
	private final Canvas canvas;

	private final FlowPanel linksPanel = new FlowPanel();
	private final Anchor editLink = new Anchor();
	private final Anchor readonlyLink = new Anchor();
	private final Button saveButton = new Button();
	private final TextBox titleBox = new TextBox();
	private final CheckBox isPublic = new CheckBox();

	@Inject
	public StoreWidget(final Canvas canvas, final StoreModel storeHelper, AsciiflowCss css) {
		this.storeModel = storeHelper;
		this.canvas = canvas;
		storeModel.addModelChangeHandler(this);

		saveButton.setStyleName(css.inline());

		saveButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				storeHelper.save();
			}
		});

		titleBox.addValueChangeHandler(new ValueChangeHandler<String>() {
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				storeHelper.getCurrentState().setTitle(event.getValue());
			}
		});

		isPublic.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				storeHelper.getCurrentState().setPublic(event.getValue());
			}
		});

		saveButton.setText("Save");
		readonlyLink.setText("Read only Link");
		editLink.setText("Edit link");
		titleBox.setText("Untitled");

		readonlyLink.setTarget("_blank");
		editLink.setTarget("_blank");

		FlowPanel panel = new FlowPanel();
		panel.setStyleName(css.block());
		panel.add(saveButton);
		panel.add(new InlineLabel(" Title: "));
		panel.add(titleBox);
		panel.add(new InlineLabel(" Public: "));
		panel.add(isPublic);
		linksPanel.add(new InlineLabel(" "));
		linksPanel.add(editLink);
		linksPanel.add(new InlineLabel(" "));
		linksPanel.add(readonlyLink);
		linksPanel.setStyleName(css.inline());
		panel.add(linksPanel);
		updateLinks();
		initWidget(panel);

		parseFragmentLoadAndDraw();

		History.addValueChangeHandler(new ValueChangeHandler<String>() {
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				parseFragmentLoadAndDraw();
			}
		});
	}

	private void updateLinks() {
		if (!storeModel.getCurrentState().hasId()) {
			linksPanel.setVisible(false);
		} else {
			String editHref = Uri.getDocumentLink(storeModel.getCurrentState().getId(), storeModel
					.getCurrentState().getEditCode());
			String readonlyHref = Uri.getDocumentLink(storeModel.getCurrentState().getId(), null);
			readonlyLink.setHref(readonlyHref);
			editLink.setHref(editHref);
			linksPanel.setVisible(true);
			// Only show edit link if there is a valid edit code.
			editLink.setVisible(storeModel.getCurrentState().isEditable());
		}
		saveButton.setEnabled(storeModel.getCurrentState().isEditable());
		titleBox.setEnabled(storeModel.getCurrentState().isEditable());
		isPublic.setEnabled(storeModel.getCurrentState().isEditable());
	}

	public void parseFragmentLoadAndDraw() {
		String hash = Window.Location.getHash();
		if (hash != null && hash.startsWith("#")) {
			try {
				String[] split = hash.substring(1).split("/");
				Long id = Long.parseLong(split[0]);
				Integer editCode = 0;
				if (split.length > 1) {
					editCode = Integer.parseInt(split[1]);
				}
				if (id.equals(storeModel.getCurrentState().getId())
						&& editCode.equals(storeModel.getCurrentState().getEditCode())) {
					// If neither field has changed, do nothing.
					return;
				}
				storeModel.load(id, editCode);
			} catch (NumberFormatException e) {
				// TODO
			}
		}
	}

	public void setTitle(String title) {
		titleBox.setText(title);
	}

	public void setPublic(Boolean isPublic) {
		this.isPublic.setValue(isPublic);
	}

	@Override
	public void onModelChange(ModelChangeEvent event) {
		State state = storeModel.getCurrentState();
		if (event.getState() == ModelChangeState.LOADED) {
			EraseTool.draw(canvas);
			canvas.drawCellStates(state.getCellStateMap());
			canvas.refreshDraw();
			canvas.commitDraw();
			setTitle(state.getTitle());
			setPublic(state.isPublic());
		}
		if (event.getState() == ModelChangeState.SAVED) {
			History.newItem(state.getId() + "/" + state.getEditCode());
		}
		if (event.getState() == ModelChangeState.CLEARED) {
			setTitle("Untitled");
			setPublic(false);
		}
		updateLinks();
	}
}
