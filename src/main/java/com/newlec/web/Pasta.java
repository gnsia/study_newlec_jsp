package com.newlec.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/pasta")
public class Pasta extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int num = 0;
		String num_ = request.getParameter("n");
		if(num_ != null && !num_.equals(""))
			num = Integer.parseInt(num_);
		
		/* Model = data */
		String model = "";
		if(num % 2 != 0)
			model = "홀";
		else
			model = "짝";
		
		//forwarding
		
		request.setAttribute("model", model);
		
		
		// 배열은 꺼낼 때 ${array[index]}
		String[] names = {"newlec", "dragon"};
		request.setAttribute("names", names);
		
		
		// 맵은 꺼낼 때 ${map.key}
		Map<String, Object> notice = new HashMap<String, Object>();
		notice.put("id", 1);
		notice.put("title", "EL은 좋아요.");
		request.setAttribute("notice", notice);
		
		
		RequestDispatcher dispatcher 
			= request.getRequestDispatcher("pasta.jsp");
		dispatcher.forward(request, response);
		
	}
}
