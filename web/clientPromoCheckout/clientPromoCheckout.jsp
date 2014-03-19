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

        <script>
            function confirmar(url)
            {
                if (confirm('¿Está seguro de eliminar el registro?'))
                {
                    window.location = url;
                }
                else
                {
                    return false;
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
                        <!-- TITULO MANTENEDOR -->
                        <h1>Mantenedor <small> Promociones compradas por Cliente</small></h1>
                        <ol class="breadcrumb">
                            <li class="active"><a href="ClientPromoCheckoutMainServlet"><i class="fa fa-table"></i> DataTable</a></li>
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

                        <!-- MENSAJE DE EXITO -->
                        <c:if test="${msgOk != null}" >
                            <div class="alert alert-info alert-dismissable">
                                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                                <td><c:out value="${msgOk}" /></td>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE EXITO -->

                        <!-- MENSAJE DE ELIMINACION -->
                        <c:if test="${msgDel != null}" >
                            <div class="alert alert-dismissable alert-warning">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgDel}" /></strong></br>
                            </div>
                        </c:if>  
                        <!-- /MENSAJE DE ELIMINACION -->

                        <!-- MENSAJE DE ERROR DE REGISTRO NO ECONTRADO -->
                        <c:if test="${msgErrorFound != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorFound}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE ERROR DE REGISTRO NO ECONTRADO -->

                        <!-- MENSAJE DE ERROR DE REFERENCIA -->
                        <c:if test="${msgErrorReference != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorReference}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE ERROR DE REFERENCIA -->
                    </div>
                </div><!-- /.row -->

                <div class="row">                  
                    <div class="col-lg-12"> 
                        <!-- DATATABLE -->
                        <div class="table-responsive">
                            <form action="ClientPromoCheckoutMainServlet" method="POST" name="form">
                                <div class="row-fluid">
                                    <div class="span12">                            
                                        <div class="box">  
                                            <!-- TITULO DATATABLE -->
                                            <div class="box-title">
                                                Datatable
                                                <object align="right"> <button class="btn btn-primary btn-mini" name="btnAdd" type="button" onclick="location.href = 'ClientPromoCheckoutAddServlet';" ><font size="1"><strong>AGREGAR</strong></font></button></object>
                                                </br>DB
                                            </div>
                                            <!-- /TITULO DATATABLE -->
                                            <div class="box-content nopadding">
                                                <table id="datatable" class="table table-striped table-bordered">
                                                    <!-- HEADER DATATABLE -->
                                                    <thead>
                                                        <tr>
                                                            <th><input class="check_all" type="checkbox" /></th>
                                                            <th>ID checkout <i class="fa fa-sort"></i></th>
                                                            <th>Lugar <i class="fa fa-sort"></i></th>
                                                            <th>ID Promo <i class="fa fa-sort"></i></th>
                                                            <th>Título Promoción <i class="fa fa-sort"></i></th>
                                                            <th>Rut <i class="fa fa-sort"></i></th>
                                                            <th>Fecha/Hora Compra <i class="fa fa-sort"></i></th>
                                                            <th></th>
                                                        </tr>
                                                    </thead>
                                                    <!-- /HEADER DATATABLE -->

                                                    <!-- BODY DATATABLE -->
                                                    <tbody>
                                                        <tr>
                                                            <c:forEach var="list" items="${list}">  
                                                                <td class="center"><input type="checkbox" name="chk" value="<c:out value="${list.idCheck}" />"/></td>
                                                                <td class="center"><c:out value="${list.idCheck}" /></td>
                                                                <td class="center"><c:out value="${list.namePlace}" /></td>
                                                                <td class="center"><c:out value="${list.idPromo}" /></td>
                                                                <td class="center"><c:out value="${list.tittlePromo}" /></td>
                                                                <td class="center"><c:out value="${list.rut}" />-<c:out value="${list.dv}" /></td>                                                                
                                                                <td class="center"><c:out value="${list.createTime}" /></td>                                                                
                                                                <td class="center">                                                                   
                                                                    <button class="btn btn-danger btn-mini delete" name="btnDelRow" onclick="confirmar('ClientPromoCheckoutMainServlet?btnDelRow=x&idCheck=<c:out value="${list.idCheck}" />');
                return false;"><strong><font size="1">ELIMINAR</font></strong></button>
                                                                </td>
                                                            </tr>
                                                        </c:forEach>
                                                    </tbody>
                                                    <!-- /BODY DATATABLE -->

                                                    <!-- FOOT DATATABLE -->
                                                    <tfoot>
                                                        <tr>
                                                            <th><button class="btn btn-danger btn-mini delete" name="btnDelCol" type="submit"><font size="1">ELIMINAR</font></button></th>
                                                            <th>ID checkout </th>
                                                            <th>Lugar </th>
                                                            <th>ID Promo </th>
                                                            <th>Título Promoción </th>
                                                            <th>Rut </th>
                                                            <th>Fecha/Hora Compra </th>
                                                            <th></th>
                                                        </tr>
                                                    </tfoot>
                                                    <!-- /FOOT DATATABLE -->
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <!-- /DATATABLE -->
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