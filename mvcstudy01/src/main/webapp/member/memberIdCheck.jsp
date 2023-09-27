<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="app.dao.MemberDao" %>

<%
MemberDao md = new MemberDao();
String memberId = request.getParameter("memberId");
int value = md.memberIdCheck(memberId);

String str = "{\"cnt\" : \""+value+"\" }";
out.println(str);
%>
