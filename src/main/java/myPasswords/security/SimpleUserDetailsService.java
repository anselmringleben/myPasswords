package myPasswords.security;

import java.util.HashMap;

import javax.annotation.PostConstruct;

import lombok.extern.log4j.Log4j;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Log4j
@Service("userDetailsService")
public class SimpleUserDetailsService implements UserDetailsService {

	HashMap<String, User> details;

	@PostConstruct
	public void init() {
		this.details = new HashMap<String, User>(5);
	}

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {

		if (!this.details.containsKey(username))
			throw new UsernameNotFoundException("Username not found: " + username);
		
		return this.details.get(username);
	}

	public void createUser(User userDetails) {
		log.debug("Method createUser called: " + userDetails);

		if (null == userDetails)
			return;

		String username = userDetails.getUsername();
		if (null == username)
			return;

		this.details.put(username, userDetails);
	}

	public boolean userExists(String username) {
		return this.details.containsKey(username);
	}
}
