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

            <h4 class="page-header">Edit ${name}</h4>

            <form id="formEdit" name="formEdit" method="POST" action="mvc?cmd=${cmd}&mtd=editAndList">
                <c:forEach var="field" items="${fields.entrySet()}">

                    <div class="row">
                        <div class="form-group col-md-4 col-md-offset-4">
                            <label for="<c:out value="${field.getKey()}"/>"><c:out value="${field.getKey()}"/>:</label>

                            <c:choose>
                                <c:when test="${empty field.getValue()}">
                                    
                                    <c:choose>
                                        <c:when test="${fn:startsWith(field.getKey(), 'Price')}">
                                            <input type="number" 
                                                   min="0"
                                                   class="form-control" 
                                                   id="<c:out value="${field.getKey()}"/>" 
                                                   name="<c:out value="${field.getKey()}"/>" 
                                                   required="required" 
                                                   placeholder="Inform the <c:out value="${field.getKey()}"/>" 
                                                   maxlength="100"
                                                   data-property="<c:out value="${field.getKey()}"/>">
                                            <span id="<c:out value="${field.getKey()}"/>Count" 
                                                  class="label label-warning">100 left!</span>
                                        </c:when>
                                        <c:when test="${fn:startsWith(field.getKey(), 'Password')}">
                                            <input type="password" 
                                                   class="form-control" 
                                                   id="<c:out value="${field.getKey()}"/>" 
                                                   name="<c:out value="${field.getKey()}"/>" 
                                                   required="required" 
                                                   placeholder="Inform the <c:out value="${field.getKey()}"/>" 
                                                   maxlength="100"
                                                   data-property="<c:out value="${field.getKey()}"/>">
                                            <span id="<c:out value="${field.getKey()}"/>Count" 
                                                  class="label label-warning">100 left!</span>
                                        </c:when>
                                        <c:when test="${not fn:startsWith(field.getKey(), 'Price') and not fn:startsWith(field.getKey(), 'Password')}">
                                            <input type="text" 
                                                   class="form-control" 
                                                   id="<c:out value="${field.getKey()}"/>" 
                                                   name="<c:out value="${field.getKey()}"/>" 
                                                   required="required" 
                                                   placeholder="Inform the <c:out value="${field.getKey()}"/>" 
                                                   maxlength="100"
                                                   data-property="<c:out value="${field.getKey()}"/>">
                                            <span id="<c:out value="${field.getKey()}"/>Count" 
                                                  class="label label-warning">100 left!</span>
                                        </c:when>
                                    </c:choose>

                                </c:when>
                                <c:when test="${not empty field.getValue()}">
                                    <SELECT id="<c:out value="${field.getKey()}"/>" 
                                            name="<c:out value="${field.getKey()}"/>" 
                                            size="1" 
                                            class="form-control"
                                            data-property="<c:out value="${field.getKey()}"/>">

                                        <c:forEach var="option" items="${field.getValue()}">
                                            <option value="${option.getId()}">${option.getNome()}</option>
                                        </c:forEach>

                                    </SELECT>
                                </c:when>
                            </c:choose>
                        </div>
                    </div>

                </c:forEach> 

                <div class="row col-md-12 text-center">
                    <button type="submit" class="btn btn-success" data-toggle="tooltip" data-placement="bottom" title="Create">Create</button>
                    <button type="button" class="btn btn-primary" data-toggle="tooltip" data-placement="right" title="Clean" id="cleanButton">Clean</button>
                    <button type="button" class="btn btn-danger listButton" data-toggle="tooltip" data-placement="right" title="Cancel" id="cleanButton">Cancel</button>
                </div>

            </form>
        </div>

        <!-- FOOTER -->  
        <%@include file="/WEB-INF/jspf/footer.jspf"%>

        <script type="text/javascript">
            $(document).ready(function () {

                $('[data-toggle="tooltip"]').tooltip();

                $('input').keyup(function () {
                    var currentSize = $(this).val().length;
                    var maxSize = 100;

                    var remainingSize = maxSize - currentSize;

                    var countName = "#" + $(this).attr("name") + "Count";

                    $(countName).text(remainingSize + " left!");
                });

                $('#cleanButton').click(function () {
                    $('#formAddNew input').val("");
                    $("#formAddNew span").text("100 left!");
                });

            });
        </script>
    </body>
</html>
