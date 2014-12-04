<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script>

        function registerSSE()
        {
            var source = new EventSource('http://localhost:8080/Retail/test');  
            source.onmessage=function(event)
            {
            	alert(event.data);
                document.getElementById("result").innerHTML+=event.data + "<br />";
            };

            /*source.addEventListener('server-time',function (e){
                alert('ea');
            },true);*/
        }
</script>
</head>
<body onload="registerSSE()">
* <a href="display">Display Items</a>
* <a href="search">Search Order</a>
* <a href="logout">logout</a>
<h1>Place Order</h1>
<hr>
<p id="result"></p>
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
<form action="order" method="post">
<table>
<tr>
<td>
<label>Item Number: </label>
</td>
<td>
<input type="text" name="itemNumber" placeholder="#itemNumber" required>
</td>
</tr>
<tr>
<td>
<label>Quantity: </label>
</td>
<td>
<input type="text" name="quantity" placeholder="quantity" required>
</td>
</tr>
</table>
<br>
<input type="submit" name="submit" value="Place Order">
</form>
</body>
</html>