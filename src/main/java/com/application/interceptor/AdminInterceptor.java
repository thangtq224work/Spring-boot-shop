package com.application.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.application.entities.Account;

@Component
public class AdminInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Account account = (Account) request.getSession().getAttribute("user");
		if(account == null) {
			response.sendRedirect(request.getContextPath()+"/login");
			return false;
		}
		if(account.getAdmin()>=2) {
			response.sendRedirect(request.getContextPath()+"/access-denied");	
			return false;
		}
		
		return account.getAdmin()<2;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		response.getWriter().print("<script>"
				+ "alert('Vui lòng đăng nhập')"
				+ "</script>");
	}
}
