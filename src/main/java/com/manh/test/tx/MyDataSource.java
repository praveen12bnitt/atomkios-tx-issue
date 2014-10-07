package com.manh.test.tx;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.jdbc.datasource.DelegatingDataSource;

public class MyDataSource extends DelegatingDataSource {	
	
	@Override
	public Connection getConnection() throws SQLException {		
		return super.getConnection();
	}
	
}
