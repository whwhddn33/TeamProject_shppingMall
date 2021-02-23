package view;

import java.util.Scanner;

public class Index {
	public static void main(String[] args) {
		while (true) {
			Scanner sc = new Scanner(
					System.in);

			System.out.println("=== 일석2조 쇼핑몰 ===");
			System.out.println("1. 회원가입 \n2. 로그인 \n3. 상품보기 \n4. 게시판 \n5. 나가기");
			int choice = sc.nextInt();

			if (choice == 5) {
				System.out.println("안녕히 가세요.");
				break;
			}
			switch (choice) {
			case 1:
				new JoinView();
				break;
			case 2:
				new LoginView();
				break;
			case 3:
				new ProdView();
				break;
			case 4:
				new BoardView();
				break;
			}
		}
	}
}