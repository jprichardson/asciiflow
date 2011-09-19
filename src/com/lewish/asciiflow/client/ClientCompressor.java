package com.lewish.asciiflow.client;

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
				if (!c.execute()) {
					postProcessUncompress(c.getUncompressedData(), state);
					callback.onFinish(true);
					return false;
				}
				return true;
			}
		});
	}
}
