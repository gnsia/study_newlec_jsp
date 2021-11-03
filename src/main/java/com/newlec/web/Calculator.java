package com.newlec.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/calculator")
public class Calculator extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cookie[] cookies = request.getCookies();
		
		String exp = "0";
		if(cookies != null) 
			for(Cookie c : cookies) 
				if(c.getName().equals("exp")) {
					exp = c.getValue();
					break;
				}
					
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		out.write("<!DOCTYPE html>");
		out.write("<html>");
		out.write("<head>");
		out.write("<meta charset=\"UTF-8\">");
		out.write("<title>Insert title here</title>");
		out.write("<style>");
		out.write("input {");
		out.write("width : 50px;");
		out.write("height : 50px;");
		out.write("}");
		out.write(".output {");
		out.write("height : 50px;");
		out.write("background : #e9e9e9;");
		out.write("font-size : 24px;");
		out.write("font-weight : bold;");
		out.write("text-align : right;");
		out.write("padding:0px 5px;");
		out.write("}");
		out.write("</style>");
		out.write("</head>");
		out.write("<body>");
		out.write("<form method=\"post\">");
		out.write("<table>");
		out.write("<tr>");
		out.printf("	<td class=\"output\" colspan=\"4\">%s</td>", exp);
		out.write("</tr>");
		out.write("<tr>");
		out.write("	<td><input type=\"submit\" name=\"operator\" value=\"CE\"></td>");
		out.write("	<td><input type=\"submit\" name=\"operator\" value=\"C\"></td>");
		out.write("	<td><input type=\"submit\" name=\"operator\" value=\"BS\"></td>");
		out.write("	<td><input type=\"submit\" name=\"operator\" value=\"/\"></td>");
		out.write("</tr>");
		out.write("<tr>");
		out.write("	<td><input type=\"submit\" name=\"value\" value=\"7\"></td>");
		out.write("	<td><input type=\"submit\" name=\"value\" value=\"8\"></td>");
		out.write("	<td><input type=\"submit\" name=\"value\" value=\"9\"></td>");
		out.write("	<td><input type=\"submit\" name=\"operator\" value=\"*\"></td>");
		out.write("</tr>");
		out.write("<tr>");
		out.write("	<td><input type=\"submit\" name=\"value\" value=\"4\"></td>");
		out.write("	<td><input type=\"submit\" name=\"value\" value=\"5\"></td>");
		out.write("	<td><input type=\"submit\" name=\"value\" value=\"6\"></td>");
		out.write("	<td><input type=\"submit\" name=\"operator\" value=\"-\"></td>");
		out.write("</tr>");
		out.write("<tr>");
		out.write("	<td><input type=\"submit\" name=\"value\" value=\"1\"></td>");
		out.write("	<td><input type=\"submit\" name=\"value\" value=\"2\"></td>");
		out.write("	<td><input type=\"submit\" name=\"value\" value=\"3\"></td>");
		out.write("	<td><input type=\"submit\" name=\"operator\" value=\"+\"></td>");
		out.write("</tr>");
		out.write("<tr>");
		out.write("	<td></td>");
		out.write("	<td><input type=\"submit\" name=\"value\" value=\"0\"></td>");
		out.write("	<td><input type=\"submit\" name=\"dot\" value=\".\"></td>");
		out.write("	<td><input type=\"submit\" name=\"operator\" value=\"=\"></td>");
		out.write("</tr>");
		out.write("</table>");
		out.write("</form>");
		out.write("</body>");
		out.write("</html>");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cookie[] cookies = request.getCookies();
		String value = request.getParameter("value");
		String operator = request.getParameter("operator");
		String dot = request.getParameter("dot");
		
		String exp = "";
		if(cookies != null) 
			for(Cookie c : cookies) 
				if(c.getName().equals("exp")) {
					exp = c.getValue();
					break;
				}
		
		if(operator != null && operator.equals("=")) {
//			ScriptEngine engine = new ScriptEngineManager().getEngineByName("js");
//			try {
//				exp = String.valueOf(engine.eval(exp));
//			} catch (ScriptException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
	        exp = cookieCalc(cookies);
		}
		else if(operator != null && operator.equals("C")) {
			exp = "";
		}
		else {			
			exp += (value == null)? "":value;
			exp += (operator == null)? "":operator;
			exp += (dot == null)? "":dot;
		}
		
		Cookie expCookie = new Cookie("exp", exp);
		
		if(operator != null && operator.equals("C"))
			expCookie.setMaxAge(0);
		
		expCookie.setPath("/calculator");
		response.addCookie(expCookie);
		response.sendRedirect("calculator");	
	}
	
	public static String cookieCalc(Cookie[] cookies) {
		LinkedList<Integer> numList = new LinkedList<Integer>(); //숫자관련
        LinkedList<Character> opList = new LinkedList<Character>(); //연산자 관

        String s = ""; //enter까지 포함한 것까지 입력
        
        for(Cookie c : cookies)
        	s = c.getValue();
        
        String num = ""; //연사자 외에 숫자들을 임시 저장할 곳
        
        for(int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i); //string을 char 타입 단위로 나눔
            
            if(ch == '+' || ch =='-' || ch == '*' || ch == '/') {
                numList.add(Integer.parseInt(num)); //숫자로 바꿔서 숫자배열에 추가
                opList.add(ch); //연산자를 연산자배열에 추가
                num = ""; //임시로 저장된 숫자를 비워준다                   
                continue; //제일 가까운 명령문으로 이동
            }
            num += ch; //연산자 앞까지의 숫자를 임시로 넣어 놓음
        }
        numList.add(Integer.parseInt(num)); //마지막 숫자
 
        while(!opList.isEmpty()) { //연산자배열이 빌 때까지
            int prevNum = numList.poll(); //poll : 앞부터 완전히 뺀다
            int nextNum = numList.poll();
            char op = opList.poll();
            
            if(op == '+') {
                numList.addFirst(prevNum + nextNum); //addFirst 배열 제일 앞에 넣는다
            } else if(op == '-') {
                numList.addFirst(prevNum - nextNum);
            } else if(op == '*') {
                numList.addFirst(prevNum * nextNum);
            } else if(op == '/') {
                numList.addFirst(prevNum / nextNum);
            }
        }
        
        String result = String.valueOf(numList.poll());
		return result;
	}

}
