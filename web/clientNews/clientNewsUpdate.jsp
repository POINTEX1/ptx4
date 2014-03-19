<%-- 
    Document   : clientNewsUpdate
    Created on : 05-03-2014, 06:29:29 PM
    Author     : alexander
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>POINTEX</title>

        <!-- Bootstrap core CSS -->
        <link href="css/bootstrap.css" rel="stylesheet">

        <!-- Add custom CSS here -->
        <link href="css/sb-admin.css" rel="stylesheet">
        <link rel="stylesheet" href="font-awesome/css/font-awesome.min.css">
        <script type="text/javascript" src="http://code.jquery.com/jquery-1.6.min.js"></script>  

        <!-- JavaScript -->
        <script src="js/jquery-1.10.2.js"></script>
        <script src="js/bootstrap.js"></script>

        <!-- Page Specific Plugins -->
        <script src="js/tablesorter/jquery.tablesorter.js"></script>
        <script src="js/tablesorter/tables.js"></script>

    </head>

    <body>

        <div id="wrapper">

            <!-- Collect the nav links, forms, and other content for toggling -->
            <c:import var="menu" url="/mainMenu.jsp" />
            <c:out value="${menu}" escapeXml="false" />
            <!-- /.navbar-collapse -->

            <div id="page-wrapper">

                <div class="row">
                    <div class="col-lg-12">
                        <!-- TITULO MANTENEDOR -->
                        <h1>Mantenedor <small> Noticias</small></h1>
                        <ol class="breadcrumb">
                            <li><a href="ClientNewsMainServlet"><i class="fa fa-table"></i> DataTable</a></li>
                            <li class="active"><i class="fa fa-edit"></i> Actualizar</li>
                        </ol>
                        <!-- /TITULO MANTENEDOR -->

                        <!-- MENSAJE DE EXITO -->
                        <c:if test="${msgOk != null}" >
                            <div class="alert alert-dismissable alert-success">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgOk}" /></strong>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE EXITO -->

                        <!-- MENSAJE DE ERROR DE REGISTRO NO ENCONTRADO -->
                        <c:if test="${msgErrorFound != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorFound}" /></strong></br>
                            </div>
                        </c:if> 
                        <!-- /MENSAJE DE ERROR DE REGISTRO NO ENCONTRADO -->

                        <!-- MENSAJE DE ERROR DE REGISTRO DUPLICADO -->
                        <c:if test="${msgErrorDup != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorDup}" /></strong></br>
                            </div>
                        </c:if> 
                        <!-- /MENSAJE DE ERROR DE REGISTRO DUPLICADO -->

                        <!-- MENSAJE DE ERROR DE TITULO -->
                        <c:if test="${msgErrorTittle != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorTittle}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE ERROR DE TITULO -->

                        <!-- MENSAJE DE ERROR DE ID CLIENT NEWS -->
                        <c:if test="${msgErrorIdClientNews != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorIdClientNews}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE ERROR DE ID CLIENT NEWS -->

                        <!-- MENSAJE DE ERROR DE RUT -->
                        <c:if test="${msgErrorRut != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorRut}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE ERROR DE RUT -->

                        <!-- MENSAJE DE ERROR DE TIPO DE NOTICIA -->
                        <c:if test="${msgErrorNewsType != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorNewsType}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE ERROR DE TIPO DE NOTICIA -->

                        <!-- MENSAJE DE ERROR DE FECHA -->
                        <c:if test="${msgErrorDate != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorDate}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE ERROR DE FECHA -->
                    </div>
                    <div class="col-lg-4">
                        <!-- FORMULARIO -->
                        <form role="form" action="ClientNewsUpdateServlet" method="POST" name="formUpdate">
                            <!-- ID CLIENT NEWS -->
                            <div class="form-group">
                                <label for="disabledSelect">ID</label>
                                <input class="form-control" id="disabledInput" type="text" placeholder="<c:out value="${cnews.idClientNews}" />" disabled>
                                <input type="hidden" name="idClientNews" value="<c:out value="${cnews.idClientNews}" />"/>
                            </div>
                            <!-- /ID CLIENT NEWS -->

                            <!-- RUT -->
                            <div class="form-group">
                                <label for="disabledSelect">RUT</label>
                                <input class="form-control" id="disabledInput" type="text" placeholder="<c:out value="${cnews.rut}" />-<c:out value="${cnews.dv}" />" disabled>
                                <input type="hidden" name="rut" value="<c:out value="${cnews.rut}" />"/>
                                <input type="hidden" name="dv" value="<c:out value="${cnews.dv}" />"/>
                            </div>
                            <!-- /RUT -->

                            <!-- NOMBRE -->
                            <div class="form-group">
                                <label for="disabledSelect">Nombre</label>
                                <input class="form-control" id="disabledInput" type="text" placeholder="<c:out value="${cnews.firstName}" /> <c:out value="${cnews.lastName}" />" disabled>
                                <input type="hidden" name="firstName" value="<c:out value="${cnews.firstName}" /> "/>
                                <input type="hidden" name="lastName" value="<c:out value="${cnews.lastName}" />"/>                          
                            </div>
                            <!-- /NOMBRE -->

                            <!-- TITULO -->
                            <c:if test="${msgErrorDup == null && msgErrorTittle == null }" >
                                <div class="form-group">
                                    <label>Título</label>
                                    <input class="form-control" required="true" maxlength="50" name="tittle" value="<c:out value="${cnews.tittle}" />">
                                </div>
                            </c:if>                                                        
                            <c:if test="${msgErrorDup != null || msgErrorTittle != null }" >
                                <div class="form-group has-error">
                                    <label class="control-label" for="inputError">Título</label>
                                    <input class="form-control" required="true" maxlength="50" name="tittle" id="inputError" value="<c:out value="${cnews.tittle}" />">
                                </div>
                            </c:if>
                            <!-- /TITULO -->

                            <!-- TIPO DE NOTICIA -->
                            <div class="form-group">
                                <label>Tipo de Noticias</label>
                                <select class="form-control" required="true" name="newsType">
                                    <option value="1"  <c:if test="${cnews.newsType == 1}" > selected </c:if>> Advertencia</option>
                                    <option value="2" <c:if test="${cnews.newsType == 2}" > selected </c:if>> Notificación</option>
                                    <option value="3" <c:if test="${cnews.newsType == 3}" > selected </c:if>> Información</option>
                                    <option value="4" <c:if test="${cnews.newsType == 4}" > selected </c:if>> Atención</option>
                                    </select>
                                </div>
                                <!-- /TIPO DE NOTICIA -->

                            <!-- FECHAS -->
                            <c:choose>
                                <c:when test="${msgErrorDup == null && msgErrorDate == null }">
                                    <div class="form-group">
                                        <label>Fecha de Inicio</label>
                                        <input class="form-control" type="datetime-local" required="true" name="dateBegin" value="<c:out value="${cnews.dateBegin}" />">
                                    </div>
                                    <div class="form-group">
                                        <label>Fecha de Término</label>
                                        <input class="form-control" type="datetime-local" required="true" name="dateEnd" value="<c:out value="${cnews.dateEnd}" />">
                                    </div>
                                </c:when>
                                <c:when test="${msgErrorDup != null && msgErrorDate == null }">
                                    <div class="form-group has-error">
                                        <label class="control-label" for="inputError">Fecha de Inicio</label>
                                        <input class="form-control" type="datetime-local" required="true" name="dateBegin" id="inputError" value="<c:out value="${cnews.dateBegin}" />">
                                    </div>
                                    <div class="form-group has-error">
                                        <label class="control-label" for="inputError">Fecha de Término</label>
                                        <input class="form-control" type="datetime-local" required="true" name="dateEnd" id="inputError" value="<c:out value="${cnews.dateEnd}" />">
                                    </div>
                                </c:when>
                                <c:when test="${msgErrorDup == null && msgErrorDate != null }">
                                    <div class="form-group has-error">
                                        <label class="control-label" for="inputError">Fecha de Inicio</label>
                                        <input class="form-control" type="datetime-local" required="true" name="dateBegin" id="inputError" value="<c:out value="${cnews.dateBegin}" />">
                                    </div>
                                    <div class="form-group has-error">
                                        <label class="control-label" for="inputError">Fecha de Término</label>
                                        <input class="form-control" type="datetime-local" required="true" name="dateEnd" id="inputError" value="<c:out value="${cnews.dateEnd}" />">
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="form-group">
                                        <label>Fecha de Inicio</label>
                                        <input class="form-control" type="datetime-local" required="true" name="dateBegin" value="<c:out value="${cnews.dateBegin}" />">
                                    </div>
                                    <div class="form-group">
                                        <label>Fecha de Término</label>
                                        <input class="form-control" type="datetime-local" required="true" name="dateEnd" value="<c:out value="${cnews.dateEnd}" />">
                                    </div>
                                </c:otherwise>
                            </c:choose>
                            <!-- /FECHAS -->
                            <button type="submit" class="btn btn-default"><strong><font size="1">ACTUALIZAR</font></strong></button>
                        </form>
                        <!-- /FORMULARIO -->           
                    </div>
                </div><!-- /.row -->

                </br>
                <!-- FOOTER -->
                <c:import var="footer" url="/footer.jsp" />
                <c:out value="${footer}" escapeXml="false" />
                <!-- /FOOTER -->

            </div><!-- /#page-wrapper -->

        </div><!-- /#wrapper -->
    </body>
</html>
