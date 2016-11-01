<%-- 
    Document   : list
    Created on : Nov 1, 2016, 6:09:38 PM
    Author     : FCGF
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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
                    <br/>
                    <a href="#" class="btn btn-info createButton" title="Create" data-action="Create">
                        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span></a>

                    <form class="form-inline pull-right" id="searchForm">
                        <div class="form-group">
                            <label for="search" class="sr-only">Search</label>
                            <input type="text" class="form-control" name="search" placeholder="Search"/>
                        </div>
                        <button type="button" class="btn btn-warning" id="searchButton" title="Search">
                            <span class="glyphicon glyphicon-search"></span></button>
                    </form>
                </div>
            </div>

            <div id="lista" class="row">
                <div class="col-md-10 col-md-offset-1">
                    <table id="dataGrid" class="table table-striped table-responsive table-hover">
                        <thead>
                            <tr>
                                <c:forEach var="field" items="${objects.get(0).entrySet()}">
                                    <td>${field.getKey()}</td>
                                </c:forEach>
                                <td> </td>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="fields" items="${objects}">
                                <tr>
                                    <c:forEach var="field" items="${fields.entrySet()}">
                                        <td><c:out value="${field.getValue()}"/></td>
                                    </c:forEach>
                                    <td>
                                        <span class="pull-right">
                                            <a href="#" class="btn btn-primary editButton" title="Edit" data-id="${fields.get("Id")}" data-name="${fields.get("Nome")}">
                                                <span class="glyphicon glyphicon-edit" aria-hidden="true"></a>

                                            <a href="#" class="btn btn-success detailsButton" title="Details" data-id="${fields.get("Id")}" data-name="${fields.get("Nome")}">
                                                <span class="glyphicon glyphicon-eye-open" aria-hidden="true"></a>

                                            <a href="#" class="btn btn-danger modal-link deleteButton" title="Delete" data-id="${fields.get("Id")}" data-name="${fields.get("Nome")}">
                                                <span class="glyphicon glyphicon-remove-sign" aria-hidden="true"></a>
                                        </span>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <!-- FOOTER -->  
        <%@include file="/WEB-INF/jspf/footer.jspf"%>

        <script type="text/javascript">
            $(document).ready(function () {
                $(".deleteButton").click(function () {
                    var id = $(this).data("id");
                    var name = $(this).data("name");

                    bootbox.confirm("Are you sure you wish to delete the element " + id + " - " + name + "?", function (result) {
                        if (result) {
                            alert("Deletar");
                        }
                    });
                });
            });
        </script>
    </body>
</html>
