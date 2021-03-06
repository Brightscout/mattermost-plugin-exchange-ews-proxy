package com.brightscout.ews.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.brightscout.ews.security.AuthEntryPoint;
import com.brightscout.ews.security.AuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebMvcConfig extends WebSecurityConfigurerAdapter {

	private AuthenticationFilter authenticationFilter;

	@Autowired
	public WebMvcConfig(AuthenticationFilter authenticationFilter) {
		this.authenticationFilter = authenticationFilter;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.exceptionHandling()
			.authenticationEntryPoint(new AuthEntryPoint())
			.and()
			.authorizeRequests()
			.anyRequest().authenticated()
			.and()
			.csrf().disable()
			.formLogin().disable()
			.httpBasic().disable()
			.logout().disable();

		http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}
}
