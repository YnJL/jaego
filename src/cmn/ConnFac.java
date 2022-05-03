package cmn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnFac {
	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			System.err.println("exception 1. Oracle Driver Missing!");
		}
		
		try {
			String url = "jdbc:oracle:thin:@localhost:1521";
			String user = "ccc";
			String pswd = "java";
			conn = DriverManager.getConnection(url, user, pswd);
		} catch (SQLException e) {
			System.err.println("exception 2. url/user/password error!");
		}
		return conn;
	}
}