package usee.com.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.log4j.Logger;

/**
 * 对数据库信息进行删除操作
 * @author pj
 *
 */
public class DbDelete {
	private static Logger logger = Logger.getLogger(DbDelete.class);  
	/**
	 * 删除关系表
	 * @param param 学号。教师工号，项目编号
	 */
	public static int delete(String sql){
        
            // 建立连接
            Connection conn = DbConnect.getConn();
            // 创建SQL执行工具
            QueryRunner qr = new QueryRunner();
            try {
            //执行查询
            int flag=qr.update(conn, sql);
            //关闭连接
            return flag;
        } catch (SQLException e) {
        	logger.error(e);
            e.printStackTrace();
            return 0;
        }
        finally{
        	 DbUtils.closeQuietly(conn);
        }
		
	}
}
