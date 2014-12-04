<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<br>
* <a href="display">Display Items</a>
* <a href="order">Order Items</a>
* <a href="orderhistory">Manage Order</a>
* <a href="search">Search Order</a>
* <a href="logout">logout</a>
<h1>Retailer System</h1>
	<form action="display" method="post">
		<table>
			<tr>
				<td>
					<label>Enter Supplier 1 IP </label>
				</td>
				<td>
					<input type="text" name="supplierIp1" placeholder="localhost" value="localhost" required>
				</td>
			</tr>
			<tr>
				<td>
					<label>Enter Supplier 1 Port </label>
				</td>
				<td>
					<input type="text" name="supplierPort1" placeholder="1099" value="1099" required>
				</td>
			</tr>
			<tr>
				<td>
					<label>Enter Supplier 2 IP </label>
				</td>
				<td>
					<input type="text" name="supplierIp2" placeholder="localhost" value="localhost" required>
				</td>
			</tr>
			<tr>
				<td>
					<label>Enter Supplier 2 Port </label>
				</td>
				<td>
					<input type="text" name="supplierPort2" placeholder="10998" value="10998" required>
				</td>
			</tr>
		</table>
		<br>
		<input type="submit" name="submit" value="Update Config">
	</form>
</body>
</html>