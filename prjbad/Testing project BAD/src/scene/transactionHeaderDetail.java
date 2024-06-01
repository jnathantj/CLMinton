package scene;

public class transactionHeaderDetail {
	private String transactionId, userId, courierType, transactionDate, email;
	private Integer deliveryInsurance;
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCourierType() {
		return courierType;
	}
	public void setCourierType(String courierType) {
		this.courierType = courierType;
	}
	public String getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
	public Integer getDeliveryInsurance() {
		return deliveryInsurance;
	}
	public void setDeliveryInsurance(Integer deliveryInsurance) {
		this.deliveryInsurance = deliveryInsurance;
	}
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	public transactionHeaderDetail(String transactionId, String userId, String email, String transactionDate,
			Integer deliveryInsurance, String courierType) {
		super();
		this.transactionId = transactionId;
		this.userId = userId;
		this.email = email;
		this.courierType = courierType;
		this.transactionDate = transactionDate;
		this.deliveryInsurance = deliveryInsurance;
	}
	
	
}
