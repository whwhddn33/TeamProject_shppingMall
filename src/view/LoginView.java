package view;

import java.util.Scanner;

import dao.Session;
import dao.UserDAO;
import dto.UserDTO;

public class LoginView {
	public LoginView() {
		Scanner sc = new Scanner(System.in);
		UserDAO udao = new UserDAO();

		System.out.print("아이디 : ");
		String userId = sc.next();
		System.out.print("비밀번호 : ");
		String userPw = sc.next();

		UserDTO session = udao.login(userId, userPw); // 로그인하려는 회원정보를 session이라는 변수에 옮겨담슴니다.
		if (session == null) {
			System.out.println("로그인 실패");
		} else {
			Session.put("session_id", session.getUserId());
			new Main();
		}
	}
}
