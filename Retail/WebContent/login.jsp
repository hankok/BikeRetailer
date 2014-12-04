<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>Retail System</h1>
<h2>Login</h2>
<% if(request.getAttribute("error") != null) { %>
<h4><font color="red"><c:out value="${error}" /></font></h4>
<br>
<br>
<% } %>
<% if(request.getAttribute("success") != null) { %>
<h4><c:out value="${success}" /></h4>
<br>
<br>
<% } %>
<form action="login" method="post">
		<table>
			<tr>
				<td>
					<label>User Name </label>
				</td>
				<td>
					<input type="text" name="userName" placeholder="user Name" required>
				</td>
			</tr>
			<tr>
				<td>
					<label>Password </label>
				</td>
				<td>
					<input type="password" name="password" placeholder="password" required>
				</td>
			</tr>
		</table>
		<br>
		<input type="submit" name="submit" value="Login">
	</form>
	<br>
	<a href="createAccount.jsp">Registration</a>
</body>
</html>