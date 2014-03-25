<%-- 
    Document   : clientNews
    Created on : 05-03-2014, 06:28:58 PM
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

            <!-- MAIN MENU -->
            <c:import var="menu" url="/mainMenu.jsp" />
            <c:out value="${menu}" escapeXml="false" />
            <!-- /MAIN MENU -->

            <div id="page-wrapper">

                <div class="row">
                    <div class="col-lg-12">
                        <!-- TITULO MANTENEDOR -->
                        <h1>Mantenedor <small> Noticias Cliente</small></h1>
                        <ol class="breadcrumb">
                            <li class="active"><a href="ClientNewsMainServlet"><i class="fa fa-table"></i> DataTable</a></li>
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
                        <!-- DATATABLE -->
                        <div class="table-responsive">
                            <form action="ClientNewsDeleteServlet" method="POST" name="form">
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
                                                    <button class="btn btn-primary btn-mini" name="btnAdd" type="button" onclick="location.href = 'ClientNewsAddServlet';" ><font size="1"><strong>AGREGAR</strong></font></button>
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
                                                            <th>Título <i class="fa fa-sort"></i></th>
                                                            <th>Tipo <i class="fa fa-sort"></i></th>
                                                            <th>Rut <i class="fa fa-sort"></i></th>
                                                            <th>Nombre <i class="fa fa-sort"></i></th>
                                                            <th>Apellido <i class="fa fa-sort"></i></th>
                                                            <th>Fecha Inicio <i class="fa fa-sort"></i></th>
                                                            <th>Fecha Término <i class="fa fa-sort"></i></th>
                                                            <th></th>
                                                            <th></th>
                                                        </tr>
                                                    </thead>
                                                    <!-- /HEADER DATATABLE -->

                                                    <!-- BODY DATATABLE -->
                                                    <tbody>
                                                        <c:forEach var="list" items="${list}">  
                                                            <tr>
                                                                <td class="center"><input type="checkbox" name="chk" value="<c:out value="${list.idClientNews}" />"/></td>  
                                                                <td class="center"><c:out value="${list.idClientNews}" /></td>
                                                                <td class="center"><c:out value="${list.tittle}" /></td>
                                                                <td class="center">
                                                                    <c:if test="${list.newsType == 1}">Advertencia</c:if>
                                                                    <c:if test="${list.newsType == 2}">Notificación</c:if>
                                                                    <c:if test="${list.newsType == 3}">Información</c:if>
                                                                    <c:if test="${list.newsType == 4}">Atención</c:if>
                                                                    </td>
                                                                    <td class="center"><c:out value="${list.rut}" />-<c:out value="${list.dv}"/></td>
                                                                <td class="center"><c:out value="${list.firstName}" /></td>
                                                                <td class="center"><c:out value="${list.lastName}" /></td>
                                                                <td class="center"><c:out value="${list.dateBegin}" /></td>
                                                                <td class="center"><c:out value="${list.dateEnd}" /></td>
                                                                <td class="center">      
                                                                    <a href="ClientNewsGetServlet?idClientNews=<c:out value="${list.idClientNews}" />&rut=<c:out value="${list.rut}" />"><button class="btn btn-primary btn-mini" name="btnUpOne" type="button"><font size="1">VER / ACTUALIZAR</font></button></a>                                                               
                                                                </td>
                                                                <td class="center">                                                                    
                                                                    <button class="btn btn-danger btn-mini delete" name="btnDelRow" onclick="confirmar('ClientNewsDeleteServlet?btnDelRow=x&idClientNews=<c:out value="${list.idClientNews}" />');
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
                                                            <th>ID </th>
                                                            <th>Título </th>
                                                            <th>Tipo</th>
                                                            <th>Rut </th>
                                                            <th>Nombre </th>
                                                            <th>Apellido </th>
                                                            <th>Fecha Inicio </th>
                                                            <th>Fecha Término </th>
                                                            <th></th>
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
                        <!-- DATATABLE -->
                    </div>
                </div><!-- /.row -->

                <p>&nbsp;</p>
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
</html>
