<%-- 
    Document   : placeNews
    Created on : 18-03-2014, 12:36:51 PM
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

        <!-- export excel -->
        <script src="js/export-excel.js"></script>

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
                        <h1>Mantenedor <small> Noticias para Lugares</small></h1>
                        <ol class="breadcrumb">
                            <li class="active"><a href="PlaceNewsMainServlet"><i class="fa fa-table"></i> DataTable</a></li>
                        </ol>
                        <!-- /TITULO MANTENEDOR -->

                        <!-- MENSAJES -->
                        <c:import var="dataTableMsg" url="/dataTableMsg.jsp" />
                        <c:out value="${dataTableMsg}" escapeXml="false" />
                        <!-- /MENSAJES -->  
                    </div>

                </div><!-- /.row -->
                <div class="row">                  
                    <div class="col-lg-12"> 

                        <div class="table-responsive">
                            <!-- DATATABLE -->
                            <form action="PlaceNewsMainServlet" method="POST" name="form">
                                <div class="row-fluid">
                                    <div class="span12">                            
                                        <div class="box">  
                                            <!-- TITULO DATATABLE -->
                                            <div class="box-title">
                                                Datatable
                                                <object align="right"> 
                                                    <!-- EXPORTAR A EXCEL -->
                                                    <button class="btn btn-primary btn-mini" name="btnExportExcel" onclick="generateExcel('datatable');" ><font size="1"><strong>EXPORT XLS</strong></font></button>                                                    
                                                    &nbsp;&nbsp;
                                                    <!-- AGREGAR REGISTRO -->
                                                    <button class="btn btn-primary btn-mini" name="btnAddPlaceNews" type="button" onclick="location.href = 'PlaceNewsAddServlet';" ><font size="1"><strong>AGREGAR</strong></font></button>
                                                </object>
                                                </br>DB
                                            </div>
                                            <!-- /TITULO DATATABLE -->
                                            <div class="box-content nopadding">
                                                <table id="datatable" class="table table-striped table-bordered">
                                                    <!-- HEADER DATATABLE -->
                                                    <thead>
                                                        <tr>
                                                            <th><input class="check_all" type="checkbox" /></th>
                                                            <th>ID <i class="fa fa-sort"></i></th>
                                                            <th>Lugar <i class="fa fa-sort"></i></th>                                                            
                                                            <th>Título Noticia <i class="fa fa-sort"></i></th>
                                                            <th>Tipo <i class="fa fa-sort"></i></th>
                                                            <th>Detalle <i class="fa fa-sort"></i></th>
                                                            <th>Fecha Inicio <i class="fa fa-sort"></i></th>
                                                            <th>Fecha Término <i class="fa fa-sort"></i></th>
                                                            <th></th>
                                                            <th></th>
                                                        </tr>
                                                    </thead>
                                                    <!-- /HEADER DATATABLE -->

                                                    <!-- BODY -->
                                                    <tbody>
                                                        <tr>
                                                            <c:forEach var="list" items="${list}">  
                                                                <td class="center"><input type="checkbox" name="chk" value="<c:out value="${list.idPlaceNews}" />"/></td>
                                                                <td class="center"><c:out value="${list.idPlaceNews}" /></td>
                                                                <td class="center"><c:out value="${list.namePlace}" /></td>                                                                
                                                                <td class="center"><c:out value="${list.tittle}" /></td>
                                                                <td class="center">
                                                                    <c:if test="${list.newsType == 1}">Advertencia</c:if>
                                                                    <c:if test="${list.newsType == 2}">Notificación</c:if>
                                                                    <c:if test="${list.newsType == 3}">Informacion</c:if>
                                                                    <c:if test="${list.newsType == 4}">Atención</c:if>
                                                                    </td>
                                                                    <td class="center"><c:out value="${list.details}" /></td>
                                                                <td class="center"><c:out value="${list.dateBegin}" /></td>
                                                                <td class="center"><c:out value="${list.dateEnd}" /></td>
                                                                <td class="center">
                                                                    <a href="PlaceNewsGetServlet?idPlace=<c:out value="${list.idPlace}" />&idPlaceNews=<c:out value="${list.idPlaceNews}" />"><button class="btn btn-primary btn-mini" name="btnUpOne" type="button"><strong><font size="1">VER / ACTUALIZAR</font></strong></button></a>
                                                                </td>
                                                                <td class="center">                                                                   
                                                                    <button class="btn btn-danger btn-mini delete" name="btnDelRow" onclick="confirmar('PlaceNewsMainServlet?btnDelRow=x&idPlaceNews=<c:out value="${list.idPlaceNews}" />');
                return false;"><strong><font size="1">ELIMINAR</font></strong></button>
                                                                </td>
                                                            </tr>
                                                        </c:forEach>                                                                              		                                    		
                                                    </tbody>
                                                    <!-- /BODY -->

                                                    <!-- FOOTER DATATABLE -->
                                                    <tfoot>
                                                        <tr>
                                                            <th><button class="btn btn-danger btn-mini delete" name="btnDelCol" type="submit"><font size="1">ELIMINAR</font></button></th>
                                                            <th>ID</th>
                                                            <th>Lugar </th>                                                            
                                                            <th>Título Noticia </th>
                                                            <th>Tipo </th>
                                                            <th>Detalle </th>
                                                            <th>Fecha Inicio </th>
                                                            <th>Fecha Término </th>
                                                            <th></th>
                                                            <th></th>
                                                        </tr>
                                                    </tfoot>
                                                    <!-- /FOOTER DATATABLE -->
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </form>
                            <!-- /DATATABLE -->
                        </div>
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
