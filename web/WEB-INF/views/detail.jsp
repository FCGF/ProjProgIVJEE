<%-- 
    Document   : detail
    Created on : Nov 1, 2016, 6:09:38 PM
    Author     : FCGF
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Detail ${name}</title>
    </head>
    <body>
        <!-- HEADER -->
        <%@include file="/WEB-INF/jspf/header.jspf"%>
        <div id="main" class="container-fluid">
            <div class="row col-md-10 col-md-offset-1">
                <h2>${name}</h2>
            </div>
            <c:forEach var="field" items="${fields.entrySet()}">
                <div class="row col-md-10 col-md-offset-1">
                    <h3><c:out value="${field.getKey()}"/>: <c:out value="${field.getValue()}"/></h3>
                </div>
            </c:forEach>
            <div class="row col-md-10 col-md-offset-1 pull-left">
                <a href="#" class="btn btn-success listButton" title="List" data-id="${fields.get("Id")}" data-name="${fields.get("Nome")}">
                    <span class="glyphicon glyphicon-list" aria-hidden="true"></a>

                <a href="#" class="btn btn-primary editButton" title="Edit" data-id="${fields.get("Id")}" data-name="${fields.get("Nome")}">
                    <span class="glyphicon glyphicon-edit" aria-hidden="true"></a>

                <a href="#" class="btn btn-danger modal-link deleteButton" title="Delete" data-id="${fields.get("Id")}" data-name="${fields.get("Nome")}">
                    <span class="glyphicon glyphicon-remove-sign" aria-hidden="true"></a>
            </div>
        </div
        
        <!--Link: prog4/mvc?cmd=${cmd}&mtd=${mtd}-->
        
        <!-- FOOTER -->  
        <%@include file="/WEB-INF/jspf/footer.jspf"%>

        
    </body>
</html>
