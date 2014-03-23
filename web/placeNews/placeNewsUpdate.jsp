<%-- 
    Document   : placeNewsUpdate
    Created on : 18-03-2014, 12:37:19 PM
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

    <div id="wrapper">

        <!-- Collect the nav links, forms, and other content for toggling -->
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
                        <li class="active"><i class="fa fa-edit"></i> Actualizar</li>
                    </ol>
                    <!-- /TITULO MANTENEDOR -->

                    <c:if test="${msg != null}" >
                        <div class="alert alert-dismissable alert-info">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong><c:out value="${msg}" /></strong>
                        </div>
                    </c:if>
                    <c:if test="${msgOk != null}" >
                        <div class="alert alert-dismissable alert-success">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong><c:out value="${msgOk}" /></strong>
                        </div>
                    </c:if>                    
                    <c:if test="${msgErrorDup != null}" >
                        <div class="alert alert-dismissable alert-danger">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong><c:out value="${msgErrorDup}" /></strong></br>
                        </div>
                    </c:if>                      
                    <c:if test="${msgErrorUrlImage != null}" >
                        <div class="alert alert-dismissable alert-danger">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong><c:out value="${msgErrorUrlImage}" /></strong></br>
                        </div>
                    </c:if>
                    <c:if test="${msgErrorNamePlace != null}" >
                        <div class="alert alert-dismissable alert-danger">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong><c:out value="${msgErrorNamePlace}" /></strong></br>
                        </div>
                    </c:if> 
                    <c:if test="${msgErrorTittle != null}" >
                        <div class="alert alert-dismissable alert-danger">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong><c:out value="${msgErrorTittle}" /></strong></br>
                        </div>
                    </c:if> 
                    <c:if test="${msgErrorDetails != null}" >
                        <div class="alert alert-dismissable alert-danger">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong><c:out value="${msgErrorDetails}" /></strong></br>
                        </div>
                    </c:if>                  
                    <c:if test="${msgErrorFound != null}" >
                        <div class="alert alert-dismissable alert-danger">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong><c:out value="${msgErrorFound}" /></strong></br>
                        </div>
                    </c:if>
                    <c:if test="${msgErrorDate != null}" >
                        <div class="alert alert-dismissable alert-danger">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong><c:out value="${msgErrorDate}" /></strong></br>
                        </div>
                    </c:if>
                </div>
                <div class="col-lg-4">
                    <!-- FORMULARIO -->
                    <form role="form" action="PlaceNewsUpdateServlet" method="POST" name="formUpdate">
                        <!-- LUGAR -->
                        <div class="form-group">
                            <label for="disabledSelect">Lugar</label>
                            <input class="form-control" id="disabledInput" type="text" placeholder="<c:out value="${namePlace}" />" disabled>
                            <input type="hidden" name="namePlace" value="<c:out value="${namePlace}" />"/>                            
                            <input type="hidden" name="idPlace" value="<c:out value="${idPlace}" />" />
                        </div>
                        <!-- /LUGAR -->

                        <!-- ID NOTICIA -->
                        <div class="form-group">
                            <label for="disabledSelect">ID Noticia Lugar</label>
                            <input class="form-control" id="disabledInput" type="text" placeholder="<c:out value="${idPlaceNews}" />" disabled>
                            <input type="hidden" name="idPlaceNews" value="<c:out value="${idPlaceNews}" />"/>
                        </div>
                        <!-- /ID NOTICIA -->

                        <!-- TITULO -->
                        <c:if test="${msgErrorDup == null && msgErrorTittle == null }" >
                            <div class="form-group">
                                <label>Título</label>
                                <input class="form-control" required="true" maxlength="50" name="tittle" value="<c:out value="${tittle}" />">
                            </div>
                        </c:if>                                                                        
                        <c:if test="${msgErrorDup != null || msgErrorTittle != null }" >
                            <div class="form-group has-error">
                                <label class="control-label" for="inputError">Título</label>
                                <input class="form-control" required="true" maxlength="50" name="tittle" id="inputError" value="<c:out value="${tittle}" />">
                            </div>
                        </c:if>
                        <!-- /TITULO -->

                        <!-- DETALLE -->
                        <c:if test="${msgErrorDetails == null}" >
                            <div class="form-group">
                                <label>Detalles</label>
                                <input class="form-control" required="true" maxlength="200" name="details" value="<c:out value="${details}" />">
                            </div>
                        </c:if>                                                                        
                        <c:if test="${msgErrorDetails != null}" >
                            <div class="form-group has-error">
                                <label class="control-label" for="inputError">Detalle</label>
                                <input class="form-control" required="true" maxlength="200" id="inputError" name="details" value="<c:out value="${details}" />">
                            </div>
                        </c:if>
                        <!-- /DETALLE -->

                        <!-- TIPO NOTICIA -->
                        <div class="form-group">
                            <label>Tipo de Noticias</label>
                            <select class="form-control" required="true" name="newsType">
                                <option value="1"  <c:if test="${newsType == 1}" > selected </c:if>> Advertencia</option>
                                <option value="2" <c:if test="${newsType == 2}" > selected </c:if>> Notificación</option>
                                <option value="3" <c:if test="${newsType == 3}" > selected </c:if>> Información</option>
                                <option value="4" <c:if test="${newsType == 4}" > selected </c:if>> Atención</option>
                                </select>
                            </div>
                            <!-- /TIPO NOTICIA -->

                            <!-- URL IMAGE -->
                        <c:if test="${msgErrorUrlImage == null}" >
                            <div class="form-group">
                                <label>Url Imagen</label>
                                <input class="form-control" required="true" maxlength="200" name="urlImage" value="<c:out value="${urlImage}" />">
                            </div>
                        </c:if>                                                                        
                        <c:if test="${msgErrorUrlImage != null}" >
                            <div class="form-group has-error">
                                <label class="control-label" for="inputError">Url Imagen</label>
                                <input class="form-control" required="true" maxlength="200" id="inputError" name="urlImage" value="<c:out value="${urlImage}" />">
                            </div>
                        </c:if>
                        <!-- /URL IMAGE -->

                        <!-- FECHA INICIO-TERMINO -->
                        <c:if test="${msgErrorDup == null && msgErrorDate == null}">
                            <div class="form-group">
                                <label>Fecha de Inicio</label>
                                <input class="form-control" type="datetime-local" required="true" name="dateBegin" value="<c:out value="${dateBegin}" />">
                            </div>
                            <div class="form-group">
                                <label>Fecha de Término</label>
                                <input class="form-control" type="datetime-local" required="true" name="dateEnd" value="<c:out value="${dateEnd}" />">
                            </div>
                        </c:if>
                        <c:if test="${msgErrorDup != null || msgErrorDate != null}">
                            <div class="form-group has-error">
                                <label class="control-label" for="inputError">Fecha de Inicio</label>
                                <input class="form-control" type="datetime-local" required="true" name="dateBegin" id="inputError" value="<c:out value="${dateBegin}" />">
                            </div>
                            <div class="form-group has-error">
                                <label class="control-label" for="inputError">Fecha de Término</label>
                                <input class="form-control" type="datetime-local" required="true" name="dateEnd" id="inputError" value="<c:out value="${dateEnd}" />">
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