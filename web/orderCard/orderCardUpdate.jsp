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

        <script type="text/javascript">
            function changeDisplay() {
                if (document.formUpdate.orderCardRequest.value == 2) {
                    document.formUpdate.reason.style.display = 'block';
                } else {
                    document.formUpdate.reason.style.display = 'none';
                }
            }
        </script>
    </head>

    <body onload="changeDisplay()">

        <div id="wrapper">

            <!-- Collect the nav links, forms, and other content for toggling -->
            <c:import var="menu" url="/mainMenu.jsp" />
            <c:out value="${menu}" escapeXml="false" />
            <!-- /.navbar-collapse -->

            <div id="page-wrapper">

                <div class="row">
                    <div class="col-lg-12">
                        <!-- TITUTLO MANTENEDOR -->
                        <h1>Mantenedor <small> Solicitud Tarjeta</small></h1>
                        <ol class="breadcrumb">
                            <li><a href="OrderCardMainServlet"><i class="fa fa-table"></i> DataTable</a></li>
                            <li class="active"><i class="fa fa-edit"></i> Actualizar</li>
                        </ol>
                        <!-- /TITUTLO MANTENEDOR -->

                        <!-- MENSAJE INFORMATIVO -->
                        <c:if test="${msg != null}" >
                            <div class="alert alert-dismissable alert-info">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msg}" /></strong>
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

                        <!-- MENSAJE DE ERROR DE REGISTRO NO ENCONTRADO -->
                        <c:if test="${msgErrorFound != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorFound}" /></strong></br>
                            </div>
                        </c:if> 
                        <!-- /MENSAJE DE ERROR DE REGISTRO NO ENCONTRADO -->

                        <!-- MENSAJE DE ERROR DE RAZON -->
                        <c:if test="${msgErrorReason != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorReason}" /></strong></br>
                            </div>
                        </c:if> 
                        <!-- /MENSAJE DE ERROR DE RAZON -->
                    </div>



                    <div class="col-lg-4">
                        <!-- FORMULARIO --->
                        <form role="form" action="OrderCardUpdateServlet" method="POST" id="formUpdate" name="formUpdate">
                            <!-- ID ORDER CARD -->
                            <div class="form-group">
                                <label for="disabledSelect">ID Orden Tarjeta</label>
                                <input class="form-control" id="disabledInput" type="text" placeholder="<c:out value="${idOrder}" />" disabled>
                                <input type="hidden" name="idOrder" value="<c:out value="${idOrder}" />"/>
                            </div>                
                            <!-- /ID ORDER CARD -->

                            <!-- TIPO TARJETA -->
                            <div class="form-group">
                                <label>Tipo Tarjeta</label>
                                <select class="form-control" name="cardType">
                                    <option value="1" <c:if test="${cardType == 1 || cardType == null}" >checked</c:if> >BASIC</option>
                                    <option value="2" <c:if test="${cardType == 2}" >checked</c:if>>SILVER</option>
                                    <option value="3" <c:if test="${cardType == 3}" >checked</c:if>>GOLDEN</option>                              
                                    </select>
                                </div>
                                <!-- /TIPO TARJETA -->

                                <!-- SOLICITUD TARJETA -->
                                <div class="form-group">
                                    <label>Solicitud de Tarjeta</label>
                                    <select class="form-control" id="orderCardRequest" name="orderCardRequest" onclick="changeDisplay()">
                                        <option value="0" <c:if test="${request == 0 || request == null}" >selected</c:if>>Pendiente</option>
                                    <option value="1" <c:if test="${request == 1}" >selected</c:if>>Aceptada</option>
                                    <option value="2" <c:if test="${request == 2}" >selected</c:if>>Rechazada</option>                                                                                             
                                    </select>
                                </div>
                                <!-- /SOLICITUD TARJETA -->

                                <!-- RAZON RECHAZO -->
                                <div class="form-group" id="reason">
                                    <label>Razón de rechazo</label>
                                    <textarea class="form-control" name="reason" maxlength="255" rows="4"><c:out value="${reason}" /></textarea>
                            </div>
                            <!-- /RAZON RECHAZO -->
                            <button type="submit" name="btnUpdate" class="btn btn-default" onclick="disabledButtonUpdate();"><strong><font size="1"><object name="btn1">ACTUALIZAR</object><object name="btn2" hidden="true">ACTUALIZANDO...</object></font></strong></button>
                        </form>
                        <!-- /FORMULARIO --->
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