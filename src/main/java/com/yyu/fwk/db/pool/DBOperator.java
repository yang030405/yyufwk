package com.yyu.fwk.db.pool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.yyu.fwk.db.exception.MultiRecordFoundException;

/**
 * Simple database operator.
 * 
 * @author Yang
 */
public class DBOperator {

	public static ResultSet executeQuery(String sql) throws SQLException {
		ResultSet result = null;
		try {
			Statement stmt = ConnectionManager.getDataSource().getConnection().createStatement();
			result = stmt.executeQuery(sql);
		}catch(SQLException e){
			throw e;
		}
		return result;
	}
	
	public static List<Map<String, String>> find(String sql) throws SQLException{
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		ResultSet rs = executeQuery(sql);
		ResultSetMetaData rsMeta = rs.getMetaData();
		Map<Integer,String> numberOfColumn = new HashMap<Integer,String>();
		for (int i = 1; i <= rsMeta.getColumnCount(); i++) {
			numberOfColumn.put(i, rsMeta.getColumnLabel(i));
		}

		while (rs.next()) {
			Map<String, String> data = new HashMap<String, String>();
			for(Entry<Integer, String> entry : numberOfColumn.entrySet()){
				Integer columnNumber = entry.getKey();
				String columnName = entry.getValue();
				String columnValue = rs.getString(columnNumber);
				data.put(columnName, columnValue == null ? "" : columnValue);
			}
			list.add(data);
		}
		return list;
	}
	
	public static Map<String, String> findOne(String sql) throws SQLException, MultiRecordFoundException{
		ResultSet rs = executeQuery(sql);
		ResultSetMetaData rsMeta = rs.getMetaData();
		Map<Integer,String> numberOfColumn = new HashMap<Integer,String>();
		for (int i = 1; i <= rsMeta.getColumnCount(); i++) {
			numberOfColumn.put(i, rsMeta.getColumnLabel(i));
		}
		
		int count = 1;
		Map<String, String> data = null;
		while (rs.next()) {
			if(count++ > 1){
				throw new MultiRecordFoundException("more than one record found with sql[" + sql + "]");
			}
			data = new HashMap<String, String>();
			for(Entry<Integer, String> entry : numberOfColumn.entrySet()){
				Integer columnNumber = entry.getKey();
				String columnName = entry.getValue();
				String columnValue = rs.getString(columnNumber);
				data.put(columnName, columnValue == null ? "" : columnValue);
			}
		}
		return data;
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
	
	public static boolean executeInTransaction(List<String> sqls) throws SQLException {
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
