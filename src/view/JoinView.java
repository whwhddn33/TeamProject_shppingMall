package view;

import java.util.Scanner;

import dao.DBConnection;
import dao.UserDAO;
import dto.UserDTO;

public class JoinView {
	public JoinView() {
		Scanner sc = new Scanner(System.in);
		UserDAO udao = new UserDAO();

		System.out.print("���̵� : ");
		String userId = sc.next();
		if (!udao.checkDup(userId)) { // ���̵� �Է°� ���ÿ� �ߺ� ���̵�˻縦 �����մϴ�.
			System.out.println("�ߺ��� ���̵� �ֽ��ϴ�. �ٽ� �õ��� �ּ���.");
			new Index();
		} else {
			System.out.print("��й�ȣ(8�ڸ� �̻�) : ");
			String userPw = sc.next();
			System.out.print("�̸� : ");
			String userName = sc.next();
			System.out.print("�ڵ�����ȣ : ");
			String userPhone = sc.next();
			System.out.print("�ּ� : ");
			sc.nextLine();
			String userAddr = sc.nextLine();
			UserDTO newUser = new UserDTO(userId, userPw, userName, userPhone, userAddr);

			int result = udao.join(newUser); // �Է��� ������ ȸ������ �޼ҵ忡 VO���·� �������ݴϴ�.
			if (result == -1) {
				System.out.println("��й�ȣ�� �ٽ� �Է����ּ���.");
			} else if (result == 0) {
				System.out.println("ȸ������ ���� �߻�!");
			} else {
				System.out.println("ȸ������ ����!");
				System.out.println(userId + "�� ������ ȯ���մϴ�.");
			}
		}

	}
}
