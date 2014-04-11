<%-- 
    Document   : cardAdd
    Created on : 09-01-2014, 03:22:18 AM
    Author     : alexander
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>

        <title>POINTEX</title>

        <!-- imperio css -->
        <link rel="stylesheet" type="text/css" href="css/bootstrap.css" />
        <link rel="stylesheet" type="text/css" href="css/bootstrap-responsive.css" />
        <link rel="stylesheet" type="text/css" href="css/imperio.css" />

        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">

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
        </nav>

        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <!-- TITULO MANTENEDOR -->
                    <h1>Mantenedor <small> Tarjetas</small></h1>
                    <ol class="breadcrumb">
                        <li><a href="CardMainServlet"><i class="fa fa-table"></i> DataTable</a></li>
                        <li class="active"><i class="fa fa-edit"></i> Agregar</li>
                    </ol>                    

                    <!-- MENSAJES -->
                    <c:import var="formMsg" url="/formMsg.jsp" />
                    <c:out value="${formMsg}" escapeXml="false" />
                </div>

                <div class="col-lg-4">
                    <!-- FORMULARIO -->
                    <form role="form" action="CardAddServlet" method="POST" id="formAdd" name="formAdd">                        
                        <!-- RUT-DV -->
                        <div class="form-group">
                            <label for="disabledSelect">RUT</label>
                            <input class="form-control" id="disabledInput" type="text" placeholder="<c:out value="${rut}" />-<c:out value="${dv}"/>" disabled>
                            <input type="hidden" name="rut" value="<c:out value="${rut}" />"/>
                            <input type="hidden" name="dv" value="<c:out value="${dv}" />"/>
                        </div>                        

                        <!-- NOMBRE-APELLIDO -->
                        <div class="form-group">
                            <label for="disabledSelect">Nombre</label>
                            <input class="form-control" id="disabledInput" type="text" placeholder="<c:out value="${firstname}" /><c:out value="${lastname}" />" disabled>
                            <input type="hidden" name="firstname" value="<c:out value="${firstname}" />"/>
                            <input type="hidden" name="lastname" value="<c:out value="${lastname}" />"/>
                        </div>                        

                        <!-- BARCODE -->
                        <c:if test="${msgErrorBarcode == null}">
                            <div class="form-group">
                                <label>Codigo de Barra </label>
                                <input class="form-control" required="true" maxlength="8" name="barCode" value="<c:out value="${barcode}" />">
                            </div>
                        </c:if>
                        <c:if test="${msgErrorBarcode != null}">
                            <div class="form-group has-error">
                                <label class="control-label" for="inputError">Codigo de Barra </label>
                                <input class="form-control" required="true"  maxlength="8" name="barCode" id="inputError" value="<c:out value="${barcode}" />">
                            </div>
                        </c:if>                        

                        <!-- TIPO TARJETA -->
                        <div class="form-group">
                            <label>Tipo de Tarjeta</label>
                            <select class="form-control" required="true" name="cardType">
                                <option value="1"  <c:if test="${cardtype == 1}" > selected </c:if>> Basic</option>
                                <option value="2" <c:if test="${cardtype == 2}" > selected </c:if>> Silver</option>
                                <option value="3" <c:if test="${cardtype == 3}" > selected </c:if>> Gold</option>
                                </select>
                            </div>                            

                            <!-- BOTONES -->
                            <input type="hidden" name="add" value="ok"/>
                            <button type="submit" name="btnAdd" class="btn btn-default" onclick="disabledButtonAdd();"><strong><font size="1"><object name="btn1">AGREGAR</object><object name="btn2" hidden="true">AGREGANDO...</object></font></strong></button>
                            <button type="reset" class="btn btn-default"><strong><font size="1">RESET</font></strong></button>
                            <!-- /BOTONES -->
                        </form>
                        <!-- FORMULARIO -->
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