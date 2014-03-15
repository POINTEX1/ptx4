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

        <script type="text/javascript">
            function changeDisplay() {
                pwd = document.getElementById("fce");
                pwd2 = document.getElementById("uce");
                pwd3 = document.getElementById("fcp");
                pwd4 = document.getElementById("ucp");
                pwd5 = document.getElementById("fcex");
                pwd6 = document.getElementById("ucex");
                pwd7 = document.getElementById("fue");
                pwd8 = document.getElementById("uue");
                pwd9 = document.getElementById("fup");
                pwd10 = document.getElementById("uup");
                pwd11 = document.getElementById("fump");
                pwd12 = document.getElementById("uump");
                pwd13 = document.getElementById("fufp");
                pwd14 = document.getElementById("uufp");
                pwd15 = document.getElementById("fuc");
                pwd16 = document.getElementById("uuc");


                if (document.formUpdate.chkfce.checked) {
                    pwd.style.display = 'block';
                } else {
                    pwd.style.display = 'none';
                }
                if (document.formUpdate.chkuce.checked) {
                    pwd2.style.display = 'block';
                } else {
                    pwd2.style.display = 'none';
                }
                if (document.formUpdate.chkfcp.checked) {
                    pwd3.style.display = 'block';
                } else {
                    pwd3.style.display = 'none';
                }
                if (document.formUpdate.chkucp.checked) {
                    pwd4.style.display = 'block';
                } else {
                    pwd4.style.display = 'none';
                }
                if (document.formUpdate.chkfcex.checked) {
                    pwd5.style.display = 'block';
                } else {
                    pwd5.style.display = 'none';
                }
                if (document.formUpdate.chkucex.checked) {
                    pwd6.style.display = 'block';
                } else {
                    pwd6.style.display = 'none';
                }
                if (document.formUpdate.chkfue.checked) {
                    pwd7.style.display = 'block';
                } else {
                    pwd7.style.display = 'none';
                }
                if (document.formUpdate.chkuue.checked) {
                    pwd8.style.display = 'block';
                } else {
                    pwd8.style.display = 'none';
                }
                if (document.formUpdate.chkfup.checked) {
                    pwd9.style.display = 'block';
                } else {
                    pwd9.style.display = 'none';
                }
                if (document.formUpdate.chkuup.checked) {
                    pwd10.style.display = 'block';
                } else {
                    pwd10.style.display = 'none';
                }
                if (document.formUpdate.chkfump.checked) {
                    pwd11.style.display = 'block';
                } else {
                    pwd11.style.display = 'none';
                }
                if (document.formUpdate.chkuump.checked) {
                    pwd12.style.display = 'block';
                } else {
                    pwd12.style.display = 'none';
                }
                if (document.formUpdate.chkfufp.checked) {
                    pwd13.style.display = 'block';
                } else {
                    pwd13.style.display = 'none';
                }
                if (document.formUpdate.chkuufp.checked) {
                    pwd14.style.display = 'block';
                } else {
                    pwd14.style.display = 'none';
                }
                if (document.formUpdate.chkfuc.checked) {
                    pwd15.style.display = 'block';
                } else {
                    pwd15.style.display = 'none';
                }
                if (document.formUpdate.chkuuc.checked) {
                    pwd16.style.display = 'block';
                } else {
                    pwd16.style.display = 'none';
                }

            }
        </script>
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
                        <c:if test="${msgErrorBanerCentralExchange != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorBanerCentralExchange}" /></strong></br>
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
                                    <input type="number" class="form-control" required="true" min="0" name="waitingCard" id="inputError" value="<c:out value="${parameter.waitingCard}" />">
                                </div>
                            </c:if>
                            <c:if test="${msgErrorWaitingCard == null }" >
                                <div class="form-group">
                                    <label>Días para solicitud de tarjeta</label>
                                    <input type="number" class="form-control" required="true" min="0" name="waitingCard" value="<c:out value="${parameter.waitingCard}" />">
                                </div>
                            </c:if>
                            <c:if test="${msgErrorNumberEvent == null}" >
                                <div class="form-group">                            
                                    <label>Días para ultimos eventos</label>
                                    <input class="form-control" required="true" min="0" max="99" name="numberEvent" value="<c:out value="${parameter.numberEvent}" />">                            
                                </div>
                            </c:if>
                            <c:if test="${msgErrorNumberEvent != null}" >
                                <div class="form-group has-error">                            
                                    <label class="control-label" for="inputError">Días para ultimos eventos</label>
                                    <input class="form-control" required="true" min="0" max="99" id="inputError" name="numberEvent" value="<c:out value="${parameter.numberEvent}" />">                            
                                </div>
                            </c:if>
                            <c:if test="${msgErrorNumberPromo == null}" >
                                <div class="form-group">                            
                                    <label>Días para ultimas promociones</label>
                                    <input class="form-control" required="true" min="0" max="99" name="numberPromo" value="<c:out value="${parameter.numberPromo}" />">                            
                                </div>
                            </c:if>
                            <c:if test="${msgErrorNumberPromo != null}" >
                                <div class="form-group has-error">                            
                                    <label class="control-label" for="inputError">Días para ultimas promociones</label>
                                    <input class="form-control" required="true" min="0" max="99" id="inputError" name="numberPromo" value="<c:out value="${parameter.numberPromo}" />">                            
                                </div>
                            </c:if>
                            <div class="form-group">
                                <label>Imagen de baner central de evento</label>
                            </div> 
                            <div class="radio-inline">
                                <label>
                                    <input type="radio" name="radioevent" id="chkfce" onClick="changeDisplay();"/> File Image
                                </label>
                            </div>
                            <div class="radio-inline">
                                <label>
                                    <input type="radio" name="radioevent" id="chkuce" onClick="changeDisplay();"/> Url image
                                </label>
                            </div>
                            <div id="fce" style="display:none">
                                <c:if test="${msgErrorBanerCentralEvent == null}" >
                                    <div class="form-group">
                                        <input type="file" required="true" maxlength="100" name="banerCentralEvent" value="<c:out value="${parameter.banerCentralEvent}" />">
                                    </div> 
                                </c:if>
                                <c:if test="${msgErrorBanerCentralEvent != null}" >
                                    <div class="form-group has-error">
                                        <label class="control-label" for="inputError"></label>
                                        <input type="file" required="true" maxlength="100" id="inputError" name="banerCentralEvent" value="<c:out value="${parameter.banerCentralEvent}" />">
                                    </div> 
                                </c:if>  
                            </div>
                            <div id="uce" style="display:none">
                                <c:if test="${msgErrorBanerCentralEvent == null}" >
                                    <div class="form-group">
                                        <input type="text" required="true" maxlength="100" name="banerCentralEvent" value="<c:out value="${parameter.banerCentralEvent}" />">
                                    </div> 
                                </c:if>
                                <c:if test="${msgErrorBanerCentralEvent != null}" >
                                    <div class="form-group has-error">
                                        <label class="control-label" for="inputError"></label>
                                        <input type="text" required="true" maxlength="100" id="inputError" name="banerCentralEvent" value="<c:out value="${parameter.banerCentralEvent}" />">
                                    </div> 
                                </c:if>  
                            </div>
                            <div class="form-group">
                                <label>Imagen de baner central de promociones</label>
                            </div> 
                            <div class="radio-inline">
                                <label>
                                    <input type="radio" name="radiopromo" id="chkfcp" onClick="changeDisplay();"/> File Image
                                </label>
                            </div>
                            <div class="radio-inline">
                                <label>
                                    <input type="radio" name="radiopromo" id="chkucp" onClick="changeDisplay();"/> Url image
                                </label>
                            </div>
                            <div id="fcp" style="display:none">
                                <c:if test="${msgErrorBanerCentralPromo == null}" >
                                    <div class="form-group">
                                        <input type="file" required="true" maxlength="100" name="banerCentralPromo" value="<c:out value="${parameter.banerCentralPromo}" />">
                                    </div> 
                                </c:if>
                                <c:if test="${msgErrorBanerCentralPromo != null}" >
                                    <div class="form-group has-error">
                                        <label class="control-label" for="inputError"></label>
                                        <input type="file" required="true" maxlength="100" id="inputError" name="banerCentralPromo" value="<c:out value="${parameter.banerCentralPromo}" />">
                                    </div> 
                                </c:if>
                            </div>
                            <div id="ucp" style="display:none">
                                <c:if test="${msgErrorBanerCentralPromo == null}" >
                                    <div class="form-group">
                                        <input class="form-control" required="true" maxlength="100" name="banerCentralPromo" value="<c:out value="${parameter.banerCentralPromo}" />">
                                    </div> 
                                </c:if>
                                <c:if test="${msgErrorBanerCentralPromo != null}" >
                                    <div class="form-group has-error">
                                        <label class="control-label" for="inputError"></label>
                                        <input class="form-control" required="true" maxlength="100" id="inputError" name="banerCentralPromo" value="<c:out value="${parameter.banerCentralPromo}" />">
                                    </div> 
                                </c:if>
                            </div>
                            <div class="form-group">
                                <label>Imagen de baner central de productos canjeables</label>
                            </div>
                            <div class="radio-inline">
                                <label>
                                    <input type="radio" name="radioexchange" id="chkfcex" onClick="changeDisplay();"/> File Image
                                </label>
                            </div>
                            <div class="radio-inline">
                                <label>
                                    <input type="radio" name="radioexchange" id="chkucex" onClick="changeDisplay();"/> Url image
                                </label>
                            </div>
                            <div id="fcex" style="display:none">
                                <c:if test="${msgErrorBanerCentralExchange == null}" >
                                    <div class="form-group">
                                        <input type="file" required="true" maxlength="100" name="banerCentralExchange" value="<c:out value="${parameter.banerCentralExchange}" />">
                                    </div> 
                                </c:if>
                                <c:if test="${msgErrorBanerCentralExchange != null}" >
                                    <div class="form-group has-error">
                                        <label class="control-label" for="inputError"></label>
                                        <input type="file" required="true" maxlength="100" id="inputError" name="banerCentralExchange" value="<c:out value="${parameter.banerCentralExchange}" />">
                                    </div> 
                                </c:if>
                            </div>
                            <div id="ucex" style="display:none">
                                <c:if test="${msgErrorBanerCentralExchange == null}" >
                                    <div class="form-group">
                                        <input class="form-control" required="true" maxlength="100" name="banerCentralExchange" value="<c:out value="${parameter.banerCentralExchange}" />">
                                    </div> 
                                </c:if>
                                <c:if test="${msgErrorBanerCentralExchange != null}" >
                                    <div class="form-group has-error">
                                        <label class="control-label" for="inputError"></label>
                                        <input class="form-control" required="true" maxlength="100" id="inputError" name="banerCentralExchange" value="<c:out value="${parameter.banerCentralExchange}" />">
                                    </div> 
                                </c:if>
                            </div>
                            <div class="form-group">
                                <label>Imagen de baner cabecera de evento</label>
                            </div>
                            <div class="radio-inline">
                                <label>
                                    <input type="radio" name="radiotevent" id="chkfue" onClick="changeDisplay();"/> File Image
                                </label>
                            </div>
                            <div class="radio-inline">
                                <label>
                                    <input type="radio" name="radiotevent" id="chkuue" onClick="changeDisplay();"/> Url image
                                </label>
                            </div>
                            <div id="fue" style="display:none">
                                <c:if test="${msgErrorBanerTopEvent == null}" >
                                    <div class="form-group">
                                        <label>File de baner en cabecera de evento</label>
                                        <input type="file" required="true" maxlength="100" name="banerTopEvent" value="<c:out value="${parameter.banerTopEvent}" />">
                                    </div>
                                </c:if>
                                <c:if test="${msgErrorBanerTopEvent != null}" >
                                    <div class="form-group has-error">
                                        <label class="control-label" for="inputError">File de baner en cabecera de evento</label>
                                        <input type="file" required="true" maxlength="100" id="inputError" name="banerTopEvent" value="<c:out value="${parameter.banerTopEvent}" />">
                                    </div> 
                                </c:if>
                            </div>
                            <div id="uue" style="display:none">
                                <c:if test="${msgErrorBanerTopEvent == null}" >
                                    <div class="form-group">
                                        <label>Url de baner en cabecera de evento</label>
                                        <input class="form-control" required="true" maxlength="100" name="banerTopEvent" value="<c:out value="${parameter.banerTopEvent}" />">
                                    </div>
                                </c:if>
                                <c:if test="${msgErrorBanerTopEvent != null}" >
                                    <div class="form-group has-error">
                                        <label class="control-label" for="inputError">Url de baner en cabecera de evento</label>
                                        <input class="form-control" required="true" maxlength="100" id="inputError" name="banerTopEvent" value="<c:out value="${parameter.banerTopEvent}" />">
                                    </div> 
                                </c:if>
                            </div>
                            <div class="form-group">
                                <label>Imagen de baner cabecera de promociones</label>
                            </div>
                            <div class="radio-inline">
                                <label>
                                    <input type="radio" name="radiotpromo" id="chkfup" onClick="changeDisplay();"/> File Image
                                </label>
                            </div>
                            <div class="radio-inline">
                                <label>
                                    <input type="radio" name="radiotpromo" id="chkuup" onClick="changeDisplay();"/> Url image
                                </label>
                            </div>
                            <div id="fup" style="display:none">
                                <c:if test="${msgErrorBanerTopPromo == null}" >
                                    <div class="form-group">
                                        <input type="file" required="true" maxlength="100" name="banerTopPromo" value="<c:out value="${parameter.banerTopPromo}" />">
                                    </div> 
                                </c:if>
                                <c:if test="${msgErrorBanerTopPromo != null}" >
                                    <div class="form-group has-error">
                                        <label class="control-label" for="inputError"></label>
                                        <input type="file" required="true" maxlength="100" id="inputError" name="banerTopPromo" value="<c:out value="${parameter.banerTopPromo}" />">
                                    </div> 
                                </c:if>
                            </div>
                            <div id="uup" style="display:none">
                                <c:if test="${msgErrorBanerTopPromo == null}" >
                                    <div class="form-group">
                                        <label>Url de baner en cabecera de promociones</label>
                                        <input class="form-control" required="true" maxlength="100" name="banerTopPromo" value="<c:out value="${parameter.banerTopPromo}" />">
                                    </div> 
                                </c:if>
                                <c:if test="${msgErrorBanerTopPromo != null}" >
                                    <div class="form-group has-error">
                                        <label class="control-label" for="inputError">URL de baner en cabecera de promociones</label>
                                        <input class="form-control" required="true" maxlength="100" id="inputError" name="banerTopPromo" value="<c:out value="${parameter.banerTopPromo}" />">
                                    </div> 
                                </c:if>
                            </div>
                            <div class="form-group">
                                <label>Imagen de baner central de mis lugares</label>
                            </div>
                            <div class="radio-inline">
                                <label>
                                    <input type="radio" name="radiotmplace" id="chkfump" onClick="changeDisplay();"/> File Image
                                </label>
                            </div>
                            <div class="radio-inline">
                                <label>
                                    <input type="radio" name="radiotmplace" id="chkuump" onClick="changeDisplay();"/> Url image
                                </label>
                            </div>
                            <div id="fump" style="display:none">
                                <c:if test="${msgErrorBanerTopMyPlace == null}" >
                                    <div class="form-group">
                                        <label>File de baner en cabecera de lugares de clientes</label>
                                        <input type="file" required="true" maxlength="100" name="banerTopMyPlace" value="<c:out value="${parameter.banerTopMyPlace}" />">
                                    </div> 
                                </c:if>
                                <c:if test="${msgErrorBanerTopMyPlace != null}" >
                                    <div class="form-group has-error">
                                        <label class="control-label" for="inputError">File de baner en cabecera de lugares de clientes</label>
                                        <input type="file" required="true" maxlength="100" id="inputError" name="banerTopMyPlace" value="<c:out value="${parameter.banerTopMyPlace}" />">
                                    </div> 
                                </c:if>
                            </div>
                            <div id="uump" style="display:none">
                                <c:if test="${msgErrorBanerTopMyPlace == null}" >
                                    <div class="form-group">
                                        <label>Url de baner en cabecera de lugares de clientes</label>
                                        <input class="form-control" required="true" maxlength="100" name="banerTopMyPlace" value="<c:out value="${parameter.banerTopMyPlace}" />">
                                    </div> 
                                </c:if>
                                <c:if test="${msgErrorBanerTopMyPlace != null}" >
                                    <div class="form-group has-error">
                                        <label class="control-label" for="inputError">URL de baner en cabecera de lugares de clientes</label>
                                        <input class="form-control" required="true" maxlength="100" id="inputError" name="banerTopMyPlace" value="<c:out value="${parameter.banerTopMyPlace}" />">
                                    </div> 
                                </c:if>
                            </div>
                            <div class="form-group">
                                <label>Imagen de baner cabecera de busqueda de lugares</label>
                            </div>
                            <div class="radio-inline">
                                <label>
                                    <input type="radio" name="radiotfplace" id="chkfufp" onClick="changeDisplay();"/> File Image
                                </label>
                            </div>
                            <div class="radio-inline">
                                <label>
                                    <input type="radio" name="radiotfplace" id="chkuufp" onClick="changeDisplay();"/> Url image
                                </label>
                            </div>
                            <div id="fufp" style="display:none">
                                <c:if test="${msgErrorBanerTopFindPlace == null}" >
                                    <div class="form-group">
                                        <label>File de baner en cabecera de buscar lugares</label>
                                        <input type="file" required="true" maxlength="100" name="banerTopFindPlace" value="<c:out value="${parameter.banerTopFindPlace}" />">
                                    </div> 
                                </c:if>
                                <c:if test="${msgErrorBanerTopFindPlace != null}" >
                                    <div class="form-group has-error">
                                        <label class="control-label" for="inputError">File de baner en cabecera de buscar lugares</label>
                                        <input type="file" required="true" maxlength="100" id="inputError" name="banerTopFindPlace" value="<c:out value="${parameter.banerTopFindPlace}" />">
                                    </div> 
                                </c:if>
                            </div>
                            <div id="uufp" style="display:none">
                                <c:if test="${msgErrorBanerTopFindPlace == null}" >
                                    <div class="form-group">
                                        <label>Url de baner en cabecera de buscar lugares</label>
                                        <input class="form-control" required="true" maxlength="100" name="banerTopFindPlace" value="<c:out value="${parameter.banerTopFindPlace}" />">
                                    </div> 
                                </c:if>
                                <c:if test="${msgErrorBanerTopFindPlace != null}" >
                                    <div class="form-group has-error">
                                        <label class="control-label" for="inputError">Url de baner en cabecera de buscar lugares</label>
                                        <input class="form-control" required="true" maxlength="100" id="inputError" name="banerTopFindPlace" value="<c:out value="${parameter.banerTopFindPlace}" />">
                                    </div> 
                                </c:if>
                            </div>
                            <div class="form-group">
                                <label>Imagen de baner cabecera de  onfiguración</label>
                            </div>
                            <div class="radio-inline">
                                <label>
                                    <input type="radio" name="radiotconfig" id="chkfuc" onClick="changeDisplay();"/> File Image
                                </label>
                            </div>
                            <div class="radio-inline">
                                <label>
                                    <input type="radio" name="radiotconfig" id="chkuuc" onClick="changeDisplay();"/> Url image
                                </label>
                            </div>
                            <div id="fuc" style="display:none">
                                <c:if test="${msgErrorBanerTopConfiguration == null}" >
                                    <div class="form-group">
                                        <label>File de baner en cabecera de configuración</label>
                                        <input type="file" required="true" maxlength="100" name="banerTopConfiguration" value="<c:out value="${parameter.banerTopConfiguration}" />">
                                    </div> 
                                </c:if>
                                <c:if test="${msgErrorBanerTopConfiguration != null}" >
                                    <div class="form-group has-error">
                                        <label class="control-label" for="inputError">File de baner en cabecera de configuración</label>
                                        <input type="file" required="true" maxlength="100" id="inputError" name="banerTopConfiguration" value="<c:out value="${parameter.banerTopConfiguration}" />">
                                    </div> 
                                </c:if>
                            </div>
                            <div id="uuc" style="display:none">
                                <c:if test="${msgErrorBanerTopConfiguration == null}" >
                                    <div class="form-group">
                                        <label>Url de baner en cabecera de configuración</label>
                                        <input class="form-control" required="true" maxlength="100" name="banerTopConfiguration" value="<c:out value="${parameter.banerTopConfiguration}" />">
                                    </div> 
                                </c:if>
                                <c:if test="${msgErrorBanerTopConfiguration != null}" >
                                    <div class="form-group has-error">
                                        <label class="control-label" for="inputError">URL de baner en cabecera de configuración</label>
                                        <input class="form-control" required="true" maxlength="100" id="inputError" name="banerTopConfiguration" value="<c:out value="${parameter.banerTopConfiguration}" />">
                                    </div> 
                                </c:if>
                            </div>
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
