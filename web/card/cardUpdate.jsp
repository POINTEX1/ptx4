<%-- 
    Document   : cardUpdate
    Created on : 09-01-2014, 03:22:32 AM
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

        <!-- disabledButton -->
        <script src="js/disabledButton.js"></script>

    </head>

    <body>
        <div id="wrapper">
            <c:import var="menu" url="/mainMenu.jsp" />
            <c:out value="${menu}" escapeXml="false" />

            <div id="page-wrapper">
                <div class="row">
                    <div class="col-lg-12">
                        <!-- TITULO DE MANTENEDOR -->
                        <h1>Mantenedor <small> Tarjetas</small></h1>
                        <ol class="breadcrumb">
                            <li><a href="CardMainServlet"><i class="fa fa-table"></i> DataTable</a></li>
                            <li class="active"><i class="fa fa-edit"></i> Actualizar</li>
                        </ol>                                        
                        <!-- /TITULO DE MANTENEDOR -->

                        <!-- MENSAJES -->
                        <c:import var="formMsg" url="/formMsg.jsp" />
                        <c:out value="${formMsg}" escapeXml="false" />
                    </div>

                    <div class="col-lg-4">
                        <!-- FORMULARIO -->
                        <form role="form" action="CardUpdateServlet" method="POST" id="formUpdate" name="formUpdate">
                            <!-- BARCODE -->
                            <div class="form-group">
                                <label for="disabledSelect">Código de Barra</label>
                                <input class="form-control" id="disabledInput" type="text" placeholder="<c:out value="${barCode}" />" disabled>
                                <input type="hidden" name="barCode" value="<c:out value="${barCode}" />"/>
                            </div>
                            <!-- /BARCODE -->

                            <!-- RUT-DV -->
                            <div class="form-group">
                                <label for="disabledSelect">RUT</label>
                                <input class="form-control" id="disabledInput" type="text" placeholder="<c:out value="${rut}" />-<c:out value="${dv}" />" disabled>
                                <input type="hidden" name="rut" value="<c:out value="${rut}" />"/>
                                <input type="hidden" name="dv" value="<c:out value="${dv}" />"/>
                            </div>
                            <!-- /RUT-DV -->

                            <!-- FIRSTNAME-LASTNAME -->
                            <div class="form-group">
                                <label for="disabledSelect">Nombre</label>
                                <input class="form-control" id="disabledInput" type="text" placeholder="<c:out value="${firstName}" /><c:out value="${lastName}" />" disabled>
                                <input type="hidden" name="firstName" value="<c:out value="${firstName}" />"/>
                                <input type="hidden" name="lastName" value="<c:out value="${lastName}" />"/>                          
                            </div>
                            <!-- /FIRSTNAME-LASTNAME -->

                            <!-- TIPO DE TARJETA -->
                            <div class="form-group">
                                <label>Tipo de Tarjeta</label>
                                <select class="form-control" required="true" name="cardType">
                                    <option value="1"  <c:if test="${cardType == 1}" > selected </c:if>> Basic</option>
                                    <option value="2" <c:if test="${cardType == 2}" > selected </c:if>> Silver</option>
                                    <option value="3" <c:if test="${cardType == 3}" > selected </c:if>> Gold</option>
                                    </select>
                                </div> 
                                <!-- /TIPO DE TARJETA -->

                                <!-- FECHA DE INICIO/TERMINO -->
                            <c:if test="${msgErrorDateBegin == null && msgErrorDateEnd == null}">
                                <div class="form-group">
                                    <label>Fecha de Inicio</label>
                                    <input class="form-control" type="datetime-local" required="true" name="dateBeginCard" value="<c:out value="${dateBegin}" />">
                                </div>                                                      
                                <div class="form-group">
                                    <label>Fecha de Caducidad</label>
                                    <input class="form-control" type="datetime-local" required="true" name="dateEndCard" value="<c:out value="${dateEnd}" />">
                                </div>
                            </c:if>
                            <c:if test="${msgErrorDateBegin != null || msgErrorDateEnd != null}">
                                <div class="form-group has-error">
                                    <label class="control-label" for="inputError">Fecha de Inicio</label>
                                    <input class="form-control" type="datetime-local" required="true" name="dateBeginCard" id="inputError" value="<c:out value="${dateBegin}" />">
                                </div>
                                <div class="form-group has-error">
                                    <label class="control-label" for="inputError">Fecha de Caducidad</label>
                                    <input class="form-control" type="datetime-local" required="true" name="dateEndCard" id="inputError" value="<c:out value="${dateEnd}" />">
                                </div>
                            </c:if>

                            <button type="submit" name="btnUpdate" class="btn btn-default" onclick="disabledButtonUpdate();"><strong><font size="1"><object name="btn1">ACTUALIZAR</object><object name="btn2" hidden="true">ACTUALIZANDO...</object></font></strong></button>
                        </form>
                        <!-- /FORMULARIO -->
                    </div>
                </div><!-- /.row -->

                <!-- FOOTER -->
                <p>&nbsp;</p>
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
