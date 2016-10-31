<%-- 
    Document   : mainMenu
    Created on : Oct 31, 2016, 7:46:54 PM
    Author     : FCGF
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Main Menu</title>
        <!-- Bootstrap -->
        <link href="/assets/css/bootstrap.min.css" rel="stylesheet">
        <!-- Bootstrap theme -->
        <link href="/assets/css/bootstrap-theme.min.css" rel="stylesheet">    

        <!-- Others -->
        <link href="/assets/css/jquery-ui.css" rel="stylesheet"/>
        <link href="/assets/css/bootstrap-social.css" rel="stylesheet"/>
        <link href="/assets/css/font-awesome.min.css" rel="stylesheet"/>
        <link href="https://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800" rel="stylesheet" type="text/css">
        <link href="https://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css">
        <link href="https://fonts.googleapis.com/css?family=Raleway:400,200,100,300,500,600,700,800,900" rel="stylesheet" type="text/css">
        <link href="https://fonts.googleapis.com/css?family=Dosis:400,200,300,500,600,700,800" rel="stylesheet" type="text/css">
    </head>
    <body>
        <!-- HEADER -->  
        <%@include file="/WEB-INF/jspf/header.jspf"%>

        <h1>Hello World Main Menu!</h1>

        <!-- FOOTER -->  
        <%@include file="/WEB-INF/jspf/footer.jspf"%>

        <!-- JQuery and Bootstrap -->
        <script src="assets/js/jquery-3.1.1.min.js"></script>
        <script src="assets/js/bootstrap.min.js"></script>

        <!-- Others -->
        <script src="assets/js/modernizr-2.6.2.js"></script>
        <script src="assets/js/jquery.unobtrusive-ajax.min.js"></script>
        <script src="assets/js/jquery.easing.min.js"></script>
        <script src="assets/js/jquery-ui.js"></script>

        <script type="text/javascript">
            $(document).ready(function () {

            });
        </script>

    </body>
</html>
