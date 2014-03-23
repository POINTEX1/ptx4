<%-- 
    Document   : clientExchangeCheck
    Created on : 19-03-2014, 08:03:54 PM
    Author     : Joseph
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
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
                        <h1>Mantenedor <small>Productos Canjeados por Cliente</small></h1>
                        <ol class="breadcrumb">
                            <li class="active"><a href="ClientExchangeCheckMainServlet"><i class="fa fa-table"></i> DataTable</a></li>
                        </ol>

                        <!-- MENSAJES -->
                        <c:import var="dataTableMsg" url="/dataTableMsg.jsp" />
                        <c:out value="${dataTableMsg}" escapeXml="false" />
                        <!-- /MENSAJES --> 
                    </div>
                </div><!-- /.row -->

                <div class="row">                  
                    <div class="col-lg-12">  
                        <div class="table-responsive">
                            <form action="ClientExchangeCheckMainServlet" method="POST" name="form">
                                <div class="row-fluid">
                                    <div class="span12">                            
                                        <div class="box">                                
                                            <div class="box-title">
                                                Datatable
                                                <object align="right"> 
                                                    <button class="btn btn-primary btn-mini" name="btnExportExcel" onclick="generateExcel('datatable');" ><font size="1"><strong>EXPORT XLS</strong></font></button>
                                                    &nbsp;&nbsp;
                                                    <button class="btn btn-primary btn-mini" name="btnAdd" type="button" onclick="location.href = 'ClientExchangeAddServlet';" ><font size="1"><strong>AGREGAR</strong></font></button>
                                                </object>
                                                </br>DB
                                            </div>
                                            <div class="box-content nopadding">   
                                                <div id="tableWrap">
                                                    <table id="datatable" class="table table-striped table-bordered">                                                                                                        
                                                        <thead>
                                                            <tr>
                                                                <th><input class="check_all" type="checkbox" /></th>
                                                                <th>ID Check <i class="fa fa-sort"></i></th>
                                                                <th>Lugar <i class="fa fa-sort"></i></th>
                                                                <th>ID Exchangeable <i class="fa fa-sort"></i></th>
                                                                <th>Título Canjeable <i class="fa fa-sort"></i></th>
                                                                <th>Rut <i class="fa fa-sort"></i></th>
                                                                <th>Fecha/Hora Compra <i class="fa fa-sort"></i></th>
                                                                <th></th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <tr>
                                                                <c:forEach var="list" items="${list}">  
                                                                    <td class="center"><input type="checkbox" name="chk" value="<c:out value="${list.idCheck}" />"/></td>
                                                                    <td class="center"><c:out value="${list.idCheck}" /></td>
                                                                    <td class="center"><c:out value="${list.namePlace}" /></td>
                                                                    <td class="center"><c:out value="${list.idExchangeable}" /></td>
                                                                    <td class="center"><c:out value="${list.tittle}" /></td>
                                                                    <td class="center"><c:out value="${list.rut}" />-<c:out value="${list.dv}" /></td>                                                                
                                                                    <td class="center"><c:out value="${list.createTime}" /></td>                                                                
                                                                    <td class="center">                                                                   
                                                                        <button class="btn btn-danger btn-mini delete" name="btnDelRow" onclick="confirmar('ClientExchangeCheckMainServlet?btnDelRow=x&idCheck=<c:out value="${list.idCheck}" />');
                return false;"><strong><font size="1">ELIMINAR</font></strong></button>
                                                                    </td>
                                                                </tr>
                                                            </c:forEach>
                                                        </tbody>
                                                        <tfoot>
                                                            <tr>
                                                                <th><button class="btn btn-danger btn-mini delete" name="btnDelCol" type="submit"><font size="1">ELIMINAR</font></button></th>
                                                                <th>ID Check </th>
                                                                <th>Lugar </th>
                                                                <th>ID Exchangeable </th>
                                                                <th>Título Canjeable </th>
                                                                <th>Rut </th>
                                                                <th>Fecha/Hora Compra </th>
                                                                <th></th>
                                                            </tr>
                                                        </tfoot> 
                                                    </table>  
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                            </form>
                        </div>
                    </div>
                </div><!-- /.row -->

                <!-- FOOTER -->
                <c:import var="footer" url="/footer.jsp" />
                <c:out value="${footer}" escapeXml="false" />
                <!-- /FOOTER -->

            </div> <!-- /. page-wrapper-->
        </div> <!-- /. wrapper-->

        <script type="text/javascript" src="js/jquery.min.js"></script>
        <script type="text/javascript" src="js/imperiobootstrap.min.js"></script>
        <script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
        <script type="text/javascript" src="js/imperio.general.js"></script>
        <script type="text/javascript" src="js/imperio.tables.js"></script>
    </body>
</html>
