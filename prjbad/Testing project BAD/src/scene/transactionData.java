package scene;


public class transactionData {
	private String id, email, date;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public transactionData(String id, String email, String date) {
		super();
		this.id = id;
		this.email = email;
		this.date = date;
	}
	
	
	

}
