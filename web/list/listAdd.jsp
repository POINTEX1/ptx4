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

            <!-- Collect the nav links, forms, and other content for toggling -->
            <c:import var="menu" url="/mainMenu.jsp" />
            <c:out value="${menu}" escapeXml="false" />            
            <!-- /.navbar-collapse -->

            <div id="page-wrapper">

                <div class="row">
                    <div class="col-lg-12">
                        <!-- TITULO MANTENEDOR -->
                        <h1>Mantenedor <small> Registros de Entradas</small></h1>
                        <ol class="breadcrumb">
                            <li><a href="EntryMainServlet"><i class="fa fa-table"></i> DataTable</a></li>
                            <li class="active"><i class="fa fa-edit"></i> Agregar</li>
                        </ol>
                        <!-- /TITULO MANTENEDOR -->

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

                        <!-- MENSAJE DE ERROR RUT -->
                        <c:if test="${msgErrorRut != null }" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorRut}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE ERROR RUT -->

                        <!-- MENSAJE DE ERROR ID PLACE -->
                        <c:if test="${msgErroridPlace != null }" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErroridPlace}" /></strong></br>
                            </div>
                        </c:if> 
                        <!-- /MENSAJE DE ERROR ID PLACE -->

                        <!-- MENSAJE DE ERROR BARCODE -->
                        <c:if test="${msgErrorBarCode != null }" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorBarCode}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE ERROR BARCODE -->

                        <!-- MENSAJE DE ERROR CARD NO ENCONTRADA -->
                        <c:if test="${msgErrorCardNotFound != null }" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorCardNotFound}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE ERROR CARD NO ENCONTRADA -->

                        <!-- MENSAJE DE ERROR EXPIRACION -->
                        <c:if test="${msgErrorExp != null }" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorExp}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE ERROR EXPIRACION -->

                        <!-- MENSAJE DE ERROR ID EVENTO -->
                        <c:if test="${msgErrorIdEvent != null }" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorIdEvent}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE ERROR ID EVENTO -->

                        <!-- MENSAJE DE ERROR REGISTRO NO ENCONTRADO -->
                        <c:if test="${msgErrorFound != null }" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorFound}" /></strong></br>
                            </div>
                        </c:if>   
                        <!-- /MENSAJE DE ERROR REGISTRO NO ENCONTRADO -->
                    </div>
                    <div class="col-lg-4">
                        <!-- FORMULARIO -->
                        <form role="form" action="ListAddServlet" method="POST" id="formAdd" name="formAdd">  
                            <!-- OPTION EVENTO -->
                            <p><label>Ingresar Evento:</label></p>
                            <div class="form-group">
                                <input type="radio" name="optionEvent" value="1" <c:if test="${optionEvent == 1 || optionEvent == null}">checked</c:if> <label>Actual</label>
                                </div>
                                <!-- /OPTION EVENTO -->

                                <!-- LUGAR -->
                                <div class="form-group">
                                    <label>Lugar</label>
                                    <select class="form-control" name="idPlace">
                                    <c:forEach var="listPlace" items="${listPlace}">  
                                        <option value="<c:out value="${listPlace.idPlace}" />" <c:if test="${reg.idPlace == listPlace.idPlace}">selected</c:if>> <c:out value="${listPlace.namePlace}" /> </option>
                                    </c:forEach>
                                </select>                                
                            </div>
                            <!-- /LUGAR -->

                            <!-- ID EVENTO -->
                            <div class="form-group">
                                <input type="radio" name="optionEvent" value="2" <c:if test="${optionEvent == 2}">checked</c:if> /> 
                                    <label>Por ID Evento</label>
                                    <input class="form-control" type="number" maxlength="8" name="idEvent" value="<c:out value="${reg.idEvent}" />" />
                            </div>
                            <!-- /ID EVENTO -->

                            <!-- BARCODE -->
                            <div class="form-group">                            
                                <label>Código de Barras</label>
                                <input class="form-control" type="number" maxlength="8" name="barCode" value="<c:out value="${reg.barCode}" />">
                            </div>     
                            <!-- BARCODE -->

                            <!-- BOTONES -->
                            <input type="hidden" name="add" value="ok"/>
                            <button type="submit" name="btnAdd" class="btn btn-default" onclick="disabledButtonAdd();"><strong><font size="1"><object name="btn1">AGREGAR</object><object name="btn2" hidden="true">AGREGANDO...</object></font></strong></button>
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
