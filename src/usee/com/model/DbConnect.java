package usee.com.model;



import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.log4j.Logger;

import usee.com.utils.MapUtil;
import usee.com.utils.PropertiesUtil;
/**
 * 建立数据库连接
 * @author pj
 *
 */
public class DbConnect {
    /*
     * 从配置文件读取数据库连接信息
     */
	private final static String driveClassName = PropertiesUtil.getValue("jdbcName");
	private final static String url = PropertiesUtil.getValue("dbUrl");
	private final static String user = PropertiesUtil.getValue("dbUser");
	private final static String psw = PropertiesUtil.getValue("dbPassword");
	private static Logger logger = Logger.getLogger(DbConnect.class);  

	public static Connection getConn() {
		Connection conn = null;
		try {
			//加载驱动
			Class.forName(driveClassName);
			//建立连接
			conn = (Connection) DriverManager.getConnection(url, user, psw);
//			System.out.println("连接成功");
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			logger.error(e);
		}
		return conn;
	}

}
