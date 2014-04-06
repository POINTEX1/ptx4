<%-- 
    Document   : clientNewsAdd
    Created on : 05-03-2014, 06:29:15 PM
    Author     : alexander
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>POINTEX</title>

        <!-- imperio css -->
        <link rel="stylesheet" type="text/css" href="css/bootstrap.css" />
        <link rel="stylesheet" type="text/css" href="css/bootstrap-responsive.css" />
        <link rel="stylesheet" type="text/css" href="css/imperio.css" />

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
                        <h1>Mantenedor <small> Noticias Cliente</small></h1>
                        <ol class="breadcrumb">
                            <li><a href="ClientNewsMainServlet"><i class="fa fa-table"></i> DataTable</a></li>
                            <li class="active"><i class="fa fa-edit"></i> Agregar</li>
                        </ol>
                        <!-- /TITULO MANTENEDOR -->

                        <!-- MENSAJES -->
                        <c:import var="formMsg" url="/formMsg.jsp" />
                        <c:out value="${formMsg}" escapeXml="false" />
                    </div>
                    <div class="col-lg-4">
                        <!-- FORMULARIO -->
                        <form role="form" action="ClientNewsAddServlet" method="POST" id="formAdd" name="formAdd">                        
                            <!-- TITULO NOTICIA -->
                            <c:if test="${msgErrorDup == null && msgErrorTittle == null }" >
                                <div class="form-group">
                                    <label>Título de Noticia </label>
                                    <input class="form-control" required="true" maxlength="100" name="tittle" value="<c:out value="${tittle}" />">
                                </div>
                            </c:if>
                            <c:if test="${msgErrorDup != null || msgErrorTittle != null}" >
                                <div class="form-group has-error">
                                    <label class="control-label" for="inputError">Título de Noticia</label>
                                    <input type="text" class="form-control" required="true" name="tittle" id="inputError" value="<c:out value="${tittle}" />">
                                </div>
                            </c:if>
                            <!-- /TITULO NOTICIA -->

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

                            <!-- FECHA DE INICIO -->
                            <c:if test="${msgErrorDup == null && errorDateBegin == null}">
                                <div class="form-group">
                                    <label>Fecha de Inicio</label>
                                    <input class="form-control" type="datetime-local" required="true" name="dateBegin" value="<c:out value="${dateBegin}" />">
                                </div>
                            </c:if>
                            <c:if test="${msgErrorDup != null || errorDateBegin != null}">
                                <div class="form-group has-error">
                                    <label class="control-label" for="inputError">Fecha de Inicio</label>
                                    <input class="form-control" type="datetime-local" required="true" name="dateBegin" id="inputError" value="<c:out value="${dateBegin}" />">
                                </div>
                            </c:if>

                            <!-- FECHA DE TERMINO -->
                            <c:if test="${msgErrorDup == null && errorDateEnd == null}">
                                <div class="form-group">
                                    <label>Fecha de Término</label>
                                    <input class="form-control" type="datetime-local" required="true" name="dateEnd" value="<c:out value="${dateEnd}" />">
                                </div>
                            </c:if>                            
                            <c:if test="${msgErrorDup != null || errorDateEnd != null}">
                                <div class="form-group has-error">
                                    <label class="control-label" for="inputError">Fecha de Término</label>
                                    <input class="form-control" type="datetime-local" required="true" name="dateEnd" id="inputError" value="<c:out value="${dateEnd}" />">
                                </div>
                            </c:if>

                            <!-- BOTONES -->
                            <input type="hidden" name="add" value="ok"/>
                            <button type="submit" name="btnAdd" class="btn btn-default" onclick="disabledButtonAdd();"><strong><font size="1"><object name="btn1">AGREGAR</object><object name="btn2" hidden="true">AGREGANDO...</object></font></strong></button>
                            <button type="reset" class="btn btn-default"><strong><font size="1">RESET</font></strong></button>
                            <!-- BOTONES -->
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

        <script type="text/javascript" src="js/jquery.min.js"></script>
        <script type="text/javascript" src="js/imperiobootstrap.min.js"></script>
        <script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
        <script type="text/javascript" src="js/imperio.general.js"></script>
        <script type="text/javascript" src="js/imperio.tables.js"></script>

    </body>
</html>
