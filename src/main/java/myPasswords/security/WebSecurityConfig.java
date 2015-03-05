package myPasswords.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebMvcSecurity
@EnableWebSecurity(debug = true)
@Configuration
@Order
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private XAuthTokenFilter xAuth;
	
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(
				SessionCreationPolicy.STATELESS);

		http.authorizeRequests()
				.antMatchers("/login").permitAll()
				.antMatchers("/resources/**").permitAll()
				.anyRequest().authenticated()
				.and()
				.addFilterBefore(this.xAuth, UsernamePasswordAuthenticationFilter.class);
	}

	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		// TODO: add DB-based auth
		auth.inMemoryAuthentication()
			.withUser("user").password("user123").roles("USER").and()
			.withUser("admin").password("admin123").roles("USER", "ADMIN");
	}
}
