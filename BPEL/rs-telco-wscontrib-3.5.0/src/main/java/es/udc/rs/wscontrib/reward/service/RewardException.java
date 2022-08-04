package es.udc.rs.wscontrib.reward.service;

import jakarta.xml.ws.WebFault;

@SuppressWarnings("serial")
@WebFault(name = "RewardException", targetNamespace = "http://rs.udc.es/reward")
public class RewardException extends Exception {

	private String message;

	public RewardException(String message) {
		this.message = message;
	}

	public String getFaultInfo() {
		return message;
	}

}
