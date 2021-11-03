<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<%
	pageContext.setAttribute("model", "hello");
%>
<body>
	<%=request.getAttribute("model") %>입니다.
	<!-- EL값 꺼내는 순서 page, req, session, application -->
	<!-- Scope한정사를 통해서 특정 영역에서만 값을 꺼낼 수도 있다. -->
	${requestScope.model}이라구요!<br>
	${names[1]} <br>
	${notice.title}<br>
	${model}<br>
	${empty param.n ? '값이 비어있습니다.' : param.n/2}<br>
	${header.cookie}<br>
</body>
</html>