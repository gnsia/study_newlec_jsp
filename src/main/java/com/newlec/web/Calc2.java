package com.newlec.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.CookieStore;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.core.AprLifecycleListener;

@WebServlet("/calc2")
public class Calc2 extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext application = request.getServletContext();
		HttpSession session = request.getSession();
		Cookie[] cookies = request.getCookies();

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		PrintWriter out = response.getWriter();
		
		String v_ = request.getParameter("v");
		String op = request.getParameter("operator");
		
		int v = 0;
		
		if(!v_.equals("")) v = Integer.parseInt(v_);
		
		//계산 
		if(op.equals("=")) {
		
			// 어플리케이션 
//			String operator = (String)application.getAttribute("op");
//			int x = (Integer)application.getAttribute("value");
			
			// 세션 
//			String operator = (String)session.getAttribute("op");
//			int x = (Integer)session.getAttribute("value");
			
			// 쿠키 
			int x = 0;			
			for(Cookie c : cookies) {
				if(c.getName().equals("value")) {
					x = Integer.parseInt(c.getValue());
					break;
				}
			}
			
			String operator = "";
			for(Cookie c : cookies) {
				if(c.getName().equals("op")) {
					operator = c.getValue();
					break;
				}
			}
			
			int y = v;
			int result = 0;

			if(operator.equals("+"))
				result = x + y;
			else
				result = x - y;

			out.println("result :" + result);
		}
		//저장 
		else {
			// 애플리케이션 
//			application.setAttribute("value", v);
//			application.setAttribute("op", op);
			
			// 세션 
//			session.setAttribute("value", v);
//			session.setAttribute("op", op);
			
			Cookie valCookie = new Cookie("value", String.valueOf(v));
			Cookie opCookie = new Cookie("op", op);
			
			valCookie.setPath("/calc2");
			valCookie.setMaxAge(24*60*60);
			
			opCookie.setPath("/calc2");
			
			response.addCookie(valCookie);
			response.addCookie(opCookie);
			
			response.sendRedirect("calc2.html");
		}		
	}
}
