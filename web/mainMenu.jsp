<%-- 
    Document   : mainMenu
    Created on : 16-ene-2014, 16:49:05
    Author     : patricio alberto
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="#">CPanel</a>
    </div>
    <div class="collapse navbar-collapse navbar-ex1-collapse">
        <ul class="nav navbar-nav side-nav">
            <li class="active"><a href="#"><i class="fa fa-dashboard"></i> Dashboard</a></li>
            <c:if test="${access == 777}" ><li><a href="#"><i class="fa fa-bar-chart-o"></i> Estadísticas</a></li></c:if>
            <c:if test="${access == 777}" ><li><a href="AdminMainServlet"><i class="fa fa-user"></i> Administradores</a></li></c:if>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-caret-square-o-down"></i> Clientes <b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li><a href="ClientMainServlet">Clientes</a></li>
                    <c:if test="${access == 777 || access == 555 }" ><li><a href="PointMainServlet">Puntos Cliente</a></li></c:if>
                        <li><a href="CardMainServlet">Tarjetas</a></li>  
                        <li><a href="OrderCardMainServlet">Solicitud Tarjetas</a></li> 
                    <c:if test="${access == 777 || access == 555 }" ><li><a href="ClientPromoMainServlet">Promociones Cliente</a></li></c:if>
                    <c:if test="${access == 777 || access == 555 }" ><li><a href="ClientPromoCheckoutMainServlet">Compras Cliente</a></li></c:if>
                    <c:if test="${access == 777 || access == 555 }" ><li><a href="ClientNewsMainServlet">Noticias clientes</a></li></c:if>
                    </ul>
                </li>

                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-caret-square-o-down"></i> Lugares <b class="caret"></b></a>
                    <ul class="dropdown-menu">
                    <c:if test="${access == 777 || access == 555 }" ><li><a href="PromoGiftMainServlet">Promociones</a></li></c:if>
                    <c:if test="${access == 777 || access == 555 }" ><li><a href="ExchangeableMainServlet">Productos Canjebles</a></li></c:if>
                    <c:if test="${access == 777 || access == 555 }" ><li><a href="EventMainServlet">Eventos</a></li></c:if>                    
                    <c:if test="${access == 777 || access == 555 }" ><li><a href="EntryMainServlet">Lista</a></li></c:if>    
                    <c:if test="${access == 777 || access == 555 }" ><li><a href="PlaceNewsMainServlet">Noticias Lugares</a></li></c:if>
                    <c:if test="${access == 777 || access == 555 }" ><li><a href="PlaceCategoryMainServlet">Categorías Lugar</a></li></c:if>
                    </ul>
                </li>

                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-caret-square-o-down"></i> Características <b class="caret"></b></a>
                    <ul class="dropdown-menu">                                                       
                    <c:if test="${access == 777 || access == 555 }" ><li><a href="CategoryMainServlet">Categorías</a></li></c:if>
                    <c:if test="${access == 777}" ><li><a href="PlaceMainServlet">Lugares</a></li></c:if>
                    <c:if test="${access == 777 || access == 555 }" ><li><a href="NewsMainServlet">Noticias generales</a></li></c:if>                    
                    <c:if test="${access == 777}" ><li><a href="UniversityMainServlet">Universidades</a></li></c:if>
                    <c:if test="${access == 777}" ><li><a href="DressCodeMainServlet">Código Vestir</a></li></c:if>
                        <li><a href="CityMainServlet">Ciudad</a></li>                                        
                    </ul>
                </li>
                <li><a href="ParameterGetServlet"><i class="fa fa-wrench"></i> Configuración</a></li>                
            </ul>

            <ul class="nav navbar-nav navbar-right navbar-user">            
                <li class="dropdown user-dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i> <c:if test="${userJsp != null}" ><c:out value="${userJsp}" /></c:if> <b class="caret"></b></a>
                <ul class="dropdown-menu">
                    <li><a href="AdminGetServlet"><i class="fa fa-user"></i> Perfil</a></li>
                    <li><a href="#"><i class="fa fa-envelope"></i> Inbox <span class="badge">7</span></a></li>
                    <li><a href="#"><i class="fa fa-gear"></i> Configuración</a></li>
                    <li class="divider"></li>
                    <li><a href="LogoutServlet"><i class="fa fa-power-off"></i> Log Out</a></li>
                </ul>
            </li>
        </ul>
    </div><!-- /.navbar-collapse -->
</nav>