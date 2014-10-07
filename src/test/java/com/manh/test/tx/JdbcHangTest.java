package com.manh.test.tx;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.manh.test.tx.config.TestAppConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestAppConfig.class, loader = AnnotationConfigContextLoader.class)
public class JdbcHangTest {

	@Resource(name="dataSource")
	private DataSource dataSource;

	@Autowired
	private MultipleConnection multipleConnection;

	public void populateData() { 		
		DefaultResourceLoader defaultResourceLoader = new DefaultResourceLoader();
		ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
		databasePopulator.addScript(defaultResourceLoader.getResource("schema.sql"));
		DatabasePopulatorUtils.execute(databasePopulator, dataSource);
	}

	@Test
	public void testFailScenario() throws Exception {		
		populateData();
		multipleConnection.useMultipleConnectionsFail();
	}
	
//	@Test
//	public void testPassScenario() throws Exception {		
//		multipleConnection.useMultipleConnectionsPass();
//	}
}
