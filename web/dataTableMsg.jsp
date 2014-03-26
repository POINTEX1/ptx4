<%-- 
    Document   : dataTableMsg
    Created on : 20-03-2014, 11:16:05 AM
    Author     : patricio
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- MENSAJE INFORMATIVO -->
<c:if test="${msg != null}" >
    <div class="alert alert-info alert-dismissable">
        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
        <td><strong><c:out value="${msg}" /></strong></td>
    </div>
</c:if>
<!-- MENSAJE INFORMATIVO -->

<!-- MENSAJE DE EXITO -->
<c:if test="${msgOk != null}" >
    <div class="alert alert-info alert-dismissable">
        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
        <td><c:out value="${msgOk}" /></td>
    </div>
</c:if>
<!-- /MENSAJE DE EXITO -->

<!-- MENSAJE DE ELIMINACION -->
<c:if test="${msgDel != null}" >
    <div class="alert alert-dismissable alert-warning">
        <button type="button" class="close" data-dismiss="alert">&times;</button>
        <strong><c:out value="${msgDel}" /></strong></br>
    </div>
</c:if>
<!-- /MENSAJE DE ELIMINACION -->                                             

<!-- MENSAJE DE ERROR DE REGISTRO NO ENCONTRADO -->
<c:if test="${msgErrorFound != null}" >
    <div class="alert alert-dismissable alert-danger">
        <button type="button" class="close" data-dismiss="alert">&times;</button>
        <strong><c:out value="${msgErrorFound}" /></strong></br>
    </div>
</c:if>
<!-- /MENSAJE DE ERROR DE REGISTRO NO ENCONTRADO -->

<!-- MENSAJE DE ERROR DE REFERENCIA -->
<c:if test="${msgErrorReference != null}" >
    <div class="alert alert-dismissable alert-danger">
        <button type="button" class="close" data-dismiss="alert">&times;</button>
        <strong><c:out value="${msgErrorReference}" /></strong></br>
    </div>
</c:if>
<!-- /MENSAJE DE ERROR DE REFERENCIA -->

<!-- MENSAJE DE ERROR DE REFERENCIA -->
<c:if test="${msgErrorConstraint != null}" >
    <div class="alert alert-dismissable alert-danger">
        <button type="button" class="close" data-dismiss="alert">&times;</button>
        <strong><c:out value="${msgErrorContraint}" /></strong></br>
    </div>
</c:if>
<!-- /MENSAJE DE ERROR DE REFERENCIA -->

<!-- MENSAJE DE ERROR ELIMINACION -->
<c:if test="${msgErrorNoDel != null}" >
    <div class="alert alert-dismissable alert-danger">
        <button type="button" class="close" data-dismiss="alert">&times;</button>
        <strong><c:out value="${msgErrorNoDel}" /></strong></br>
    </div>
</c:if>
<!-- /MENSAJE DE ERROR ELIMINACION -->

<!-- MENSAJE DE ERROR ELIMINACION -->
<c:if test="${msgErrorDel != null}" >
    <div class="alert alert-dismissable alert-danger">
        <button type="button" class="close" data-dismiss="alert">&times;</button>
        <strong><c:out value="${msgErrorDel}" /></strong></br>
    </div>
</c:if>
<!-- /MENSAJE DE ERROR ELIMINACION -->
