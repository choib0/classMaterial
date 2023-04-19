<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="${pageContext.request.contextPath }/resources/css/inc.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/resources/css/subpage.css" rel="stylesheet" type="text/css">
</head>
<body>
	<header>
		<!-- inc/top.jsp 페이지 삽입 -->
		<jsp:include page="inc/top.jsp" />
	</header>
	<article>
		<h1>메인 페이지</h1>
		<h1>
			<a href="${pageContext.request.contextPath }/write.bo">글쓰기</a> <%-- write_form.jsp --%>
			<a href="${pageContext.request.contextPath }/list.bo">글목록</a> <%-- board_list.jsp --%>
		</h1>
	</article>
	
</body>
</html>












