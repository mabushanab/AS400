package com.app.security;
//package com.jk.security;
//
//import java.io.IOException;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.GenericFilterBean;
//
//import com.jk.util.JK;
//import com.jk.util.exceptions.JKSecurityException;
//import com.jk.util.factory.JKFactory;
//import com.jk.util.logging.JKLogger;
//import com.jk.util.logging.JKLoggerFactory;
//import com.jk.web.models.Account;
//import com.jk.web.services.AccountServices;
//
//@Component
//public class TokenAuthenticationFilter extends GenericFilterBean {
//	JKLogger logger=JKLoggerFactory.getLogger(getClass());
//
//
//
//	@Override
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//		final HttpServletRequest httpRequest = (HttpServletRequest) request;
//
//		// extract token from header
//		final String accessToken = httpRequest.getHeader("header-name");
//		String requestURI = httpRequest.getRequestURI();
//		String tokenString = httpRequest.getParameter("ac");// access-code
//		if (null != accessToken) {
//			JK.implementMe();
//			// get and check whether token is valid ( from DB or file wherever you are
//			// storing the token)
//
//			// Populate SecurityContextHolder by fetching relevant information using token
////			final User user = new User("username", "password", true, true, true, true, authorities);
////			final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
////			SecurityContextHolder.getContext().setAuthentication(authentication);
//		} else if (tokenString != null && requestURI.equals("/login2")) {
//			httpRequest.getSession().invalidate();
//			// try to check query string
//			// add extra validation
//			AccountServices service = JKFactory.instance(AccountServices.class);
//			try {
//				Account account = service.checkToken(tokenString);
//				setAutheintcated(account, httpRequest);
//				service.setEmailVerified(account);
//			} catch (JKSecurityException e) {
//				logger.error(e);
//			}
//		}
//		chain.doFilter(request, response);
//	}
//
//	/**
//	 * 
//	 * @param account
//	 * @param request
//	 */
//	private void setAutheintcated(Account account, HttpServletRequest request) {
//		final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(account, account.getPassword(),
//				account.getAuthorities());
//		SecurityContext context = SecurityContextHolder.getContext();
//		context.setAuthentication(authentication);
//		
//		HttpSession session = request.getSession(true);
//		session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);
//	}
//
//	@Override
//	public void destroy() {
//	}
//
//}
