package com.liuyuesi.demo;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

//此文件是为了搭配WAR格式压缩包使用
public class SpringBootStartApplication extends SpringBootServletInitializer {
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources( CarRentalApplication.class);
	}
	
	
}
