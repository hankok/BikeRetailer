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
      Login
    </title>
  </head>
  <body>
    <center>
      <h1>
      	Login
      </h1>
      <form id="form" name="form" method="get" action="login">
        <label>
          username:
        </label>
        <input name="username" type="text" />
        
        <label>
          password:
        </label>
        <input name="password" type="password" />

        <input name="login" type="submit"/>
      </form>
      
      <br>
      <br>
      <a href = "Signup.jsp">Not registered? >>Sign Up</a>
    </center>
  </body>
</html>
