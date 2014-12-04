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
* <a href="display">Display Items</a>
* <a href="order">Order Items</a>
* <a href="logout">logout</a>
<h1>Search Order</h1>
<br>
<c:if test="${fn:length(orders) gt 0}">
<table border=1>
<tr>
<th>orderId</th>
<th>firstName</th>
<th>lastName</th>
<th>itemNumber</th>
<th>quantity</th>
<th>price</th>
<th>status</th>
</tr>
<c:forEach var="order" items="${orders}">
<tr><td>${order.orderId}</td>
<td>${order.firstName}</td>
<td>${order.lastName}</td>
<td>${order.itemNumber}</td>
<td>${order.quantity}</td>
<td>${order.price}</td>
<td>${order.status}</td>
</tr>
</c:forEach>
</table>
</c:if>
</body>
</html>