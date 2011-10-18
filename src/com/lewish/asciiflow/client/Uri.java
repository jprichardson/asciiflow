package com.lewish.asciiflow.client;

import static com.lewish.asciiflow.shared.UriConstants.NIGHTLY_HOST;
import static com.lewish.asciiflow.shared.UriConstants.STABLE_HOST;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class Uri {

	public static interface UriChangeHandler extends EventHandler {
		public void onModelChange(UriChangeEvent event);
	}

	public static class UriChangeEvent extends GwtEvent<UriChangeHandler> {

		public static final Type<UriChangeHandler> TYPE = new Type<UriChangeHandler>();

		@Override
		public Type<UriChangeHandler> getAssociatedType() {
			return TYPE;
		}

		@Override
		protected void dispatch(UriChangeHandler handler) {
			handler.onModelChange(this);
		}
	}

	private final HandlerManager handlerManager = new HandlerManager(this);

	private String activity = null;
	private long id = 0;
	private int editCode = 0;

	@Inject
	private Uri() {
		History.addValueChangeHandler(new ValueChangeHandler<String>() {
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				parseHash();
				fireUriChangeEvent();
			}
		});
		parseHash();
	}


	public String getHost() {
		String host = "http://" + Window.Location.getHost();
		if (!host.endsWith("/")) {
			host += "/";
		}
		return host;
	}


	public String getDocumentLink(Long id, Integer editCode) {
		String link = getHost() + "#" + String.valueOf(id);
		if (editCode != null && editCode != 0) {
			link += "/" + editCode;
		}
		return link;
	}

	public String getInlineTextLink(Long id) {
		return getHost() + "inline?id=" + id.toString();
	}

	public String getInlineHtmlLink(Long id) {
		return getHost() + "inline?html=true&id=" + id.toString();
	}

	public boolean isNightly() {
		return Window.Location.getHref().contains(
				NIGHTLY_HOST.subSequence(7, NIGHTLY_HOST.length() - 2));
	}

	public boolean isStable() {
		return Window.Location.getHref().contains(
				STABLE_HOST.subSequence(7, STABLE_HOST.length() - 2));
	}

	public boolean hasId() {
		return id != 0;
	}

	public boolean hasEditCode() {
		return editCode != 0;
	}

	public boolean hasActivity() {
		return activity != null && !"".equals(activity);
	}

	public long getId() {
		return id;
	}

	public int getEditCode() {
		return editCode;
	}

	public String getActivity() {
		return activity;
	}

	public void setId(long id) {
		this.id = id;
		buildHash();
	}

	public void setIdAndEditCode(long id, int editCode) {
		this.id = id;
		this.editCode = editCode;
		buildHash();
	}

	public void setActivity(String activity) {
		this.activity = activity;
		buildHash();
	}

	private void parseHash() {
		activity = null;
		id = 0;
		editCode = 0;
		RegExp regex = RegExp.compile("([a-zA-Z]+)(\\d*)/*(\\d*)");
		MatchResult matcher = regex.exec(History.getToken());
		if (matcher == null) {
			return;
		}
		int groups = matcher.getGroupCount();
		if (groups >= 2) {
			activity = matcher.getGroup(1);
		}
		if (groups >= 3) {
			id = Long.parseLong(matcher.getGroup(2));
		}
		if (groups >= 4) {
			editCode = Integer.parseInt(matcher.getGroup(3));
		}
	}

	private void buildHash() {
		History.newItem(activity 
			+ (id == 0 ? "" : id) 
			+ (editCode == 0 ? "" : ("/" + editCode)),
			false);
	}

	private void fireUriChangeEvent() {
		handlerManager.fireEvent(new UriChangeEvent());
	}

	public HandlerRegistration addUriChangeHandler(UriChangeHandler handler) {
		return handlerManager.addHandler(UriChangeEvent.TYPE, handler);
	}
}