<%-- 
    Document   : parameterUpdate
    Created on : 28-02-2014, 03:54:24 AM
    Author     : alexander
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>OTL - Parametros</title>

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
                        <h1>Mantenedor <small> Actualizar</small></h1>
                        <ol class="breadcrumb">
                            <li class="active"><i class="fa fa-edit"></i> Actualizar</li>
                        </ol>
                        <c:if test="${msg != null}" >
                            <div class="alert alert-info alert-dismissable">
                                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                                <td><strong><c:out value="${msg}" /></strong></td>
                            </div> 
                        </c:if>
                        <c:if test="${msgOk != null}" >
                            <div class="alert alert-dismissable alert-success">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgOk}" /></strong>
                            </div>
                        </c:if>
                        <c:if test="${msgErrorWaitingCard != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorWaitingCard}" /></strong></br>
                            </div>
                        </c:if>
                        <c:if test="${msgErrorNumberEvent != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorNumberEvent}" /></strong></br>
                            </div>
                        </c:if> 
                        <c:if test="${msgErrorNumberPromo != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorNumberPromo}" /></strong></br>
                            </div>
                        </c:if>    
                        <c:if test="${msgErrorBanerCentralEvent != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorBanerCentralEvent}" /></strong></br>
                            </div>
                        </c:if>
                        <c:if test="${msgErrorBanerCentralPromo != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorBanerCentralPromo}" /></strong></br>
                            </div>
                        </c:if> 
                        <c:if test="${msgErrorBanerCentralExchangeable != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorBanerCentralExchangeable}" /></strong></br>
                            </div>
                        </c:if>
                        <c:if test="${msgErrorBanerCentralVip != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorBanerCentralVip}" /></strong></br>
                            </div>
                        </c:if>
                        <c:if test="${msgErrorBanerCentralAboutUs != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorBanerCentralAboutUs}" /></strong></br>
                            </div>
                        </c:if> 
                        <c:if test="${msgErrorBanerTopEvent != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorBanerTopEvent}" /></strong></br>
                            </div>
                        </c:if> 
                        <c:if test="${msgErrorBanerTopPromo != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorBanerTopPromo}" /></strong></br>
                            </div>
                        </c:if> 
                        <c:if test="${msgErrorBanerTopMyPlace != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorBanerTopMyPlace}" /></strong></br>
                            </div>
                        </c:if> 
                        <c:if test="${msgErrorBanerTopFindPlace != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorBanerTopFindPlace}" /></strong></br>
                            </div>
                        </c:if>
                        <c:if test="${msgErrorBanerTopConfiguration != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorBanerTopConfiguration}" /></strong></br>
                            </div>
                        </c:if>
                        <c:if test="${msgErrorBanerTopSocialNetworks != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorBanerTopSocialNetworks}" /></strong></br>
                            </div>
                        </c:if>
                        <c:if test="${msgErrorFound != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorFound}" /></strong></br>
                            </div>
                        </c:if>
                    </div>
                    <div class="col-lg-4">
                        <form role="form" action="ParameterUpdateServlet" method="POST" name="formUpdate">
                            <c:if test="${msgErrorWaitingCard != null }" >
                                <div class="form-group has-error">
                                    <label class="control-label" for="inputError">Días para solicitud de tarjeta</label>
                                    <input type="number" class="form-control" required="true" min="0" name="waitingCard" id="inputError" value="<c:out value="${waitingCard}" />">
                                </div>
                            </c:if>
                            <c:if test="${msgErrorWaitingCard == null }" >
                                <div class="form-group">
                                    <label>Días para solicitud de tarjeta</label>
                                    <input type="number" class="form-control" required="true" min="0" name="waitingCard" value="<c:out value="${waitingCard}" />">
                                </div>
                            </c:if>
                            <c:if test="${msgErrorNumberEvent == null}" >
                                <div class="form-group">                            
                                    <label>Días para ultimos eventos</label>
                                    <input class="form-control" required="true" min="0" max="99" name="numberEvent" value="<c:out value="${numberEvent}" />">                            
                                </div>
                            </c:if>
                            <c:if test="${msgErrorNumberEvent != null}" >
                                <div class="form-group has-error">                            
                                    <label class="control-label" for="inputError">Días para ultimos eventos</label>
                                    <input class="form-control" required="true" min="0" max="99" id="inputError" name="numberEvent" value="<c:out value="${numberEvent}" />">                            
                                </div>
                            </c:if>
                            <c:if test="${msgErrorNumberPromo == null}" >
                                <div class="form-group">                            
                                    <label>Días para ultimas promociones</label>
                                    <input class="form-control" required="true" min="0" max="99" name="numberPromo" value="<c:out value="${numberPromo}" />">                            
                                </div>
                            </c:if>
                            <c:if test="${msgErrorNumberPromo != null}" >
                                <div class="form-group has-error">                            
                                    <label class="control-label" for="inputError">Días para ultimas promociones</label>
                                    <input class="form-control" required="true" min="0" max="99" id="inputError" name="numberPromo" value="<c:out value="${numberPromo}" />">                            
                                </div>
                            </c:if>
                            <c:if test="${msgErrorBanerCentralEvent == null}" >
                                <div class="form-group">
                                    <label>Baner central de evento (Url Image)</label>
                                    <input class="form-control" required="true" maxlength="200" name="banerCentralEvent" value="<c:out value="${banerCentralEvent}" />">
                                </div>
                            </c:if>
                            <c:if test="${msgErrorBanerCentralEvent != null}" >
                                <div class="form-group has-error">
                                    <label class="control-label" for="inputError">Baner Central de Evento (Url Image)</label>
                                    <input class="form-control" required="true" maxlength="200" id="inputError" name="banerCentralEvent" value="<c:out value="${banerCentralEvent}" />">
                                </div>
                            </c:if> 
                            <c:if test="${msgErrorBanerCentralPromo == null}" >
                                <div class="form-group">
                                    <label>Baner central de promoción (Url Image)</label>
                                    <input class="form-control" required="true" maxlength="200" name="banerCentralPromo" value="<c:out value="${banerCentralPromo}" />">
                                </div>
                            </c:if>
                            <c:if test="${msgErrorBanerCentralPromo != null}" >
                                <div class="form-group has-error">
                                    <label class="control-label" for="inputError">Baner Central de Promoción (Url Image)</label>
                                    <input class="form-control" required="true" maxlength="200" id="inputError" name="banerCentralPromo" value="<c:out value="${banerCentralPromo}" />">
                                </div>
                            </c:if>
                            <c:if test="${msgErrorBanerCentralExchangeable == null}" >
                                <div class="form-group">
                                    <label>Baner central de productos canjeables (Url Image)</label>
                                    <input class="form-control" required="true" maxlength="200" name="banerCentralExchangeable" value="<c:out value="${banerCentralExchangeable}" />">
                                </div>
                            </c:if>
                            <c:if test="${msgErrorBanerCentralExchangeable != null}" >
                                <div class="form-group has-error">
                                    <label class="control-label" for="inputError">Baner central de productos canjeables (Url Image)</label>
                                    <input class="form-control" required="true" maxlength="200" id="inputError" name="banerCentralExchangeable" value="<c:out value="${banerCentralExchangeable}" />">
                                </div>
                            </c:if>
                            <c:if test="${msgErrorBanerCentralVip == null}" >
                                <div class="form-group">
                                    <label>Baner central de vip (Url Image)</label>
                                    <input class="form-control" required="true" maxlength="200" name="banerCentralVip" value="<c:out value="${banerCentralVip}" />">
                                </div>
                            </c:if>
                            <c:if test="${msgErrorBanerCentralVip != null}" >
                                <div class="form-group has-error">
                                    <label class="control-label" for="inputError">Baner central de vip (Url Image)</label>
                                    <input class="form-control" required="true" maxlength="200" id="inputError" name="banerCentralVip" value="<c:out value="${banerCentralVip}" />">
                                </div>
                            </c:if>
                            <c:if test="${msgErrorBanerCentralAboutUs == null}" >
                                <div class="form-group">
                                    <label>Baner central de sobre nostros (Url Image)</label>
                                    <input class="form-control" required="true" maxlength="200" name="banerCentralAboutUs" value="<c:out value="${banerCentralAboutUs}" />">
                                </div>
                            </c:if>
                            <c:if test="${msgErrorBanerCentralVip != null}" >
                                <div class="form-group has-error">
                                    <label class="control-label" for="inputError">Baner central de sobre nostros (Url Image)</label>
                                    <input class="form-control" required="true" maxlength="200" id="inputError" name="banerCentralAboutUs" value="<c:out value="${banerCentralAboutUs}" />">
                                </div>
                            </c:if> 
                            <c:if test="${msgErrorBanerTopEvent == null}" >
                                <div class="form-group">
                                    <label>Baner de cabecera de evento (Url Image)</label>
                                    <input class="form-control" required="true" maxlength="200" name="banerTopEvent" value="<c:out value="${banerTopEvent}" />">
                                </div>
                            </c:if>
                            <c:if test="${msgErrorBanerTopEvent != null}" >
                                <div class="form-group has-error">
                                    <label class="control-label" for="inputError">Baner de cabecera de evento (Url Image)</label>
                                    <input class="form-control" required="true" maxlength="200" id="inputError" name="banerTopEvent" value="<c:out value="${banerTopEvent}" />">
                                </div>
                            </c:if>
                            <c:if test="${msgErrorBanerTopPromo == null}" >
                                <div class="form-group">
                                    <label>Baner de cabecera de Promoción (Url Image)</label>
                                    <input class="form-control" required="true" maxlength="200" name="banerTopPromo" value="<c:out value="${banerTopPromo}" />">
                                </div>
                            </c:if>
                            <c:if test="${msgErrorBanerTopPromo != null}" >
                                <div class="form-group has-error">
                                    <label class="control-label" for="inputError">Baner de cabecera de promoción (Url Image)</label>
                                    <input class="form-control" required="true" maxlength="200" id="inputError" name="banerTopPromo" value="<c:out value="${banerTopPromo}" />">
                                </div>
                            </c:if>
                            <c:if test="${msgErrorBanerTopMyPlace == null}" >
                                <div class="form-group">
                                    <label>Baner de cabecera de mis lugares (Url Image)</label>
                                    <input class="form-control" required="true" maxlength="200" name="banerTopMyPlace" value="<c:out value="${banerTopMyPlace}" />">
                                </div>
                            </c:if>
                            <c:if test="${msgErrorBanerTopMyPlace != null}" >
                                <div class="form-group has-error">
                                    <label class="control-label" for="inputError">Baner de cabecera de mis lugares (Url Image)</label>
                                    <input class="form-control" required="true" maxlength="200" id="inputError" name="banerTopMyPlace" value="<c:out value="${banerTopMyPlace}" />">
                                </div>
                            </c:if>
                            <c:if test="${msgErrorBanerTopFindPlace == null}" >
                                <div class="form-group">
                                    <label>Baner de cabecera de busqueda de lugares (Url Image)</label>
                                    <input class="form-control" required="true" maxlength="200" name="banerTopFindPlace" value="<c:out value="${banerTopFindPlace}" />">
                                </div>
                            </c:if>
                            <c:if test="${msgErrorBanerTopFindPlace != null}" >
                                <div class="form-group has-error">
                                    <label class="control-label" for="inputError">Baner de cabecera de busqueda de lugares (Url Image)</label>
                                    <input class="form-control" required="true" maxlength="200" id="inputError" name="banerTopFindPlace" value="<c:out value="${banerTopFindPlace}" />">
                                </div>
                            </c:if>
                            <c:if test="${msgErrorBanerTopConfiguration == null}" >
                                <div class="form-group">
                                    <label>Baner de cabecera de configuración (Url Image)</label>
                                    <input class="form-control" required="true" maxlength="200" name="banerTopConfiguration" value="<c:out value="${banerTopConfiguration}" />">
                                </div>
                            </c:if>
                            <c:if test="${msgErrorBanerTopConfiguration != null}" >
                                <div class="form-group has-error">
                                    <label class="control-label" for="inputError">Baner de cabecera de configuración (Url Image)</label>
                                    <input class="form-control" required="true" maxlength="200" id="inputError" name="banerTopConfiguration" value="<c:out value="${banerTopConfiguration}" />">
                                </div>
                            </c:if>
                            <c:if test="${msgErrorBanerTopSocialNetworks == null}" >
                                <div class="form-group">
                                    <label>Baner de cabecera de redes sociales (Url Image)</label>
                                    <input class="form-control" required="true" maxlength="200" name="banerTopSocialNetworks" value="<c:out value="${banerTopSocialNetworks}" />">
                                </div>
                            </c:if>
                            <c:if test="${msgErrorBanerTopSocialNetworks != null}" >
                                <div class="form-group has-error">
                                    <label class="control-label" for="inputError">Baner de cabecera de redes sociales (Url Image)</label>
                                    <input class="form-control" required="true" maxlength="200" id="inputError" name="banerTopSocialNetworks" value="<c:out value="${banerTopSocialNetworks}" />">
                                </div>
                            </c:if>
                            <div class="form-group">
                                <button type="submit" class="btn btn-default"><strong><font size="1">ACTUALIZAR</font></strong></button>   
                            </div>
                        </form>

                    </div>
                </div><!-- /.row -->
                <div class="row">                  
                    <div class="col-lg-12">                        

                    </div>
                </div><!-- /.row -->

            </div><!-- /#page-wrapper -->

        </div><!-- /#wrapper -->
    </body>
</html>
