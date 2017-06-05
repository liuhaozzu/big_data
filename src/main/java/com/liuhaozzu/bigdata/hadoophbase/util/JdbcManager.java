package com.liuhaozzu.bigdata.hadoophbase.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.hadoop.conf.Configuration;

import com.liuhaozzu.bigdata.hadoophbase.common.GlobalConstants;

/**
 * jdbc管理
 * 
 * @author 肖斌
 *
 */
public class JdbcManager {
	/**
	 * 根据配置获取获取关系型数据库的jdbc连接
	 * 
	 * @param conf
	 *            hadoop配置信息
	 * @param flag
	 *            区分不同数据源的标志位
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection(Configuration conf, String flag) throws SQLException {
		String driverStr = String.format(GlobalConstants.JDBC_DRIVER, flag);
		String urlStr = String.format(GlobalConstants.JDBC_URL, flag);
		String usernameStr = String.format(GlobalConstants.JDBC_USERNAME, flag);
		String passwordStr = String.format(GlobalConstants.JDBC_PASSWORD, flag);

		String driverClass = conf.get(driverStr);
		// String url = conf.get(urlStr);
		String url = "jdbc:mysql://hadoop1:3306/result_db?useUnicode=true&amp;characterEncoding=utf8";
		// String username = conf.get(usernameStr);
		// String password = conf.get(passwordStr);
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// nothing
		}
		return DriverManager.getConnection(url, "root", "root");
	}
}
