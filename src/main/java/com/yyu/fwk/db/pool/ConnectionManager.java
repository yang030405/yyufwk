package com.yyu.fwk.db.pool;

import java.sql.Connection;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.yyu.fwk.util.PropertyUtil;

/**
 * Simple connection pool.
 * 
 * @author Yang
 */
public class ConnectionManager {
	private static ComboPooledDataSource datasource;
	
	public static synchronized ComboPooledDataSource getDataSource(){
		if(datasource == null){
			datasource = new ComboPooledDataSource();
			init();
		}
		return datasource;
	}
	
	private ConnectionManager(){
	}
	
	private static void init(){
		String driverClass = PropertyUtil.get("database.driver");
		String jdbcUrl = PropertyUtil.get("database.url");
		String username = PropertyUtil.get("database.username");
		String pwd = PropertyUtil.get("database.password");
		int maxPoolSize = 30;
		int minPoolSize =3;
		int initialPoolSize = 3;
		int idleConnectionTestPeriod = 3600;
		
		if(PropertyUtil.get("database.poolsize.max") != null)
			maxPoolSize = Integer.parseInt(PropertyUtil.get("database.poolsize.max") );
		if(PropertyUtil.get("database.poolsize.min") != null)
			minPoolSize = Integer.parseInt(PropertyUtil.get("database.poolsize.min"));
		if(PropertyUtil.get("database.poolsize.initial") != null)
			initialPoolSize = Integer.parseInt(PropertyUtil.get("database.poolsize.initial"));
		if(PropertyUtil.get("database.idletestpriod") != null)
			idleConnectionTestPeriod = Integer.parseInt(PropertyUtil.get("database.idletestpriod"));
		
		try{
			datasource.setDriverClass(driverClass);
			datasource.setJdbcUrl(jdbcUrl);
			datasource.setUser(username);
			datasource.setPassword(pwd);
			datasource.setMaxPoolSize(maxPoolSize);
			datasource.setMinPoolSize(minPoolSize);
			datasource.setInitialPoolSize(initialPoolSize);
			datasource.setIdleConnectionTestPeriod(idleConnectionTestPeriod);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public static void releaseConnection(Connection conn){
		if(conn != null){
			try{
				conn.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}

}
