package com.lewish.asciiflow.client.common;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.dellroad.lzma.client.LZMAByteArrayCompressor;
import org.dellroad.lzma.client.LZMAByteArrayDecompressor;

import com.google.gwt.core.client.Scheduler;
import com.lewish.asciiflow.shared.Compressor;
import com.lewish.asciiflow.shared.State;

public class ClientCompressor extends Compressor {

	public void compress(final State state, final Callback callback) {
		final byte[] data = preProcessCompress(state);
		Scheduler.get().scheduleIncremental(new Scheduler.RepeatingCommand() {
			LZMAByteArrayCompressor c = new LZMAByteArrayCompressor(data);

			@Override
			public boolean execute() {
				if (!c.execute()) {
					state.setCompressedState(c.getCompressedData());
					callback.onFinish(true);
					return false;
				}
				return true;
			}
		});
	}

	public void uncompress(final State state, final Callback callback) {
		Scheduler.get().scheduleIncremental(new Scheduler.RepeatingCommand() {
			LZMAByteArrayDecompressor c = new LZMAByteArrayDecompressor(state.getCompressedState());

			@Override
			public boolean execute() {
				try {
					if (!c.execute()) {
						postProcessUncompress(c.getUncompressedData(), state);
						callback.onFinish(true);
						return false;
					}
					return true;
				} catch (Exception e) {
					callback.onFinish(false);
					return false;
				}
			}
		});
	}

	public void uncompress(final List<State> states, final Callback callback) {
		final AtomicInteger count = new AtomicInteger(states.size());
		for (State state : states) {
			uncompress(state, new Callback() {
				@Override
				public void onFinish(boolean result) {
					int remaining = count.decrementAndGet();
					if (remaining == 0) {
						callback.onFinish(true);
					}
				}
			});
		}
	}
}
