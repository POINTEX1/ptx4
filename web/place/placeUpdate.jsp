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
                        <h1>Mantenedor <small> Lugares</small></h1>
                        <ol class="breadcrumb">
                            <li><a href="PlaceMainServlet"><i class="fa fa-table"></i> DataTable</a></li>
                            <li class="active"><i class="fa fa-edit"></i> Actualizar</li>
                        </ol>
                        
                        <!-- MENSAJES -->
                        <c:import var="formMsg" url="/formMsg.jsp" />
                        <c:out value="${formMsg}" escapeXml="false" />
                    </div>
                    
                    <div class="col-lg-4">
                        <!-- FORMULARIO -->
                        <form role="form" action="PlaceUpdateServlet" method="POST" id="formUpdate" name="formUpdate">
                            <!-- ID PLACE -->
                            <div class="form-group">
                                <label for="disabledSelect">ID</label>
                                <input class="form-control" id="disabledInput" type="text" placeholder="<c:out value="${idPlace}" />" disabled>
                                <input type="hidden" name="idPlace" value="<c:out value="${idPlace}" />"/>
                            </div>
                            <!-- /ID PLACE -->

                            <!-- NOMBRE -->
                            <c:if test="${msgErrorDup == null && msgErrorNamePlace == null }" >
                                <div class="form-group">
                                    <label>Nombre</label>
                                    <input class="form-control" required="true" maxlength="50" name="namePlace" value="<c:out value="${namePlace}" />">
                                </div>
                            </c:if>                                                                                    
                            <c:if test="${msgErrorDup != null || msgErrorNamePlace != null }" >
                                <div class="form-group has-error">
                                    <label class="control-label" for="inputError">Nombre</label>
                                    <input class="form-control" required="true" maxlength="50" name="namePlace" id="inputError" value="<c:out value="${namePlace}" />">
                                </div>
                            </c:if>
                            <!-- /NOMBRE -->

                            <!-- DIRECCION -->
                            <c:if test="${msgErrorAddress == null }" >
                                <div class="form-group">
                                    <label>Dirección</label>
                                    <input class="form-control" required="true" maxlength="100" name="address" value="<c:out value="${address}" />">
                                </div> 
                            </c:if>
                            <c:if test="${msgErrorAddress != null }" >
                                <div class="form-group has-error">
                                    <label class="control-label" for="inputError">Dirección</label>
                                    <input class="form-control" required="true" maxlength="100" id="inputError" name="address" value="<c:out value="${address}" />">
                                </div> 
                            </c:if>
                            <!-- /DIRECCION -->

                            <!-- CIUDAD -->
                            <div class="form-group">
                                <label>Ciudad</label>
                                <select class="form-control" name="idCity">
                                    <c:forEach var="listCity" items="${listCity}">  
                                        <option value="<c:out value="${listCity.idCity}" />" <c:if test="${idCity == listCity.idCity}">selected</c:if> > <c:out value="${listCity.nameCity}" /> </option>
                                    </c:forEach>
                                </select>
                            </div>
                            <!-- CIUDAD -->

                            <!-- ESTADO SERVICIO -->
                            <div class="form-group">
                                <label>Estado del servicio</label>
                                <select class="form-control" name="status">
                                    <c:if test="${status == 0}" >
                                        <option value="0" selected>de Alta</option>
                                        <option value="1" >de Baja</option>
                                    </c:if>
                                    <c:if test="${status == 1}" >
                                        <option value="0">de Alta</option>
                                        <option value="1" selected>de Baja</option>
                                    </c:if>
                                </select>
                            </div>
                            <!-- /ESTADO SERVICIO -->

                            <!-- DESCRIPCION -->
                            <c:if test="${msgErrorDes == null}" >
                                <div class="form-group">
                                    <label>Descripción</label>
                                    <input class="form-control" required="true" maxlength="200" name="description" value="<c:out value="${description}" />">
                                </div>
                            </c:if>
                            <c:if test="${msgErrorDes != null}" >
                                <div class="form-group has-error">
                                    <label class="control-label" for="inputError">Descripción</label>
                                    <input class="form-control" required="true" maxlength="200" id="inputError" name="description" value="<c:out value="${description}" />">
                                </div>
                            </c:if> 
                            <!-- /DESCRIPCION -->

                            <!-- URL IMAGEN -->
                            <c:if test="${msgErrorUrlImage == null}" >
                                <div class="form-group">
                                    <label>Url Imagen</label>
                                    <input class="form-control" required="true" maxlength="200" name="urlImage" value="<c:out value="${urlImage}" />">
                                </div>
                            </c:if>                            
                            <c:if test="${msgErrorUrlImage != null}" >
                                <div class="form-group has-error">
                                    <label class="control-label" for="inputError">Url Imagen</label>
                                    <input class="form-control" required="true" maxlength="200" id="inputError" name="urlImage" value="<c:out value="${urlImage}" />">
                                </div>
                            </c:if>
                            <!-- /URL IMAGEN -->

                            <!-- URL LOGO -->
                            <c:if test="${msgErrorUrlLogo == null}" >
                                <div class="form-group">
                                    <label>Url de Logo</label>
                                    <input class="form-control" maxlength="255" required="true" name="urlLogo" value="<c:out value="${urlLogo}" />">
                                </div>
                            </c:if>
                            <c:if test="${msgErrorUrlLogo != null}" >
                                <div class="form-group has-error">
                                    <label  class="control-label" for="inputError">Url de Logo</label>
                                    <input class="form-control" maxlength="255" required="true" name="urlLogo" id="inputError" value="<c:out value="${urlLogo}" />">
                                </div>
                            </c:if>
                            <!-- /URL LOGO -->

                            <!-- TELEFONO COTACTO -->
                            <c:if test="${msgErrorContact == null}" >
                                <div class="form-group">
                                    <label>Teléfono de Contacto</label>
                                    <input class="form-control" required="true" maxlength="10" name="contact" value="<c:out value="${contact}" />">
                                </div>
                            </c:if>
                            <c:if test="${msgErrorContact != null}" >
                                <div class="form-group has-error">
                                    <label class="control-label" for="inputError">Teléfono de Contacto</label>
                                    <input class="form-control" required="true" maxlength="10" id="inputError" name="contact" value="<c:out value="${contact}" />">
                                </div>
                            </c:if>
                            <!-- /TELEFONO COTACTO -->                                                        
                            <button type="submit" name="btnUpdate" class="btn btn-default" onclick="disabledButtonUpdate();"><strong><font size="1"><object name="btn1">ACTUALIZAR</object><object name="btn2" hidden="true">ACTUALIZANDO...</object></font></strong></button>
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