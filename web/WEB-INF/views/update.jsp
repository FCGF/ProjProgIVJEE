<%-- 
    Document   : create
    Created on : 05/11/2016, 17:34:04
    Author     : Fernando
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create ${name}</title>
    </head>
    <body>
        <!-- HEADER -->
        <%@include file="/WEB-INF/jspf/header.jspf"%>

        <div id="main" class="container-fluid">

            <h2 class="page-header">Edit ${name}</h2>
            <form id="formEdit" name="formEdit" method="POST" action="mvc?cmd=${cmd}&mtd=editAndList">
                <c:forEach var="field" items="${fields}">
                    <div class="row">
                        <div class="form-group col-md-4 col-md-offset-4">
                            <label for="<c:out value="${field.getName()}"/>"><c:out value="${field.getName()}"/>:</label>

                            <c:choose>
                                <c:when test="${field.isCombo()}">
                                    <SELECT id="<c:out value="${field.getName()}"/>" 
                                            name="<c:out value="${field.getName()}"/>" 
                                            size="1" 
                                            class="form-control"
                                            data-property="<c:out value="${field.getName()}"/>">

                                        <c:forEach var="option" items="${field.getAllValues()}">
                                            <option value="${option.getId()}" ${option == field.getValue() ? 'selected="selected"' : ''}>${option.getNome()}</option>
                                        </c:forEach>

                                    </SELECT>
                                </c:when>
                                <c:when test="${not field.isCombo()}">

                                    <c:choose>
                                        <c:when test="${field.getType() == 'ID'}">
                                            <p type="text" 
                                               class="form-control-static">${field.getValue()}</p>
                                            <input type="hidden"
                                                   id="<c:out value="${field.getName()}"/>" 
                                                   name="<c:out value="${field.getName()}"/>"
                                                   data-property="<c:out value="${field.getName()}"/>"
                                                   value="${field.getValue()}"> 
                                        </c:when>
                                        <c:when test="${field.getType() == 'NUMBER'}">
                                            <input type="number" 
                                                   min="0"
                                                   class="form-control" 
                                                   id="<c:out value="${field.getName()}"/>" 
                                                   name="<c:out value="${field.getName()}"/>" 
                                                   required="required" 
                                                   placeholder="Inform the <c:out value="${field.getName()}"/>" 
                                                   maxlength="100"
                                                   data-property="<c:out value="${field.getName()}"/>"
                                                   value="${field.getValue()}">
                                            <span id="<c:out value="${field.getName()}"/>Count" 
                                                  class="label label-warning">100 left!</span>
                                        </c:when>
                                        <c:when test="${field.getType() == 'PASSWORD'}">
                                            <input type="password" 
                                                   class="form-control" 
                                                   id="<c:out value="${field.getName()}"/>" 
                                                   name="<c:out value="${field.getName()}"/>" 
                                                   required="required" 
                                                   placeholder="Inform the <c:out value="${field.getName()}"/>" 
                                                   maxlength="100"
                                                   data-property="<c:out value="${field.getName()}"/>"
                                                   value="${field.getValue()}">
                                            <span id="<c:out value="${field.getName()}"/>Count" 
                                                  class="label label-warning">100 left!</span>
                                        </c:when>
                                        <c:when test="${field.getType() == 'TEXT'}">
                                            <input type="text" 
                                                   class="form-control" 
                                                   id="<c:out value="${field.getName()}"/>" 
                                                   name="<c:out value="${field.getName()}"/>" 
                                                   required="required" 
                                                   placeholder="Inform the <c:out value="${field.getName()}"/>" 
                                                   maxlength="100"
                                                   data-property="<c:out value="${field.getName()}"/>"
                                                   value="${field.getValue()}">
                                            <span id="<c:out value="${field.getName()}"/>Count" 
                                                  class="label label-warning">100 left!</span>
                                        </c:when>
                                        <c:when test="${field.getType() == 'EMAIL'}">
                                            <input type="email" 
                                                   class="form-control" 
                                                   id="<c:out value="${field.getName()}"/>" 
                                                   name="<c:out value="${field.getName()}"/>" 
                                                   required="required" 
                                                   placeholder="Inform the <c:out value="${field.getName()}"/>" 
                                                   maxlength="100"
                                                   data-property="<c:out value="${field.getName()}"/>"
                                                   value="${field.getValue()}">
                                            <span id="<c:out value="${field.getName()}"/>Count" 
                                                  class="label label-warning">100 left!</span>
                                        </c:when>
                                    </c:choose>

                                </c:when>

                            </c:choose>
                        </div>
                    </div>

                </c:forEach> 

                <div class="row col-md-12 text-center">
                    <button type="submit" class="btn btn-success" data-toggle="tooltip" data-placement="left" title="Edit">Edit</button>
                    <button type="reset" class="btn btn-primary" data-toggle="tooltip" data-placement="bottom" title="Reset" id="cleanButton">Reset</button>
                    <button type="button" class="btn btn-warning listButton" data-toggle="tooltip" data-placement="right" title="Cancel">Cancel</button>
                </div>

            </form>
        </div>

        <!-- FOOTER -->  
        <%@include file="/WEB-INF/jspf/footer.jspf"%>

        <script type="text/javascript">
            $(document).ready(function () {

                $('input').keyup(function () {
                    var currentSize = $(this).val().length;
                    var maxSize = 100;

                    var remainingSize = maxSize - currentSize;

                    var countName = "#" + $(this).attr("name") + "Count";

                    $(countName).text(remainingSize + " left!");
                });

                $('#cleanButton').click(function () {
                    $('#formEdit input').val("");
                    $("#formEdit span").text("100 left!");
                });

            });
        </script>
    </body>
</html>
