package hu.elte.szgy.tudor.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class TudorWebSecurity extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
        .authorizeRequests()
    		.antMatchers(HttpMethod.GET,"/**").permitAll()
//            .antMatchers(HttpMethod.GET,"/","/extjs/**").permitAll()
//            .antMatchers(HttpMethod.GET,"/beteg/self").hasRole("BETEG")
//            .antMatchers(HttpMethod.GET,"/beteg/*", "beteg/kezeles/*").hasRole("DOLGOZO")
//            .antMatchers(HttpMethod.GET,"/beteg/*/esetek","/beteg/*/*/kezelesek").hasAnyRole("ADMIN","ORVOS","RECEPCIO","BETEG")
//            .antMatchers(HttpMethod.GET,"/beteg/kezelesek/*/alt_dates").hasAnyRole("RECEPCIO","BETEG")
//            .antMatchers(HttpMethod.GET,"/ellato/**","osztaly/**").hasRole("DOLGOZO")
//            .antMatchers(HttpMethod.GET,"/user/self").authenticated()
//            .antMatchers(HttpMethod.GET,"/user/*").hasAnyRole("ADMIN","RECEPCIO")
//            .antMatchers(HttpMethod.POST,"/beteg/new","/beteg/*","beteg/*/eset/new").hasRole("RECEPCIO")
//            .antMatchers(HttpMethod.POST,"/beteg/*/eset/*").hasRole("ORVOS")
//            .antMatchers(HttpMethod.POST,"/beteg/eset/<eid>/kezeles/new").hasAnyRole("RECEPCIO","ORVOS")
//            .antMatchers(HttpMethod.POST,"/beteg/kezeles/*/set_orvos").hasAnyRole("ORVOS","ADMIN")
//            .antMatchers(HttpMethod.POST,"/beteg/kezeles/*/nyit", "beteg/kezeles/*/zar").hasAnyRole("ORVOS","LABOR")
//            .antMatchers(HttpMethod.POST,"/beteg/kezeles/*/select_date").hasAnyRole("BETEG","RECEPCIO")
//            .antMatchers(HttpMethod.POST,"/ellato/**","osztaly/**").hasRole("ADMIN")
//            .antMatchers(HttpMethod.POST,"/user/new").hasAnyRole("ADMIN","RECEPCIO")
//            .antMatchers(HttpMethod.POST,"/user/password").authenticated()
//            .antMatchers(HttpMethod.POST,"/user/password/*").hasAnyRole("ADMIN", "RECEPCIO")
//            .antMatchers(HttpMethod.POST,"/user/delete/*").hasAnyRole("ADMIN", "RECEPCIO")
            .and()
        .csrf().disable()
        .formLogin()
            .loginPage("/login")
            //.successForwardUrl( "/user/dispatch" )
            //.failureForwardUrl("/error")
            .permitAll()
            .and()
            .logout()
            .permitAll();
		
	}
	
	
	@SuppressWarnings("deprecation")
	@Bean
	@Override
	public UserDetailsService userDetailsService() {
		// SIMPLE USERSERVICE TO BE USED FOR TESTING ONLY
		/* There is no PasswordEncoder mapped for the id “null” -- solutions: 
		 * https://www.mkyong.com/spring-boot/spring-security-there-is-no-passwordencoder-mapped-for-the-id-null/
		 */
		UserDetails user = 
				User.withDefaultPasswordEncoder()
					.username("usr")
					.password("pwd")
					.roles("ADMIN")
					.build();
		return new InMemoryUserDetailsManager(user); 
		//return new TudorUserService();
	}
	 
	
	/*
	 * @Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		//https://www.mkyong.com/spring-boot/spring-security-there-is-no-passwordencoder-mapped-for-the-id-null/
		auth
			.inMemoryAuthentication()
			.withUser("usr").password("{noop}pwd").roles("ADMIN");
	}
	*/
}
