package com.lctech.swm.config;


import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;


@Component
public class SpringContextListener implements ApplicationListener<ContextRefreshedEvent> {
	
	

	/**
	 * This method will run on application startup and load up the default book
	 * collection into the MongoDB database.
	 */
	public void onApplicationEvent(ContextRefreshedEvent event) {

	};
}
