package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	// DB에 연결하기 위한 정보들(인터페이스,클래스,계정정보)
	public static Connection conn = null;
	public static String driver = "oracle.jdbc.driver.OracleDriver";
	public static String url = "jdbc:oracle:thin:@localhost:1521:xe";
	public static String user = "web";
	public static String password = "web";

	// 기본생성자
	public DBConnection() {
	}

	// 실질적으로 DB연결을 실행하는 메소드
	public static Connection getConnection() {
		if (conn == null) {
			try {
				Class.forName(driver); // driver란 이름(변수)로 클래스를 불러오는것
				System.out.println("jdbc driver 로딩 성공");
				conn = DriverManager.getConnection(url, user, password);
				System.out.println("오라클 연결 성공");
			} catch (ClassNotFoundException e) {
				System.out.println("jdbc driver 로딩 실패");
			} catch (SQLException sqle) {
				System.out.println("오라클 연결 실패");
			}
		}
		return conn;
	}
}
