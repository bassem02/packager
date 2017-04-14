package tn.wevioo.security;

import java.io.IOException;
import java.util.StringTokenizer;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.web.filter.GenericFilterBean;

import tn.wevioo.authentication.dao.UsersDAO;

@EnableAutoConfiguration(exclude = JpaRepositoriesAutoConfiguration.class)
public class CustomFilter extends GenericFilterBean {

	private static final Log LOGGER = LogFactory.getLog(CustomFilter.class);

	/**
	 * Localhosot ipv6
	 */
	private static final String LOCALHOST_IPV6 = "0:0:0:0:0:0:0:1";

	/**
	 * Localhosot ipv4
	 */
	private static final String LOCALHOST_IPV4 = "127.0.0.1";

	/**
	 * AUTHENTICATION_HEADER.
	 */
	public static final String AUTHENTICATION_HEADER = "Authorization";

	/**
	 * WADL file.
	 */
	public static final String WADL = "_wadl";

	@Autowired
	public UsersDAO usersDAO;

	private static String formatPathInfo(String pathInfo) {
		String result = pathInfo;
		if (result.indexOf("/") != -1) {
			result = result.substring(result.indexOf("/") + 1, result.length());
		}
		if (result.indexOf("/") != -1) {
			result = result.substring(0, result.indexOf("/"));
		}
		return result;
	}

	public boolean authenticate(HttpServletRequest httpServletRequest) {
		String queryString = httpServletRequest.getQueryString();
		if (!StringUtils.isEmpty(queryString) && queryString.startsWith(WADL)) {
			return true;
		}
		boolean checkAuthentication = false;
		String authCredentials = httpServletRequest.getHeader(AUTHENTICATION_HEADER);
		if (authCredentials == null) {
			return false;
		}
		final String encodedUserPassword = authCredentials.replaceFirst("Basic" + " ", "");
		String usernameAndPassword = null;
		try {
			byte[] decodedBytes = DatatypeConverter.parseBase64Binary(encodedUserPassword);
			usernameAndPassword = new String(decodedBytes, "UTF-8");
		} catch (IOException e) {
			LOGGER.debug(e);
		}
		final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
		if (!tokenizer.hasMoreElements()) {
			return false;
		}
		// NECESSARY - DO NOT DELETE tokenizer.nextToken() !!
		final String user = tokenizer.nextToken();
		LOGGER.debug("Authenticating user : " + user);
		final String password = tokenizer.nextToken();
		final String ip = LOCALHOST_IPV6.equals(httpServletRequest.getRemoteAddr()) ? LOCALHOST_IPV4
				: httpServletRequest.getRemoteAddr();
		// final String pathInfo =
		// formatPathInfo(httpServletRequest.getPathInfo());

		checkAuthentication = checkAuthentication(user, password, ip);

		/*
		 * try { checkAuthentication =
		 * this.authenticationModule.checkAuthentication(password, ip,
		 * pathInfo);
		 * 
		 * } catch (NotRespectedRulesException e) { LOGGER.debug(e); }
		 */

		return checkAuthentication;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filter)
			throws IOException, ServletException {
		if (request instanceof HttpServletRequest) {
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			Boolean checkAuthentication = this.authenticate(httpServletRequest);
			if (checkAuthentication) {
				filter.doFilter(request, response);
			} else {
				if (response instanceof HttpServletResponse) {
					// response
					HttpServletResponse httpServletResponse = (HttpServletResponse) response;
					httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					httpServletResponse.sendError(401, "The request requires HTTP authentication ()");
					httpServletResponse.setContentType("application/json");
				}
			}
		}
	}

	private boolean checkAuthentication(String username, String password, String ip) {

		return usersDAO.checkUserPass(username, password);

	}

}
