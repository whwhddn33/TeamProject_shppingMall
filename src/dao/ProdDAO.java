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

	// -------------------------��ǰ��Ϻ���---------------------------
	public String prodList() {
		String sql = "SELECT * FROM TBL_PRODUCT ORDER BY PRODIDX"; // ��ǰ����� ������ȣ ������ ������ �������Դϴ�.
		String result = "";
		try {
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery(); // �ҷ��� ��ǰ����� �ִٸ� 1�� �ο��˴ϴ�.
			while (rs.next()) { // rs�� 1�̶�� ����result�� ��ǰ ������ �Ʒ����Ŀ� ���߾� �㽿�ϴ�.
				result += "\n" + rs.getInt(1) + " - ��ǰ�� : " + rs.getString(3) + " / ��ǰ���� : " + rs.getString(4)
						+ " / ���� : " + rs.getInt(5) + "�� / " + rs.getInt(6) + "���ƿ� ";
			}
		} catch (SQLException e) {
			System.out.println("�� �� ���� ����.prodlist");
			e.printStackTrace();
		} finally {
			try {
				pstm.close();
				rs.close();
			} catch (SQLException e) {
				System.out.println("�� �� ���� ����.prodpstm,rs");
				e.printStackTrace();
			}
		}
		return result;
	}

	// -------------------------��� Ȯ��(��ǰ �ֹ� ���ɿ���)---------------------------
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
				System.out.println("��ǰ ��ȣ�� �ٽ� �Է��ϼ���"); // ���� gui������ view���� �޴� ���̴�. ������ �ܼ�ȯ���̹Ƿ� ���⼭ ó��!
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

	// -------------------------�ֹ��ϱ�---------------------------
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
		System.out.println("�� " + selectProdNum + "�� ��ǰ ���� ��");

		try {
			// ----------�α��� ������ �ܾ� ��ȸ�ϱ�
			pstm = conn.prepareStatement(user_sql);
			pstm.setString(1, userId);
			rs = pstm.executeQuery();
			if (rs.next()) {
				userPoint = rs.getInt(1);
			}
			System.out.println("��  �� ����Ʈ : " + userPoint);
			// ----------������ ��ǰ�� ���ݰ� ���� ��ȸ�ϱ�
			pstm = conn.prepareStatement(prod_sql);
			pstm.setInt(1, Integer.valueOf(selectProdNum));
			rs = pstm.executeQuery();
			if (rs.next()) {
				prodPrice = rs.getInt(1);
				System.out.println("��  ��ǰ���� : " + prodPrice + "��");
				prodAmount = rs.getInt(2);
				// -----------������ �ܾ��� ��ǰ�ݾ׺��� ���ٸ� �ֹ�����(��������Ʈ�� ��ǰ���� ����)
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

					System.out.println("������ �ֹ��� �Ϸ��Ͽ����ϴ�. ������");
					System.out.println("�� �ܿ� ����Ʈ : " + userPoint + "��");
					// �ֹ� ���ɽ� �ֹ� ��Ͽ��� �ش� ������� �̸����� �ֹ� ���� �߰�
					try {
						pstm = conn.prepareStatement(addOrderList_sql);
						pstm.setString(1, userId);
						pstm.setInt(2, selectProdNum);
						pstm.setString(3, result);
						pstm.executeUpdate();
						System.out.println("�ֹ��Ϸ�Ǿ����ϴ�.");
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
					System.out.println("�ܾ��� �����մϴ�. ���� �� �̿����ּ���.");
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
			return "��ǰ�غ���";
		else if (num == 2)
			return "�����";
		else
			return "��ۿϷ�";
	}
	
	// -------------------------�ֹ� ��� �����ֱ�---------------------------	
	public void showOrderList(String userId) {
		String sql = "SELECT * FROM TBL_ORDERLIST where userid = ?";
		
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, userId);
			rs = pstm.executeQuery();
			while (rs.next()) {
				System.out.println("�ֹ���ȣ : "+rs.getInt(1)+" / ��ǰ��ȣ : "+rs.getInt(3)+" / ��ۻ��� : "+rs.getString(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstm.close();
			} catch (SQLException e) {
				System.out.println("�ֹ� ����Ʈ �ҷ����⿡ �����Ͽ����ϴ�");
			}
		}
	}

	// -------------------------��ٱ���---------------------------
	public void addBasket(String userid, int selectProdNum) {
		String basket_sql = "INSERT INTO TBL_BASKET VALUES(SEQ_BASKET.NEXTVAL,?,?)";
		
		try {
			pstm=conn.prepareStatement(basket_sql);
			pstm.setString(1,userid);
			pstm.setInt(2, selectProdNum);
			rs = pstm.executeQuery();
			
			System.out.println(selectProdNum+"�� ��ǰ�� ��ٱ��Ͽ� ��ҽ��ϴ�.");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				pstm.close();
			} catch (SQLException e) {
				System.out.println("��ǰ�� ��ٱ��Ͽ� �߰����� ���߽��ϴ�. �ٽ� �õ����ּ���.");
			}
		}
		
	}
	//-------------------------��ٱ��� ����---------------------------
	public ArrayList<BasketDTO> showBasketList(String userid) {
		String showBasketList_sql = "SELECT b.*,p.PRODNAME, p.prodprice FROM TBL_BASKET b "
				+ "INNER JOIN TBL_PRODUCT p ON b.PRODIDX = p.PRODIDX WHERE b.USERID=? ORDER BY BASKETIDX";
		ArrayList<BasketDTO> basketlist = new ArrayList<>();
			try {
				pstm=conn.prepareStatement(showBasketList_sql);
				pstm.setString(1,userid);
				rs = pstm.executeQuery();
				System.out.println("-------"+userid+"���� ��ٱ��� ���-------");
				while (rs.next()) {
					basketlist.add(new BasketDTO(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getString(4),rs.getInt(5)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("��ٱ��� ��ȸ�� �����߽��ϴ�.");
			}finally {
				try {
					rs.close();
					pstm.close();
				} catch (SQLException e) {
					System.out.println("����� �ҷ����� ���Ͽ����ϴ�.");
				}
			}
			return basketlist;
	}
	//-------------------------��ٱ��� ����---------------------------
	public void delBasketList(int choiceBasketlist) {
		String delBasketList_sql = "DELETE FROM tbl_basket WHERE basketidx = ?";
		try {
			pstm = conn.prepareStatement(delBasketList_sql);
			pstm.setInt(1, choiceBasketlist);
			pstm.executeUpdate();
		} catch (SQLException e) {
			System.out.println("delBasketList : �������� ����");
		} finally {
			try {
				pstm.close();
			} catch (SQLException e) {
				System.out.println("delBasketList : pstm �ݱ� ����");
			}
		}
	}
	// -------------------------���ƿ�---------------------------
	public int likeAdd(int prodIdx) {
		// �ش� ��ǰ ������ prodLike�� 1�� �߰��Ͽ� ������Ʈ�մϴ�.
		String sql = "UPDATE TBL_PRODUCT SET PRODLIKE=PRODLIKE+1 WHERE PRODIDX=?";
		int result = 0;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, prodIdx);
			result = pstm.executeUpdate();

		} catch (SQLException e) {
			System.out.println("�� �� ���� ����.likeresult");
			e.printStackTrace();
		} finally {
			try {
				pstm.close();
			} catch (SQLException e) {
				System.out.println("�� �� ���� ����.likepstm");
				e.printStackTrace();
			}
		}
		return result;
	}

}
