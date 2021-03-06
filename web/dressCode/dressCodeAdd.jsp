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
                        <h1>Mantenedor <small> Código de Vestir</small></h1>
                        <ol class="breadcrumb">
                            <li><a href="DressCodeMainServlet"><i class="fa fa-table"></i> DataTable</a></li>
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

                        <!-- MENSAJE DE ERROR DE REGISTRO DUPLICADO -->
                        <c:if test="${msgErrorDup != null }" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorDup}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE ERROR DE REGISTRO DUPLICADO -->

                        <!-- MENSAJE DE ERROR DE TITULO -->
                        <c:if test="${msgErrorTittle != null }" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorTittle}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE ERROR DE TITULO -->

                        <!-- MENSAJE DE ERROR DE DETALLE HOMBRES -->
                        <c:if test="${msgErrorMenDetails != null }" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorMenDetails}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE ERROR DE DETALLE HOMBRES -->

                        <!-- MENSAJE DE ERROR DE DETALLE MUJERES -->
                        <c:if test="${msgErrorWomenDetails != null }" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorWomenDetails}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE ERROR DE DETALLE MUJERES -->

                        <!-- MENSAJE DE ERROR DE URL -->
                        <c:if test="${msgErrorUrlImage != null }" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorUrlImage}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- MENSAJE DE ERROR DE URL -->
                    </div>
                    <div class="col-lg-4">
                        <!-- FORMULARIO -->
                        <form role="form" action="DressCodeAddServlet" method="POST" id="formAdd" name="formAdd">   
                            <!-- TITULO -->
                            <c:if test="${msgErrorDup == null && msgErrorTittle == null}" >
                                <div class="form-group">
                                    <label>Título</label>
                                    <input class="form-control" required="true" maxlength="30" name="nameDressCode" value="<c:out value="${nameDressCode}" />">
                                </div>
                            </c:if>                                                                                  
                            <c:if test="${msgErrorDup != null || msgErrorTittle != null }" >
                                <div class="form-group has-error">
                                    <label class="control-label" for="inputError">Título</label>
                                    <input class="form-control" required="true" maxlength="30" name="nameDressCode" id="inputError" value="<c:out value="${nameDressCode}" />">
                                </div>
                            </c:if>
                            <!-- /TITULO -->

                            <!-- DESCRIPCION HOMBRES -->
                            <c:if test="${msgErrorMenDetails == null}" >
                                <div class="form-group">
                                    <label>Descripción para Hombres</label>
                                    <input class="form-control" required="true" maxlength="255" name="menDetails" value="<c:out value="${menDetails}" />">
                                </div>
                            </c:if>                                                            
                            <c:if test="${msgErrorMenDetails != null }" >
                                <div class="form-group has-error">
                                    <label class="control-label" for="inputError">Descripción para Hombres</label>
                                    <input class="form-control" required="true" maxlength="255" name="menDetails" id="inputError" value="<c:out value="${menDetails}" />">
                                </div>
                            </c:if>
                            <!-- /DESCRIPCION HOMBRES -->

                            <!-- DESCRIPCION PARA MUJERES -->
                            <c:if test="${msgErrorWomenDetails == null}" >
                                <div class="form-group">
                                    <label>Descripción para Mujeres</label>
                                    <input class="form-control" required="true" maxlength="255" name="womenDetails" value="<c:out value="${womenDetails}" />">
                                </div>
                            </c:if>
                            <c:if test="${msgErrorWomenDetails != null }" >
                                <div class="form-group has-error">
                                    <label class="control-label" for="inputError">Descripción para Mujeres</label>
                                    <input class="form-control" required="true" maxlength="255" name="womenDetails" id="inputError" value="<c:out value="${womenDetails}" />">
                                </div>
                            </c:if>
                            <!-- /DESCRIPCION PARA MUJERES -->

                            <!-- URL IMAGEN -->
                            <c:if test="${msgErrorUrlImage == null}" >
                                <div class="form-group">
                                    <label>URL imagen</label>
                                    <input class="form-control" required="true" maxlength="255" name="urlImage" value="<c:out value="${urlImage}" />">
                                </div>
                            </c:if>
                            <c:if test="${msgErrorUrlImage != null }" >
                                <div class="form-group has-error">
                                    <label class="control-label" for="inputError">URL imagen</label>
                                    <input class="form-control" required="true" maxlength="255" name="urlImage" id="inputError" value="<c:out value="${urlImage}" />">
                                </div>
                            </c:if>
                            <!-- /URL IMAGEN -->

                            <!-- BOTONES -->
                            <input type="hidden" name="add" value="ok"/>
                            <button type="submit" name="btnAdd" class="btn btn-default" onclick="disabledButtonAdd();"><strong><font size="1"><object name="btn1">AGREGAR</object><object name="btn2" hidden="true">AGREGANDO...</object></font></strong></button>
                            <button type="reset" class="btn btn-default"><strong><font size="1">RESET</font></strong></button> 
                            <!-- /BOTONES -->
                        </form>
                        <!-- /FORMULARIO -->
                    </div>
                </div><!-- /.row --> 

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