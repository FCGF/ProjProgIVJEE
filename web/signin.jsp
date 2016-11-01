<%-- 
    Document   : signin
    Created on : Oct 17, 2016, 7:19:00 PM
    Author     : FCGF
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="SignIn">
        <meta name="author" content="Fernando Garcia">
        <link rel="icon" href="assets/icons/qb-icon.png">
        <title>Log In</title>
         <!-- Bootstrap -->
        <link href="assets/core/bootstrap-3.3.5-dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Bootstrap theme -->
        <link href="assets/core/bootstrap-3.3.5-dist/css/bootstrap-theme.min.css" rel="stylesheet"> 
    </head>
    <body style="background: #F7F7F7;">
        <div id="main" class="container-fluid">
            <h1 class="page-header col-md-offset-4">Autenticação</h1>
            <form id="formSignIn" name="formSignIn" method="POST" action="signin">
                <div class="row">
                    <div class="form-group col-md-4 col-md-offset-4">
                        <label for="email">E-mail:</label>
                        <input type="email" class="form-control" id="email" name="email" required="required" placeholder="Digite seu nome de usuario" maxlength="50"/>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group col-md-4 col-md-offset-4">
                        <label for="password">Password:</label>
                        <input type="password" class="form-control" id="password" name="password" required="required" placeholder="Digite sua senha" maxlength="50"/>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group col-md-4 col-md-offset-4">
                        <input type="submit" class="btn btn-primary" id="submitBtn" name="submitBtn" required="required" value="Log In"/>
                        <p style="color: red;">${msg}</p> 
                    </div>
                </div>
            </form>
        </div>

        <script type="text/javascript" src="assets/core/jquery-2.1.1.min.js"></script>
        <script type="text/javascript" src="assets/core/bootstrap-3.3.5-dist/js/bootstrap.min.js"></script> 

    </body>
</html>
