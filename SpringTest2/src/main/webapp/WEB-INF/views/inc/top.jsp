<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>      
<script>
	function logout() {
		let isLogout = confirm("로그아웃하시겠습니까?");
		
		if(isLogout) {
			location.href = "logout.me";
		}
	}
</script>    
<div id="member_area">
	<a href="./">홈</a> |
	<!-- 세션 아이디가 없을 경우 로그인, 회원가입 링크 표시 -->
	<!-- 아니면, 아이디 표시, 로그아웃 링크 표시. 단, 관리자는 관리자페이지 링크도 표시 -->
	<c:choose>
		<c:when test="${empty sessionScope.sId }">
			<a href="login.me">로그인</a> | 
			<a href="MemberJoinForm.me">회원가입</a>
		</c:when>
		<c:otherwise>
			<a href="MemberInfo.me">${sessionScope.sId }</a> 님 | 
			<a href="javascript:logout()">로그아웃</a>
			<c:if test="${sessionScope.sId eq 'admin' }">
				| <a href="AdminMain.me">관리자페이지</a>
			</c:if>
		</c:otherwise>
	</c:choose>
</div>    
<hr>
<div id="menu">
	<!-- 
	하이퍼링크 등을 통해 페이지 이동을 위한 경로 지정 시 상대 경로를 사용하면
	현재 위치에 따라 경로 접근이 달라질 수 있다.
	따라서, 상대 경로 대신 절대 경로를 사용하여 상대 경로처럼 지정할 수 있는데
	이 때, 컨텍스트 루트부터 시작하도록 경로를 설정하면 항상 같은 위치에서 자원에 접근 가능
	request 객체의 getContextPath() 메서드를 호출하면 컨텍스트 루트까지의 경로를 얻어낼 수 있음
	-->
<!-- 	<a href="./">홈</a> -->
<!-- 	<a href="main3">메인페이지</a> -->
<!-- 	<a href="push">데이터전달</a> -->
<!-- 	<a href="redirect">리다이렉트</a> -->
<!-- 	<a href="mav">ModelAndView</a> -->
	
	<%-- 프로젝트 루트(컨텍스트 루트)를 지정하여 경로를 선택하는 방법 - request.getContextPath() --%>
<%-- 	<a href="<%=request.getContextPath() %>">홈</a> --%>
<%-- 	<a href="<%=request.getContextPath() %>/main3">메인페이지</a> --%>
<%-- 	<a href="<%=request.getContextPath() %>/push">데이터전달</a> --%>
<%-- 	<a href="<%=request.getContextPath() %>/redirect">리다이렉트</a> --%>
<%-- 	<a href="<%=request.getContextPath() %>/mav">ModelAndView</a> --%>
	
	<%-- EL 을 사용하여 request.getContext() 메서드와 동일한 작업 수행 --%>
	<%-- ${pageContext.request.contextPath} 사용 --%>
	<a href="${pageContext.request.contextPath }">홈</a> <%-- home.jsp --%>
	<a href="${pageContext.request.contextPath }/main3">메인페이지</a> <%-- test2/main.jsp --%>
	<a href="${pageContext.request.contextPath }/push">데이터전달</a> <%-- test2/push.jsp --%>
	<a href="${pageContext.request.contextPath }/redirect">리다이렉트</a> <%-- test2/redirect.jsp --%>
	<a href="${pageContext.request.contextPath }/mav">ModelAndView</a> <%-- test2/model_and_view.jsp --%>
	<%-- ================================================================== --%>
	<%-- MemberController 를 사용하여 login.me 서블릿 요청 처리 --%>
	<a href="${pageContext.request.contextPath }/login.me">로그인</a> <%-- test2/login_form.jsp --%>
	<%-- BoardController 를 사용하여 write.bo 서블릿 요청 처리 --%>
	<a href="${pageContext.request.contextPath }/write.bo">글쓰기</a> <%-- test2/write_form.jsp --%>
</div>













