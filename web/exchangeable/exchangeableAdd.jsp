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
                        <h1>Mantenedor <small> Productos Canjeables</small></h1>
                        <ol class="breadcrumb">
                            <li><a href="ExchangeableMainServlet"><i class="fa fa-table"></i> Principal</a></li>
                            <li class="active"><i class="fa fa-edit"></i> Agregar </li>
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

                        <!-- MENSAJE DE ERROR IMAGE -->
                        <c:if test="${msgErrorUrlImage != null }" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorUrlImage}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE ERROR IMAGE -->

                        <!-- MENSAJE DE ERROR TITULO -->
                        <c:if test="${msgErrorTittle != null }" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorTittle}" /></strong></br>
                            </div>
                        </c:if>                   
                        <!-- /MENSAJE DE ERROR TITULO -->

                        <!-- MENSAJE DE ERROR REGISRTO DUPLICADO -->
                        <c:if test="${msgErrorDup != null }" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorDup}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE ERROR REGISRTO DUPLICADO -->

                        <!-- MENSAJE DE ERROR PUNTOS -->
                        <c:if test="${msgErrorPoints != null }" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorPoints}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE ERROR PUNTOS -->

                        <!-- MENSAJE DE EXITO -->
                        <c:if test="${msgOk != null}" >
                            <div class="alert alert-dismissable alert-success">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgOk}" /></strong>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE EXITO -->

                        <!-- MENSAJE DE ERROR PETICION -->
                        <c:if test="${msgErrorRequest != null }" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorRequest}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE ERROR PETICION -->
                    </div>

                    <div class="col-lg-4">
                        <form role="form" action="ExchangeableAddServlet" method="POST" id="formAdd" name="formAdd">                         
                            <!-- LUGARES -->
                            <div class="form-group">
                                <label>Lugares</label>
                                <select class="form-control" name="idPlace">
                                    <c:forEach var="listPlace" items="${listPlace}">  
                                        <option value="<c:out value="${listPlace.idPlace}" />" <c:if test="${exchange.idPlace == listPlace.idPlace}">selected</c:if>> <c:out value="${listPlace.namePlace}" /> </option>
                                    </c:forEach>
                                </select>                                
                            </div>
                            <!-- /LUGARES -->

                            <!-- TITULO -->
                            <c:if test="${msgErrorDup == null && msgErrorTittle == null }" >
                                <div class="form-group">
                                    <label>Título de Producto </label>
                                    <input class="form-control" required="true" maxlength="100" name="tittle" value="<c:out value="${exchange.tittle}" />">
                                </div>
                            </c:if>
                            <c:if test="${msgErrorDup != null || msgErrorTittle != null}" >
                                <div class="form-group has-error">
                                    <label class="control-label" for="inputError">Título de Producto</label>
                                    <input type="text" class="form-control" required="true" name="tittle" id="inputError" value="<c:out value="${exchange.tittle}" />">
                                </div>
                            </c:if>                       
                            <!-- /TITULO -->

                            <!-- PUNTOS -->
                            <c:if test="${msgErrorPoints == null }" > 
                                <div class="form-group">
                                    <label>Puntos</label>
                                    <input class="form-control" type="number" required="true" min="0" max="99999" name="points" value="<c:out value="${exchange.points}" />">
                                </div>
                            </c:if>
                            <c:if test="${msgErrorPoints != null }" >     
                                <div class="form-group has-error">
                                    <label class="control-label" for="inputError"> Puntos</label>
                                    <input class="form-control" type="number" required="true" min="0" max="99999" name="points" value="<c:out value="${exchange.points}" />">
                                </div>
                            </c:if>                           
                            <!-- /PUNTOS -->

                            <!-- URL IMAGE -->
                            <c:if test="${msgErrorUrlImage == null }" >
                                <div class="form-group">
                                    <label>URL imagen </label>
                                    <input class="form-control" required="true" maxlength="200" name="urlImage" value="<c:out value="${exchange.urlImage}" />">
                                </div>
                            </c:if>
                            <c:if test="${msgErrorUrlImage != null }" >
                                <div class="form-group has-error">
                                    <label class="control-label" for="inputError">URL imagen </label>
                                    <input class="form-control" required="true" maxlength="200" id="inputError" name="urlImage" value="<c:out value="${exchange.urlImage}" />">
                                </div>
                            </c:if>  
                            <!-- URL IMAGE -->

                            <!-- BOTONES -->
                            <input type="hidden" name="add" value="ok"/>
                            <button type="submit" name="btnAdd" class="btn btn-default" onclick="disabledButtonAdd();"><strong><font size="1"><object name="btn1">AGREGAR</object><object name="btn2" hidden="true">AGREGANDO...</object></font></strong></button>
                            <button type="reset" class="btn btn-default"><strong><font size="1">RESET</font></strong></button> 
                            <!-- /BOTONES -->
                        </form>
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