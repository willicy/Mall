package dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
/**
 * ���bJDBC������JDBC�Ĳ�����
 * @author Willicy
 *
 */
public class DBUtils {
	private static String driver;
	private static String url;
	private static String username;
	private static String password;
	
	private static BasicDataSource dataSource;
	
	static {
		Properties prop = new Properties();
		InputStream ips = DBUtils.class
			.getClassLoader()
			.getResourceAsStream("jdbc.properties");
		try {
			prop.load(ips);
			driver = prop.getProperty("driver");
			url = prop.getProperty("url");
			username = prop.getProperty("username");
			password = prop.getProperty("password");
		
			//��������Դ����
			dataSource = 
					new BasicDataSource();
			dataSource.setDriverClassName(driver);
			dataSource.setUrl(url);
			dataSource.setUsername(username);
			dataSource.setPassword(password);
			dataSource.setInitialSize(3);
			dataSource.setMaxActive(3);
		
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				ips.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	public static Connection getConn() 
			throws Exception {
//		Class.forName(driver);
//		Connection conn = 
//				DriverManager.getConnection(
//				url, username, password);
		
		return dataSource.getConnection();
	}
	public static void close(Connection conn,
			Statement stat, ResultSet rs) {
		try {
			if(rs!=null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if(stat!=null) {
				stat.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if(conn!=null) {
				//���Զ��ύ
				conn.setAutoCommit(true);
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
}




