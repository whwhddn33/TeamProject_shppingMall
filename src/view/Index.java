package view;

import java.util.Scanner;

public class Index {
	public static void main(String[] args) {
		while (true) {
			Scanner sc = new Scanner(
					System.in);

			System.out.println("=== �ϼ�2�� ���θ� ===");
			System.out.println("1. ȸ������ \n2. �α��� \n3. ��ǰ���� \n4. �Խ��� \n5. ������");
			int choice = sc.nextInt();

			if (choice == 5) {
				System.out.println("�ȳ��� ������.");
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