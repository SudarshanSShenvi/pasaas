package com.pervazive.kheddah.web.rest.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

/**
 * Utility class for HTTP headers creation.
 */
public class MessageObject {

    private static final Logger log = LoggerFactory.getLogger(MessageObject.class);

    public MessageObject() {
    }

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
