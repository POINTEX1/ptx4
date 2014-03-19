<%-- 
    Document   : admin
    Created on : 26-dic-2013, 16:08:09
    Author     : patricio alberto
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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

            <!-- Sidebar -->

            <!-- Collect the nav links, forms, and other content for toggling -->
            <c:import var="menu" url="/mainMenu.jsp" />
            <c:out value="${menu}" escapeXml="false" />
            <!-- /.navbar-collapse -->

            <div id="page-wrapper">

                <div class="row">
                    <div class="col-lg-12">
                        <!-- TITULO MANTENEDOR -->
                        <h1>Mantenedor <small> Categorias</small></h1>
                        <ol class="breadcrumb">
                            <li class="active"><a href="CategoryMainServlet"><i class="fa fa-table"></i> DataTable</a></li>
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
                            <div class="alert alert-dismissable alert-success">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgOk}" /></strong>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE EXITO -->

                        <!-- MENSAJE DE ERROR DE REGISTRO NO ENCONTRADO -->
                        <c:if test="${msgErrorFound != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorFound}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE ERROR DE REGISTRO NO ENCONTRADO -->

                        <!-- MENSAJE DE ERROR DE REFERENCIA -->
                        <c:if test="${msgErrorReference != null}" >
                            <div class="alert alert-dismissable alert-danger">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgErrorReference}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE ERROR DE REFERENCIA -->

                        <!-- MENSAJE DE ERROR DE ELIMINACION -->
                        <c:if test="${msgDel != null}" >
                            <div class="alert alert-dismissable alert-warning">
                                <button type="button" class="close" data-dismiss="alert">&times;</button>
                                <strong><c:out value="${msgDel}" /></strong></br>
                            </div>
                        </c:if>
                        <!-- /MENSAJE DE ERROR DE ELIMINACION -->
                    </div>
                </div><!-- /.row -->
                <div class="row">                  
                    <div class="col-lg-12"> 
                        <!-- DATATABLE -->
                        <div class="table-responsive">
                            <form action="CategoryMainServlet" method="POST" name="form">
                                <div class="row-fluid">
                                    <div class="span12">                            
                                        <div class="box">    
                                            <!-- TITULO DE DATATABLE -->
                                            <div class="box-title">
                                                Datatable
                                                <object align="right"> <button class="btn btn-primary btn-mini" name="btnAdd" type="button" onclick="location.href = 'CategoryAddServlet';" ><font size="1"><strong>AGREGAR</strong></font></button></object>
                                                </br>DB
                                            </div>
                                            <!-- /TITULO DE DATATABLE -->
                                            <div class="box-content nopadding">
                                                <table id="datatable" class="table table-striped table-bordered">
                                                    <!-- HEADER DE DATATABLE -->
                                                    <thead>
                                                        <tr>
                                                            <th><input class="check_all" type="checkbox" /></th>
                                                            <th>Id Categoria <i class="fa fa-sort"></i></th>
                                                            <th>Nombre Categoria <i class="fa fa-sort"></i></th>
                                                            <th></th>
                                                            <th></th>
                                                        </tr>
                                                    </thead>
                                                    <!-- /HEADER DE DATATABLE -->

                                                    <!-- BODY DE DATATABLE -->
                                                    <tbody>
                                                        <c:forEach var="list" items="${list}"> 
                                                            <tr>
                                                                <th><input type="checkbox" name="chk" value="<c:out value="${list.idCategory}" />"/></th>
                                                                <td class="center"><c:out value="${list.idCategory}" /></td>
                                                                <td class="center"><c:out value="${list.nameCategory}" /></td>
                                                                <td class="center">
                                                                    <a href="CategoryGetServlet?idCategory=<c:out value="${list.idCategory}" />"><button class="btn btn-primary btn-mini" name="btnUpOne" type="button"><strong><font size="1">ACTUALIZAR</font></strong></button></a>                                                                
                                                                </td>
                                                                <td class="center">                                                                    
                                                                    <button class="btn btn-danger btn-mini delete" name="btnDelRow" onclick="confirmar('CategoryMainServlet?btnDelRow=x&idCategory=<c:out value="${list.idCategory}" />');
                return false;"><strong><font size="1">ELIMINAR</font></strong></button>
                                                                </td>
                                                            </tr>
                                                        </c:forEach>                                                                                		                                    		
                                                    </tbody>
                                                    <!-- /BODY DE DATATABLE -->

                                                    <!-- FOOTER DE DATATABLE -->
                                                    <tfoot>
                                                        <tr>
                                                            <th><button class="btn btn-danger btn-mini delete" name="btnDelCol" type="submit"><font size="1">ELIMINAR</font></button></th>
                                                            <th>Id Categoria</th>
                                                            <th>Nombre Categoria</th>
                                                            <th></th>
                                                            <th></th>
                                                        </tr>
                                                    </tfoot>
                                                    <!-- /FOOTER DE DATATABLE -->
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div> <!-- /.responsive -->
                        <!-- /DATATABLE -->
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