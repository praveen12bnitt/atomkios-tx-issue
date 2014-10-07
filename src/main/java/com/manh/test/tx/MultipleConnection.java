package com.manh.test.tx;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.annotation.Resource;
import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

@Transactional
@Service
public class MultipleConnection {

	@Resource(name = "dataSource")
	private DataSource ds;

	public void useMultipleConnectionsFail() throws Exception {

		Connection conn1 = null;
		Connection conn2 = null;

		// Get connection from the datasource and not do anything with it. Atomikos give conn1
		conn1 = ds.getConnection();

		// Get second connection from the datasource and run a query. Atomikos gives conn2. Not sure why. As per spec, within a transaction
		// any number of times you get connection, it should give you the same phyiscal connection
		conn2 = ds.getConnection();
		PreparedStatement ps2 = conn2.prepareStatement("insert into employee values('PALANIVEL1', '404-509-7085');");
		ps2.execute();		
		
		// Since are in a JTA transaction, conn2.close() does not close the connection. It will close it only when a commit is issued
		ps2.close();		
		conn2.close();

		// Now use conn1 to read the data. Here atomikos uses a diff db connection and the select query below waits for conn2 to commit.
		PreparedStatement ps1 = conn1.prepareStatement("select * from employee");

		ResultSet rs1 = ps1.executeQuery();
		while (rs1.next()) {
			System.out.println("Fist element");
		}
		
		rs1.close();
		ps1.close();
		conn1.close();

	}

	public void useMultipleConnectionsPass() throws Exception {

		Connection conn1 = null;
		Connection conn2 = null;

		
		// Similar testcase as above, but here i am not getting conn1 in the beginning.
		
		// Get connection, use it and close it. 
		conn2 = ds.getConnection();
		PreparedStatement ps2 = conn2.prepareStatement("insert into employee values('PALANIVEL1', '404-509-7085');");
		ps2.execute();
		conn2.close();

		// Now get connection from the datasource and atomikos get the same physical connection. 
		conn1 = ds.getConnection();
		
		// Reading data has no problems since atomikos gives you the same connection.
		PreparedStatement ps1 = conn1.prepareStatement("select * from employee");
		ResultSet rs1 = ps1.executeQuery();
		while (rs1.next()) {
			System.out.println("Fist element");
		}
		rs1.close();
		ps1.close();
		conn1.close();

	}

}
