<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="app.domain.BoardVo" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%
//BoardVo bv = (BoardVo)request.getAttribute("bv");
%>      
    
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>글수정</title>
<script>
function check(){

	var fm = document.frm; //문서객체안의 폼객체이름
	
	if(fm.subject.value ==""){
		alert("제목을 입력하세요");
		fm.subject.focus();
		return;
	}else if (fm.contents.value ==""){
		alert("내용을 입력하세요");
		fm.contents.focus();
		return;		
	}else if (fm.writer.value ==""){
		alert("작성자를 입력하세요");
		fm.writer.focus();
		return;		
	}else if (fm.pwd.value ==""){
		alert("비밀번호를 입력하세요");
		fm.pwd.focus();
		return;		
	}	
	
	//처리하기위해 이동하는 주소
	fm.action ="${pageContext.request.contextPath}/board/boardModifyAction.do";  
	fm.method = "post";  //이동하는 방식  get 노출시킴 post 감추어서 전달
	//fm.enctype= "multipart/form-data";
	fm.submit(); //전송시킴
	return;
}
</script>
<style type="text/css">
table {
	margin : auto;
}
input[type=text]{
	width:300px;
	border : 2px solid rgb(0, 64, 128);
	border-radius : 10px;
}
input[type=password]{
	width:300px;
	border : 2px solid rgb(0, 64, 128);
	border-radius : 10px;
}

input[type=submit]{
	width: 100px;
}
.write12 .wbody {
	table-layout: fixed;
    height: 300px; white-space: pre-line; word-break: break-all
}
h3 {
	text-align : center;
}
.smt {
	text-align : right;
}
textarea{
	border : 2px solid rgb(0, 64, 128);
	border-radius : 10px;
}

</style>

</head>
<body>

<h3>글수정페이지</h3>
<hr>
	<form name="frm" class="write12">
	<input type="hidden" name="bidx" value="<%//=bv.getBidx()%>${bv.bidx}">
		<table>
		<tr>
		<th>제목</th>
		<td>
		<input type="text" name="subject" value="<%//=bv.getSubject()%>${bv.subject}">		
		</td>
		</tr>
		<tr>
		<th>내용</th>
		<td>
		<textarea name="contents" cols="50" rows="10">
		<%//=bv.getContents().trim() %>
		<c:set var="contents" value="${fn:trim(bv.contents)}"/>
		</textarea>		
		</td>
		</tr>
		<tr>
		<th>작성자</th>
		<td>
		<input type="text" name="writer" value="<%//=bv.getWriter()%>${bv.writer}">		
		</td>
		</tr>
		<tr>
		<th>비밀번호</th>
		<td>
		<input type="password" name="pwd">		
		</td>
		</tr>		
		<tr>
		<th>파일첨부</th>
		<td>
		<input type="file" name="filename">		
		</td>
		</tr>
		<tr>
		<td class="smt" colspan="2" style="text-align:center;">
		<input type="button" name="smt" value="확인" onclick="check();"><!--데이터전송기능버튼 -->
		</td>
		</tr>
</table>
</form>
</body>
</html>