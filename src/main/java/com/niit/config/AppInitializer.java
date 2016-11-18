package com.niit.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer{

	private static final Logger logger=LoggerFactory.getLogger(AppInitializer.class);
	protected Class[] getRootConfigClasses() {
		System.out.println("Testing");
		return new Class[] { AppConfig.class};
	}

	@Override
	protected Class[] getServletConfigClasses() {
		return new Class[] {AppConfig.class};
	}

	@Override
	protected String[] getServletMappings() {
		System.out.println("Working....");
		return new String[] { "/" };
	}

}
