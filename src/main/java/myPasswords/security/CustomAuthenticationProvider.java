package myPasswords.security;

import lombok.extern.log4j.Log4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@Log4j
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		log.debug("Method: authenticate called: " + authentication);

		String username = authentication.getName();
		String password = authentication.getCredentials().toString();

		UserDetails userDetails;

		try {
			userDetails = this.userDetailsService.loadUserByUsername(username);

		} catch (UsernameNotFoundException e) {
			throw new BadCredentialsException("Bad Credentials");
		}

		if (!userDetails.getPassword().equals(password))
			throw new BadCredentialsException("Bad Credentials");

		UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(
				username, authentication.getCredentials(), userDetails.getAuthorities());
		result.setDetails(authentication.getDetails());

		return result;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
