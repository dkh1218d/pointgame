package com.ksinfo.pointgame.utils;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import com.ksinfo.pointgame.dto.userDTO;

public class Intercepter implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		
		if(session.getAttribute("member_number")==null || !request.isRequestedSessionIdValid()) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script> alert('セッションが切れました。\\nログインページに転移します。');"
					+ "location.href='/'; </script>");
			out.flush();
			out.close();
			return false;
		}
		if((int)session.getAttribute("gameover")==1) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script> alert('本日の挑戦記録があります。\\nメインページに遷移します。');"
					+ "location.href='/'; </script>");
			out.flush();
			out.close();
		}
		return true;
	}
}
