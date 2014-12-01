 <%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE 
    html
    PUBLIC
    "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Login
    </title>
  </head>
  <body>
    <center>
      <h1>
      	Login
      </h1>
      <form id="form" name="form" method="get" action="login">
		 <table>
                <tr>
                    <td>Enter your Username (email id):</td>
                    <td><input type=text name=username></td>
                </tr>
                <tr>
                    <td>Enter your Password:</td>
                    <td><input type=password name=password></td>
                </tr>
                <tr>
                	<td></td>>
                    <td><input type=submit value=Login name=login></td>
                </tr>
          </table>

      </form>      
      <br>
      <br>
      <a href = "Signup.jsp">Not registered? >>Sign Up</a>
    </center>
  </body>
</html>
