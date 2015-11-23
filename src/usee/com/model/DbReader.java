package usee.com.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.omg.CORBA.TCKind;
/**
 * 数据库读操作，使用commons-dbutils-1.6.jar
 * @author pj
 */
public class DbReader {
	private static Logger logger = Logger.getLogger(DbReader.class);  
    /**
     * 根据sql语句查询一条记录
     * @param sql
     * @param tClass
     * @return
     */
	@Test
    public static <T>T getBean(String sql, Class<T> tClass) {
        try {
            //得到需要的Bean
            T bean;
            // 建立连接
            Connection conn = DbConnect.getConn();
            List<T> list;
            // 创建SQL执行工具
            QueryRunner qr = new QueryRunner();
            //执行查询
            list = qr.query(conn, sql, new BeanListHandler<T>(tClass));
            //关闭连接
            DbUtils.closeQuietly(conn);
            if (list.size() != 0) {
            	//读取的内容保存在list中，取第一个（实际也只有一个）
               return list.get(0);
            } else {
                return null;
            }
        } catch (SQLException e) {
        	logger.error(e);
            e.printStackTrace();
            return null;
        }
    }
	/**
	 * 根据sql语句查询多条记录
	 * @param sql
	 * @param tClass
	 * @return
	 */
	@Test
    public static <T>List<T> getBeans(String sql, Class<T> tClass) {
        try {
            //得到需要的Bean
            T bean;
            // 建立连接
            Connection conn = DbConnect.getConn();
            List<T> list;
            // 创建SQL执行工具
            QueryRunner qr = new QueryRunner();
            //执行查询
            list = qr.query(conn, sql, new BeanListHandler<T>(tClass));
            //关闭连接
            DbUtils.closeQuietly(conn);
            if (list.size() != 0) {
            	//读取的内容保存在list中，取第一个（实际也只有一个）
               return list;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
	
}