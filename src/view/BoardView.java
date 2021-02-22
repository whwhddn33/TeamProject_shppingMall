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
			System.out.println("=== 게시판 ===");
			bdao.boardAllList();
			System.out.println("==========");
			System.out.println("1. 게시글 작성하기 \n2. 내가 올린 글(수정,삭제) \n3. 게시글 자세히 보기 \n4. 나가기");
			int choice = sc.nextInt();
			if (choice == 4) {
				break;
			}
			if (Session.get("session_id") != null) {
				switch (choice) {
				case 1:
					System.out.print("제목 : ");
					String title = sc.next();
					System.out.print("내용 : ");
					sc.nextLine();
					String content = sc.nextLine();
					BoardDTO newBoard = new BoardDTO(Session.get("session_id"), title, content);
					int result = bdao.boardWrite(newBoard);
					if (result == 0) {
						System.out.println("실패");
					} else {
						System.out.println("게시글 작성을 완료하였습니다.");
					}
					break;
				case 2:
					bdao.myBoardList();
					System.out.println("게시글을 선택하여 [수정하기/삭제하기] 기능을 이용하세요!");
					System.out.print("게시글 선택(나가기0) : ");
					int boardChoice = sc.nextInt();
					if (boardChoice == 0) {
						break;
					}
					System.out.print("1.수정하기 2.삭제하기");
					int actChoice = sc.nextInt();
					if(actChoice == 1) {
						System.out.print("수정할 항목을 선택해주세요 [1.제목 2.콘텐츠]");
						int modiAns = sc.nextInt();
						System.out.println("수정할 내용을 입력해주세요 : ");
						sc.nextLine();
						String newData = sc.nextLine();		
						bdao.boardModify(boardChoice, modiAns, newData);
					}else if(actChoice == 2) {
						System.out.println("정말 삭제하시겠습니까?[Y/N]");
						String delAns = sc.next();
						if(delAns.equals("Y")) {
							bdao.boardDel(boardChoice);
						}
					}
					break;
				case 3:
					System.out.println("내용을 확인 할 게시글을 선택해주세요.");
					int readChoice = sc.nextInt();
					bdao.readBoard(readChoice);
					break;
				}
			} else {
				System.out.println("로그인을 해주세요.");
			}
		}
	}
}
