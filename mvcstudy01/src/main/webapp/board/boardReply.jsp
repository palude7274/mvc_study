<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="app.domain.BoardVo" %>
<%
 if (session.getAttribute("midx") ==null){
	 out.println("<script>alert('로그인하셔야합니다.');location.href='"+request.getContextPath()+"/member/memberLogin.do'</script>");	 
 }
 	BoardVo bv = (BoardVo)request.getAttribute("bv");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>답변</title>
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
	fm.action ="<%=request.getContextPath()%>/board/boardReplyAction.do";  
	fm.method = "post";  //이동하는 방식  get 노출시킴 post 감추어서 전달
	//fm.enctype= "multipart/form-data";
	fm.submit(); //전송시킴
	return;
}
</script>
</head>
<body>
<h3>답변</h3>
<hr>
<form name="frm" class="write12">
<input type="hidden" name="bidx" value="<%=bv.getBidx()%>">
<input type="hidden" name="originbidx" value="<%=bv.getOriginbidx()%>">
<input type="hidden" name="depth" value="<%=bv.getDepth()%>">
<input type="hidden" name="level_" value="<%=bv.getLevel_()%>">

<table>
	<tr>
		<th>제목 :</th>
		<td>
			<input type="text" name="subject">
		</td>
	</tr>
	<tr>
		<th>내용 :</th>
		<td>
		<textarea name="contents" cols="50" rows="10"></textarea>	
		</td>
	</tr>
	<tr>
		<th>작성자 :</th>
		<td>
			<input type="text" name="writer">
		</td>
	</tr>
	<tr>
		<th>비밀번호 :</th>
		<td>
			<input type="password" name="pwd">
		</td>
	</tr>
	<tr>
		<th>파일첨부 :</th>
		<td>
			   <input type="file" name="filename"id="file">
		</td>
	</tr>
	<tr>
		<td class="smt" colspan="2">
		<input type="button" name="smt" value="확인" onclick="check();">
		</td>
	</tr>
</table>
</form>
</body>
</html>