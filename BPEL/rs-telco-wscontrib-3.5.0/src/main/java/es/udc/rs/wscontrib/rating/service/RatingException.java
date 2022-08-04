package es.udc.rs.wscontrib.rating.service;

import jakarta.xml.ws.WebFault;

@SuppressWarnings("serial")
@WebFault(name = "RatingException", targetNamespace = "http://rs.udc.es/rating")
public class RatingException extends Exception {

	private String message;

	public RatingException(String message) {
		this.message = message;
	}

	public String getFaultInfo() {
		return message;
	}

}
