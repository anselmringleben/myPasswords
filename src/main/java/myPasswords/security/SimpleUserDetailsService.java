package myPasswords.security;

import java.util.HashMap;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SimpleUserDetailsService implements UserDetailsService {

	HashMap<String, User> details;

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {

		return this.details.get(username);
	}
}
