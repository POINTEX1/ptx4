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
                if (document.formUpdate.exchangeRequest.value == 2) {
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
                        <!-- TITULO MANTENEDOR -->
                        <h1>Mantenedor <small> Productos canjeables</small></h1>
                        <ol class="breadcrumb">
                            <li><a href="ExchangeableMainServlet"><i class="fa fa-table"></i> DataTable</a></li>
                            <li class="active"><i class="fa fa-edit"></i> Actualizar</li>
                        </ol>
                        <!-- /TITULO MANTENEDOR -->

                        <!-- MENSAJES -->
                        <c:import var="formMsg" url="/formMsg.jsp" />
                        <c:out value="${formMsg}" escapeXml="false" /> 
                    </div>
                    <div class="col-lg-4">
                        <!-- FORMULARIO -->
                        <form role="form" action="ExchangeableUpdateServlet" method="POST" id="formUpdate" name="formUpdate">
                            <!-- LUGAR -->
                            <div class="form-group">
                                <label for="disabledSelect">Lugar</label>
                                <input class="form-control" id="disabledInput" type="text" placeholder="<c:out value="${namePlace}" />" disabled>
                                <input type="hidden" name="namePlace" value="<c:out value="${namePlace}" />"/>                                
                            </div>
                            <!-- /LUGAR -->

                            <!-- ID EXCHANGEABLE -->
                            <div class="form-group">
                                <label for="disabledSelect">ID Exchangeable</label>
                                <input class="form-control" id="disabledInput" type="text" placeholder="<c:out value="${idExchangeable}" />" disabled>
                                <input type="hidden" name="idExchangeable" value="<c:out value="${idExchangeable}" />"/>
                            </div>
                            <!-- /ID EXCHANGEABLE -->

                            <!-- TITULO -->
                            <c:if test="${msgErrorDup == null && msgErrorTittle == null}" >
                                <div class="form-group">
                                    <label>Título de Producto</label>
                                    <input class="form-control" required="true" maxlength="100" name="tittle" value="<c:out value="${tittle}" />">
                                </div>
                            </c:if>
                            <c:if test="${msgErrorDup != null || msgErrorTittle != null}" >
                                <div class="form-group has-error">
                                    <label class="control-label" for="inputError">Título de Producto</label>
                                    <input class="form-control" required="true" maxlength="100" id="inputError" name="tittle" value="<c:out value="${tittle}" />">
                                </div>
                            </c:if> 
                            <!-- /TITULO -->

                            <!-- PUNTOS -->
                            <c:if test="${msgErrorPoints != null }" >     
                                <div class="form-group has-error">
                                    <label class="control-label" for="inputError"> Puntos</label>
                                    <input class="form-control" type="number" required="true" min="0" max="99999" name="points" value="<c:out value="${points}" />">
                                </div>
                            </c:if>
                            <c:if test="${msgErrorPoints == null }" > 
                                <div class="form-group">
                                    <label>Puntos</label>
                                    <input class="form-control" type="number" required="true" min="0" max="99999" name="points" value="<c:out value="${points}" />">
                                </div>
                            </c:if>
                            <!-- /PUNTOS -->

                            <!-- IMAGE -->
                            <c:if test="${msgErrorUrlImage == null }" >
                                <div class="form-group">
                                    <label>URL imagen </label>
                                    <input class="form-control" required="true" maxlength="200" name="urlImage" value="<c:out value="${urlImage}" />">
                                </div>
                            </c:if>
                            <c:if test="${msgErrorUrlImage != null }" >
                                <div class="form-group has-error">
                                    <label class="control-label" for="inputError">URL imagen </label>
                                    <input class="form-control" required="true" maxlength="200" id="inputError" name="urlImage" value="<c:out value="${urlImage}" />">
                                </div>
                            </c:if>
                            <!-- /IMAGE -->

                            <!-- SOLICITUD -->
                            <div class="form-group">
                                <label>Solicitud: </label>
                                <select class="form-control" id="exchangeRequest" name="exchangeRequest" onchange="changeDisplay();">                                
                                    <option value="0" <c:if test="${request == 0}">selected</c:if>>Pendiente</option>
                                    <option value="1" <c:if test="${request == 1}">selected</c:if>>Aceptada</option>
                                    <option value="2" <c:if test="${request == 2}">selected</c:if>>Rechazada</option>
                                    </select>          
                                </div>
                                <!-- /SOLICITUD -->

                                <!-- RAZON RECHAZO -->
                            <c:if test="${msgErrorReason == null}">
                                <div class="form-group" id="reason">
                                    <label>Razón de rechazo</label>
                                    <textarea class="form-control" name="reason" maxlength="255" rows="4"><c:out value="${reason}" /></textarea>
                                </div>
                            </c:if>
                            <c:if test="${msgErrorReason != null}">
                                <div class="form-group has-error" id="reason">
                                    <label class="control-label" for="inputError">Razón de rechazo</label>
                                    <textarea class="form-control" name="reason" maxlength="255" rows="4" id="inputError"><c:out value="${reason}" /></textarea>
                                </div>
                            </c:if>

                            <button type="submit" name="btnUpdate" class="btn btn-default" onclick="disabledButtonUpdate();"><strong><font size="1"><object name="btn1">ACTUALIZAR</object><object name="btn2" hidden="true">ACTUALIZANDO...</object></font></strong></button>
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
    </body>
</html>