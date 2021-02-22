package view;

import java.util.Scanner;

import dao.ProdDAO;
import dao.Session;
import dao.UserDAO;
import dto.BasketDTO;
import dto.ProductDTO;

public class MyInfoView {
	public MyInfoView() {
		Scanner sc = new Scanner(System.in);
		UserDAO udao = new UserDAO();
		ProdDAO pdao = new ProdDAO();
		BasketDTO bto=new BasketDTO();
		ProductDTO pto=new ProductDTO();
		Session session = new Session();

		while (true) {
			if (Session.get("session_id") != null) {
				System.out.println(
						"1. 충전금액 보기\n2. 충전하기 \n3. 비밀번호 수정하기 \n4. 주소 수정하기 \n5. 주문내역 보기 \n6. 장바구니 보기\n7. 회원탈퇴\n8. 메인으로 돌아가기");
				int choice = sc.nextInt();
				if (choice == 8) {
					break;
				}
				switch (choice) {
				case 1:
					int checkPoint = udao.check(Session.get("session_id"));
					System.out.println("현재 보유 잔액 : " + checkPoint + "원");
					break;
				case 2:
					System.out.print("충전 금액 : ");
					int chargePoint = sc.nextInt();
					if (udao.charge(chargePoint, Session.get("session_id")) != 0) {
						System.out.println("충전을 완료하였습니다.");
					} else {
						System.out.println("실패. 다시 시도하세요.");
					}
					break;
				case 3:
					System.out.print("새로운 비밀번호를 입력하세요 : ");
					String newPw = sc.next();
					udao.modify(0, udao.encrypt(newPw), Session.get("session_id"));
					System.out.println("수정을 완료하였습니다.");
					break;
				case 4:
					System.out.print("새로운 주소를 입력하세요 : ");
					String newAddr = sc.next();
					udao.modify(1, newAddr, Session.get("session_id"));
					System.out.println("수정을 완료하였습니다.");
					break;
				case 5:
					pdao.showOrderList(Session.get("session_id"));
					break;
					
				case 6:// 장바구니목록보기, 주문
					int basketListProdidx = 0;
					for (BasketDTO i : pdao.showBasketList(Session.get("session_id"))) {
						System.out.println(i);
					}
					System.out.println("1.장바구니에서 주문하기, 2.나가기");
					int choiceOrderOrBreak = sc.nextInt();
					while(true) {
						if (choiceOrderOrBreak==1) {
							System.out.println("주문할 장바구니번호를 입력해주세요.");
							int choiceBasketlist = sc.nextInt();
							for (BasketDTO i : pdao.showBasketList(Session.get("session_id"))) {
								if (i.getBasketIdx() == choiceBasketlist) {
									basketListProdidx = i.getProdIdx();
									break;
								}
							}
							pdao.order(Session.get("session_id"), basketListProdidx);
							pdao.delBasketList(choiceBasketlist);
							break;
						}else if (choiceOrderOrBreak==2) {
							break;
						}else {
							System.out.println("입력이 잘못되었습니다.");
						}
					}
					break;
				case 7:
					System.out.println("정말 탈퇴하시겠습니까? \n1. 네\n2. 아니요");
					int finalChoice = sc.nextInt();

					switch (finalChoice) {
					case 1:
						System.out.print("비밀번호를 입력하세요 : ");
						String checkPw = sc.next();
						if (udao.leaveId(checkPw, Session.get("session_id"))) {
							Session.put("session_id", null);
							System.out.println("이용해 주셔서 감사합니다.");
						} else {
							System.out.println("비밀번호가 잘못되었습니다. 다시 시도해주세요.");
						}
						break;
					case 2:
						break;
					}
				}
			} else {
				break;
			}
		}
	}
}
