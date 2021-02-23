package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dto.UserDTO;

public class UserDAO {
	Connection conn = null; // DB연결을 위한 객체
	PreparedStatement pstm = null; // DB에 sql문을 전달하기 위한 객체
	private static int KEY = 4;
	ResultSet rs = null;

	public UserDAO() {
		conn = DBConnection.getConnection(); // UserDAO을 생성하면 DB연결을 하도록 한다.
	}

	// ---------------------아이디 중복 체크------------------------------
	public boolean checkDup(String userId) {
		String sql = "SELECT COUNT(*) FROM TBL_MEMBER WHERE USERID=?";
		int result = 0;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, userId);
			rs = pstm.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("중복값을 조회할 수 없습니다.");
		} finally {
			try {
				rs.close();
				pstm.close();
			} catch (SQLException e) {
				System.out.println("오류 발생");
			}
		}
		return result == 0; // result에 1이 담기지 않았다는 것은 중복된 아이디를 찾을 수 없다는 것이다.
	}

	// ---------------------회원가입------------------------------
	public int join(UserDTO newUser) {
		String sql = "INSERT INTO TBL_MEMBER VALUES(SEQ_USER.NEXTVAL,?,?,?,?,?,?)";
		int check = 0;
		if (newUser.getUserPw().length() < 8) {
			// 비밀번호 길이 제한을 검사합니다.
			return -1;
		} else {
			// 회원가입 가능한 상태이므로 아래 코드를 진행합니다.
			String en_pw = encrypt(newUser.getUserPw());
			newUser.setUserPw(en_pw); // 비밀번호 암호화한 값으로 만들기
			try {
				pstm = conn.prepareStatement(sql);
				pstm.setString(1, newUser.getUserId());
				pstm.setString(2, newUser.getUserPw());
				pstm.setString(3, newUser.getUserName());
				pstm.setString(4, newUser.getUserPhone());
				pstm.setString(5, newUser.getUserAddr());
				pstm.setInt(6, newUser.getUserPoint());
				check = pstm.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e);
			} finally {
				try {
					pstm.close();
				} catch (SQLException e) {
					System.out.println("오류 발생");
				}
			}
			return check;
		}
	}

	public String encrypt(String userPw) {
		String en_pw = "";
		for (int i = 0; i < userPw.length(); i++) {
			en_pw += (char) (userPw.charAt(i) + KEY);
		}
		return en_pw;
	}

	public String decrypt(String en_pw) {
		String de_pw = "";
		for (int i = 0; i < en_pw.length(); i++) {
			de_pw += (char) (en_pw.charAt(i) - KEY);
		}
		return de_pw;
	}

	// ---------------------로그인------------------------
	public UserDTO login(String userId, String userPw) {
		String sql = "SELECT * FROM TBL_MEMBER WHERE USERID = ? AND USERPW = ?";
		UserDTO loginUser = null;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, userId);
			pstm.setString(2, encrypt(userPw));
			rs = pstm.executeQuery();
			if (rs.next()) {
				loginUser = new UserDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getInt(7));
			}
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			try {
				rs.close();
				pstm.close();
			} catch (SQLException e) {
				System.out.println("오류 발생");
			}
		}
		return loginUser;
	}

	// -----------------수정하기---------------------
	public void modify(int idx, String newData, String userId) {
		String[] columns = { "userPw", "userAddr" };
		String sql = "UPDATE TBL_MEMBER SET " + columns[idx] + "= ? WHERE userId = ?";
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, newData);
			pstm.setString(2, userId);
			pstm.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			try {
				pstm.close();
			} catch (SQLException e) {
				System.out.println("오류 발생");
			}
		}
	}

	// ----------------잔액 확인하기----------------------
	public int check(String userId) {
		String sql = "SELECT USERPOINT FROM TBL_MEMBER WHERE USERID = ?";
		int result = 0;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, userId);
			rs = pstm.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			try {
				pstm.close();
			} catch (SQLException e) {
				System.out.println("오류 발생");
			}
		}
		return result;
	}

	// -----------------충전하기---------------------------
	public int charge(int point, String userId) {
		String sql = "UPDATE TBL_MEMBER SET USERPOINT = USERPOINT + ? WHERE USERID = ?";
		int result = 0;

		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, point);
			pstm.setString(2, userId);
			result = pstm.executeUpdate();

		} catch (SQLException e) {
			System.out.println("알 수 없는 오류.charge");
			e.printStackTrace();
		} finally {
			try {
				pstm.close();
			} catch (SQLException e) {
				System.out.println("알 수 없는 오류.charge");
				e.printStackTrace();
			}
		}

		return result;
	}

	// ------------------삭제-------------------------
	public boolean leaveId(String userPw, String userId) {
		// 비밀번호 검사 먼저
		String sql = "SELECT USERPW FROM TBL_MEMBER WHERE USERID = ?";
		String en_pw = "";
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, userId);
			rs = pstm.executeQuery();
			if (rs.next()) {
				en_pw = rs.getString(1);
			}
		} catch (SQLException e) {
			System.out.println("알 수 없는 오류.leaveId");
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstm.close();
			} catch (SQLException e) {
				System.out.println("알 수 없는 오류.leaveId");
				e.printStackTrace();
			}
		}
		// 비밀번호가 맞으면, 계정 삭제 진행
		if (userPw.equals(decrypt(en_pw))) {
			String user_sql = "DELETE FROM TBL_MEMBER WHERE USERID = ?";
			String orderList_sql = "DELETE FROM TBL_ORDERLIST WHERE USERID = ?";
			String board_sql = "DELETE FROM TBL_BOARD WHERE USERID = ?";
			//String basket_sql = ""; 
			
			try {
				pstm = conn.prepareStatement(board_sql);
				pstm.setString(1, userId);
				pstm.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e);
			} finally {
				try {
					pstm.close();
				} catch (SQLException e) {
					System.out.println(e);
				}
			}
			try {
				pstm = conn.prepareStatement(orderList_sql);
				pstm.setString(1, userId);
				pstm.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e);
			} finally {
				try {
					pstm.close();
				} catch (SQLException e) {
					System.out.println(e);
				}
			}
			try {
				pstm = conn.prepareStatement(user_sql);
				pstm.setString(1, userId);
				pstm.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e);
			} finally {
				try {
					pstm.close();
				} catch (SQLException e) {
					System.out.println(e);
				}
			}
		}
		return true;
	}
}