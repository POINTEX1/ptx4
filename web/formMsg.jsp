<%-- 
    Document   : formMsg
    Created on : 03-04-2014, 10:39:27 PM
    Author     : patricio
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- MENSAJE INFORMATIVO -->
<c:if test="${msg != null}" >
    <div class="alert alert-dismissable alert-info">
        <button type="button" class="close" data-dismiss="alert">&times;</button>
        <strong><c:out value="${msg}" /></strong>
    </div>
</c:if>                        

<!-- MENSAJE DE EXITO -->
<c:if test="${msgOk != null}" >
    <div class="alert alert-dismissable alert-success">
        <button type="button" class="close" data-dismiss="alert">&times;</button>
        <strong>Correcto!</strong>
        <c:out value="${msgOk}" />
    </div>
</c:if> 

<!-- LISTA DE ERRORES -->
<c:if test="${msgList != null}">                            
    <div class="alert alert-dismissable alert-danger">
        <button type="button" class="close" data-dismiss="alert">&times;</button>                                    
        <strong>ERROR!</strong>
        <ul class="list-style">                                                                          
            <c:forEach var="msgList" items="${msgList}">
                <li><c:out value="${msgList.msg}" /></li>
                </c:forEach>
        </ul>
    </div>
</c:if>
