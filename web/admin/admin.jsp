<%-- 
    Document   : admin
    Created on : 26-dic-2013, 16:08:09
    Author     : patricio alberto
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
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

            <!-- Sidebar -->

            <!-- Collect the nav links, forms, and other content for toggling -->
            <c:import var="menu" url="/mainMenu.jsp" />
            <c:out value="${menu}" escapeXml="false" />
            <!-- /.navbar-collapse -->

            <div id="page-wrapper">
                <div class="row">
                    <div class="col-lg-12">
                        <!-- TITULO MANTENEDOR -->
                        <h1>Mantenedor <small> Admin</small></h1>
                        <ol class="breadcrumb">
                            <li class="active"><a href="AdminMainServlet"><i class="fa fa-table"></i> DataTable</a></li>
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
                            <!-- DATA TABLE -->
                            <form action="AdminMainServlet" method="POST" name="form">
                                <div class="row-fluid">
                                    <div class="span12">                            
                                        <div class="box"> 
                                            <!-- TITULAR DEL DATATABLE -->
                                            <div class="box-title">
                                                DataTable
                                                <!-- AGREGAR REGISTRO -->
                                                <object align="right"> <button class="btn btn-primary btn-mini" name="btnAddAdmin" type="button" onclick="location.href = 'AdminAddServlet';" ><font size="1"><strong>AGREGAR</strong></font></button></object>
                                                <!-- /AGREGAR REGISTRO -->
                                                </br>DB
                                            </div>
                                            <!-- /TITULAR DEL DATATABLE -->
                                            <div class="box-content nopadding">
                                                <table id="datatable" class="table table-striped table-bordered">
                                                    <!-- HEAD DEL DATATABLE -->
                                                    <thead>
                                                        <tr>
                                                            <th><input class="check_all" type="checkbox" /></th>
                                                            <th>ID <i class="fa fa-sort"></i></th>
                                                            <th>Username <i class="fa fa-sort"></i></th>
                                                            <th>Email <i class="fa fa-sort"></i></th>
                                                            <th>Fecha de Creación <i class="fa fa-sort"></i></th>
                                                            <th> </th>
                                                            <th> </th>
                                                        </tr>
                                                    </thead>
                                                    <!-- /HEAD DEL DATATABLE -->

                                                    <!-- BODY DEL DATATABLE -->
                                                    <tbody>
                                                        <c:forEach var="list" items="${list}"> 
                                                            <tr>
                                                                <td class="center">
                                                                    <c:if test="${list.idAdmin != idUser}" >
                                                                        <input type="checkbox" name="chk" value="<c:out value="${list.idAdmin}" />"/>
                                                                    </c:if>
                                                                </td>                                            
                                                                <td class="center"><c:out value="${list.idAdmin}" /></td>
                                                                <td class="center"><c:out value="${list.username}" /></td>
                                                                <td class="center"><c:out value="${list.email}" /></td>
                                                                <td class="center"><c:out value="${list.createTime}" /></td>
                                                                <td class="center">
                                                                    <a href="AdminGetServlet?id=<c:out value="${list.idAdmin}" />"><button class="btn btn-primary btn-mini" name="btnUpOne" type="button"><strong><font size="1">ACTUALIZAR</font></strong></button></a>                                                                         
                                                                </td>
                                                                <td class="center">
                                                                    <c:if test="${list.idAdmin != idUser}" >                                                                           
                                                                        <button class="btn btn-danger btn-mini delete" name="btnDelRow" onclick="confirmar('AdminMainServlet?btnDelRow=x&id=<c:out value="${list.idAdmin}"/>');
                return false;"><strong><font size="1">ELIMINAR</font></strong></button>
                                                                            </c:if> 
                                                                </td>
                                                            </tr>
                                                        </c:forEach>                                                                              		                                    		
                                                    </tbody>
                                                    <!-- /BODY DEL DATATABLE -->

                                                    <!-- FOOT DEL DATATABLE -->
                                                    <tfoot>
                                                        <tr>
                                                            <th><button class="btn btn-danger btn-mini delete" name="btnDelCol" type="submit"><font size="1">ELIMINAR</font></button></th>
                                                            <th>ID </th>
                                                            <th>Username </th>
                                                            <th>Email </th>
                                                            <th>Fecha de Creación </th>
                                                            <th> </th>
                                                            <th> </th>
                                                        </tr>
                                                    </tfoot>
                                                    <!-- /FOOT DEL DATATABLE --> 
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </form>
                            <!-- /DATA TABLE -->    
                        </div>
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