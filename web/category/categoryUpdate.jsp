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
                        <h1>Mantenedor <small> Categorías</small></h1>
                        <ol class="breadcrumb">
                            <li><a href="CategoryMainServlet"><i class="fa fa-table"></i> DataTable</a></li>
                            <li class="active"><i class="fa fa-edit"></i> Actualizar</li>
                        </ol>
                        <!-- /TITULO MANTENEDOR -->

                        <!-- MENSAJES -->
                        <c:import var="formMsg" url="/formMsg.jsp" />
                        <c:out value="${formMsg}" escapeXml="false" />
                    </div>

                    <div class="col-lg-4">
                        <!-- FORMULARIO -->
                        <form role="form" action="CategoryUpdateServlet" method="POST" id="formUpdate" name="formUpdate">
                            <!-- ID CATEGORY -->
                            <div class="form-group">
                                <label for="disabledSelect">ID Categoría</label>
                                <input class="form-control" id="disabledInput" type="text" placeholder="<c:out value="${idCategory}" />" disabled>
                                <input type="hidden" name="idCategory" value="<c:out value="${idCategory}" />"/>
                            </div>
                            <!-- /ID CATEGORY -->

                            <!-- NOMBRE CATEGORIA -->
                            <c:if test="${msgErrorDup == null && msgErrorNameCategory == null }" >
                                <div class="form-group">
                                    <label>Nombre Categoría</label>
                                    <input class="form-control" required="true" maxlength="50" name="nameCategory" value="<c:out value="${nameCategory}" />">
                                </div>
                            </c:if>                                                                                    
                            <c:if test="${msgErrorDup != null || msgErrorNameCategory != null}" >
                                <div class="form-group has-error">
                                    <label class="control-label" for="inputError">Nombre Categoría</label>
                                    <input class="form-control" required="true" maxlength="50" name="nameCategory" id="inputError" value="<c:out value="${nameCategory}" />">
                                </div>
                            </c:if>
                            <!-- /NOMBRE CATEGORIA -->
                            <button type="submit" name="btnUpdate" class="btn btn-default" onclick="disabledButtonUpdate();"><strong><font size="1"><object name="btn1">ACTUALIZAR</object><object name="btn2" hidden="true">ACTUALIZANDO...</object></font></strong></button>
                        </form>
                        <!-- FORMULARIO -->
                    </div>
                </div><!-- /.row -->  

                <p>&nbsp;</p>
                <p>&nbsp;</p>
                <p>&nbsp;</p>                
                </br>
                <c:import var="footer" url="/footer.jsp" />
                <c:out value="${footer}" escapeXml="false" />
                <!-- /FOOTER -->

            </div><!-- /#page-wrapper -->

        </div><!-- /#wrapper -->
    </body>
</html>