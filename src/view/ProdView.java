package view;

import java.util.ArrayList;
import java.util.Scanner;

import dao.ProdDAO;
import dao.Session;
import dto.BasketDTO;
import dto.ProductDTO;
import dto.UserDTO;

public class ProdView {
	ProdDAO pdao = new ProdDAO();
	ProductDTO pdto = new ProductDTO();
	Scanner sc = new Scanner(System.in);

	public ProdView() {
		String prodList = pdao.prodList();// ��ǰ�������� ��� ������ prodList������ �Ű� ����ϴ�.
		if (prodList != null) {
			System.out.println(prodList);
			System.out.println("��ǰ�� �����Ͽ� [�ֹ��ϱ�/��ٱ���/���ƿ�] ����� �̿��ϼ���! (���ư���:4) ");
			int selectProdNum = sc.nextInt();
			if (selectProdNum == 4) {
				;
			} else {
				if (Session.get("session_id") != null) {
					System.out.println("1. �ֹ��ϱ� \n2. ��ٱ��� \n3. ���ƿ�");
					int choice = sc.nextInt();
					switch (choice) {
					case 1:
						//�ֹ��ϱ� ����
						if (!pdao.checkProdAmount(selectProdNum)) {
							// �ֹ��� ���� ���θ� �����ϱ� ���� �ش� ��ǰ�� ������ȣ(��ǰ��ȣ)�� �޼ҵ忡 �Ѱ��ݴϴ�.
							pdao.order(Session.get("session_id"), selectProdNum);
						} else {
							System.out.println("��� ������ �����ϴ�.");
						}
						break;
					case 2:
						//��ٱ��� ����
						pdao.addBasket(Session.get("session_id"), selectProdNum);
						break;
					case 3:
						//���ƿ� ����
						int likeResult = pdao.likeAdd(selectProdNum);
						if (likeResult != 0) {
							System.out.println("���ƿ� +1");
						} else {
							System.out.println("����. �ٽ� �õ��ϼ���");
						}
						break;
					}
				} else {
					System.out.println("�α����� ���ּ���.");
				}
			}
		}
	}

}
