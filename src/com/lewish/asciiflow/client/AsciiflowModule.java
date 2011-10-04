//Copyright Lewis Hemens 2011
package com.lewish.asciiflow.client;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.lewish.asciiflow.client.common.ClientCompressor;
import com.lewish.asciiflow.client.common.Layout;
import com.lewish.asciiflow.client.gallery.GalleryLayout;
import com.lewish.asciiflow.client.resources.AsciiflowClientBundle;
import com.lewish.asciiflow.client.resources.AsciiflowCss;
import com.lewish.asciiflow.shared.Compressor;

public class AsciiflowModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(Compressor.class).to(ClientCompressor.class).in(Singleton.class);
	}

	@Provides
	@Singleton
	public AsciiflowCss getCss(AsciiflowClientBundle clientBundle) {
		return clientBundle.css();
	}

	@Provides
	@Singleton
	public Map<Activity, Layout> getActivityMap(DrawLayout drawLayout, AboutLayout aboutLayout, GalleryLayout galleryLayout) {
		return ImmutableMap.<Activity, Layout>builder()
			.put(Activity.Draw, drawLayout)
			.put(Activity.About, aboutLayout)
			.put(Activity.Gallery, galleryLayout)
			.build();
	}
}
