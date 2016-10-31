<%-- 
    Document   : notfoundcmd.jsp
    Created on : 18/10/2016, 00:29:43
    Author     : FABIO TAVARES DIPPOLD
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>DEFAULT APP ERROR PAGE</title>
        <!-- Bootstrap -->
        <link href="assets/core/bootstrap-3.3.5-dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Bootstrap theme -->
        <link href="assets/core/bootstrap-3.3.5-dist/css/bootstrap-theme.min.css" rel="stylesheet">        
    </head>
    
    <body>
        
        <!-- MAIN CONTAINER -->   
        <div id="main" class="container-fluid">
            <BR>
            <div class="alert alert-danger" role="alert">
                ${msg}
                <a href="signin.jsp" class="alert-link">Login Page</a>
            </div>        
        </div><!--/MAIN CONTAINER --> 

        <!-- CORE JAVASCRIPT LYBRARIES...
        ================================================== -->
        <script type="text/javascript" src="assets/core/jquery-2.1.1.min.js"></script>
        <script type="text/javascript" src="assets/core/bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>

        <script type="text/javascript">
            $(document).ready(function () {

            });
        </script>

    </body>
</html>
