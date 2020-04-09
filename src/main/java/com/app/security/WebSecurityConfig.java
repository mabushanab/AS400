package com.app.security;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	ServletContext context;

	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http.csrf().disable().authorizeRequests().
				antMatchers("/error/**").permitAll()
				.antMatchers("/login/**").permitAll()
				.antMatchers("/public/**").permitAll()
				.antMatchers("/resources/**", "*.css", "*.js", "/javax.faces.resource/**").permitAll()
				.antMatchers("/pages/**").authenticated().anyRequest().permitAll()
				.antMatchers("/**").hasRole("ADMIN").anyRequest().permitAll()
				.antMatchers("/*/components/**").denyAll()
				.anyRequest().authenticated().and().formLogin().loginProcessingUrl("/login").loginPage("/login/index.xhtml")
				.defaultSuccessUrl("/").failureUrl("/login/").permitAll()
				.and().logout().logoutUrl("/logout").logoutSuccessUrl("/").deleteCookies("JSESSIONID").permitAll();
		// @formatter:on
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		SecurityService service = new SecurityService();
		authProvider.setUserDetailsService(service);
		authProvider.setPasswordEncoder(encoder());
		return authProvider;
	}

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public HttpFirewall defaultHttpFirewall() {
		return new DefaultHttpFirewall();
	}

	@Bean
	public ServletContext getServletContext() {
		return context;
	}
}