package com.yyu.fwk.db.pool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Simple database operator.
 * 
 * @author Yang
 */
public class DBOperator {

	public static ResultSet executeQuery(String SqlStr) throws SQLException {
		ResultSet result = null;
		try {
			Statement stmt = ConnectionManager.getDataSource().getConnection().createStatement();
			result = stmt.executeQuery(SqlStr);
		}catch(SQLException e){
			throw e;
		}
		return result;
	}
	
	public static int executeUpdate(String SqlStr) throws SQLException{
		int result = -1;
		Connection conn = ConnectionManager.getDataSource().getConnection();
		try {
			Statement stmt = conn.createStatement();
			result = stmt.executeUpdate(SqlStr);
			return result;
		} catch (SQLException e) {
			throw e;
		}finally{
			ConnectionManager.releaseConnection(conn);
		}
	}
	
	public static boolean handleTransaction(List<String> sqls) throws SQLException {
		Connection conn = ConnectionManager.getDataSource().getConnection();
		boolean result = false;
		try{
			try {
				Statement stmt = conn.createStatement();
				conn.setAutoCommit(false);
				for (int i = 0; i < sqls.size(); i++) {
					stmt.executeUpdate(sqls.get(i));
				}
				conn.commit();
				conn.setAutoCommit(true);
				result = true;
			} catch (SQLException e) {
				try {
					conn.rollback();
				} catch (SQLException rbe) {
					throw rbe;
				}
				throw e;
			}
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				throw e;
			}
		}finally{
			ConnectionManager.releaseConnection(conn);
		}
		return result;
	}
}
