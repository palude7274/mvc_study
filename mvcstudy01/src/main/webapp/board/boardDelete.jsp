<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="app.domain.BoardVo" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%// BoardVo bv = (BoardVo)request.getAttribute("bv"); %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>글 삭제</title>
<style type="text/css">
input[type=text] {
	border : 2px solid rgb(0, 64, 128);
	border-radius : 10px;
}
input[type=password] {
	border : 2px solid rgb(0, 64, 128);
	border-radius : 10px;
}
table {
	margin : auto;
	text-align : center;
}
h3 {
	text-align : center;
}

</style>

<script type="text/javascript">
	function check(){
		//alert("클릭확인");
		var fm = document.frm;
		
		if(fm.pwd.value ==""){
			alert("비밀번호를 입력해주세요");
			document.frm.pwd.focus();
			return;
		}
		
		<%-- fm.action="<%=request.getContextPath()%>/board/boardDeleteAction.do"; --%>
		fm.action="${pageContext.request.contextPath}/board/boardDeleteAction.do";
		fm.method="post";
		fm.submit();
		
		return;
	}
</script>
</head>
<body>
<h3>글 삭제</h3>
<hr>

<form name="frm">
<input type="hidden" name="bidx" value="<%//=bv.getBidx()%>${bv.bidx}">
<table>
	<tr>
		<th>제목 :</th>
		<td>
			<%//=bv.getSubject() %>
			${bv.subject}
		</td>
	</tr>
	<tr>
		<th>비밀번호 :</th>
		<td>
			<input type="password" name="pwd">
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