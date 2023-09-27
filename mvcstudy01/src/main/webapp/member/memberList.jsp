<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="java.util.*" %>
<%@ page import="app.domain.*" %>

<% 
// forward는 공유속성때문에 넘겨받을 수 있다.
ArrayList<MemberVo> list = (ArrayList<MemberVo>)request.getAttribute("list");
%>


<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>회원목록</title>
<style>
table {
	border : 1px solid blue;
	margin : auto
}
thead, tfoot{
	background: darkgray;
	color : yellow;
}
thead th{
	height : 40px;
	width : 100px;	
}
td, th{
	border : 1px dotted green;
	height : 20px;
	width : 200px;
	padding : 5px;
	text-align : center;
}
tbody tr:nth-child(even){
	background : aliceblue;
}
tbody tr:hover{
	background : pink;
}
thead tr:hover{
	background : rgb(64, 0, 64);
}
h3 {
	text-align : center;
}
</style>
</head>
<body>
<h3>회원목록</h3>
<hr>
<table>
	<thead>
		<tr>
			<th>회원번호</th>
			<th>회원아이디</th>
			<th>회원이름</th>
			<th>가입일</th>
		</tr>
	</thead>
	<tbody>
		<% for(MemberVo mv : list) {%>
		<tr>
			<td><%=mv.getMidx() %></td>
			<td><%=mv.getMemberId() %></td>
			<td><%=mv.getMemberName()%></td>
			<td><%=mv.getWriteday()%></td>
		</tr>
		<% }%>
	</tbody>
</table>
</body>
</html>