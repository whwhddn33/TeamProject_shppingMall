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
		String prodList = pdao.prodList();// 상품정보들을 모두 가져와 prodList변수에 옮겨 담습니다.
		if (prodList != null) {
			System.out.println(prodList);
			System.out.println("상품을 선택하여 [주문하기/장바구니/좋아요] 기능을 이용하세요! (돌아가기:4) ");
			int selectProdNum = sc.nextInt();
			if (selectProdNum == 4) {
				;
			} else {
				if (Session.get("session_id") != null) {
					System.out.println("1. 주문하기 \n2. 장바구니 \n3. 좋아요");
					int choice = sc.nextInt();
					switch (choice) {
					case 1:
						//주문하기 영역
						if (!pdao.checkProdAmount(selectProdNum)) {
							// 주문을 가능 여부를 점검하기 위해 해당 제품의 고유번호(상품번호)를 메소드에 넘겨줍니다.
							pdao.order(Session.get("session_id"), selectProdNum);
						} else {
							System.out.println("재고 수량이 없습니다.");
						}
						break;
					case 2:
						//장바구니 영역
						pdao.addBasket(Session.get("session_id"), selectProdNum);
						break;
					case 3:
						//좋아요 영역
						int likeResult = pdao.likeAdd(selectProdNum);
						if (likeResult != 0) {
							System.out.println("좋아요 +1");
						} else {
							System.out.println("실패. 다시 시도하세요");
						}
						break;
					}
				} else {
					System.out.println("로그인을 해주세요.");
				}
			}
		}
	}

}