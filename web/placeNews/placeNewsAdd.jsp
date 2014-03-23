<%-- 
    Document   : placeNewsAdd
    Created on : 18-03-2014, 12:37:05 PM
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

            <!-- Sidebar -->
            <!-- Brand and toggle get grouped for better mobile display -->
            <c:import var="menu" url="/mainMenu.jsp" />
            <c:out value="${menu}" escapeXml="false" />
            <!-- /.navbar-collapse -->

            <div id="page-wrapper">

                <div class="row">
                    <div class="col-lg-12">
                        <!-- TITULO MANTENEDOR -->
                        <h1>Mantenedor <small> Noticia para Lugar</small></h1>
                        <ol class="breadcrumb">
                            <li><a href="PlaceNewsMainServlet"><i class="fa fa-table"></i> DataTable</a></li>
                            <li class="active"><i class="fa fa-edit"></i> Agregar</li>
                        </ol>
                        <!-- /TITULO MANTENEDOR -->


                        <c:if test="${msg != null}" >
                            <div class="alert alert-info alert-dismissable">
                                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                                <td><strong><c:out value="${msg}" /></strong></td>
                            </div>
                        </c:if>
                        <c:if test="${msgOk != null}" >
                            <div class="alert alert-dismissable alert-success">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgOk}" /></strong>
                            </div>
                        </c:if>
                        <c:if test="${msgErrormsgErrorIdPlaceNews != null }" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrormsgErrorIdPlace}" /></strong></br>
                            </div>
                        </c:if>
                        <c:if test="${msgErrormsgErrorIdPlace != null }" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrormsgErrorId}" /></strong></br>
                            </div>
                        </c:if>
                        <c:if test="${msgErrorDate != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorDate}" /></strong></br>
                            </div>
                        </c:if>
                        <c:if test="${msgErrorTittle != null }" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorTittle}" /></strong></br>
                            </div>
                        </c:if>
                        <c:if test="${msgErrorDetails != null }" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorDetails}" /></strong></br>
                            </div>
                        </c:if>
                        <c:if test="${msgErrorDateBegin != null }" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorDateBegin}" /></strong></br>
                            </div>
                        </c:if>
                        <c:if test="${msgErrorDateEnd != null }" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorDateEnd}" /></strong></br>
                            </div>
                        </c:if>
                        <c:if test="${msgErrorDate != null }" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorDate}" /></strong></br>
                            </div>
                        </c:if>
                        <c:if test="${msgErrorUrlImage != null }" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorUrlImage}" /></strong></br>
                            </div>
                        </c:if>
                        <c:if test="${msgErrorDup != null }" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorDup}" /></strong></br>
                            </div>
                        </c:if>

                    </div>
                    <div class="col-lg-4">
                        <!-- FORMULARIO -->
                        <form role="form" action="PlaceNewsAddServlet" method="POST" name="formAdd">                        
                            <!-- LUGAR -->
                            <div class="form-group">
                                <label>Lugar</label>
                                <select class="form-control" name="idPlace">
                                    <c:forEach var="listPlace" items="${listPlace}">  
                                        <option value="<c:out value="${listPlace.idPlace}" />" <c:if test="${event.idPlace == listPlace.idPlace}">selected</c:if>> <c:out value="${listPlace.namePlace}" /> </option>
                                    </c:forEach>
                                </select>                                
                            </div>
                            <!-- /LUGAR -->

                            <c:if test="${msgErrorDup == null && msgErrorTittle == null}">
                                <div class="form-group">
                                    <label>Título de Noticia</label>
                                    <input class="form-control" required="true" maxlength="100" name="tittle" value="<c:out value="${pnews.tittle}" />">
                                </div>
                            </c:if>
                            <c:if test="${msgErrorDup != null || msgErrorTittle != null}">
                                <div class="form-group has-error">
                                    <label class="control-label" for="inputError">Título de Noticia</label>
                                    <input type="text" class="form-control" required="true" name="tittle" id="inputError" value="<c:out value="${pnews.tittle}" />">
                                </div>
                            </c:if>

                            <!-- TIPO NOTICIA -->
                            <div class="form-group">
                                <label>Tipo de Noticias</label>
                                <select class="form-control" required="true" name="newsType">
                                    <option value="1" <c:if test="${pnews.newsType == 1}" > selected </c:if>> Advertencia</option>
                                    <option value="2" <c:if test="${pnews.newsType == 2}" > selected </c:if>> Notificación</option>
                                    <option value="3" <c:if test="${pnews.newsType == 3}" > selected </c:if>> Información</option>
                                    <option value="4" <c:if test="${pnews.newsType == 4}" > selected </c:if>> Atención</option>
                                    </select>
                                </div> 
                                <!-- /TIPO NOTICIA -->

                                <!-- DETALLE -->
                                <div class="form-group">
                                    <label>Detalles</label>
                                    <input class="form-control" maxlength="100" name="details" value="<c:out value="${pnews.details}" />">
                            </div>
                            <!-- /DETALLE -->

                            <!-- URL IMAGE -->
                            <c:if test="${msgErrorUrlImage == null}" >
                                <div class="form-group">
                                    <label>Url Image</label>
                                    <input class="form-control" required="true" maxlength="200" name="urlImage" value="<c:out value="${pnews.urlImage}" />">
                                </div>
                            </c:if>                                                                                    
                            <c:if test="${msgErrorUrlImage != null}" >
                                <div class="form-group has-error">
                                    <label class="control-label" for="inputError">Url Image</label>
                                    <input class="form-control" required="true" maxlength="200" id="inputError" name="urlImage" value="<c:out value="${pnews.urlImage}" />">
                                </div>
                            </c:if>
                            <!-- /URL IMAGE -->

                            <!-- FECHA INICIO-TERMINO -->
                            <c:choose>
                                <c:when test="${msgErrorDup == null && msgErrorDate == null }">
                                    <div class="form-group">
                                        <label>Fecha de Inicio</label>
                                        <input class="form-control" type="datetime-local" required="true" name="dateBegin" value="<c:out value="${pnews.dateBegin}" />">
                                    </div>
                                    <div class="form-group">
                                        <label>Fecha de Término</label>
                                        <input class="form-control" type="datetime-local" required="true" name="dateEnd" value="<c:out value="${pnews.dateEnd}" />">
                                    </div>
                                </c:when>
                                <c:when test="${msgErrorDup != null && msgErrorDate == null }">
                                    <div class="form-group has-error">
                                        <label class="control-label" for="inputError">Fecha de Inicio</label>
                                        <input class="form-control" type="datetime-local" required="true" name="dateBegin" id="inputError" value="<c:out value="${pnews.dateBegin}" />">
                                    </div>
                                    <div class="form-group has-error">
                                        <label class="control-label" for="inputError">Fecha de Término</label>
                                        <input class="form-control" type="datetime-local" required="true" name="dateEnd" id="inputError" value="<c:out value="${pnews.dateEnd}" />">
                                    </div>
                                </c:when>
                                <c:when test="${msgErrorDup == null && msgErrorDate != null }">
                                    <div class="form-group has-error">
                                        <label class="control-label" for="inputError">Fecha de Inicio</label>
                                        <input class="form-control" type="datetime-local" required="true" name="dateBegin" id="inputError" value="<c:out value="${pnews.dateBegin}" />">
                                    </div>
                                    <div class="form-group has-error">
                                        <label class="control-label" for="inputError">Fecha de Término</label>
                                        <input class="form-control" type="datetime-local" required="true" name="dateEnd" id="inputError" value="<c:out value="${pnews.dateEnd}" />">
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="form-group">
                                        <label>Fecha de Inicio</label>
                                        <input class="form-control" type="datetime-local" required="true" name="dateBegin" value="<c:out value="${pnews.dateBegin}" />">
                                    </div>
                                    <div class="form-group">
                                        <label>Fecha de Término</label>
                                        <input class="form-control" type="datetime-local" required="true" name="dateEnd" value="<c:out value="${pnews.dateEnd}" />">
                                    </div>
                                </c:otherwise>
                            </c:choose>      
                            <!-- FECHA INICIO-TERMINO -->

                            <!-- BOTONES -->
                            <button type="submit" name="add" class="btn btn-default"><strong><font size="1">AGREGAR</font></strong></button>
                            <button type="reset" class="btn btn-default"><strong><font size="1">RESET</font></strong></button> 
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

    </body>
</html>
