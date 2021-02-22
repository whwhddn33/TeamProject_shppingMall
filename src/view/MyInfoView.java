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
						"1. �����ݾ� ����\n2. �����ϱ� \n3. ��й�ȣ �����ϱ� \n4. �ּ� �����ϱ� \n5. �ֹ����� ���� \n6. ��ٱ��� ����\n7. ȸ��Ż��\n8. �������� ���ư���");
				int choice = sc.nextInt();
				if (choice == 8) {
					break;
				}
				switch (choice) {
				case 1:
					int checkPoint = udao.check(Session.get("session_id"));
					System.out.println("���� ���� �ܾ� : " + checkPoint + "��");
					break;
				case 2:
					System.out.print("���� �ݾ� : ");
					int chargePoint = sc.nextInt();
					if (udao.charge(chargePoint, Session.get("session_id")) != 0) {
						System.out.println("������ �Ϸ��Ͽ����ϴ�.");
					} else {
						System.out.println("����. �ٽ� �õ��ϼ���.");
					}
					break;
				case 3:
					System.out.print("���ο� ��й�ȣ�� �Է��ϼ��� : ");
					String newPw = sc.next();
					udao.modify(0, udao.encrypt(newPw), Session.get("session_id"));
					System.out.println("������ �Ϸ��Ͽ����ϴ�.");
					break;
				case 4:
					System.out.print("���ο� �ּҸ� �Է��ϼ��� : ");
					String newAddr = sc.next();
					udao.modify(1, newAddr, Session.get("session_id"));
					System.out.println("������ �Ϸ��Ͽ����ϴ�.");
					break;
				case 5:
					pdao.showOrderList(Session.get("session_id"));
					break;
					
				case 6:// ��ٱ��ϸ�Ϻ���, �ֹ�
					int basketListProdidx = 0;
					for (BasketDTO i : pdao.showBasketList(Session.get("session_id"))) {
						System.out.println(i);
					}
					System.out.println("1.��ٱ��Ͽ��� �ֹ��ϱ�, 2.������");
					int choiceOrderOrBreak = sc.nextInt();
					while(true) {
						if (choiceOrderOrBreak==1) {
							System.out.println("�ֹ��� ��ٱ��Ϲ�ȣ�� �Է����ּ���.");
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
							System.out.println("�Է��� �߸��Ǿ����ϴ�.");
						}
					}
					break;
				case 7:
					System.out.println("���� Ż���Ͻðڽ��ϱ�? \n1. ��\n2. �ƴϿ�");
					int finalChoice = sc.nextInt();

					switch (finalChoice) {
					case 1:
						System.out.print("��й�ȣ�� �Է��ϼ��� : ");
						String checkPw = sc.next();
						if (udao.leaveId(checkPw, Session.get("session_id"))) {
							Session.put("session_id", null);
							System.out.println("�̿��� �ּż� �����մϴ�.");
						} else {
							System.out.println("��й�ȣ�� �߸��Ǿ����ϴ�. �ٽ� �õ����ּ���.");
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
