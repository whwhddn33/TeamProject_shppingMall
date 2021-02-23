package view;

import java.util.Scanner;

import dao.DBConnection;
import dao.UserDAO;
import dto.UserDTO;

public class JoinView {
	public JoinView() {
		Scanner sc = new Scanner(System.in);
		UserDAO udao = new UserDAO();

		System.out.print("아이디 : ");
		String userId = sc.next();
		if (!udao.checkDup(userId)) { // 아이디 입력과 동시에 중복 아이디검사를 진행합니다.
			System.out.println("중복된 아이디가 있습니다. 다시 시도해 주세요.");
			new Index();
		} else {
			System.out.print("비밀번호(8자리 이상) : ");
			String userPw = sc.next();
			System.out.print("이름 : ");
			String userName = sc.next();
			System.out.print("핸드폰번호 : ");
			String userPhone = sc.next();
			System.out.print("주소 : ");
			sc.nextLine();
			String userAddr = sc.nextLine();
			UserDTO newUser = new UserDTO(userId, userPw, userName, userPhone, userAddr);

			int result = udao.join(newUser); // 입력한 정보를 회원가입 메소드에 VO형태로 전달해줍니다.
			if (result == -1) {
				System.out.println("비밀번호를 다시 입력해주세요.");
			} else if (result == 0) {
				System.out.println("회원가입 오류 발생!");
			} else {
				System.out.println("회원가입 성공!");
				System.out.println(userId + "님 가입을 환영합니다.");
			}
		}

	}
}