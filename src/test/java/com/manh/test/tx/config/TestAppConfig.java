package com.manh.test.tx.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(MyAppConfig.class)
public class TestAppConfig {	

}
