package br.com.ganix.establishment.service;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;


@SpringBootApplication 
@ImportResource("classpath*:/establishment-persistence/application-context.xml")
public class StartService {
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(StartService.class); 
        app.setShowBanner(false); 
        app.run(args);
	}	
	
	@Bean
	public JettyEmbeddedServletContainerFactory jettyEmbeddedServletContainerFactory() {
	    JettyEmbeddedServletContainerFactory jettyContainer = new JettyEmbeddedServletContainerFactory();
	    jettyContainer.setContextPath("/API");
	    return jettyContainer;
	}

	/**
	 * Properties Configurations
	 * @return
	 */
	@Bean
	public static PropertyPlaceholderConfigurer properties() {

		/**
		 * groupId
		 * artifactId
		 * version 
		 * m2e.projectName
		 * m2e.projectLocation
		 */
		Resource[] resources = new ClassPathResource[] {new ClassPathResource("/maven.properties")};

		PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
		ppc.setLocations(resources);
		ppc.setIgnoreUnresolvablePlaceholders(true);
		return ppc;
	}

}