package view;

import java.util.Scanner;

import dao.BoardDAO;
import dao.Session;
import dto.BoardDTO;

public class BoardView {
	Scanner sc = new Scanner(System.in);
	BoardDAO bdao = new BoardDAO();

	public BoardView() {
		while (true) {
			System.out.println("=== �Խ��� ===");
			bdao.boardAllList();
			System.out.println("==========");
			System.out.println("1. �Խñ� �ۼ��ϱ� \n2. ���� �ø� ��(����,����) \n3. �Խñ� �ڼ��� ���� \n4. ������");
			int choice = sc.nextInt();
			if (choice == 4) {
				break;
			}
			if (Session.get("session_id") != null) {
				switch (choice) {
				case 1:
					System.out.print("���� : ");
					String title = sc.next();
					System.out.print("���� : ");
					sc.nextLine();
					String content = sc.nextLine();
					BoardDTO newBoard = new BoardDTO(Session.get("session_id"), title, content);
					int result = bdao.boardWrite(newBoard);
					if (result == 0) {
						System.out.println("����");
					} else {
						System.out.println("�Խñ� �ۼ��� �Ϸ��Ͽ����ϴ�.");
					}
					break;
				case 2:
					bdao.myBoardList();
					System.out.println("�Խñ��� �����Ͽ� [�����ϱ�/�����ϱ�] ����� �̿��ϼ���!");
					System.out.print("�Խñ� ����(������0) : ");
					int boardChoice = sc.nextInt();
					if (boardChoice == 0) {
						break;
					}
					System.out.print("1.�����ϱ� 2.�����ϱ�");
					int actChoice = sc.nextInt();
					if(actChoice == 1) {
						System.out.print("������ �׸��� �������ּ��� [1.���� 2.������]");
						int modiAns = sc.nextInt();
						System.out.println("������ ������ �Է����ּ��� : ");
						sc.nextLine();
						String newData = sc.nextLine();		
						bdao.boardModify(boardChoice, modiAns, newData);
					}else if(actChoice == 2) {
						System.out.println("���� �����Ͻðڽ��ϱ�?[Y/N]");
						String delAns = sc.next();
						if(delAns.equals("Y")) {
							bdao.boardDel(boardChoice);
						}
					}
					break;
				case 3:
					System.out.println("������ Ȯ�� �� �Խñ��� �������ּ���.");
					int readChoice = sc.nextInt();
					bdao.readBoard(readChoice);
					break;
				}
			} else {
				System.out.println("�α����� ���ּ���.");
			}
		}
	}
}
