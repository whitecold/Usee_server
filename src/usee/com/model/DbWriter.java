package usee.com.model;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 数据库写操作，使用commons-dbutils-1.6.jar
 */
public class DbWriter {
	private static Logger logger = Logger.getLogger(DbWriter.class);  
	public static int write(String sql,String par[]) {
		// 建立连接
		Connection conn = DbConnect.getConn();
		int flag = 0;
		// 创建sql执行工具
		QueryRunner qr = new QueryRunner();
		// sql插入语句
		try {
			// 执行插入
			flag = qr.update(conn, sql, par);
			return flag;
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);
			return 0;
		}
		finally{
		DbUtils.closeQuietly(conn);
		}
//		DbUtils.close(conn);
		
	}

	
}
