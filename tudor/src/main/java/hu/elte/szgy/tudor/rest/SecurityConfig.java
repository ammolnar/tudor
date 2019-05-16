package hu.elte.szgy.tudor.rest;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	/*@Autowired
	private DataSource dataSource;
	
	@Value("{spring.queries.users-query}")
	private String usersQuery;
	
	@Value("{spring.queries.roles-query}")
	private String rolesQuery;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.jdbcAuthentication()
			.usersByUsernameQuery(usersQuery)
			.authoritiesByUsernameQuery(rolesQuery)
			.dataSource(dataSource);

	}*/
	
	
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)
			.passwordEncoder(getPasswordEncoder());
	}
	
	
	/*@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		//https://www.mkyong.com/spring-boot/spring-security-there-is-no-passwordencoder-mapped-for-the-id-null/
		auth
			.inMemoryAuthentication()
			.withUser("usr").password("{noop}pwd").roles("ADMIN");
	}*/
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
        .authorizeRequests()
        	.antMatchers("/css/**", "/js/**", "/images/**").permitAll()
        	.antMatchers("/", "/home", "/hello").authenticated()
        	.antMatchers("/admin_home").hasRole("ADMIN")
        	.antMatchers("/ugyfel_home").hasRole("UGYFEL")
        	
        	
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
        	.anyRequest().authenticated()
        	.and()
        .csrf().disable()
        .formLogin()
        	.loginPage("/login")
        	.successForwardUrl( "/user/dispatch" )
        	//.successForwardUrl( "/dispatch" )
        	//.failureForwardUrl("/error")
        	.permitAll()
        	.and()
        .logout()
        	.permitAll();
		
	}
	
	/*
	@SuppressWarnings("deprecation")
	@Bean
	@Override
	public UserDetailsService userDetailsService() {
		// SIMPLE USERSERVICE TO BE USED FOR TESTING ONLY
		// There is no PasswordEncoder mapped for the id “null” -- solutions: 
		 // https://www.mkyong.com/spring-boot/spring-security-there-is-no-passwordencoder-mapped-for-the-id-null/
		UserDetails user = 
				User.withDefaultPasswordEncoder()
					.username("usr")
					.password("pwd")
					.roles("ADMIN")
					.build();
		return new InMemoryUserDetailsManager(user); 
		//return new TudorUserService();
	}
	*/
	
	private PasswordEncoder getPasswordEncoder() {
		return new PasswordEncoder() {
			@Override
			public String encode(CharSequence charSequence) {
				return charSequence.toString();
			}
			
			@Override
			public boolean matches(CharSequence charSequence, String s) {
				return true;
			}
		};
	}
	
}
