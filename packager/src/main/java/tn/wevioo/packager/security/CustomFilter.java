package tn.wevioo.packager.security;

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
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.web.filter.GenericFilterBean;

import tn.wevioo.packager.entities.AuthorizedIpAddress;
import tn.wevioo.packager.entities.WebServiceUser;
import tn.wevioo.packager.service.AuthorizedIpAddressService;
import tn.wevioo.packager.service.WebServiceUserService;

@EnableAutoConfiguration(exclude = JpaRepositoriesAutoConfiguration.class)
public class CustomFilter extends GenericFilterBean {

	private static final Log LOGGER = LogFactory.getLog(CustomFilter.class);

	private static final String LOCALHOST_IPV6 = "0:0:0:0:0:0:0:1";

	private static final String LOCALHOST_IPV4 = "127.0.0.1";

	public static final String AUTHENTICATION_HEADER = "Authorization";

	public static final String WADL = "_wadl";

	private final AuthorizedIpAddressService authorizedIpAddressService;

	WebServiceUserService webServiceUserService;

	public CustomFilter(AuthorizedIpAddressService authorizedIpAddressService,
			WebServiceUserService webServiceUserService) {
		this.authorizedIpAddressService = authorizedIpAddressService;
		this.webServiceUserService = webServiceUserService;
	}

	public AuthorizedIpAddressService getAuthorizedIpAddressService() {
		return authorizedIpAddressService;
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
		final String user = tokenizer.nextToken();
		LOGGER.debug("Authenticating user : " + user);
		final String password = tokenizer.nextToken();
		final String ip = LOCALHOST_IPV6.equals(httpServletRequest.getRemoteAddr()) ? LOCALHOST_IPV4
				: httpServletRequest.getRemoteAddr();
		checkAuthentication = checkAuthentication(user, password, ip, webServiceUserService.getWebserviceUser());
		return checkAuthentication;
	}

	private boolean checkAuthentication(String username, String password, String ip, WebServiceUser webServiceUser) {

		AuthorizedIpAddress authorizedIpAddress = authorizedIpAddressService.findByWebServiceUser(webServiceUser);
		return checkAdresseIp(ip, authorizedIpAddress.getAddress());

	}

	private boolean checkAdresseIp(String authentificationIp, String authorizedIp) {

		String ch1 = "";
		String ch2 = "";

		ch1 = authorizedIp.substring(0, authorizedIp.indexOf("*"));
		ch2 = authentificationIp.substring(0, authorizedIp.indexOf("*"));

		return ch1.equals(ch2);
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

}
