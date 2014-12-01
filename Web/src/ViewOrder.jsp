<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE 
    html
    PUBLIC
    "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>
      View Order
    </title>
    Logon User：<%= session.getAttribute("username") %>
  </head>
  <body>
    <center>
      <h1>
        View Order
      </h1>
      <form id="form" name="form" method="get" action="viewOrder">
       <table>
                <tr>
                    <td> View my order:</td>
                    <td> <input name="submit" type="submit" value=View/></td>
                </tr>
          </table>
      </form>
      <br>
      <br>
      <br>
      <a href = "ViewInventory.jsp">Home Page</a>
    </center>
  </body>
</html>
