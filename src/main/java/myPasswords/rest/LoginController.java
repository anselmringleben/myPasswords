package myPasswords.rest;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.log4j.Log4j;
import myPasswords.security.TokenUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Log4j
@RestController
public class LoginController {

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private TokenUtils tokenUtils;

	@RequestMapping(value = "/login", method = { RequestMethod.POST })
	public UserTransfer authorize(@RequestParam String username,
			@RequestParam String password) {
		log.debug("Method: authorize called: " + username + " " + password);

		Map<String, Boolean> roles = new HashMap<String, Boolean>();

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
				username, password);
		Authentication authentication = this.authManager.authenticate(token);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		for (GrantedAuthority authority : authentication.getAuthorities()) {
			roles.put(authority.toString(), Boolean.TRUE);
		}

		return new UserTransfer(authentication.getName(), roles,
				this.tokenUtils.createToken(username, password));
	}

	@AllArgsConstructor
	public class UserTransfer {
		@Getter
		private String name;

		@Getter
		private Map<String, Boolean> roles;

		@Getter
		private String token;
	}
}
