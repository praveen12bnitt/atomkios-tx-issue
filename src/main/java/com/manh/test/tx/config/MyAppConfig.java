package com.manh.test.tx.config;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import com.manh.test.tx.MyDataSource;

@EnableTransactionManagement
@Configuration
@ComponentScan("com.manh")
public class MyAppConfig {

	@Autowired
	private AtomikosJtaConfiguration jtaConfiguration;

	@Resource(name = "atomikosDataSource")
	private DataSource atomkiosDatasource;

	@Bean
	public DataSource dataSource() {
		MyDataSource myDs = new MyDataSource();
		myDs.setTargetDataSource(atomkiosDatasource);
		return myDs;
	}

	@Bean
	public PlatformTransactionManager platformTransactionManager() throws Throwable {
		return new JtaTransactionManager(jtaConfiguration.userTransaction(), jtaConfiguration.transactionManager());
	}

}
