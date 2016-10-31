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
        <!-- Bootstrap -->
        <link href="assets/core/bootstrap-3.3.5-dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Bootstrap theme -->
        <link href="assets/core/bootstrap-3.3.5-dist/css/bootstrap-theme.min.css" rel="stylesheet">        
    </head>

    <body>

        <!-- MAIN CONTAINER -->   
        <div id="main" class="container-fluid">
            <BR>
            <h2>${appName}</h2>
            <!-- LINHA-2 -->
            <div class="row">
                <div class="form-group col-md-4">
                    <label for="comboRule">Regra:</label>
                    <!-- COMBOBOX RULE -->
                    <SELECT id="comboRule" name="comboRule" size="1" class="form-control">
                        <c:forEach var="rule" items="${rules}">
                            <option value="${rule.id}">${rule.nome}</option>
                        </c:forEach>                                    
                    </SELECT><!-- /COMBOBOX RULE -->                      
                </div>
            </div><!-- LINHA-2 -->            

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
