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
      <form id="form" name="form" method="get" action="signup">
        <label>
          username:
        </label>
        <input name="username" type="text" />
        
        <label>
          password:
        </label>
        <input name="password" type="text" />
      
        <label>
          Name - Fisrt/Last:
        </label>
        <input name="customerName" type="text" />
        
        <label>
          Address:
        </label>
        <input name="Address" type="text" />

        <label>
          City:
        </label>
        <input name="City" type="text" />
        
        <label>
          State:
        </label>
        <input name="State" type="text" />

        <label>
          Zipcode:
        </label>
        <input name="Zipcode" type="text" />

        <input name="signup" type="submit"/>
        
        
      </form>

    </center>
  </body>
</html>