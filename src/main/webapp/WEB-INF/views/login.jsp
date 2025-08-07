<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
 <h2>Login</h2>
    <h2>Login</h2>
<form action="/api/loginController" method="post">
    <label for="username">Email:</label>
    <input type="text" name="username" required>

    <label>Password:</label><br>
    <input type="password" name="password" required><br><br>

    <button type="submit">Login</button>
</form>
<a href="registration.jsp">Register here</a>
</body>
</html>