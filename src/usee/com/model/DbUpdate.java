package usee.com.model;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;

/**
 * 数据库更新操作
 * 
 * @author pj
 *
 */
public class DbUpdate {
	
	/**
	 * 根据主键更新学生信息
	 * @param id 主键
	 * @param st_info
	 * @param st_email
	 * @return 成果与否的标志
	 */
	public static int update(String sql) {
		// 建立连接
		Connection conn = DbConnect.getConn();
		int flag = 0;
		// 创建sql执行工具
		QueryRunner qr = new QueryRunner();
//		// sql插入语句
		try {
			// 执行更新
			flag = qr.update(conn, sql, null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DbUtils.closeQuietly(conn);
		return flag;
	}
	
}
