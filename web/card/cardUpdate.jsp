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

        <script type="text/javascript">
            function disabledButton() {
                if (document.formUpdate.btnUpdate.disabled == false) {
                    document.getElementById("formUpdate").submit();
                    document.formUpdate.btnUpdate.disabled = true;
                }
            }
        </script> 

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

                        <!-- MENSAJE DE ERROR DE RUT -->
                        <c:if test="${msgErrorRut != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorRut}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE ERROR DE RUT -->

                        <!-- MENSAJE DE ERROR DE DV -->
                        <c:if test="${msgErrorDv != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorDv}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE ERROR DE DV -->

                        <!-- MENSAJE DE ERROR DE FIRSTNAME -->
                        <c:if test="${msgErrorFirstName != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorFirstName}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE ERROR DE FIRSTNAME -->

                        <!-- MENSAJE DE ERROR DE LASTNAME -->
                        <c:if test="${msgErrorLastName != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorLastName}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE ERROR DE LASTNAME -->

                        <!-- MENSAJE DE ERROR DE BARCODE -->
                        <c:if test="${msgErrorBarCode != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorBarCode}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE ERROR DE BARCODE -->

                        <!-- MENSAJE DE ERROR DE TIPO DE TARJETA -->
                        <c:if test="${msgErrorType != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorType}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE ERROR DE TIPO DE TARJETA -->

                        <!-- MENSAJE DE ERROR DE FECHA DE INICIO -->
                        <c:if test="${msgErrorDateBegin != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorDateBegin}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE ERROR DE FECHA DE INICIO -->

                        <!-- MENSAJE DE EROR DE FECHA DE TERMINO -->
                        <c:if test="${msgErrorDateEnd != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorDateEnd}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE EROR DE FECHA DE TERMINO -->

                        <!-- MENSAJE DE ERROR DE REGISTRO NO ENCONTRADO -->
                        <c:if test="${msgErrorFound != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorFound}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE ERROR DE REGISTRO NO ENCONTRADO -->

                        <!-- MENSAJE DE EXITO -->
                        <c:if test="${msgOk != null}" >
                            <div class="alert alert-dismissable alert-success">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgOk}" /></strong>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE EXITO -->
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
                                <input class="form-control" id="disabledInput" type="text" placeholder="<c:out value="${firstName}" /> <c:out value="${lastName}" />" disabled>
                                <input type="hidden" name="firstName" value="<c:out value="${firstName}" /> "/>
                                <input type="hidden" name="lastName" value="<c:out value="${lastName}" />"/>                          
                            </div>
                            <!-- /FIRSTNAME-LASTNAME -->

                            <!-- TIPO DE TARJETA -->
                            <div class="form-group">
                                <label>Tipo de Tarjeta *</label>
                                <select class="form-control" required="true" name="cardType">
                                    <option value="1"  <c:if test="${cardType == 1}" > selected </c:if>> Basic</option>
                                    <option value="2" <c:if test="${cardType == 2}" > selected </c:if>> Silver</option>
                                    <option value="3" <c:if test="${cardType == 3}" > selected </c:if>> Gold</option>
                                    </select>
                                </div> 
                                <!-- /TIPO DE TARJETA -->

                                <!-- FECHA DE INICIO -->
                                <div class="form-group">
                                    <label>Fecha de Inicio</label>
                                    <input class="form-control" type="datetime-local" required="true" name="dateBeginCard" value="<c:out value="${dateBegin}" />">
                            </div>
                            <!-- /FECHA DE INICIO -->

                            <!-- FECHA DE TERMINO -->
                            <div class="form-group">
                                <label>Fecha de Caducidad</label>
                                <input class="form-control" type="datetime-local" required="true" name="dateEndCard" value="<c:out value="${dateEnd}" />">
                            </div>
                            <!-- /FECHA DE TERMINO -->                            
                            <button type="submit" name="btnUpdate" class="btn btn-default" onclick="disabledButton();"><strong><font size="1">ACTUALIZAR</font></strong></button>
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
