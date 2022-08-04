package es.udc.rs.wscontrib.billing.service;

public class BillSummary {

	private Long billId;
	private double amount;
	
	
	public BillSummary() {
	}
	
	public BillSummary(Long billId, double ammount) {
		super();
		this.billId = billId;
		this.amount = ammount;
	}

	public Long getBillId() {
		return billId;
	}
	
	public void setBillId(Long billId) {
		this.billId = billId;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	@Override
	public String toString() {
		return "BillSummary [billId=" + billId + ", amount=" + amount + "]";
	}
	
	
}
