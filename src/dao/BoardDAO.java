package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import dto.BoardDTO;

public class BoardDAO {
	Connection conn = null;
	PreparedStatement pstm = null;
	ResultSet rs = null;

	public BoardDAO() {
		conn = DBConnection.getConnection();
	}

	// ----------------�Խ��� ��ü����
	public void boardAllList() {
		String allList_sql = "SELECT * FROM TBL_BOARD";
		try {
			pstm = conn.prepareStatement(allList_sql);
			rs = pstm.executeQuery();
			while (rs.next()) {
				System.out.println("��ȣ (" + rs.getInt(1) + ") / ���� : " + rs.getString(3) + " / �Խ��� " + rs.getDate(5));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstm.close();
			} catch (SQLException e) {
				System.out.println("�Խ��� ����Ʈ �ҷ����⿡ �����Ͽ����ϴ�");
			}
		}
	}

	// ----------------�Խñ� �ۼ��ϱ�
	public int boardWrite(BoardDTO newBoard) {
		String write_sql = "INSERT INTO TBL_BOARD(BOARDIDX,USERID,BOARDTITLE,BOARDCONTENTS)\r\n"
				+ "VALUES(SEQ_BOARD.NEXTVAL,?,?,?)";
		int check = 0;
		try {
			pstm = conn.prepareStatement(write_sql);
			pstm.setString(1, newBoard.getUserId());
			pstm.setString(2, newBoard.getTitle());
			pstm.setString(3, newBoard.getContent());
			check = pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstm.close();
			} catch (SQLException e) {
				System.out.println("���� �߻�");
			}
		}
		return check;
	}

	// ----------------���� �ø� �� Ȯ���ϱ�
	public void myBoardList() {
		String myList_sql = "SELECT * FROM TBL_BOARD WHERE USERID = ?";
		try {
			pstm = conn.prepareStatement(myList_sql);
			pstm.setString(1, Session.get("session_id"));
			rs = pstm.executeQuery();

			while (rs.next()) {
				System.out.println("��ȣ (" + rs.getInt(1) + ") / ���� : " + rs.getString(3) + " / �Խ��� " + rs.getDate(5));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstm.close();
			} catch (SQLException e) {
				System.out.println("���� �߻�");
			}
		}

	}

	// ----------------�� �Խñ� �����ϱ�
	public void boardModify(int boardChoice, int modiAns, String newData) {
		String modify_sql;

		if (modiAns == 1) {
			modify_sql = "UPDATE TBL_BOARD SET BOARDTITLE=? WHERE BOARDIDX = ?";
		} else if (modiAns == 2) {
			modify_sql = "UPDATE TBL_BOARD SET BOARDCONTENTS=? WHERE BOARDIDX = ?";
		} else {
			System.out.println("�߸� �Է��ϼ̽��ϴ�");
			return;
		}
		try {
			pstm = conn.prepareStatement(modify_sql);
			pstm.setString(1, newData);
			pstm.setInt(2, boardChoice);
			pstm.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			try {
				pstm.close();
			} catch (SQLException e) {
				System.out.println("�� �� ���� ����");
			}
		}

	}

	// ----------------�� �Խñ� �����ϱ�
	public void boardDel(int boardChoice) {
		String del_sql;
		del_sql = "DELETE FROM TBL_BOARD WHERE BOARDIDX = ?";
		try {
			pstm = conn.prepareStatement(del_sql);
			pstm.setInt(1, boardChoice);
			pstm.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			try {
				pstm.close();
			} catch (SQLException e) {
				System.out.println("�� �� ���� ����");
			}
		}

	}

	// ----------------�� �Խñ� �ڼ�������
	public void readBoard(int readChoice) {
		String read_sql = "SELECT * FROM TBL_BOARD WHERE BOARDIDX = ?";
		try {
			pstm = conn.prepareStatement(read_sql);
			pstm.setInt(1, readChoice);
			rs = pstm.executeQuery();
			if (rs.next()) {
				System.out.println("��ȣ (" + rs.getInt(1) + ") / ���� : " + rs.getString(3) + "���� : " + rs.getString(3)
						+ " / �Խ��� " + rs.getDate(5));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstm.close();
			} catch (SQLException e) {
				System.out.println("�Խ��� ����Ʈ �ҷ����⿡ �����Ͽ����ϴ�");
			}
		}
	}
	
}
