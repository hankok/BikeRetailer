<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
* <a href="display">Display Items</a>
* <a href="order">Order Items</a>
* <a href="search">Search Order</a>
* <a href="logout">logout</a>
<h1>Manage Order</h1>
<% if(request.getAttribute("error") != null) { %>
<h4><font color="red"><c:out value="${error}" /></font></h4>
<br>
<br>
<% } %>
<% if(request.getAttribute("msg") != null) { %>
<h4><c:out value="${msg}" /></h4>
<br>
<br>
<% } %>
<form action="orderhistory" method="post">
<label>Enter Order Id: </label>
<input type="text" name="orderid" placeholder="order id" required>
<br>
Set Status:
<select name="status">
  <option value="2">Complete</option>
  <option value="1">In progress</option>
</select>
<input type="submit" name="submit" value="Update">
</form>
</body>
</html>