package com.github.nadeem.api.interceptors;

import static com.github.nadeem.Constants.REQ_ATTR_ACTIVE_USER;
import static com.github.nadeem.Constants.SPACE;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.github.nadeem.service.TokenService;
import com.github.nadeem.support.ActiveUser;

@Component
public class TokenRequiredInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private TokenService tokenService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		HandlerMethod handlerMethod = (HandlerMethod) handler;
		TokenRequired tokenRequired = handlerMethod.getMethod().getAnnotation(TokenRequired.class);

		if (tokenRequired == null) {
			return true;
		}

		String auth = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(auth == null) {
        	response.reset();
        	response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "{ auth: false, message: 'No token provided.' }");
        	return false;
        }

        processToken(request, auth);

		return true;
	}

	private void processToken(HttpServletRequest request, String auth) {
		String token = auth.split(SPACE)[1];

        ActiveUser activeUser = tokenService.getActiveUser(token);

        request.setAttribute(REQ_ATTR_ACTIVE_USER, activeUser);
	}
}
