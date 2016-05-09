/*
 * @(#)com.vvideo.server.Bootstrap	- Nov 17, 2011 9:56:00 PM
 *
 * Copyright        
 * type 			Bootstrap.java
 * Author			 - vvideo  <a href="mailto:author@gmail.com">sendToMe:author@gmail.com</a>
 */
package com.etl.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class Bootstrap {
	private static Logger log = LoggerFactory.getLogger(Bootstrap.class);

	public static void main(String[] args) {
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.bootStrap();
	}

	@SuppressWarnings("resource")
	private void bootStrap() {

		FileSystemXmlApplicationContext ctx = new FileSystemXmlApplicationContext(//
				new String[] { "classpath:/applicationContext.xml" }, false);
		ctx.setClassLoader(Thread.currentThread().getContextClassLoader());
		ctx.refresh();

		log.info(ctx.getDisplayName());

		// ApplicationContext context = new
		// ClassPathXmlApplicationContext("applicationContext.xml");
		// log.info(context.getDisplayName());

		// try {
		// Thread.sleep(1);
		// } catch (InterruptedException e) {
		// } finally {
		// if ((context instanceof ConfigurableApplicationContext)) {
		// ((ConfigurableApplicationContext) context).close();
		// }
		// }
	}
}
