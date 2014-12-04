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
* <a href="order">Order Items</a>
* <a href="search">Search Order</a>
* <a href="logout">logout</a>
<h1>Inventory Details</h1>
<table border=1>
<tr>
<th>itemNumber</th>
<th>Name</th>
<th>Price</th>
<th>quantity</th>
</tr>
<c:forEach var="item" items="${items}">
<tr><td>${item.modelNumber}</td>
<td>${item.description}</td>
<td>${item.price}</td>
<td>${item.quantity}</td>
</tr>
</c:forEach>
</table>
</body>
</html>