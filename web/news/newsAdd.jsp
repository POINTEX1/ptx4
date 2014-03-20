<%-- 
    Document   : newsAdd
    Created on : 11-03-2014, 11:32:39 PM
    Author     : alexander
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>POINTEX</title>

        <!-- imperio css -->
        <link rel="stylesheet" type="text/css" href="css/bootstrap.css" />
        <link rel="stylesheet" type="text/css" href="css/bootstrap-responsive.css" />
        <link rel="stylesheet" type="text/css" href="css/imperio.css" />

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
                            <li class="active"><i class="fa fa-edit"></i> Agregar</li>
                        </ol>
                        <!-- /TITULO MANTENEDOR -->

                        <!-- MENSAJE INFORMATIVO -->
                        <c:if test="${msg != null }" >
                            <div class="alert alert-dismissable alert-info">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msg}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- /MENSAJE INFORMATIVO -->

                        <!-- MENSAJE DE ERROR EN TITULO -->
                        <c:if test="${msgErrorTittle != null }" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorTittle}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE ERROR EN TITULO -->

                        <!-- MENSAJE DE ERROR FECHA -->
                        <c:if test="${msgErrorDate != null }" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorDate}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE ERROR FECHA -->

                        <!-- MENSAJE DE ERROR DETALLES -->
                        <c:if test="${msgErrorDetails != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorDetails}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE ERROR DETALLES -->

                        <!-- MENSAJE DE ERROR IMAGE -->
                        <c:if test="${msgErrorUrlImage != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorFirstName}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE ERROR IMAGE -->

                        <!-- MENSAJE DE EXITO -->
                        <c:if test="${msgAdd != null}" >
                            <div class="alert alert-dismissable alert-success">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgAdd}" /></strong>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE EXITO -->

                        <!-- MENSAJE DE EXITO -->
                        <c:if test="${msgOk != null}" >
                            <div class="alert alert-dismissable alert-success">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgOk}" /></strong>
                            </div>
                        </c:if>   
                        <!-- /MENSAJE DE EXITO -->

                        <!-- MENSAJE DE ERROR TIPO DE NOTICIA -->
                        <c:if test="${msgErrorTypeNews != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorTypeNews}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE ERROR TIPO DE NOTICIA -->

                        <!-- MENSAJE DE ERROR REGISTRO DUPLICADO -->
                        <c:if test="${msgErrorDup != null }" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorDup}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE ERROR REGISTRO DUPLICADO -->
                    </div>
                    <div class="col-lg-4">
                        <!-- FORMULARIO -->
                        <form role="form" action="NewsAddServlet" method="POST" name="formAdd">                        
                            <!-- TITULO NOTICIA -->
                            <c:if test="${msgErrorDup == null && msgErrorTittle == null }" >
                                <div class="form-group">
                                    <label>Título de Noticia </label>
                                    <input class="form-control" required="true" maxlength="100" name="tittle" value="<c:out value="${news.tittle}" />">
                                </div>
                            </c:if>                                                                                    
                            <c:if test="${msgErrorDup != null || msgErrorTittle != null}" >
                                <div class="form-group has-error">
                                    <label class="control-label" for="inputError">Título de Noticia</label>
                                    <input type="text" class="form-control" required="true" name="tittle" id="inputError" value="<c:out value="${news.tittle}" />">
                                </div>
                            </c:if>
                            <!-- /TITULO NOTICIA -->

                            <!-- DETALLE -->
                            <c:choose>
                                <c:when test="${msgErrorDetails == null}">
                                    <div class="form-group">
                                        <label>Detalle </label>
                                        <input class="form-control" required="true" maxlength="200" name="details" value="<c:out value="${news.details}" />">
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="form-group has-error">
                                        <label class="control-label" for="inputError">Detalle </label>
                                        <input class="form-control" required="true" maxlength="200" name="details" id="inputError" value="<c:out value="${news.details}" />">
                                    </div>
                                </c:otherwise>
                            </c:choose>
                            <!-- /DETALLE -->

                            <!-- TIPO NOTICIA -->
                            <div class="form-group">
                                <label>Tipo de Noticias</label>
                                <select class="form-control" required="true" name="newsType">
                                    <option value="1" <c:if test="${news.newsType == 1}" > selected </c:if>> Advertencia</option>
                                    <option value="2" <c:if test="${news.newsType == 2}" > selected </c:if>> Notificación</option>
                                    <option value="3" <c:if test="${news.newsType == 3}" > selected </c:if>> Información</option>
                                    <option value="4" <c:if test="${news.newsType == 4}" > selected </c:if>> Atención</option>
                                    </select>
                                </div> 
                                <!-- /TIPO NOTICIA -->

                                <!-- IMAGEN -->
                            <c:choose>
                                <c:when test="${msgErrorUrlImage == null}">
                                    <div class="form-group">
                                        <label>Url Imagen *</label>
                                        <input class="form-control" required="true" maxlength="200" name="urlImage" value="<c:out value="${news.urlImage}" />">
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="form-group has-error">
                                        <label class="control-label" for="inputError">Imagen </label>
                                        <input class="form-control" required="true" maxlength="200" name="urlImage" id="inputError" value="<c:out value="${news.urlImage}" />">
                                    </div>
                                </c:otherwise>
                            </c:choose>
                            <!-- /IMAGEN -->

                            <!-- FECHA DE INICIO-TERMINO -->
                            <c:choose>
                                <c:when test="${msgErrorDup == null && msgErrorDate == null }">
                                    <div class="form-group">
                                        <label>Fecha de Inicio</label>
                                        <input class="form-control" type="datetime-local" required="true" name="dateBegin" value="<c:out value="${news.dateBegin}" />">
                                    </div>
                                    <div class="form-group">
                                        <label>Fecha de Término</label>
                                        <input class="form-control" type="datetime-local" required="true" name="dateEnd" value="<c:out value="${news.dateEnd}" />">
                                    </div>
                                </c:when>
                                <c:when test="${msgErrorDup != null && msgErrorDate == null }">
                                    <div class="form-group has-error">
                                        <label class="control-label" for="inputError">Fecha de Inicio</label>
                                        <input class="form-control" type="datetime-local" required="true" name="dateBegin" id="inputError" value="<c:out value="${news.dateBegin}" />">
                                    </div>
                                    <div class="form-group has-error">
                                        <label class="control-label" for="inputError">Fecha de Término</label>
                                        <input class="form-control" type="datetime-local" required="true" name="dateEnd" id="inputError" value="<c:out value="${news.dateEnd}" />">
                                    </div>
                                </c:when>
                                <c:when test="${msgErrorDup == null && msgErrorDate != null }">
                                    <div class="form-group has-error">
                                        <label class="control-label" for="inputError">Fecha de Inicio</label>
                                        <input class="form-control" type="datetime-local" required="true" name="dateBegin" id="inputError" value="<c:out value="${news.dateBegin}" />">
                                    </div>
                                    <div class="form-group has-error">
                                        <label class="control-label" for="inputError">Fecha de Término</label>
                                        <input class="form-control" type="datetime-local" required="true" name="dateEnd" id="inputError" value="<c:out value="${news.dateEnd}" />">
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="form-group">
                                        <label>Fecha de Inicio</label>
                                        <input class="form-control" type="datetime-local" required="true" name="dateBegin" value="<c:out value="${news.dateBegin}" />">
                                    </div>
                                    <div class="form-group">
                                        <label>Fecha de Término</label>
                                        <input class="form-control" type="datetime-local" required="true" name="dateEnd" value="<c:out value="${news.dateEnd}" />">
                                    </div>
                                </c:otherwise>
                            </c:choose>
                            <!-- /FECHA DE INICIO-TERMINO -->

                            <!-- BOTONES -->
                            <button type="submit" name="add" class="btn btn-default">Add</button>
                            <button type="reset" class="btn btn-default">Reset</button> 
                            <!-- /BOTONES -->
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

        <script type="text/javascript" src="js/jquery.min.js"></script>
        <script type="text/javascript" src="js/imperiobootstrap.min.js"></script>
        <script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
        <script type="text/javascript" src="js/imperio.general.js"></script>
        <script type="text/javascript" src="js/imperio.tables.js"></script>

    </body>
</html>
