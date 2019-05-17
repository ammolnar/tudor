package hu.elte.szgy.tudor.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
	public void addViewControllers(ViewControllerRegistry registry) {
		//registry.addViewController("/home").setViewName("home");
		//registry.addViewController("/").setViewName("home");
		registry.addViewController("/hello").setViewName("hello");
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/admin_home").setViewName("admin_home");
		registry.addViewController("/ugyfel_home").setViewName("ugyfel_home");
		registry.addViewController("/tudor_home").setViewName("tudor_home");
		//registry.addViewController("/users").setViewName("users");
		//registry.addViewController("/error").setViewName("error");
	}

}
