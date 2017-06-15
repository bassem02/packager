package tn.wevioo.packager.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import nordnet.architecture.exceptions.explicit.NotFoundException;
import tn.wevioo.packager.service.AuthorizedIpAddressService;
import tn.wevioo.packager.service.WebServiceUserService;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	public AuthorizedIpAddressService authorizedIpAddressService;

	@Autowired
	private DataSource datasource;

	@Autowired
	public WebServiceUserService webServiceUserService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.jdbcAuthentication().dataSource(datasource)
				.usersByUsernameQuery(
						"select login as principal, password as credentials, true from web_service_user where login= ?")
				.authoritiesByUsernameQuery(
						"select login as principal,role as role from web_service_user where login=?")
				.rolePrefix("ADMIN");

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/*").permitAll();
		http.httpBasic();
		http.csrf().disable();
		http.addFilterAfter(customFilter(), BasicAuthenticationFilter.class);
	}

	private CustomFilter customFilter() throws NotFoundException {
		return new CustomFilter(authorizedIpAddressService, webServiceUserService);
	}

}
