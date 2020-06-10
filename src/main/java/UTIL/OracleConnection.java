package UTIL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Oracle连接类
 * @author lvcy
 *
 */
public class OracleConnection {
	private static Connection connection = null;
	private static boolean isInit = false;
	private static String username = "rivp";
	private static String password = "rivp";
	private static String urlString = "";
	
	public static String RDBMSType = "MYSQL";	
	public static String sysDate = "SYSDATE";
	
	//rdbms = "mysql";
	//oraSource = "192.192.191.38:3306";
	//oraUserName = "root";
	//oraPassword = "123456";
	
	public static Connection connect(String nSrc, String nUsername, String nPassword) throws ClassNotFoundException, SQLException {
		String jdbcString;
		if("MYSQL".equals(RDBMSType)) {
			urlString = "jdbc:mysql://" + nSrc;                   //MYSQL
			jdbcString = "com.mysql.cj.jdbc.Driver";            //MYSQL
			sysDate = "NOW()";
		}		
		else if("DM".equals("RDBMSType")) {
			urlString = "jdbc:dm://" + nSrc;                   //DM
			jdbcString = "dm.jdbc.driver.DmDriver";            //DM
		}
		else {
			urlString = "jdbc:oracle:thin:@" + nSrc; //ORACLE
			jdbcString = "oracle.jdbc.driver.OracleDriver";    //ORACLE
		}
		
		username = nUsername;
		password = nPassword;
		if(!isInit) {
			Class.forName(jdbcString);//通知jvm将driver注册到DriverManager
			isInit = true;
			DriverManager.setLoginTimeout(5);		// 超时时间5s
		}
		if(connection != null) {
			try {
				connection.close();
			} catch(Exception e) {
				// do nothing
			}
		}
		connection = DriverManager.getConnection(urlString, username, password);
		return connection;
	}

	public static Connection getConnection() throws SQLException {
		if(connection == null || !connection.isValid(500)) {
			// 重连
			if(connection != null) {
				try {
					connection.close();
				} catch(Exception e) {
					// do nothing
				}
			}
			connection = DriverManager.getConnection(urlString, username, password);
		}
		return connection;
	}	
}
