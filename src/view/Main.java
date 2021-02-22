package view;

import java.util.Scanner;

import dao.Session;
import dto.UserDTO;

public class Main {
	public Main() {
		Scanner sc = new Scanner(System.in);
		while (true) {
			if (Session.get("session_id") != null) {

				System.out.println("=== 1��2�� ���θ�(Main) ===");
				System.out.println("1. ��ǰ���� \n2. �� ����  \n3. �Խ��� \n4. �α׾ƿ�");
				int choice = sc.nextInt();
				if (choice == 4) {
					Session.put("session_id", null);
					System.out.println("�α׾ƿ��� �Ϸ�Ǿ����ϴ�.");
					break;
				}

				switch (choice) {
				case 1:
					new ProdView();
					break;
				case 2:
					new MyInfoView();
					break;
				case 3:
					new BoardView();
					break;
				}
			} else {
				break;
			}
		}
	}
}
