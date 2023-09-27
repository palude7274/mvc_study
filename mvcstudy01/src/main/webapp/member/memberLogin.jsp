<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>로그인</title>
</head>
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
<body>

<script>
function check(){
	
	//alert("체크함수 들어옴");
	
	var fm = document.frm; 

	if(fm.memberId.value ==""){
		alert("아이디를 입력하세요");
		fm.memberId.focus();
		return;
	}else if (fm.memberPwd.value ==""){
		alert("비밀번호를 입력하세요");
		fm.memberPwd.focus();
		return;		
	}
	fm.action ="<%=request.getContextPath()%>/member/memberLoginAction.do";  
	fm.method = "post";  
	fm.submit(); 
	return;
}
</script>


<h3>로그인 화면</h3>
<hr>
<form name="frm">
<table>
	<tr>
		<th>아이디 :</th>
		<td>
			<input type="text" name="memberId">
		</td>
	</tr>
	<tr>
		<th>비밀번호 :</th>
		<td>
			<input type="password" name="memberPwd">
		</td>
	</tr>
	<tr>
	 	<td colspan="2">
	 		<input type="button" name="smt" value="로그인" onclick="check();">
	 	</td>
	</tr>
</table>


</form>
</body>
</html>