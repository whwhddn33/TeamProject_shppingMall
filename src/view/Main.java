package view;

import java.util.Scanner;

import dao.Session;
import dto.UserDTO;

public class Main {
	public Main() {
		Scanner sc = new Scanner(System.in);
		while (true) {
			if (Session.get("session_id") != null) {

				System.out.println("=== 1석2조 쇼핑몰(Main) ===");
				System.out.println("1. 상품보기 \n2. 내 정보  \n3. 게시판 \n4. 로그아웃");
				int choice = sc.nextInt();
				if (choice == 4) {
					Session.put("session_id", null);
					System.out.println("로그아웃이 완료되었습니다.");
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
