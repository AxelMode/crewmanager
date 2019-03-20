package com.napptilus.crewmanager.exceptions;

public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 7156407626191898732L;

	public UserNotFoundException(final String message) {
		super(message);
	}

	public UserNotFoundException(final String message, final Throwable th) {
		super(message, th);
	}
}