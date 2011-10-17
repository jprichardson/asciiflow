package com.lewish.asciiflow.server;

import java.util.List;

import org.dellroad.lzma.client.LZMAByteArrayCompressor;
import org.dellroad.lzma.client.LZMAByteArrayDecompressor;

import com.lewish.asciiflow.shared.Compressor;
import com.lewish.asciiflow.shared.State;

public class ServerCompressor extends Compressor {

	public void compress(final State state, final Callback callback) {
		final byte[] data = preProcessCompress(state);
		LZMAByteArrayCompressor c = new LZMAByteArrayCompressor(data);
		while (c.execute()) {
		}
		state.setCompressedState(c.getCompressedData());
		callback.onFinish(true);
	}

	public void uncompress(final State state, final Callback callback) {
		LZMAByteArrayDecompressor c = new LZMAByteArrayDecompressor(state.getCompressedState());

		while (c.execute()) {
		}
		postProcessUncompress(c.getUncompressedData(), state);
		callback.onFinish(true);
	}

	@Override
	public void uncompress(List<State> state, Callback callback) {
		throw new UnsupportedOperationException();
	}
}
