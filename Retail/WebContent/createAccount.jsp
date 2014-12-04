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
<h2>New User Registration</h2>
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
<form action="register" method="post">
		<table>
			<tr>
				<td>
					<label>First Name </label>
				</td>
				<td>
					<input type="text" name="firstName" placeholder="First Name" required>
				</td>
			</tr>
			<tr>
				<td>
					<label>Last Name </label>
				</td>
				<td>
					<input type="text" name="lastName" placeholder="Last Name" required>
				</td>
			</tr>
			<tr>
				<td>
					<label>Address </label>
				</td>
				<td>
					<input type="text" name="address" placeholder="address" required>
				</td>
			</tr>
			<tr>
				<td>
					<label>City </label>
				</td>
				<td>
					<input type="text" name="city" placeholder="City" required>
				</td>
			</tr>
			<tr>
				<td>
					<label>State </label>
				</td>
				<td>
					<input type="text" name="state" placeholder="state" required>
				</td>
			</tr>
			<tr>
				<td>
					<label>Zipcode </label>
				</td>
				<td>
					<input type="text" name="zipcode" placeholder="Zip code" required>
				</td>
			</tr>
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
		<input type="submit" name="submit" value="Sign Up">
	</form>
</body>
</html>