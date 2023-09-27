package app.dbconn;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConn {

	private String url = "jdbc:mysql://localhost:3306/sys?serverTimezone=UTC&useSSL=false";
	private String user = "root";
	private String password = "rwdwe5519";

	public Connection getConnection() {
		Connection conn= null;
		
		try {
			Class clz = Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return conn;
	}
}
