<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="app.domain.BoardVo" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
//	if (session.getAttribute("midx") ==null){
//		 out.println("<script>alert('로그인하셔야 합니다.');location.href='"+request.getContextPath()+"/member/memberLogin.do'</script>");	 
//	}
	
//	BoardVo bv = (BoardVo)request.getAttribute("bv");
%>   
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>글내용보기</title>

<c:if test="${sessionScope.midx == null}">
<script>alert("로그인하셔야 합니다.");
<c:set var="login" value="${pageContext.request.contextPath}/member/memberLogin.do" />
location.href="${login}";
</script>
</c:if>

<link href="./css/board.css" type="text/css" rel="stylesheet">
<!-- 1.cdn주소걸고 (라이브러리) -->
<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
<script type="text/javascript">
//자동실행영역
$(document).ready(function(){
	$.boardCommentList();
	
	//버튼을 클릭하면 입력된 데이터를 가지고 commentWrite.do로 넘겨서 DB에 입력한다 
	$("#save").on("click",function(){
		//alert("클릭");
		
		let cwriter = $("#cwriter").val();
		let ccontents = $("#ccontents").val();
		let bidx = ${bv.bidx};
		let midx = ${sessionScope.midx};
		
		$.ajax({
			type : "post",
			url : "${pageContext.request.contextPath}/comment/commentWrite.do",
			dataType : "json",
			data : {
					"bidx" : bidx,
					"midx" : midx,
					"cwriter" : cwriter,
					"ccontents" : ccontents				
			},			
			cache : false,
			success : function(data){
				//alert("통신성공");	
				//alert(data.value);
				//if (data.value ==1){
					//alert("등록성공");
				//}
				$.boardCommentList();
				$("#cwriter").val("");
				$("#ccontents").val("");
							
			},
			error : function(){
				alert("통신오류 실패");				
			}		
		});		
	});		
});


$.boardCommentList= function(){	
	
	$.ajax({
		type : "get",
		url : "${pageContext.request.contextPath}/comment/commentList.do",
		dataType : "json",
		cache : false,
		success : function(data){
			//alert("통신성공");
			
				commentList(data);
				//$.each(data, function(index){				   	
			     //  alert(data[index].midx);		
			      //});			
		},
		error : function(){
			alert("통신오류 실패");
			
		}		
	});
	
}

function commentDel(cidx){
	
	$.ajax({
		type : "get",
		url : "${pageContext.request.contextPath}/comment/commentDelete.do?cidx="+cidx,
		dataType : "json",
		cache : false,
		success : function(data){
			//alert("통신성공");
			//if (data.value ==1){
			//		alert("삭제성공");
			//	}			
			
				$.boardCommentList();
				//$.each(data, function(index){				   	
			     //  alert(data[index].midx);		
			      //});			
		},
		error : function(){
			alert("통신오류 실패");			
		}		
	});	
	
	return;
}



function commentList(data){
	
	var str="";
	str = "<tr><td>번호</td><td>작성자</td><td>내용</td><td>등록일</td><td>삭제</td></tr>"
	
	var delBtn= "";		
	
	var loginMidx = "${sessionScope.midx}"; 
		
	$(data).each(function(){
		
		if (loginMidx == this.midx){
			delBtn= "<button type='button' id='btn' onclick='commentDel("+this.cidx+");'>삭제</button>";
		}else{
			delBtn="";
		}
		
		str = str + "<tr><td>"+this.cidx+"</td><td>"+this.cwriter+"</td><td>"+this.ccontents+"</td><td>"+this.cwriteday+"</td><td>"+delBtn+"</td></tr>"	
			
	});
	
	$("#tbl").html("<table border=1 style='width:600px'>"+str+"</table>");
	
	return;
}


</script>
</head>
<body>
<h1>게시판 글내용</h1>
		<table border=1 style="width:600px;">
		<tr>
		<th>제목</th>
		<td>${bv.subject}</td>
		<th>조회수</th>
		<td>${bv.viewcnt}</td>
		</tr>
		<tr>
		<th style="height:200px;">내용</th>
		<td colspan=3>${bv.contents}</td>
		</tr>
		<tr>
		<th>작성자</th>
		<td colspan=3>${bv.writer}</td>
		</tr>
		<tr>
		<th></th>
		<td style="text-align:right;">
		<button type="button" onclick="location.href='${pageContext.request.contextPath}/board/boardModify.do?bidx=${bv.bidx}'">수정</button>
		<button type="button" onclick="location.href='${pageContext.request.contextPath}/board/boardDelete.do?bidx=${bv.bidx}'">삭제</button>
		<button type="button" onclick="location.href='${pageContext.request.contextPath}/board/boardReply.do?bidx=${bv.bidx}&originbidx=${bv.originbidx}&depth=${bv.depth}&level_=${bv.level_}'">답변</button>
		<button type="button" onclick="location.href='${pageContext.request.contextPath}/board/boardList.do'">목록</button>			
		</td>
		</tr>				
</table>
<br>
<table border=1 style="width:600px;">
<tr>
<td>작성자</td>
<td>
<input type="text" name="cwriter" id="cwriter" size="20">
</td>
<td rowspan=2>
<input type="button" name="btn" value="저장" id="save"> </td>
</tr>
<tr>
<td>내용</td>
<td>
<textarea name="ccontents" id="ccontents" cols="50" rows="3" placeholder="내용입력하세요"></textarea>
</td>
</tr>
</table>

<div id="tbl"></div>

</body>
</html>