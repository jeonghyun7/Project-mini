<%-- <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.*,sec02.ex02.*"
    isELIgnored="false" 
 %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<%
	request.setCharacterEncoding("UTF-8");
%>

<!DOCTYPE html>
<html>
<head>
	<c:choose>
		<c:when test='${msg=="addMember" }'>
		 <script>
		 	window.onload = function () {
		 		alert("회원을 등록했습니다.");
		 	}
		 </script>
		</c:when>
		<c:when test='${msg=="modified" }'>
		 <script>
		 	window.onload = function () {
		 		alert("회원 정보를 수정했습니다.");
		 		}
		 </script> 
		</c:when>
		<c:when test='${msg=="deleted" }'>
		 <script>
		 	window.onload = function () {
		 		alert("회원 정보를 삭제했습니다.");
		 	}
		 </script>
		</c:when>
	</c:choose>
	
<meta charset="UTF-8">
<title>회원 정보 출력창</title>
<style>
	.cls1 {
		font-size: 40px;
		text-align: center;
	}
	.cls2 {
		text-align: center;
	}
</style>
</head>
<body>
	<p class="cls1">회원정보</p>
	<table align="center" width="100%">
		<tr align="center" bgcolor="#99ccff">
			<td width="7%">아이디</td>
			<td width="7%">비밀번호</td>
			<td width="7%">이름</td>
			<td width="11%">이메일</td>
			<td width="7%">가입일</td>
			<td width="7%">수정</td>
			<td width="7%">삭제</td>
		</tr>
		<!-- choose when은 if,else구문 역할을 한다. -->
		<c:choose> 
			<c:when test="${empty membersList}">
				<tr>
					<td colspan=7>
						<b>등록된 회원이 없습니다.</b>
					 </td>
				</tr>
			</c:when>
			<c:when test="${!empty membersList}">
<!--범위안에서 반복문 수행 for와 동일한 역할을 한다. mem이라는 변수를 사용하여 
	리스트의 0인덱스 값부터 마지막인덱스까지 for문을 돌려 받음(items속성_List,Map일경우 필수)  -->
				<c:forEach var="mem" items="${membersList}">
					<tr align="center">
						<td>${mem.id }</td>
						<td>${mem.pwd }</td>
						<td>${mem.name }</td>
						<td>${mem.email }</td>
						<td>${mem.joinDate }</td>
						<td><a href="${contextPath}/member/modMemberForm.do?id=${mem.id }">수정</a></td>
						<td><a href="${contextPath}/member/delMember.do?id=${mem.id }">삭제</a></td>
					</tr>
				</c:forEach>
			</c:when>
		</c:choose>
	</table>
	<a href="${contextPath}/member/memberForm.do">
		<p class="cls2">회원 가입하기</p>
	</a>
</body>
</html> --%>