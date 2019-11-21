package kz.ktzh.models;

public class UserCreationRequest {

	private Users user;
	private UserInfo userInfo;

	public UserCreationRequest(Users user, UserInfo userInfo) {
		this.user = user;
		this.userInfo = userInfo;
	}

	public Users getUser() {
		return user;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

}
