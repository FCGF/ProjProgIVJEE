<%-- 
    Document   : detail
    Created on : Nov 1, 2016, 6:09:38 PM
    Author     : FCGF
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
            <div class="row col-md-6 col-md-offset-3">
                <h2>${name} Details</h2>
                <hr/>
            </div>
            <c:forEach var="field" items="${fields}">
                <div class="row col-md-6 col-md-offset-3">
                    <c:choose>
                        <c:when test="${field.isCombo()}">
                            <h4><b><c:out value="${field.getName()}"/>:</b></h4> <p><c:out value="${field.getValue().getNome()}"/></p>
                        </c:when>
                        <c:when test="${not field.isCombo()}">
                            <h4><b><c:out value="${field.getName()}"/>:</b></h4> <p><c:out value="${field.getValue()}"/></p>
                        </c:when>
                    </c:choose>
                    <c:choose>
                        <c:when test="${field.getName() == 'Id'}">
                            <c:set var="id" scope="page" value="${field.getValue()}"/>
                        </c:when>
                        <c:when test="${field.getName() == 'Nome'}">
                            <c:set var="nome" scope="page" value="${field.getValue()}"/>
                        </c:when>
                    </c:choose>
                </div>
            </c:forEach>
            <div class="row col-md-12 text-center">
                <a href="#" class="btn btn-primary editButton" data-toggle="tooltip" data-placement="left" title="Edit" data-id="${id}" data-name="${nome}">
                    <span class="glyphicon glyphicon-edit" aria-hidden="true"></a>

                <a href="#" class="btn btn-success listButton" data-toggle="tooltip" data-placement="bottom" title="List" >
                    <span class="glyphicon glyphicon-list" aria-hidden="true"></a>

                <a href="#" class="btn btn-danger modal-link deleteButton" data-toggle="tooltip" data-placement="right" title="Delete" data-id="${id}" data-name="${nome}">
                    <span class="glyphicon glyphicon-remove-sign" aria-hidden="true"></a>
            </div>
        </div

        <!-- FOOTER -->  
        <%@include file="/WEB-INF/jspf/footer.jspf"%>
    </body>
</html>
