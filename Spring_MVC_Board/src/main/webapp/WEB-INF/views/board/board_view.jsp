<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MVC 게시판</title>
<!-- 외부 CSS 가져오기 -->
<link href="${pageContext.request.contextPath }/resources/css/inc.css" rel="stylesheet" type="text/css">
<style type="text/css">
	#articleForm {
		width: 500px;
		height: 550px;
		margin: auto;
	}
	
	h2 {
		text-align: center;
	}
	
	table {
		border: 1px solid black;
		border-collapse: collapse; 
	 	width: 500px;
	}
	
	th {
		text-align: center;
	}
	
	td {
		width: 150px;
		text-align: center;
	}
	
	#basicInfoArea {
		height: 70px;
		text-align: center;
	}
	
	#articleContentArea {
		background: orange;
		margin-top: 20px;
		height: 350px;
		text-align: center;
		overflow: auto;
		white-space: pre-line;
	}
	
	#commandList {
		margin: auto;
		width: 500px;
		text-align: center;
	}
</style>
</head>
<body>
	<header>
		<!-- Login, Join 링크 표시 영역 -->
		<jsp:include page="../inc/top.jsp"></jsp:include>
	</header>
	<!-- 게시판 상세내용 보기 -->
	<section id="articleForm">
		<h2>글 상세내용 보기</h2>
		<section id="basicInfoArea">
			<table border="1">
			<tr><th width="70">제 목</th><td colspan="3" >${board.board_subject }</td></tr>
			<tr>
				<th width="70">작성자</th><td>${board.board_name }</td>
				<th width="70">작성일</th>
				<td><fmt:formatDate value="${board.board_date }" pattern="yy-MM-dd HH:mm:ss" /></td>
			</tr>
			<tr>
				<th width="70">첨부파일</th>
				<td>
					<%--
					[ 업로드 된 파일명에서 실제 파일명 추출하기 ]
					=> JSTL 의 함수를 활용(JSTL functions 라이브러리 추가 필요)
					1. 파일명 길이 계산(fn:length(문자열))
					2. 파일명에서 구분자의 인덱스 알아내기(fn:indexOf(대상문자열, 찾을문자열))
					3. 구분자의 인덱스 + 1 위치부터 마지막 인덱스(= 파일명 길이)까지를 추출하기
					   (fn:substring(대상문자열, 시작인덱스, 끝인덱스))
					--%>   
					<c:set var="length" value="${fn:length(board.board_file) }" />
					<c:set var="index" value="${fn:indexOf(board.board_file, '_') }" />
					<c:set var="fileName" value="${fn:substring(board.board_file, index + 1, length) }" />
					<%--					   
					[ 첨부파일 다운로드 ]
					- 하이퍼링크를 사용하여 컨텍스트경로/resources/upload 디렉토리 내의 파일 지정
					  (단, 업로드 시 현재 날짜를 폴더 구조로 사용했으므로 board_file_path 도 경로에 포함)
					- download 속성 설정 시 파일 다운로드가 자동으로 수행됨(HTML5 에서 지원)
					  => download 속성값으로 다운로드 될 파일명 지정하면 해당 이름으로 다운로드 됨
					--%>
					<a href="${pageContext.request.contextPath }/resources/upload/${board.board_file_path}/${board.board_file}" download="${fileName }">${fileName }</a>
				</td>
				<th width="70">조회수</th>
				<td>${board.board_readcount }</td>
			</tr>
			</table>
		</section>
		<section id="articleContentArea">
			${board.board_content }
		</section>
	</section>
	<section id="commandList">
		<input type="button" value="답변" onclick="location.href='BoardReplyForm.bo?board_num=${board.board_num}&pageNum=${param.pageNum}'">
		<input type="button" value="수정" onclick="location.href='BoardModifyForm.bo?board_num=${board.board_num}&pageNum=${param.pageNum}'">
		<input type="button" value="삭제" onclick="location.href='BoardDeleteForm.bo?board_num=${board.board_num}&pageNum=${param.pageNum}'">
		<input type="button" value="목록" onclick="location.href='BoardList.bo?pageNum=${param.pageNum}'">
	</section>
</body>
</html>
















