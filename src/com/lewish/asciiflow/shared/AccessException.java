package com.lewish.asciiflow.shared;

import java.io.Serializable;

public class AccessException extends Exception implements Serializable {

	private static final long serialVersionUID = 6278056247339829109L;

	public AccessException() {
		super();
	}

	public AccessException(State state) {
		super("You do not have permission to edit this flow with access code "
				+ state.getEditCode());
	}
}
