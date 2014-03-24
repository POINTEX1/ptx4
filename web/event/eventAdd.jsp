<%-- 
    Document   : admin
    Created on : 26-dic-2013, 16:08:09
    Author     : patricio alberto
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

        <!-- disabledButton -->
        <script src="js/disabledButton.js"></script>

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
                        <h1>Mantenedor <small> Eventos</small></h1>
                        <ol class="breadcrumb">
                            <li><a href="EventMainServlet"><i class="fa fa-table"></i> DataTable</a></li>
                            <li class="active"><i class="fa fa-edit"></i> Agregar</li>
                        </ol>
                        <!-- /TITULO MANTENEDOR -->

                        <!-- MENSAJE INFORMATIVO -->
                        <c:if test="${msg != null}" >
                            <div class="alert alert-info alert-dismissable">
                                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                                <td><strong><c:out value="${msg}" /></strong></td>
                            </div>
                        </c:if>
                        <!-- /MENSAJE INFORMATIVO -->

                        <!-- MENSAJE DE EXITO -->
                        <c:if test="${msgOk != null}" >
                            <div class="alert alert-dismissable alert-success">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgOk}" /></strong>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE EXITO -->

                        <!-- MENSAJE DE ERROR ID PLACE -->
                        <c:if test="${msgErrormsgErrorIdPlace != null }" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrormsgErrorIdPlace}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE ERROR ID PLACE -->

                        <!-- MENSAJE DE ERROR DE FECHA -->
                        <c:if test="${msgErrorDate != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorDate}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE ERROR DE FECHA -->

                        <!-- MENSAJE DE ERROR DE TITULO -->
                        <c:if test="${msgErrorTittle != null }" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorTittle}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE ERROR DE TITULO -->

                        <!-- MENSAJE DE ERROR DE DETALLE -->
                        <c:if test="${msgErrorDetails != null }" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorDetails}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE ERROR DE DETALLE -->

                        <!-- MENSAJE DE ERROR DE URL IMAGE -->
                        <c:if test="${msgErrorUrlImage != null }" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorUrlImage}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE ERROR DE URL IMAGE -->

                        <!-- MENSAJE DE ERROR FECHA DE INICIO -->
                        <c:if test="${msgErrorDateBegin != null }" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorDateBegin}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE ERROR FECHA DE INICIO -->

                        <!-- MENSAJE DE ERROR DE FECHA DE TERMINO -->
                        <c:if test="${msgErrorDateEnd != null }" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorDateEnd}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE ERROR DE FECHA DE TERMINO -->

                        <!-- MENSAJE DE ERROR DE EVENTO -->
                        <c:if test="${msgErrorEvent != null }" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorEvent}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE ERROR DE EVENTO -->

                        <!-- MENSAJE DE ERROR DE PUNTOS -->
                        <c:if test="${msgErrorPoints != null }" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorPoints}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE ERROR DE PUNTOS -->

                        <!-- MENSAJE DE ERROR DE PETICION -->
                        <c:if test="${msgErrorRequest != null }" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorRequest}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- MENSAJE DE ERROR DE PETICION -->
                    </div>
                    <div class="col-lg-4">
                        <!-- FORMULARIO -->
                        <form role="form" action="EventAddServlet" method="POST" id="formAdd" name="formAdd">
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

                            <!-- TITULO -->
                            <c:if test="${msgErrorEvent == null && msgErrorTittle == null}">
                                <div class="form-group">
                                    <label>Título de Evento</label>
                                    <input class="form-control" required="true" maxlength="100" name="tittle" value="<c:out value="${event.tittle}" />">
                                </div>
                            </c:if>
                            <c:if test="${msgErrorEvent != null || msgErrorTittle != null}">
                                <div class="form-group has-error">
                                    <label class="control-label" for="inputError">Título de Evento</label>
                                    <input type="text" class="form-control" required="true" name="tittle" id="inputError" value="<c:out value="${event.tittle}" />">
                                </div>
                            </c:if>                            
                            <!-- /TITULO -->

                            <!-- DETALLE -->
                            <div class="form-group">
                                <label>Detalles</label>
                                <input class="form-control" maxlength="100" name="details" value="<c:out value="${event.details}" />">
                            </div>
                            <!-- DETALLE -->

                            <!-- PUNTOS -->
                            <c:if test="${msgErrorPoints == null }" >
                                <div class="form-group">
                                    <label>Puntos</label>
                                    <input type="number" class="form-control" min="0" max="99999" name="points" value="<c:out value="${event.points}" />">
                                </div>
                            </c:if>
                            <c:if test="${msgErrorPoints != null }" >
                                <div class="form-group has-error">
                                    <label class="control-label" for="inputError">Puntos</label>
                                    <input type="number" class="form-control" min="0" max="99999" name="points" id="inputError" value="<c:out value="${event.points}" />">
                                </div>
                            </c:if>
                            <!-- /PUNTOS -->

                            <!-- CODIGO VESTIR -->
                            <div class="form-group">
                                <label>Código de Vestir</label>
                                <select class="form-control" name="idDressCode">
                                    <c:forEach var="listDressCode" items="${listDressCode}">  
                                        <option value="<c:out value="${listDressCode.idDressCode}" />" <c:if test="${event.idDressCode == listDressCode.idDressCode}">selected</c:if>> <c:out value="${listDressCode.nameDressCode}" /> </option>
                                    </c:forEach>
                                </select>                                
                            </div>
                            <!-- CODIGO VESTIR -->

                            <!-- IMAGE -->
                            <c:if test="${msgErrorUrlImage == null}">
                                <div class="form-group">
                                    <label>Imagen</label>
                                    <input class="form-control" maxlength="200" name="url" value="<c:out value="${event.urlImage}" />">
                                </div>
                            </c:if>
                            <c:if test="${msgErrorUrlImage != null}">
                                <div class="form-group has-error">
                                    <label class="control-label" for="inputError">Imagen</label>
                                    <input class="form-control" maxlength="200" name="url" id="inputError" value="<c:out value="${event.urlImage}" />">
                                </div>
                            </c:if>
                            <!-- /IMAGE -->

                            <!-- FECHA INICIO-TERMINO -->
                            <c:if test="${msgErrorEvent == null && msgErrorDate == null && msgErrorDate == null && msgErrorDateBegin == null && msgErrorDateEnd == null}">
                                <div class="form-group">
                                    <label>Fecha de Inicio</label>
                                    <input class="form-control" type="datetime-local" required="true" name="dateBegin" value="<c:out value="${event.dateBegin}" />">
                                </div>
                                <div class="form-group">
                                    <label>Fecha de Término</label>
                                    <input class="form-control" type="datetime-local" required="true" name="dateEnd" value="<c:out value="${event.dateEnd}" />">
                                </div>
                            </c:if>
                            <c:if test="${msgErrorEvent != null || msgErrorDate != null || msgErrorDate != null || msgErrorDateBegin != null || msgErrorDateEnd != null}">
                                <div class="form-group has-error">
                                    <label class="control-label" for="inputError">Fecha de Inicio</label>
                                    <input class="form-control" type="datetime-local" required="true" name="dateBegin" id="inputError" value="<c:out value="${event.dateBegin}" />">
                                </div>
                                <div class="form-group has-error">
                                    <label class="control-label" for="inputError">Fecha de Término</label>
                                    <input class="form-control" type="datetime-local" required="true" name="dateEnd" id="inputError" value="<c:out value="${event.dateEnd}" />">
                                </div>
                            </c:if>
                            <!-- /FECHA INICIO-TERMINO -->

                            <!-- BOTONES -->
                            <input type="hidden" name="add" value="ok"/>
                            <button type="submit" name="btnAdd" class="btn btn-default" onclick="disabledButtonAdd();"><strong><font size="1"><object name="btn1">AGREGAR</object><object name="btn2" hidden="true">AGREGANDO...</object></font></strong></button>
                            <button type="reset" class="btn btn-default"><strong><font size="1">RESET</font></strong></button> 
                            <!-- /BOTONES -->
                        </form>
                        <!-- FORMULARIO -->
                    </div>
                </div><!-- /.row -->

                <!-- FOOTER -->
                <p>&nbsp;</p>

                <c:import var="footer" url="/footer.jsp" />
                <c:out value="${footer}" escapeXml="false" />
                <!-- /FOOTER -->

            </div><!-- /#page-wrapper -->

        </div><!-- /#wrapper -->

    </body>
</html>