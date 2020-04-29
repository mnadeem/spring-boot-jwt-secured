package com.github.nadeem.api.interceptors;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class RolesAllowedInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest requestServlet, HttpServletResponse responseServlet, Object handler)
			throws Exception {

		HandlerMethod handlerMethod = (HandlerMethod) handler;
		RolesAllowed rolesAllowed = handlerMethod.getMethod().getAnnotation(RolesAllowed.class);
		if (shouldAuthorize(rolesAllowed)) {
			if (!isUserAuthorized(requestServlet, rolesAllowed.value())) {
				throw new RuntimeException();
			}
		}

		return true;
	}

	private boolean shouldAuthorize(final RolesAllowed rolesAllowed) {
		return rolesAllowed != null && rolesAllowed.value().length != 0;
	}

	private boolean isUserAuthorized(final HttpServletRequest request, final String[] roles) {
		for (String role : roles) {
			if (request.isUserInRole(role)) {
				return true;
			}
		}
		return false;
	}

}
