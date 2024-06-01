package scene;

public class userData {
	private String userId, userEmail, userPassword,userGender, userNationality, userRole;
	private Integer userAge;
	
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserGender() {
		return userGender;
	}
	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}
	public String getUserNationality() {
		return userNationality;
	}
	public void setUserNationality(String userNationality) {
		this.userNationality = userNationality;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	public Integer getUserAge() {
		return userAge;
	}
	public void setUserAge(Integer userAge) {
		this.userAge = userAge;
		
	}
	public userData(String userId, String userEmail, String userPassword, Integer userAge, String userGender, String userNationality, String userRole
			) {
		super();
		this.userId = userId;
		this.userEmail = userEmail;
		this.userPassword = userPassword;
		this.userGender = userGender;
		this.userNationality = userNationality;
		this.userRole = userRole;
		this.userAge = userAge;
	}
}
