package myPasswords.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebMvcSecurity
@EnableWebSecurity(debug = true)
@Configuration
@Order
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private XAuthTokenFilter xAuth;

	@Autowired
	private SimpleUserDetailsService userDetailsService;

	@Autowired
	private CustomAuthenticationProvider customAuthenticationProvider;

	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(
				SessionCreationPolicy.STATELESS);

		http.authorizeRequests()
				.antMatchers("/login")
				.permitAll()
				.antMatchers("/resources/**")
				.permitAll()
				.anyRequest()
				.authenticated()
				.and()
				.addFilterBefore(this.xAuth,
						UsernamePasswordAuthenticationFilter.class);
	}

	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		auth.authenticationProvider(customAuthenticationProvider);

		// auth.userDetailsService(userDetailsService)/*
		// .passwordEncoder(encoder) */;
		// TODO: add DB-based auth
		if (!userDetailsService.userExists("user")) {
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			authorities.add(new SimpleGrantedAuthority("USER"));
			User userDetails = new User("user", /* encoder.encode( */
			"user123"/* ) */, authorities);

			this.userDetailsService.createUser(userDetails);
		}
	}

	@Bean(name = "myAuthenticationManager")
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
