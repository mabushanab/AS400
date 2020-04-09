package com.app.security;
//package com.jk.clowiz.apps.security;
//
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//
//import com.jk.clowiz.apps.account.AccountServices;
//import com.jk.util.JK;
//import com.jk.web.servlets.WebConstants;
//
//public class SuccessAuthenticationFilter extends BasicAuthenticationFilter {
//
//	public SuccessAuthenticationFilter(AuthenticationManager authenticationManager) {
//		super(authenticationManager);
//		// TODO Auto-generated constructor stub
//	}
//
//	@Override
//	protected void onSuccessfulAuthentication(final javax.servlet.http.HttpServletRequest request,
//			final javax.servlet.http.HttpServletResponse response, final Authentication authResult) {
//		AccountServices service = new AccountServices();
//		UserDetails account = service.loadUserByUsername(authResult.getName());
//		request.getSession().setAttribute(WebConstants.CURRENT_ACCOUNT, account);
//
//		JK.printBlock("Account : " + account);
//		// Generate Token
//		// Save the token for the logged in user
//		// send token in the response
//		response.setHeader("header-name", "token");
//
//	}
//
//}