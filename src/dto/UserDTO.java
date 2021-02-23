package dto;

public class UserDTO {
	private int userIdx; // 회원 고유번호(PK)
	private String userId; // 아이디(FK)
	private String userPw; // 비밀번호
	private String userName; // 이름(FK)
	private String userPhone; // 폰번호
	private String userAddr; // 주소(FK)
	private int userPoint; // 유저잔액(FK)

	public int getUserIdx() {
		return userIdx;
	}

	public void setUserIdx(int userIdx) {
		this.userIdx = userIdx;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPw() {
		return userPw;
	}

	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserAddr() {
		return userAddr;
	}

	public void setUserAddr(String userAddr) {
		this.userAddr = userAddr;
	}

	public int getUserPoint() {
		return userPoint;
	}

	public void setUserPoint(int userPoint) {
		this.userPoint = userPoint;
	}

	public UserDTO() {
	}

	public UserDTO(String userId, String userPw, String userName, String userPhone, String userAddr) {
		this.userIdx = 0;
		this.userId = userId;
		this.userPw = userPw;
		this.userName = userName;
		this.userPhone = userPhone;
		this.userAddr = userAddr;
		this.userPoint = 0;
	}
	
	public UserDTO(int userIdx, String userId, String userPw, String userName, String userPhone, String userAddr,
			int userPoint) {
		this.userIdx = userIdx;
		this.userId = userId;
		this.userPw = userPw;
		this.userName = userName;
		this.userPhone = userPhone;
		this.userAddr = userAddr;
		this.userPoint = userPoint;
	}

}