package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
//import java.util.concurrent.SynchronousQueue;
//
//import dto.UserDTO;
//import view.ProdView;

import dto.BasketDTO;

public class ProdDAO {
	Connection conn = null;
	PreparedStatement pstm = null;
	ResultSet rs = null;

	public ProdDAO() {
		conn = DBConnection.getConnection();
	}

	// -------------------------상품목록보기---------------------------
	public String prodList() {
		String sql = "SELECT * FROM TBL_PRODUCT ORDER BY PRODIDX"; // 상품목록을 고유번호 순으로 얻어오는 쿼리문입니다.
		String result = "";
		try {
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery(); // 불러올 상품목록이 있다면 1이 부여됩니다.
			while (rs.next()) { // rs가 1이라면 변수result에 상품 정보를 아래형식에 맞추어 담슴니다.
				result += "\n" + rs.getInt(1) + " - 상품명 : " + rs.getString(3) + " / 상품설명 : " + rs.getString(4)
						+ " / 가격 : " + rs.getInt(5) + "원 / " + rs.getInt(6) + "좋아요 ";
			}
		} catch (SQLException e) {
			System.out.println("알 수 없는 오류.prodlist");
			e.printStackTrace();
		} finally {
			try {
				pstm.close();
				rs.close();
			} catch (SQLException e) {
				System.out.println("알 수 없는 오류.prodpstm,rs");
				e.printStackTrace();
			}
		}
		return result;
	}

	// -------------------------재고 확인(상품 주문 가능여부)---------------------------
	public boolean checkProdAmount(int selectProdNum) {
		String sql = "SELECT PRODAMOUNT FROM TBL_PRODUCT WHERE PRODIDX=? ";
		int checkAmount = 0;

		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, String.valueOf(selectProdNum));
			rs = pstm.executeQuery();

			if (rs.next()) {
				checkAmount = rs.getInt(1);
			} else {
				System.out.println("상품 번호를 다시 입력하세요"); // 보통 gui형태의 view에서 받는 값이다. 하지만 콘솔환경이므로 여기서 처리!
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			rs.close();
			pstm.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return checkAmount == 0;
	}

	// -------------------------주문하기---------------------------
	public void order(String userId, int selectProdNum) {
		String user_sql = "SELECT USERPOINT FROM TBL_MEMBER WHERE USERID = ?";
		String prod_sql = "SELECT PRODPRICE, PRODAMOUNT FROM TBL_PRODUCT WHERE PRODIDX = ?";
		String updateUserPoint_sql = "UPDATE TBL_MEMBER SET USERPOINT = ? WHERE USERID = ?";
		String updateProdAmount_sql = "UPDATE TBL_PRODUCT SET PRODAMOUNT = ? WHERE PRODIDX = ?";
		String addOrderList_sql = "INSERT INTO TBL_ORDERLIST VALUES(SEQ_ORDER.NEXTVAL,?,?,?)";
		String result = randomstats();
		int userPoint = 0;
		int prodPrice = 0;
		int prodAmount = 0;
		System.out.println("★ " + selectProdNum + "번 상품 선택 ★");

		try {
			// ----------로그인 유저의 잔액 조회하기
			pstm = conn.prepareStatement(user_sql);
			pstm.setString(1, userId);
			rs = pstm.executeQuery();
			if (rs.next()) {
				userPoint = rs.getInt(1);
			}
			System.out.println("☞  내 포인트 : " + userPoint);
			// ----------선택한 상품의 가격과 수량 조회하기
			pstm = conn.prepareStatement(prod_sql);
			pstm.setInt(1, Integer.valueOf(selectProdNum));
			rs = pstm.executeQuery();
			if (rs.next()) {
				prodPrice = rs.getInt(1);
				System.out.println("☞  상품가격 : " + prodPrice + "원");
				prodAmount = rs.getInt(2);
				// -----------유저의 잔액이 상품금액보다 많다면 주문가능(유저포인트와 상품수량 수정)
				if (userPoint >= prodPrice) {
					userPoint -= prodPrice;
					pstm = conn.prepareStatement(updateUserPoint_sql);
					pstm.setInt(1, userPoint);
					pstm.setString(2, userId);
					pstm.executeUpdate();

					prodAmount--;
					pstm = conn.prepareStatement(updateProdAmount_sql);
					pstm.setInt(1, prodAmount);
					pstm.setInt(2, selectProdNum);
					pstm.executeUpdate();

					System.out.println("♥♥♥ 주문을 완료하였습니다. ♥♥♥");
					System.out.println("내 잔여 포인트 : " + userPoint + "원");
					// 주문 가능시 주문 목록에도 해당 사용자의 이름으로 주문 정보 추가
					try {
						pstm = conn.prepareStatement(addOrderList_sql);
						pstm.setString(1, userId);
						pstm.setInt(2, selectProdNum);
						pstm.setString(3, result);
						pstm.executeUpdate();
						System.out.println("주문완료되었습니다.");
					} catch (SQLException e) {
						System.out.println(e);
					} finally {
						try {
							pstm.close();
						} catch (SQLException e) {
							System.out.println(e);
						}
					}
				} else {
					System.out.println("잔액이 부족합니다. 충전 후 이용해주세요.");
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			rs.close();
			pstm.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String randomstats() {
		Random r = new Random();
		int num = r.nextInt(2) + 1;
		if (num == 1)
			return "상품준비중";
		else if (num == 2)
			return "배송중";
		else
			return "배송완료";
	}
	
	// -------------------------주문 목록 보여주기---------------------------	
	public void showOrderList(String userId) {
		String sql = "SELECT * FROM TBL_ORDERLIST where userid = ?";
		
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, userId);
			rs = pstm.executeQuery();
			while (rs.next()) {
				System.out.println("주문번호 : "+rs.getInt(1)+" / 상품번호 : "+rs.getInt(3)+" / 배송상태 : "+rs.getString(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstm.close();
			} catch (SQLException e) {
				System.out.println("주문 리스트 불러오기에 실패하였습니다");
			}
		}
	}

	// -------------------------장바구니---------------------------
	public void addBasket(String userid, int selectProdNum) {
		String basket_sql = "INSERT INTO TBL_BASKET VALUES(SEQ_BASKET.NEXTVAL,?,?)";
		
		try {
			pstm=conn.prepareStatement(basket_sql);
			pstm.setString(1,userid);
			pstm.setInt(2, selectProdNum);
			rs = pstm.executeQuery();
			
			System.out.println(selectProdNum+"번 상품을 장바구니에 담았습니다.");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				pstm.close();
			} catch (SQLException e) {
				System.out.println("상품을 장바구니에 추가하지 못했습니다. 다시 시도해주세요.");
			}
		}
		
	}
	//-------------------------장바구니 보기---------------------------
	public ArrayList<BasketDTO> showBasketList(String userid) {
		String showBasketList_sql = "SELECT b.*,p.PRODNAME, p.prodprice FROM TBL_BASKET b "
				+ "INNER JOIN TBL_PRODUCT p ON b.PRODIDX = p.PRODIDX WHERE b.USERID=? ORDER BY BASKETIDX";
		ArrayList<BasketDTO> basketlist = new ArrayList<>();
			try {
				pstm=conn.prepareStatement(showBasketList_sql);
				pstm.setString(1,userid);
				rs = pstm.executeQuery();
				System.out.println("-------"+userid+"님의 장바구니 목록-------");
				while (rs.next()) {
					basketlist.add(new BasketDTO(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getString(4),rs.getInt(5)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("장바구니 조회에 실패했습니다.");
			}finally {
				try {
					rs.close();
					pstm.close();
				} catch (SQLException e) {
					System.out.println("목록을 불러오지 못하였습니다.");
				}
			}
			return basketlist;
	}
	//-------------------------장바구니 삭제---------------------------
	public void delBasketList(int choiceBasketlist) {
		String delBasketList_sql = "DELETE FROM tbl_basket WHERE basketidx = ?";
		try {
			pstm = conn.prepareStatement(delBasketList_sql);
			pstm.setInt(1, choiceBasketlist);
			pstm.executeUpdate();
		} catch (SQLException e) {
			System.out.println("delBasketList : 쿼리전송 실패");
		} finally {
			try {
				pstm.close();
			} catch (SQLException e) {
				System.out.println("delBasketList : pstm 닫기 오류");
			}
		}
	}
	// -------------------------좋아요---------------------------
	public int likeAdd(int prodIdx) {
		// 해당 상품 정보의 prodLike를 1씩 추가하여 업데이트합니다.
		String sql = "UPDATE TBL_PRODUCT SET PRODLIKE=PRODLIKE+1 WHERE PRODIDX=?";
		int result = 0;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, prodIdx);
			result = pstm.executeUpdate();

		} catch (SQLException e) {
			System.out.println("알 수 없는 오류.likeresult");
			e.printStackTrace();
		} finally {
			try {
				pstm.close();
			} catch (SQLException e) {
				System.out.println("알 수 없는 오류.likepstm");
				e.printStackTrace();
			}
		}
		return result;
	}

}