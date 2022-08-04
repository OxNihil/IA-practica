package es.udc.rs.wscontrib.reward.service;

import java.util.HashMap;
import java.util.Map;

import jakarta.annotation.PostConstruct;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

@WebService(name = "RewardProvider", serviceName = "RewardProviderService", targetNamespace = "http://rs.udc.es/reward")
public class RewardService {

	private Map<Long, Integer> pendingPoints = new HashMap<Long, Integer>();
	private Map<Long, Integer> points = new HashMap<Long, Integer>();

	public RewardService() {
	}

	@PostConstruct()
	private void init() {
	}

	@WebMethod(operationName = "getPoints")
	public int getPoints(@WebParam(name = "customerId") Long customerId)
			throws RewardException {

		System.out.println("getPoints\n  input parameters" + "\n    customerId: "
				+ customerId);

		int customerPoints = 0;
		if (points.containsKey(customerId)) {
			customerPoints = points.get(customerId);
		}

		System.out.println("  return value" + "\n    " + customerPoints);

		return customerPoints;
	}

	@WebMethod(operationName = "getPendingPoints")
	public int getPendingPoints(@WebParam(name = "customerId") Long customerId)
			throws RewardException {

		System.out.println("getPendingPoints\n  input parameters" + "\n    customerId: "
				+ customerId);

		int customerPendingPoints = 0;
		if (pendingPoints.containsKey(customerId)) {
			customerPendingPoints = pendingPoints.get(customerId);
		}

		System.out.println("  return value" + "\n    "
				+ customerPendingPoints);

		return customerPendingPoints;
	}

	@WebMethod(operationName = "addPendingPoints")
	public void addPendingPoints(@WebParam(name = "customerId") Long customerId,
			@WebParam(name = "points") int points) throws RewardException {

		System.out.println("addPendingPoints\n  input parameters" + "\n    customerId: "
				+ customerId + "\n    points: " + points);

		if (pendingPoints.containsKey(customerId)) {
			pendingPoints.put(customerId, pendingPoints.get(customerId) + points);
		} else {
			pendingPoints.put(customerId, points);
		}
	}

	@WebMethod(operationName = "confirmPendingPoints")
	public void confirmPendingPoints(
			@WebParam(name = "customerId") Long customerId,
			@WebParam(name = "points") int points) throws RewardException {

		System.out.println("confirmPendingPoints\n  input parameters" + "\n    customerId: "
				+ customerId + "\n    points: " + points);

		Integer currentPendingPoints = pendingPoints.get(customerId);
		if (currentPendingPoints == null || currentPendingPoints < points) {
			System.out.println("  error: " + "Cannot confirm " + points
					+ " pending points for customer " + customerId);
			throw new RewardException("Cannot confirm " + points
					+ " pending points for customer " + customerId);
		}
		if (this.points.containsKey(customerId)) {
			this.points.put(customerId, this.points.get(customerId) + points);
		} else {
			this.points.put(customerId, points);
		}
		this.pendingPoints.put(customerId, currentPendingPoints - points);
	}

	@WebMethod(operationName = "removePendingPoints")
	public void removePendingPoints(@WebParam(name = "customertId") Long customerId,
			@WebParam(name = "points") int points) throws RewardException {

		System.out.println("removePendingPoints\n  input parameters" + "\n    customerId: "
				+ customerId + "\n    points: " + points);

		Integer currentPendingPoints = pendingPoints.get(customerId);
		if (currentPendingPoints == null || currentPendingPoints < points) {
			System.out.println("  error: " + "Cannot remove " + points
					+ " pending points for customer " + customerId);
			throw new RewardException("Cannot remove " + points
					+ " pending points for customer " + customerId);
		} else {
			pendingPoints.put(customerId, pendingPoints.get(customerId) - points);
		}
	}

}
