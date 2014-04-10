<%-- 
    Document   : formMsg
    Created on : 03-04-2014, 10:39:27 PM
    Author     : patricio
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- MENSAJE INFORMATIVO -->
<c:if test="${msg != null}" >
    <div class="alert alert-block alert-info">
        <a class="close" data-dismiss="alert" href="#">×</a>
        <h4 class="alert-heading"><i class="fa fa-info-circle"></i> Info!</h4>
        <p>
            <c:out value="${msg}" />
        </p>
    </div>
</c:if>

<!-- MENSAJES DE EXITO -->
<c:if test="${msgOk != null}" >
    <div class="alert alert-block alert-success">
        <a class="close" data-dismiss="alert" href="#">×</a>
        <h4 class="alert-heading"><i class="fa fa-ok"></i> Correcto!</h4>
        <p>
            <c:out value="${msgOk}" />
        </p>
    </div>
</c:if>

<!-- MENSAJE DE ERROR -->
<c:if test="${msgList != null}" >
    <div class="alert alert-block alert-danger">
        <a class="close" data-dismiss="alert" href="#">×</a>
        <h4 class="alert-heading"><i class="fa fa-times-circle"></i> Error!</h4>
        <ul class="list-style">                                                                          
            <c:forEach var="msgList" items="${msgList}">
                <li><c:out value="${msgList.msg}" /></li>
                </c:forEach>
        </ul>
    </div>
</c:if>
