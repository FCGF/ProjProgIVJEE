<%-- 
    Document   : notfoundcmd.jsp
    Created on : 18/10/2016, 00:29:43
    Author     : FABIO TAVARES DIPPOLD, FCGF
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>DEFAULT APP ERROR PAGE</title>    
    </head>

    <body>
        <!-- HEADER -->
        <%@include file="/WEB-INF/jspf/header.jspf"%>

        <!-- MAIN CONTAINER -->   
        <div id="main" class="container-fluid">
            <BR>
            <div class="alert alert-danger" role="alert">
                ${msg}
                <a href="signin.jsp" class="alert-link">Login Page</a>
            </div>        
        </div><!--/MAIN CONTAINER --> 

        <!-- FOOTER -->  
        <%@include file="/WEB-INF/jspf/footer.jspf"%>

    </body>
</html>
