<%-- 
    Document   : list
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
        <title>List ${name}</title>
    </head>
    <body>
        <!-- HEADER -->
        <%@include file="/WEB-INF/jspf/header.jspf"%>

        <div id="main" class="container-fluid">
            <div class="row">
                <div class="col-md-10 col-md-offset-1">
                    <h2>${name}</h2>
                    <hr/>
                    <a href="#" class="btn btn-info createButton" data-toggle="tooltip" data-placement="right" title="Create" data-action="Create">
                        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span></a>

                    <div class="form-inline pull-right" id="searchForm">
                        <div class="form-group">
                            <label for="search" class="sr-only">Search</label>
                            <input type="text" class="form-control search"　id="search" name="search" placeholder="Search"/>
                        </div>
                        <button type="button" class="btn btn-default searchButton" data-toggle="tooltip" data-placement="top" id="searchButton" title="Search">
                            <span class="glyphicon glyphicon-search"></span></button>
                    </div>
                </div>
            </div>

            <div id="lista" class="row">
                <div class="col-md-10 col-md-offset-1">
                    <c:if test="${empty objects}">
                        <h3>No results found!</h3>
                    </c:if>

                    <c:if test="${not empty objects}">
                        <table id="dataGrid" class="table table-striped table-responsive table-hover">
                            <thead>
                                <tr>
                                    <c:forEach var="field" items="${objects.get(0)}">
                                        <td>${field.getName()}</td>
                                    </c:forEach>
                                    <td> </td>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="fields" items="${objects}">
                                    <tr>
                                        <c:forEach var="field" items="${fields}">
                                            <c:choose>
                                                <c:when test="${field.isCombo()}">
                                                    <td><c:out value="${field.getValue().getNome()}"/></td>
                                                </c:when>
                                                <c:when test="${not field.isCombo()}">
                                                    <td><c:out value="${field.getValue()}"/></td>
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
                                        </c:forEach>
                                        <td>
                                            <span class="pull-right">
                                                <a href="#" class="btn btn-primary editButton" data-toggle="tooltip" data-placement="left" title="Edit" data-id="${id}" data-name="${nome}">
                                                    <span class="glyphicon glyphicon-edit" aria-hidden="true"></a>

                                                <a href="#" class="btn btn-success detailsButton" data-toggle="tooltip" data-placement="bottom" title="Details" data-id="${id}" data-name="${nome}">
                                                    <span class="glyphicon glyphicon-eye-open" aria-hidden="true"></a>

                                                <a href="#" class="btn btn-danger modal-link deleteButton" data-toggle="tooltip" data-placement="right" title="Delete" data-id="${id}" data-name="${nome}">
                                                    <span class="glyphicon glyphicon-remove-sign" aria-hidden="true"></a>
                                            </span>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:if>
                </div>
            </div>
        </div>

        <!-- FOOTER -->  
        <%@include file="/WEB-INF/jspf/footer.jspf"%>

    </body>
</html>
