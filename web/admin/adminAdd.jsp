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
                        <h1>Mantenedor <small> Admin</small></h1>
                        <ol class="breadcrumb">
                            <li><a href="AdminMainServlet"><i class="fa fa-table"></i> DataTable</a></li>
                            <li class="active"><i class="fa fa-edit"></i> Agregar</li>
                        </ol>

                        <!-- MENSAJES -->
                        <c:import var="formMsg" url="/formMsg.jsp" />
                        <c:out value="${formMsg}" escapeXml="false" />
                    </div>
                    <div class="col-lg-4">
                        <!-- FORMULARIO -->
                        <form role="form" action="AdminAddServlet" method="POST" id="formAdd" name="formAdd"> 
                            <!-- USERNAME -->
                            <c:if test="${msgErrorUsername == null}">
                                <div class="form-group">
                                    <label>Username </label>
                                    <input class="form-control" required="true" maxlength="30" name="username" value="<c:out value="${username}" />">
                                </div>
                            </c:if>
                            <c:if test="${msgErrorUsername != null}">
                                <div class="form-group has-error">
                                    <label class="control-label" for="inputError">Username </label>
                                    <input class="form-control" required="true" maxlength="30" name="username" id="inputError" value="<c:out value="${username}" />">
                                </div>
                            </c:if>
                            <!-- /USERNAME -->                          

                            <!-- EMAIL -->
                            <c:if test="${msgErrorEmail == null}">
                                <div class="form-group input-group">
                                    <span class="input-group-addon">@</span>
                                    <input type="email" required="true" name="email" maxlength="254" class="form-control" placeholder="Email" value="<c:out value="${email}" />" >
                                </div>
                            </c:if>
                            <c:if test="${msgErrorEmail != null}">
                                <div class="form-group input-group has-error">
                                    <span class="input-group-addon" for="inputError">@</span>
                                    <input type="email" required="true" name="email" maxlength="254" id="inputError" maxlength="50" class="form-control" placeholder="Email" value="<c:out value="${email}" />" >
                                </div>
                            </c:if>
                            <!-- /EMAIL -->

                            <!-- PERMISOS -->
                            <div class="form-group">
                                <label>Permisos</label>
                                <select class="form-control" name="type_admin">
                                    <option value="333" <c:if test="${type == 333}">selected</c:if>>Nivel 3: Acceso a tarjetas y clientes</option>
                                    <option value="555" <c:if test="${type == 555}">selected</c:if>>Nivel 5: Acceso a eventos, promos y regalos</option>
                                    <option value="777" <c:if test="${type == 777}">selected</c:if>>Nivel 7: Acceso a todo</option>
                                    </select>                                
                                </div>
                                <!-- /PERMISOS -->

                                <!-- PASSWORD -->
                            <c:if test="${msgErrorPwd1 == null && msgErrorPwd2 == null }" >
                                <div class="form-group">
                                    <label>Password (min 6 caracteres) </label>
                                    <input class="form-control" type="password" maxlength="20" required="true" name="pwd1">
                                </div>
                                <div class="form-group">
                                    <label>Confirmar Password </label>
                                    <input class="form-control" type="password" maxlength="20" required="true" name="pwd2">
                                </div>
                            </c:if>
                            <c:if test="${msgErrorPwd1 != null || msgErrorPwd2 != null}" >
                                <div class="form-group has-error">
                                    <label  class="control-label" for="inputError">Password (min 6 caracteres) </label>
                                    <input class="form-control" type="password" maxlength="20" required="true" name="pwd1" id="inputError">
                                </div>
                                <div class="form-group has-error">
                                    <label  class="control-label" for="inputError">Repetir Password </label>
                                    <input class="form-control" type="password" maxlength="20" required="true" name="pwd2" id="inputError">
                                </div>
                            </c:if>
                            <!-- /PASSWORD -->

                            <!-- BOTONES -->     
                            <input type="hidden" name="add" value="1"/>
                            <button type="submit" name="btnAdd" class="btn btn-default" onclick="disabledButtonAdd();"><strong><font size="1"><object name="btn1">AGREGAR</object><object name="btn2" hidden="true">AGREGANDO...</object></font></strong></button>
                            <button type="reset" class="btn btn-default"><strong><font size="1">RESET</font></strong></button> 
                            <!-- /BOTONES -->
                        </form>
                        <!-- /FORMULARIO -->
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