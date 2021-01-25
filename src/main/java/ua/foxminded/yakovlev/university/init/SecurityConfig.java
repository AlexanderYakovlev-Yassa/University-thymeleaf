package ua.foxminded.yakovlev.university.init;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
		.antMatchers("/courses/**").authenticated()
		.antMatchers("/timetable/**").hasRole("ADMIN")
		.antMatchers("/students/**").hasAnyRole("STUDENT", "LECTURER")
		.and()
		.formLogin()
		.and()
		.logout().logoutSuccessUrl("/index");
	}
	
	/*@Bean
	public UserDetailsService users() {
		
		UserDetails user = User.builder()
				.username("Admin")
				.password("{bcrypt}$2y$12$Cndo7HfXurCkHqytTOluPOtaPufa0Oz8c53IYmt0nu/rJelpQ5nLq")
				.roles("ADMIN")
				.build();
	}*/
}
