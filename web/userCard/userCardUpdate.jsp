<%-- 
    Document   : userCardUpdate
    Created on : 08-01-2014, 05:17:07 PM
    Author     : alexander
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>OTL - Actualizar Cliente</title>

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

        <script type="text/javascript">
            function changeDisplay() {
                chk = document.getElementById("chk");
                pwd = document.getElementById("pwd");


                if (document.formUpdate.chk.checked) {
                    pwd.style.display = 'block';
                } else {
                    pwd.style.display = 'none';
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
                        <h1>Mantenedor <small> Clientes</small></h1>
                        <ol class="breadcrumb">
                            <li><a href="ClientMainServlet"><i class="fa fa-table"></i> DataTable</a></li>
                            <li class="active"><i class="fa fa-edit"></i> Actualizar</li>
                        </ol>
                        <!-- MENSAJES -->
                        <c:import var="formMsg" url="/formMsg.jsp" />
                        <c:out value="${formMsg}" escapeXml="false" />
                    </div>
                    <div class="col-lg-4">
                        <form role="form" action="UserCardUpdateServlet" method="POST" id="formUpdate" name="formUpdate">
                            <div class="form-group">
                                <label for="disabledSelect">Rut</label>
                                <input class="form-control" id="disabledInput" type="text" placeholder="<c:out value="${rut}" />-<c:out value="${dv}" />" disabled>
                                <input type="hidden" name="rut" value="<c:out value="${rut}" />"/>
                            </div>

                            <c:choose>
                                <c:when test="${msgErrorFirstName == null}">
                                    <div class="form-group">
                                        <label>Nombres </label>
                                        <input class="form-control" required="true" maxlength="50" name="firstName" value="<c:out value="${firstName}" />">
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="form-group has-error">
                                        <label class="control-label" for="inputError">Nombres </label>
                                        <input class="form-control" required="true" maxlength="50" name="firstName" id="inputError" value="<c:out value="${firstName}" />">
                                    </div>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${msgErrorLastName == null}">
                                    <div class="form-group">
                                        <label>Apellidos </label>
                                        <input class="form-control" required="true" maxlength="50" name="lastName" value="<c:out value="${lastName}" />">
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="form-group has-error">
                                        <label class="control-label" for="inputError">Apellidos </label>
                                        <input class="form-control" required="true" maxlength="50" name="lastName" id="inputError" value="<c:out value="${lastName}" />">
                                    </div>
                                </c:otherwise>
                            </c:choose>
                            <div class="form-group">
                                <label>Género </label>
                                <select class="form-control" required="true" name="gender">
                                    <option value="0"  <c:if test="${gender == 0}" > selected </c:if>> Masculino</option>
                                    <option value="1" <c:if test="${gender == 1}" > selected </c:if>> Femenino</option>
                                    </select>
                                </div>
                            <c:choose>
                                <c:when test="${msgErrorDateBirth == null}">
                                    <div class="form-group">
                                        <label>Fecha de Nacimiento </label>
                                        <input class="form-control" type="date" required="true" name="dateBirth" value="<c:out value="${dateBirth}" />">
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="form-group has-error">
                                        <label class="control-label" for="inputError">Fecha de Nacimiento </label>
                                        <input class="form-control" type="date" required="true" name="dateBirth" id="inputError" value="<c:out value="${dateBirth}" />">
                                    </div>
                                </c:otherwise>
                            </c:choose>
                            <div class="form-group">
                                <label>Cuidad</label>
                                <select class="form-control" required="true" name="idCity">
                                    <c:forEach var="listCity" items="${listCity}">
                                        <option value="<c:out value="${listCity.idCity}" />" <c:if test="${idCity == listCity.idCity}">selected</c:if>> <c:out value="${listCity.nameCity}" /> </option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>Universidad</label>
                                <select class="form-control" required="true" name="idUniversity">
                                    <c:forEach var="listUniversity" items="${listUniversity}">
                                        <option value="<c:out value="${listUniversity.idUniversity}" />" <c:if test="${listUniversity.idUniversity == idUniversity}">selected</c:if> > <c:out value="${listUniversity.nameUniversity}" /> </option>
                                    </c:forEach>
                                </select>
                            </div>
                            <c:choose>
                                <c:when test="${msgErrorEmail == null}">
                                    <div class="form-group input-group">
                                        <span class="input-group-addon">@</span>
                                        <input type="email" required="true" name="email" maxlength="254" class="form-control" placeholder="Email" value="<c:out value="${email}" />" >
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="form-group input-group has-error">
                                        <span class="input-group-addon" for="inputError">@</span>
                                        <input type="email" required="true" name="email" maxlength="254" id="inputError" maxlength="50" class="form-control" placeholder="Email" value="<c:out value="${email}" />" >
                                    </div>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${msgErrorTelephone == null}">
                                    <div class="form-group">
                                        <label>Teléfono Celular (8 dígitos)</label>
                                        <input class="form-control" required="true" maxlength="8" name="telephone" value="<c:out value="${telephone}" />">
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="form-group has-error">
                                        <label class="control-label" for="inputError">Teléfono Celular (8 dígitos)</label>
                                        <input class="form-control" required="true" maxlength="8" name="telephone" id="inputError" value="<c:out value="${telephone}" />">
                                    </div>
                                </c:otherwise>
                            </c:choose>                            
                            <c:choose>
                                <c:when test="${msgErrorFacebook == null}">
                                    <div class="form-group">
                                        <label>Facebook </label>
                                        <input class="form-control" required="true" name="facebook" value="<c:out value="${facebook}" />">
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="form-group has-error">
                                        <label class="control-label" for="inputError">Facebook </label>
                                        <input class="form-control" required="true" name="facebook" id="inputError" value="<c:out value="${facebook}" />">
                                    </div>
                                </c:otherwise>
                            </c:choose>
                            <div class="form-group">
                                <label class="checkbox-inline">
                                    <input type="checkbox" name="chk" id="chk" onClick="changeDisplay();"/> Restablecer password
                                </label>
                            </div>     
                            <div id="pwd" style="display:none">
                                <c:if test="${msgErrorPwd1 == null && msgErrorPwd2 == null}">
                                    <div class="form-group">
                                        <label>Password (min 6 caracteres) </label>
                                        <input class="form-control" type="password" maxlength="20" name="pwd1" value="<c:out value="${pwd1}" />">
                                    </div>
                                    <div class="form-group">
                                        <label>Repetir Password </label>
                                        <input class="form-control" type="password" maxlength="20" name="pwd2" value="<c:out value="${pwd2}" />">
                                    </div>
                                </c:if>
                                <c:if test="${msgErrorPwd1 != null || msgErrorPwd2 != null}">
                                    <div class="form-group has-error">
                                        <label  class="control-label" for="inputError">Password (min 6 caracteres) </label>
                                        <input class="form-control" type="password" maxlength="20" name="pwd1" id="inputError" value="<c:out value="${pwd1}" />">
                                    </div>
                                    <div class="form-group has-error">
                                        <label  class="control-label" for="inputError">Repetir Password </label>
                                        <input class="form-control" type="password" maxlength="20" name="pwd2" id="inputError" value="<c:out value="${pwd2}" />">
                                    </div>
                                </c:if>                              
                            </div>
                            <button type="submit" name="btnUpdate" class="btn btn-default" onclick="disabledButtonUpdate();"><strong><font size="1"><object name="btn1">ACTUALIZAR</object><object name="btn2" hidden="true">ACTUALIZANDO...</object></font></strong></button>
                        </form>

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
