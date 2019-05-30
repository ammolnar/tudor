package hu.elte.szgy.tudor.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {
	public void addViewControllers(ViewControllerRegistry registry) {
		//registry.addViewController("/home").setViewName("home");
		//registry.addViewController("/").setViewName("home");
		registry.addViewController("/hello").setViewName("hello");
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/admin_home").setViewName("admin_home");
		registry.addViewController("/user/add_user").setViewName("add_user");
		registry.addViewController("/user/update_user").setViewName("update_user");
		registry.addViewController("/ugyfel_home").setViewName("ugyfel_home");
		registry.addViewController("/ugyfel/kerdes/uj").setViewName("uj_kerdes");
		registry.addViewController("/tudor_home").setViewName("tudor_home");
		//registry.addViewController("/users").setViewName("users");
		//registry.addViewController("/error").setViewName("error");
	}
	
	@Override
    public void addCorsMappings(CorsRegistry registry) {
		/* In any modern browser, the Cross-Origin Resource Sharing (CORS) is a relevant 
		 * specification with the emergence of HTML5 and JS clients that consume data via REST APIs.
		 * 
		 * https://www.baeldung.com/spring-cors
		 * enables CORS requests from any origin to any endpoint in the application
		 * */
        registry.addMapping("/**");
    }
	
	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
		// https://stackoverflow.com/questions/14861720/annotation-configuration-replacement-for-mvcresources-spring
	    registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}

}
