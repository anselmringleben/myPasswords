package myPasswords.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

@Component
public class XAuthTokenFilter extends GenericFilterBean {

	@Autowired
	private UserDetailsService detailsService;

	@Autowired
	private TokenUtils tokenUtils;

	private String xAuthTokenHeaderName = "x-auth-token";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String authToken = httpServletRequest
				.getHeader(this.xAuthTokenHeaderName);

		if (StringUtils.hasText(authToken)) {
			String username = this.tokenUtils.getUserNameFromToken(authToken);

			UserDetails details = this.detailsService
					.loadUserByUsername(username);

			if (this.tokenUtils.validateToken(authToken, details)) {
				UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
						details, details.getPassword(),
						details.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(token);
			}
		}
		chain.doFilter(request, response);
	}
}
