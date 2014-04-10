<%-- 
    Document   : leftMenu
    Created on : 09-04-2014, 02:47:51 PM
    Author     : patricio
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<aside id="left-panel">

    <!-- User info -->
    <div class="login-info">
        <span> <!-- User image size is adjusted inside CSS, it should stay as it --> 

            <a href="javascript:void(0);" id="show-shortcut">
                <img src="img/avatars/sunny.png" alt="me" class="online" /> 
                <span>
                    patricio.castro 
                </span>
                <i class="fa fa-angle-down"></i>
            </a> 

        </span>
    </div>
    <!-- end user info -->

    <!-- NAVIGATION : This navigation is also responsive

    To make this navigation dynamic please make sure to link the node
    (the reference to the nav > ul) after page load. Or the navigation
    will not initialize.
    -->
    <nav>
        <!-- NOTE: Notice the gaps after each icon usage <i></i>..
        Please note that these links work a bit different than
        traditional hre="" links. See documentation for details.
        -->

        <ul>
            <li>
                <a href="index.html" title="Dashboard"><i class="fa fa-lg fa-fw fa-home"></i> <span class="menu-item-parent">Dashboard (*)</span></a>
            </li>
            <li>
                <a href="inbox.html"><i class="fa fa-lg fa-fw fa-inbox"></i> <span class="menu-item-parent">Inbox (*)</span><span class="badge pull-right inbox-badge">14</span></a>
            </li>
            <li>
                <a href="#"><i class="fa fa-lg fa-fw fa-bar-chart-o"></i> <span class="menu-item-parent">Estadísticas (*)</span></a>
                <ul>
                    <li>
                        <a href="flot.html">Flot Chart</a>
                    </li>
                    <li>
                        <a href="morris.html">Morris Charts</a>
                    </li>
                    <li>
                        <a href="inline-charts.html">Inline Charts</a>
                    </li>
                </ul>
            </li>

            <li>
                <a href="#"><i class="fa fa-lg fa-fw fa-table"></i> <span class="menu-item-parent">Clientes</span></a>
                <ul>                 
                    <li>
                        <a href="ClientMainServlet">Clientes</a>
                    </li>
                    <li>
                        <a href="PointMainServlet">Puntos Clientes</a>
                    </li>
                    <li>
                        <a href="CardMainServlet">Tarjetas</a>
                    </li>
                    <li>
                        <a href="OrderCardMainServlet">Solicitud Tarjetas</a>
                    </li>
                    <li>
                        <a href="ClientPromoMainServlet">Promociones Cliente</a>
                    </li>
                    <li>
                        <a href="ClientPromoCheckoutMainServlet">Compras Clientes</a>
                    </li>
                    <li>
                        <a href="ClientExchangeCheckMainServlet">Productos Canjeados</a>
                    </li>
                    <li>
                        <a href="ClientNewsMainServlet">Noticias Clientes</a>
                    </li>
                </ul>
            </li>

            <li>
                <a href="#"><i class="fa fa-lg fa-fw fa-table"></i> <span class="menu-item-parent">Lugares</span></a>
                <ul>
                    <li>
                        <a href="PlaceMainServlet">Lugares</a>
                    </li>
                    <li>
                        <a href="PromoMainServlet">Promociones</a>
                    </li>
                    <li>
                        <a href="ExchangeableMainServlet">Productos Canjeables</a>
                    </li>
                    <li>
                        <a href="EventMainServlet">Eventos</a>
                    </li>
                    <li>
                        <a href="EntryMainServlet">Lista</a>
                    </li>
                    <li>
                        <a href="PlaceNewsMainServlet">Noticias Lugar</a>
                    </li>
                    <li>
                        <a href="PlaceMainServlet">Categorías Lugar</a>
                    </li>                            
                </ul>
            </li>

            <li>
                <a href="#"><i class="fa fa-lg fa-fw fa-table"></i> <span class="menu-item-parent">Características</span></a>
                <ul>
                    <li>
                        <a href="CategoryMainServlet">Categorías</a>
                    </li>                   
                    <li>
                        <a href="NewsMainServlet">Noticias Generales</a>
                    </li>
                    <li>
                        <a href="UniversityMainServlet">Universidades</a>
                    </li>
                    <li>
                        <a href="DressCodeMainServlet">Código Vestir</a>
                    </li>
                    <li>
                        <a href="CityMainServlet">Ciudad</a>
                    </li>                                                      
                </ul>
            </li>


            <li>
                <a href="#"><i class="fa fa-lg fa-fw fa-pencil-square-o"></i> <span class="menu-item-parent">Forms</span></a>
                <ul>
                    <li>
                        <a href="form-elements.html">Smart Form Elements</a>
                    </li>
                    <li>
                        <a href="form-templates.html">Smart Form Layouts</a>
                    </li>
                    <li>
                        <a href="validation.html">Smart Form Validation</a>
                    </li>
                    <li>
                        <a href="bootstrap-forms.html">Bootstrap Form Elements</a>
                    </li>
                    <li>
                        <a href="plugins.html">Form Plugins</a>
                    </li>
                    <li>
                        <a href="wizard.html">Wizards</a>
                    </li>
                    <li>
                        <a href="other-editors.html">Bootstrap Editors</a>
                    </li>
                    <li>
                        <a href="dropzone.html">Dropzone <span class="badge pull-right inbox-badge bg-color-yellow">new</span></a>
                    </li>
                </ul>
            </li>
            <li>
                <a href="#"><i class="fa fa-lg fa-fw fa-desktop"></i> <span class="menu-item-parent">UI Elements</span></a>
                <ul>
                    <li>
                        <a href="general-elements.html">General Elements</a>
                    </li>
                    <li>
                        <a href="buttons.html">Buttons</a>
                    </li>
                    <li>
                        <a href="#">Icons</a>
                        <ul>
                            <li>
                                <a href="fa.html"><i class="fa fa-plane"></i> Font Awesome</a>
                            </li>	
                            <li>
                                <a href="glyph.html"><i class="glyphicon glyphicon-plane"></i> Glyph Icons </a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="grid.html">Grid</a>
                    </li>
                    <li>
                        <a href="treeview.html">Tree View</a>
                    </li>
                    <li>
                        <a href="nestable-list.html">Nestable Lists</a>
                    </li>
                    <li>
                        <a href="jqui.html">JQuery UI</a>
                    </li>
                </ul>
            </li>                                           
        </ul>
    </nav>
    <span class="minifyme"> <i class="fa fa-arrow-circle-left hit"></i> </span>

</aside>
