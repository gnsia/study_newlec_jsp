package com.newlec.web.controller.admin.notice;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newlec.web.entity.Notice;
import com.newlec.web.entity.NoticeView;
import com.newlec.web.service.NoticeService;


@WebServlet("/admin/notice/list")
public class ListController extends HttpServlet{
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] openIds = request.getParameterValues("open-id");
		String[] delIds = request.getParameterValues("del-id");
		for (String openId : openIds)
			System.out.println("opeqn id : " +openId);
		
		for (String delId : delIds)
			System.out.println("del id : " + delId);
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// "list?p=1&f=title&q=a" 
		// 위와 같은 url주소를 통해 받아온 값들을 임시변수에 세팅한다.
		String field_ = request.getParameter("f");
		String query_ = request.getParameter("q");
		String page_ = request.getParameter("p");
		
		// Service로 넘겨줄 인자에는 기본값을 넣고 
		// 리퀘스트로 받아 값을 담은 임시변수가 null또는 빈 문자열이 아닐경우에만 인자에 넣어준다.
		String field = "title";
		if(field_ != null && !field_.equals(""))
			field = field_;

		String query = "";
		if(query_ != null && !query_.equals(""))
			query = query_;

		int page = 1;
		if(page_ != null && !page_.equals(""))
			page = Integer.parseInt(page_);

		// 서비스 객체를 생성하고 받아온 인자를 넣어 메소드를 호출하고 
		// List<NoticeView>, int 등 각각의 메소드가 반환 할 수 있는 형태의 값으로 반환 받는다.
		NoticeService service = new NoticeService();
		List<NoticeView> list = service.getNoticeList(field, query, page);
		int count = service.getNoticeCount(field, query);
		
		
		// jsp 페이지로 넘겨줄 총알을 장전하고!
		request.setAttribute("list", list);
		request.setAttribute("count", count);
		
		// 목표물을 향해 포워딩!
		request
		.getRequestDispatcher("/WEB-INF/view/admin/board/notice/list.jsp")
		.forward(request, response);
		

	}
}
