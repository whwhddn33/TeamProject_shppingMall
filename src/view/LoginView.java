package view;

import java.util.Scanner;

import dao.Session;
import dao.UserDAO;
import dto.UserDTO;

public class LoginView {
	public LoginView() {
		Scanner sc = new Scanner(System.in);
		UserDAO udao = new UserDAO();

		System.out.print("���̵� : ");
		String userId = sc.next();
		System.out.print("��й�ȣ : ");
		String userPw = sc.next();

		UserDTO session = udao.login(userId, userPw); // �α����Ϸ��� ȸ�������� session�̶�� ������ �Űܴ㽿�ϴ�.
		if (session == null) {
			System.out.println("�α��� ����");
		} else {
			Session.put("session_id", session.getUserId());
			new Main();
		}
	}
}
