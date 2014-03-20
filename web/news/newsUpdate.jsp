<%-- 
    Document   : newsUpdate
    Created on : 02-03-2014, 08:48:56 PM
    Author     : alexander
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
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
                            <li><a href="NewsMainServlet"><i class="fa fa-table"></i> DataTable</a></li>
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

                        <!-- MENSAJE DE ERROR REGISITRO NO ENCONTRADO -->
                        <c:if test="${msgErrorFound != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorFound}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE ERROR REGISITRO NO ENCONTRADO -->

                        <!-- MENSAJE DE ERROR REGISTRO DUPLICADO -->
                        <c:if test="${msgErrorDup != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorDup}" /></strong></br>
                            </div>
                        </c:if> 
                        <!-- /MENSAJE DE ERROR REGISTRO DUPLICADO -->

                        <!-- MENSAJE DE ERROR TITULO -->
                        <c:if test="${msgErrorTittle != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorTittle}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE ERROR TITULO -->

                        <!-- MENSAJE DE ERROR DETALLE -->
                        <c:if test="${msgErrorDetails != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorDetails}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE ERROR DETALLE -->

                        <!-- MENSAJE DE ERROR IMAGE -->
                        <c:if test="${msgErrorUrlImage != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorUrlImage}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE ERROR IMAGE -->

                        <!-- MENSAJE DE ERROR ID NEWS -->
                        <c:if test="${msgErrorIdNews != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorIdNews}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE ERROR ID NEWS -->

                        <!-- MENSAJE DE ERROR TIPO NOTICIA -->
                        <c:if test="${msgErrorTypeNews != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorTypeNews}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE ERROR TIPO NOTICIA -->

                        <!-- MENSAJE DE ERROR FECHA -->
                        <c:if test="${msgErrorDate != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorDate}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE ERROR FECHA -->

                        <!-- MENSAJE DE ERROR FECHA INICIO -->
                        <c:if test="${msgErrorDateBegin != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorDateBegin}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE ERROR FECHA INICIO -->

                        <!-- MENSAJE DE ERROR FECHA TERMINO -->
                        <c:if test="${msgErrorDateEnd != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorDateEnd}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE ERROR FECHA TERMINO -->
                    </div>
                    <div class="col-lg-4">
                        <!-- FORMULARIO -->
                        <form role="form" action="NewsUpdateServlet" method="POST" name="formUpdate">
                            <!-- ID NEWS -->
                            <div class="form-group">
                                <label for="disabledSelect">ID Noticia</label>
                                <input class="form-control" id="disabledInput" type="text" placeholder="<c:out value="${news.idNews}" />" disabled>
                                <input type="hidden" name="idNews" value="<c:out value="${news.idNews}" />"/>
                            </div>
                            <!-- /ID NEWS -->

                            <!-- TITULO -->
                            <c:if test="${msgErrorDup == null && msgErrorTittle == null }" >
                                <div class="form-group">
                                    <label>Título</label>
                                    <input class="form-control" required="true" maxlength="50" name="tittle" value="<c:out value="${news.tittle}" />">
                                </div>
                            </c:if>
                            <c:if test="${msgErrorDup != null || msgErrorTittle != null }" >
                                <div class="form-group has-error">
                                    <label class="control-label" for="inputError">Título</label>
                                    <input class="form-control" required="true" maxlength="50" name="tittle" id="inputError" value="<c:out value="${news.tittle}" />">
                                </div>
                            </c:if>
                            <!-- /TITULO -->

                            <!-- DETALLE -->
                            <c:if test="${msgErrorDetails == null}" >
                                <div class="form-group">
                                    <label>Detalles</label>
                                    <input class="form-control" required="true" maxlength="200" name="details" value="<c:out value="${news.details}" />">
                                </div>
                            </c:if>
                            <c:if test="${msgErrorDetails != null}" >
                                <div class="form-group has-error">
                                    <label class="control-label" for="inputError">Detalle</label>
                                    <input class="form-control" required="true" maxlength="200" id="inputError" name="details" value="<c:out value="${news.details}" />">
                                </div>
                            </c:if>
                            <!-- /DETALLE -->

                            <!-- TIPO NOTICIA -->
                            <div class="form-group">
                                <label>Tipo de Noticias</label>
                                <select class="form-control" required="true" name="newsType">
                                    <option value="1"  <c:if test="${news.newsType == 1}" > selected </c:if>> Advertencia</option>
                                    <option value="2" <c:if test="${news.newsType == 2}" > selected </c:if>> Notificación</option>
                                    <option value="3" <c:if test="${news.newsType == 3}" > selected </c:if>> Información</option>
                                    <option value="4" <c:if test="${news.newsType == 4}" > selected </c:if>> Atención</option>
                                    </select>
                                </div>
                                <!-- /TIPO NOTICIA -->

                                <!-- IMAGE -->
                            <c:if test="${msgErrorUrlImage == null}" >
                                <div class="form-group">
                                    <label>Url Imagen</label>
                                    <input class="form-control" required="true" maxlength="200" name="urlImage" value="<c:out value="${news.urlImage}" />">
                                </div>
                            </c:if>                                                                                                            
                            <c:if test="${msgErrorUrlImage != null}" >
                                <div class="form-group has-error">
                                    <label class="control-label" for="inputError">Url Imagen</label>
                                    <input class="form-control" required="true" maxlength="200" id="inputError" name="urlImage" value="<c:out value="${news.urlImage}" />">
                                </div>
                            </c:if>
                            <!-- /IMAGE -->

                            <!-- FECHA INICIO-TERMINO -->
                            <c:if test="${msgErrorDup == null && msgErrorDate == null}">
                                <div class="form-group">
                                    <label>Fecha de Inicio</label>
                                    <input class="form-control" type="datetime-local" required="true" name="dateBegin" value="<c:out value="${news.dateBegin}" />">
                                </div>
                                <div class="form-group">
                                    <label>Fecha de Término</label>
                                    <input class="form-control" type="datetime-local" required="true" name="dateEnd" value="<c:out value="${news.dateEnd}" />">
                                </div>
                            </c:if>
                            <c:if test="${msgErrorDup != null || msgErrorDate != null}">
                                <div class="form-group has-error">
                                    <label class="control-label" for="inputError">Fecha de Inicio</label>
                                    <input class="form-control" type="datetime-local" required="true" name="dateBegin" id="inputError" value="<c:out value="${news.dateBegin}" />">
                                </div>
                                <div class="form-group has-error">
                                    <label class="control-label" for="inputError">Fecha de Término</label>
                                    <input class="form-control" type="datetime-local" required="true" name="dateEnd" id="inputError" value="<c:out value="${news.dateEnd}" />">
                                </div>
                            </c:if>
                            <!-- /FECHA INICIO-TERMINO -->

                            <button type="submit" class="btn btn-default"><strong><font size="1">ACTUALIZAR</font></strong></button>
                        </form>
                        <!-- FORMULARIO -->            
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