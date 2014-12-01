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
      Sign Up
    </title>
  </head>
  <body>
    <center>
      <h1>
      	Sign Up
      </h1>
      <form id="form" name="form" method="get" action="Signup">
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
                    <td>Enter your Name (First/Last):</td>
                    <td><input type=text name=customerName></td>
                </tr>
                <tr>
                    <td>Enter your Address:</td>
                    <td><input type=text name=Address></td>
                </tr>
                <tr>
                    <td>Enter your City:</td>
                    <td><input type=text name=City></td>
                </tr>
                                <tr>
                    <td>Enter your State:</td>
                    <td><input type=text name=State></td>
                </tr>
                                <tr>
                    <td>Enter your Zipcode:</td>
                    <td><input type=text name=Zipcode></td>
                </tr>
                <tr>
	                <td><input type=reset value=Refresh></td>
                    <td><input type=submit value=Submit></td>
                </tr>
            </table>
        
      </form>
If already a member: <a href="/Final_AWS/Login.jsp">Log in</a>
    </center>
  </body>
</html>
