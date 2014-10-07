package com.manh.test.tx.config;

import javax.sql.DataSource;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import com.atomikos.jdbc.nonxa.AtomikosNonXADataSourceBean;
import com.manh.test.tx.MyDataSource;

@Configuration
public class AtomikosJtaConfiguration {

	@Autowired
	private Environment environment;

	@Bean
	public UserTransaction userTransaction() throws Throwable {
		UserTransactionImp userTransactionImp = new UserTransactionImp();
		userTransactionImp.setTransactionTimeout(1000);
		return userTransactionImp;
	}
	
	@Bean(initMethod = "init", destroyMethod = "close")
    public TransactionManager transactionManager() throws Throwable {
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        userTransactionManager.setForceShutdown(false);
        return userTransactionManager;
    }
	
	@Bean
	public DataSource atomikosDataSource() {
		
		AtomikosNonXADataSourceBean atomikosDs = new AtomikosNonXADataSourceBean();
		atomikosDs.setUniqueResourceName("atomikos-main");
		atomikosDs.setDriverClassName("org.h2.Driver");
		atomikosDs.setUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;MODE=ORACLE");
		atomikosDs.setUser("sa");
		atomikosDs.setPassword("");
		atomikosDs.setMinPoolSize(10);
		atomikosDs.setMaxPoolSize(100);
		
		return atomikosDs;
	}

}
