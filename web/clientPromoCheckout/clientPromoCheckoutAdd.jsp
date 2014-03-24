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
                        <!-- TITULO DE MANTENEDOR -->
                        <h1>Mantenedor <small> Promociones compradas por Cliente</small></h1>
                        <ol class="breadcrumb">
                            <li><a href="ClientPromoCheckoutMainServlet"><i class="fa fa-table"></i> DataTable</a></li>
                            <li class="active"><i class="fa fa-edit"></i> Agregar</li>
                        </ol>
                        <!-- /TITULO DE MANTENEDOR -->

                        <!-- MENSAJE INFORMATIVO -->
                        <c:if test="${msg!= null }" >
                            <div class="alert alert-dismissable alert-info">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msg}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- /MENSAJE INFORMATIVO -->

                        <!-- MESAJE DE ERROR DE PROMOCION -->
                        <c:if test="${msgErrorPromo != null }" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorPromo}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- /MESAJE DE ERROR DE PROMOCION -->

                        <!-- MENSAJE DE ERROR DE RUT -->
                        <c:if test="${msgErrorRut != null }" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorRut}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE ERROR DE RUT -->

                        <!-- MENSAJE DE ERROR DE CLIENTE NO ENCONTRADO -->
                        <c:if test="${msgErrorUserFound != null }" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorUserFound}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE ERROR DE REGISTRO NO ENCONTRADO -->

                        <!-- MENSAJE DE ERROR DE REGISTRO NO ENCONTRADO -->
                        <c:if test="${msgErrorFound != null }" >
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
                        <form role="form" action="ClientPromoCheckoutAddServlet" method="POST" id="formAdd" name="formAdd">                             
                            <!-- ID PROMO -->
                            <c:if test="${msgErrorPromo == null }" >
                                <div class="form-group">
                                    <label>ID Promo</label>
                                    <input type="number" class="form-control" required="true" min="1" name="idPromo" value="<c:out value="${idPromo}" />">
                                </div>
                            </c:if>                                                                                   
                            <c:if test="${msgErrorPromo != null }" >
                                <div class="form-group has-error">
                                    <label class="control-label" for="inputError">ID Promo</label>
                                    <input type="number" class="form-control" required="true" min="1" name="idPromo" id="inputError" value="<c:out value="${idPromo}" />">
                                </div>
                            </c:if>
                            <!-- /ID PROMO -->

                            <!-- RUT -->
                            <c:if test="${msgErrorRut == null}">
                                <div class="form-group">
                                    <label>RUT</label>
                                    <input class="form-control" required="true" maxlength="12" name="rut" value="<c:out value="${rut}" />">
                                </div>
                            </c:if>
                            <c:if test="${msgErrorRut != null}">
                                <div class="form-group has-error">
                                    <label class="control-label" for="inputError">RUT</label>
                                    <input class="form-control" required="true" maxlength="12" name="rut" id="inputError" value="<c:out value="${rut}" />">
                                </div>
                            </c:if>
                            <!-- /RUT -->

                            <!-- BOTONES -->
                            <input type="hidden" name="add" value="ok"/>
                            <button type="submit" name="btnAdd" class="btn btn-default" onclick="disabledButtonAdd();"><strong><font size="1"><object name="btn1">AGREGAR</object><object name="btn2" hidden="true">AGREGANDO...</object></font></strong></button>
                            <button type="reset" class="btn btn-default"><strong><font size="1">RESET</font></strong></button> 
                            <!-- /BOTONES -->
                        </form>
                        <!-- FORMULARIO -->
                    </div>
                </div><!-- /.row -->    

                </br>
                <!-- FOOTER -->
                <p>&nbsp;</p>
                <p>&nbsp;</p>

                <c:import var="footer" url="/footer.jsp" />
                <c:out value="${footer}" escapeXml="false" />
                <!-- /FOOTER -->

            </div><!-- /#page-wrapper -->

        </div><!-- /#wrapper -->

    </body>
</html>