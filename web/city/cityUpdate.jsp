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
        <meta charset="utf-8">
        <!--<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">-->

        <title> POINTEX | Ciudades</title>
        <meta name="description" content="">
        <meta name="author" content="">

        <!-- Use the correct meta names below for your web application
                 Ref: http://davidbcalhoun.com/2010/viewport-metatag 
                 
        <meta name="HandheldFriendly" content="True">
        <meta name="MobileOptimized" content="320">-->

        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">

        <!-- Basic Styles -->
        <link rel="stylesheet" type="text/css" media="screen" href="css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" media="screen" href="css/font-awesome.min.css">

        <!-- SmartAdmin Styles : Please note (smartadmin-production.css) was created using LESS variables -->
        <link rel="stylesheet" type="text/css" media="screen" href="css/smartadmin-production.css">
        <link rel="stylesheet" type="text/css" media="screen" href="css/smartadmin-skins.css">

        <!-- SmartAdmin RTL Support is under construction
        <link rel="stylesheet" type="text/css" media="screen" href="css/smartadmin-rtl.css"> -->

        <!-- We recommend you use "your_style.css" to override SmartAdmin
             specific styles this will also ensure you retrain your customization with each SmartAdmin update.
        <link rel="stylesheet" type="text/css" media="screen" href="css/your_style.css"> -->

        <!-- Demo purpose only: goes with demo.js, you can delete this css when designing your own WebApp -->
        <link rel="stylesheet" type="text/css" media="screen" href="css/demo.css">

        <!-- FAVICONS -->
        <link rel="shortcut icon" href="img/favicon/favicon.ico" type="image/x-icon">
        <link rel="icon" href="img/favicon/favicon.ico" type="image/x-icon">

        <!-- GOOGLE FONT -->
        <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400italic,700italic,300,400,700">

    </head>
    <body class="">
        <!-- possible classes: minified, fixed-ribbon, fixed-header, fixed-width-->

        <!-- HEADER -->
        <header id="header">
            <div id="logo-group">

                <!-- PLACE YOUR LOGO HERE -->
                <c:import var="logo" url="/logo.jsp" />
                <c:out value="${logo}" escapeXml="false" />
                <!-- END LOGO PLACEHOLDER -->

                <!-- Note: The activity badge color changes when clicked and resets the number to 0
                Suggestion: You may want to set a flag when this happens to tick off all checked messages / notifications -->
                <span id="activity" class="activity-dropdown"> <i class="fa fa-user"></i> <b class="badge"> 21 </b> </span>

                <!-- AJAX-DROPDOWN : control this dropdown height, look and feel from the LESS variable file -->
                <div class="ajax-dropdown">

                    <!-- the ID links are fetched via AJAX to the ajax container "ajax-notifications" -->
                    <div class="btn-group btn-group-justified" data-toggle="buttons">
                        <label class="btn btn-default">
                            <input type="radio" name="activity" id="ajax/notify/mail.html">
                            Msgs (14) </label>
                        <label class="btn btn-default">
                            <input type="radio" name="activity" id="ajax/notify/notifications.html">
                            notify (3) </label>
                        <label class="btn btn-default">
                            <input type="radio" name="activity" id="ajax/notify/tasks.html">
                            Tasks (4) </label>
                    </div>

                    <!-- notification content -->
                    <div class="ajax-notifications custom-scroll">

                        <div class="alert alert-transparent">
                            <h4>Click a button to show messages here</h4>
                            This blank page message helps protect your privacy, or you can show the first message here automatically.
                        </div>

                        <i class="fa fa-lock fa-4x fa-border"></i>

                    </div>
                    <!-- end notification content -->

                    <!-- footer: refresh area -->
                    <span> Last updated on: 12/12/2013 9:43AM
                        <button type="button" data-loading-text="<i class='fa fa-refresh fa-spin'></i> Loading..." class="btn btn-xs btn-default pull-right">
                            <i class="fa fa-refresh"></i>
                        </button> </span>
                    <!-- end footer -->

                </div>
                <!-- END AJAX-DROPDOWN -->
            </div>

            <!-- projects dropdown -->
            <c:import var="projectsDropdown" url="/projectsDropdown.jsp" />
            <c:out value="${projectsDropdown}" escapeXml="false" />
            <!-- end projects dropdown -->

            <!-- pulled right: nav area -->
            <c:import var="pulledRight" url="/pulledRight.jsp" />
            <c:out value="${pulledRight}" escapeXml="false" />
            <!-- end pulled right: nav area -->

        </header>
        <!-- END HEADER -->

        <!-- Left panel : Navigation area -->
        <!-- Note: This width of the aside area can be adjusted through LESS variables -->
        <c:import var="leftMenu" url="/leftMenu.jsp" />
        <c:out value="${leftMenu}" escapeXml="false" />
        <!-- END NAVIGATION -->

        <!-- MAIN PANEL -->
        <div id="main" role="main">

            <!-- RIBBON -->
            <div id="ribbon">

                <span class="ribbon-button-alignment"> <span id="refresh" class="btn btn-ribbon" data-title="refresh"  rel="tooltip" data-placement="bottom" data-original-title="<i class='text-warning fa fa-warning'></i> Warning! This will reset all your widget settings." data-html="true"><i class="fa fa-refresh"></i></span> </span>

                <!-- breadcrumb -->
                <ol class="breadcrumb">
                    <li>
                        Características
                    </li>
                    <li>
                        Ciudades
                    </li>
                </ol>
                <!-- end breadcrumb -->

                <!-- You can also add more buttons to the
                ribbon for further usability

                Example below:

                <span class="ribbon-button-alignment pull-right">
                <span id="search" class="btn btn-ribbon hidden-xs" data-title="search"><i class="fa-grid"></i> Change Grid</span>
                <span id="add" class="btn btn-ribbon hidden-xs" data-title="add"><i class="fa-plus"></i> Add</span>
                <span id="search" class="btn btn-ribbon" data-title="search"><i class="fa-search"></i> <span class="hidden-mobile">Search</span></span>
                </span> -->

            </div>
            <!-- END RIBBON -->

            <!-- MAIN CONTENT -->
            <div id="content">


                <div class="row">
                    <div class="col-xs-12 col-sm-9 col-md-9 col-lg-9">
                        <h1 class="page-title txt-color-blueDark">

                            <!-- PAGE HEADER -->
                            <i class="fa-fw fa fa-pencil-square-o"></i> 
                            <a href="CityMainServlet">Mantenedor <span>Ciudades</span></a>
                            <span>
                                >  
                                Actualizar
                            </span>
                        </h1>
                    </div>                   
                </div>

                <!-- MENSAJES -->
                <c:import var="formMsg" url="/formMsg.jsp" />
                <c:out value="${formMsg}" escapeXml="false" />

                <!-- widget grid -->
                <section id="widget-grid" class="">

                    <!-- START ROW -->
                    <div class="row">

                        <!-- NEW COL START -->
                        <article class="col-sm-12 col-md-12 col-lg-6">

                            <!-- Widget ID (each widget will need unique ID)-->
                            <div class="jarviswidget" id="wid-id-1" data-widget-editbutton="false" data-widget-custombutton="false">
                                <!-- widget options:
                                        usage: <div class="jarviswidget" id="wid-id-0" data-widget-editbutton="false">
                                        
                                        data-widget-colorbutton="false"	
                                        data-widget-editbutton="false"
                                        data-widget-togglebutton="false"
                                        data-widget-deletebutton="false"
                                        data-widget-fullscreenbutton="false"
                                        data-widget-custombutton="false"
                                        data-widget-collapsed="true" 
                                        data-widget-sortable="false"
                                        
                                -->
                                <header>
                                    <span class="widget-icon"> <i class="fa fa-edit"></i> </span>
                                    <h2>Formulario </h2>
                                </header>

                                <!-- widget div-->
                                <div>

                                    <!-- widget edit box -->
                                    <div class="jarviswidget-editbox">
                                        <!-- This area used as dropdown edit box -->

                                    </div>
                                    <!-- end widget edit box -->

                                    <!-- widget content -->
                                    <div class="widget-body no-padding">

                                        <form id="formUpdate" name="formUpdate" class="smart-form" novalidate="novalidate" action="CityUpdateServlet" method="POST">
                                            <fieldset>
                                                <section>
                                                    <c:if test="${msgErrorDup == null && msgErrorNameCity == null}" >
                                                        <label for="nameCity" class="input">
                                                            <input type="text" name="nameCity" id="nameCity" placeholder=" Nombre de ciudad" value="<c:out value="${nameCity}" />">
                                                        </label>
                                                    </c:if>
                                                    <c:if test="${msgErrorDup != null || msgErrorNameCity != null}" >
                                                        <div class="form-group has-error">
                                                            <div class="col-md-10">
                                                                <div class="input-group">                                                                
                                                                    <input class="form-control" type="text" name="nameCity" id="nameCity" placeholder=" Nombre de ciudad" value="<c:out value="${nameCity}" />">
                                                                    <span class="input-group-addon"><i class="glyphicon glyphicon-remove-circle"></i></span>
                                                                </div>
                                                                <span class="help-block"><i class="fa fa-warning"></i> Porfavor debe corregir el error</span>
                                                            </div>
                                                        </div>
                                                    </c:if>
                                                </section>                                                
                                            </fieldset>                                     

                                            <footer>
                                                <input type="hidden" name="add" value="ok"/>
                                                <input type="hidden" name="idCity" value="<c:out value="${idCity}" />"/>
                                                <button name="btnUpdate" class="btn btn-primary" onclick="disabledButtonUpdate();">
                                                    <strong><font size="1"><object name="btn1">ACTUALIZAR</object><object name="btn2" hidden="true">ACTUALIZANDO...</object></font></strong>
                                                </button>
                                            </footer>
                                        </form>

                                    </div>
                                    <!-- end widget content -->

                                </div>
                                <!-- end widget div -->

                            </div>
                            <!-- end widget -->                           

                        </article>
                        <!-- END COL -->                       		

                    </div>
                    <!-- END ROW -->

                </section>
                <!-- end widget grid -->

            </div>
            <!-- END MAIN CONTENT -->

        </div>
        <!-- END MAIN PANEL -->

        <!-- SHORTCUT AREA : With large tiles (activated via clicking user name tag)
        Note: These tiles are completely responsive,
        you can add as many as you like
        -->
        <c:import var="shortcut" url="/shortcut.jsp" />
        <c:out value="${shortcut}" escapeXml="false" />
        <!-- END SHORTCUT AREA -->

        <!--================================================== -->

        <!-- PACE LOADER - turn this on if you want ajax loading to show (caution: uses lots of memory on iDevices)-->
        <script data-pace-options='{ "restartOnRequestAfter": true }' src="js/plugin/pace/pace.min.js"></script>

        <!-- Link to Google CDN's jQuery + jQueryUI; fall back to local -->
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
        <script>
                                                    if (!window.jQuery) {
                                                        document.write('<script src="js/libs/jquery-2.0.2.min.js"><\/script>');
                                                    }
        </script>

        <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
        <script>
                                                    if (!window.jQuery.ui) {
                                                        document.write('<script src="js/libs/jquery-ui-1.10.3.min.js"><\/script>');
                                                    }
        </script>

        <!-- JS TOUCH : include this plugin for mobile drag / drop touch events
        <script src="js/plugin/jquery-touch/jquery.ui.touch-punch.min.js"></script> -->

        <!-- BOOTSTRAP JS -->
        <script src="js/bootstrap/bootstrap.min.js"></script>

        <!-- CUSTOM NOTIFICATION -->
        <script src="js/notification/SmartNotification.min.js"></script>

        <!-- JARVIS WIDGETS -->
        <script src="js/smartwidgets/jarvis.widget.min.js"></script>

        <!-- EASY PIE CHARTS -->
        <script src="js/plugin/easy-pie-chart/jquery.easy-pie-chart.min.js"></script>

        <!-- SPARKLINES -->
        <script src="js/plugin/sparkline/jquery.sparkline.min.js"></script>

        <!-- JQUERY VALIDATE -->
        <script src="js/plugin/jquery-validate/jquery.validate.min.js"></script>

        <!-- JQUERY MASKED INPUT -->
        <script src="js/plugin/masked-input/jquery.maskedinput.min.js"></script>

        <!-- JQUERY SELECT2 INPUT -->
        <script src="js/plugin/select2/select2.min.js"></script>

        <!-- JQUERY UI + Bootstrap Slider -->
        <script src="js/plugin/bootstrap-slider/bootstrap-slider.min.js"></script>

        <!-- browser msie issue fix -->
        <script src="js/plugin/msie-fix/jquery.mb.browser.min.js"></script>

        <!-- FastClick: For mobile devices -->
        <script src="js/plugin/fastclick/fastclick.js"></script>

        <!--[if IE 7]>

        <h1>Your browser is out of date, please update your browser by going to www.microsoft.com/download</h1>

        <![endif]-->

        <!-- Demo purpose only -->
        <script src="js/demo.js"></script>

        <!-- MAIN APP JS FILE -->
        <script src="js/app.js"></script>

        <!-- PAGE RELATED PLUGIN(S) -->
        <script src="js/plugin/jquery-form/jquery-form.min.js"></script>

        <!-- disabledButton -->
        <script src="js/disabledButton.js"></script>


        <script type="text/javascript">

                                                    // DO NOT REMOVE : GLOBAL FUNCTIONS!

                                                    $(document).ready(function() {

                                                        pageSetUp();

                                                        var $checkoutForm = $('#formAdd').validate({
                                                            // Rules for form validation
                                                            rules: {
                                                                nameCity: {
                                                                    required: true
                                                                }
                                                            },
                                                            // Messages for form validation
                                                            messages: {
                                                                nameCity: {
                                                                    required: 'Por favor ingrese nombre de ciudad.'
                                                                }
                                                            },
                                                            // Do not change code below
                                                            errorPlacement: function(error, element) {
                                                                error.insertAfter(element.parent());
                                                            }
                                                        });

                                                        // START AND FINISH DATE
                                                        $('#startdate').datepicker({
                                                            dateFormat: 'dd.mm.yy',
                                                            prevText: '<i class="fa fa-chevron-left"></i>',
                                                            nextText: '<i class="fa fa-chevron-right"></i>',
                                                            onSelect: function(selectedDate) {
                                                                $('#finishdate').datepicker('option', 'minDate', selectedDate);
                                                            }
                                                        });

                                                        $('#finishdate').datepicker({
                                                            dateFormat: 'dd.mm.yy',
                                                            prevText: '<i class="fa fa-chevron-left"></i>',
                                                            nextText: '<i class="fa fa-chevron-right"></i>',
                                                            onSelect: function(selectedDate) {
                                                                $('#startdate').datepicker('option', 'maxDate', selectedDate);
                                                            }
                                                        });



                                                    })

        </script>

        <!-- Your GOOGLE ANALYTICS CODE Below -->
        <script type="text/javascript">
            var _gaq = _gaq || [];
            _gaq.push(['_setAccount', 'UA-XXXXXXXX-X']);
            _gaq.push(['_trackPageview']);

            (function() {
                var ga = document.createElement('script');
                ga.type = 'text/javascript';
                ga.async = true;
                ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
                var s = document.getElementsByTagName('script')[0];
                s.parentNode.insertBefore(ga, s);
            })();

        </script>

    </body>

</html>