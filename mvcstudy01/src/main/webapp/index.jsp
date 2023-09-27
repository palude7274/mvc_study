<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
</head>
<body>
기본 페이지입니다.
<br>
<% 
//String memberId ="";
//세션 값이 이다는 이야기는 로그인을 했다는 말
//if(session.getAttribute("memberId") !=null) {
//memberId = (String)session.getAttribute("memberId");
//out.println(memberId);
//out.println("<a href='"+request.getContextPath()+"/member/memberLogout.do'>로그아웃</a>");
//}
%>

<c:if test="${sessionScope.memberId != null }">
<c:out value="${sessionScope.memberId}" escapeXml="false"/>
<br>
<a href="${pageContext.request.contextPath}/member/memberLogout.do">로그아웃</a>

</c:if>
<br>
<a href="${pageContext.request.contextPath}/member/memberList.do">회원정보보기</a>
<br>
<a href="${pageContext.request.contextPath}/member/memberJoin.do">회원가입페이지</a>
<br>
<a href="${pageContext.request.contextPath}/member/memberLogin.do">회원로그인페이지</a>
<br>
<a href="${pageContext.request.contextPath}/board/boardList.do">게시판가기</a>
<br>
</body>
</html>