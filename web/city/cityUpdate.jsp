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

            <!-- MAIN MENU -->
            <c:import var="menu" url="/mainMenu.jsp" />
            <c:out value="${menu}" escapeXml="false" />
            <!-- /MAIN MENU -->

            <div id="page-wrapper">

                <div class="row">
                    <div class="col-lg-12">
                        <!-- TITULO MANTENEDOR -->
                        <h1>Mantenedor <small> Ciudades</small></h1>
                        <ol class="breadcrumb">
                            <li><a href="CityMainServlet"><i class="fa fa-table"></i> DataTable</a></li>
                            <li class="active"><i class="fa fa-edit"></i> Actualizar</li>
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

                        <!-- MENSAJE DE ERROR DE ID CIUDAD -->
                        <c:if test="${msgErrorId != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorId}" /></strong></br>
                            </div>
                        </c:if> 
                        <!-- /MENSAJE DE ERROR DE ID CIUDAD -->

                        <!-- MENSAJE DE ERROR DE NOMBRE DE CIUDAD -->
                        <c:if test="${msgErrorNameCity != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorNameCity}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE ERROR DE NOMBRE DE CIUDAD -->

                        <!-- MENSAJE DE ERROR DE REGISTRO DUPLICADO -->
                        <c:if test="${msgErrorDup != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorDup}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE ERROR DE NOMBRE DE CIUDAD -->

                        <!-- MENSAJE DE ERROR DE REGISTRO NO ENCONTRADO -->
                        <c:if test="${msgErrorFound != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorFound}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE ERROR DE NOMBRE DE CIUDAD -->
                    </div>
                    <div class="col-lg-4">
                        <!-- FORMULARIO -->
                        <form role="form" action="CityUpdateServlet" method="POST" id="formUpdate" name="formUpdate">
                            <!-- ID CIUDAD -->
                            <div class="form-group">
                                <label for="disabledSelect">ID Ciudad</label>
                                <input class="form-control" id="disabledInput" type="text" placeholder="<c:out value="${idCity}" />" disabled>
                                <input type="hidden" name="idCity" value="<c:out value="${idCity}" />"/>
                            </div>
                            <!-- /ID CIUDAD -->

                            <!-- NOMBRE CIUDAD -->
                            <c:if test="${msgErrorDup == null && msgErrorNameCity == null}" >
                                <div class="form-group">
                                    <label>Nombre Ciudad</label>
                                    <input class="form-control" required="true" maxlength="50" name="nameCity" value="<c:out value="${nameCity}" />">
                                </div>
                            </c:if>
                            <c:if test="${msgErrorDup != null || msgErrorNameCity != null }" >
                                <div class="form-group has-error">
                                    <label class="control-label" for="inputError">Nombre Ciudad</label>
                                    <input class="form-control" required="true" maxlength="50" name="nameCity" id="inputError" value="<c:out value="${nameCity}" />">
                                </div>
                            </c:if>
                            <!-- /NOMBRE CIUDAD -->
                            <button type="submit" name="btnUpdate" class="btn btn-default" onclick="disabledButtonUpdate();"><strong><font size="1"><object name="btn1">ACTUALIZAR</object><object name="btn2" hidden="true">ACTUALIZANDO...</object></font></strong></button>
                        </form>
                        <!-- FORMULARIO -->
                    </div>
                </div><!-- /.row -->

                <p>&nbsp;</p>
                <p>&nbsp;</p>                                
                <p>&nbsp;</p>

                <!-- FOOTER -->
                <c:import var="footer" url="/footer.jsp" />
                <c:out value="${footer}" escapeXml="false" />
                <!-- /FOOTER -->

            </div><!-- /#page-wrapper -->

        </div><!-- /#wrapper -->
    </body>
</html>