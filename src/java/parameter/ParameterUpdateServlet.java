/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parameter;

import Helpers.Format;
import java.io.IOException;
import java.sql.Connection;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 *
 * @author alexander
 */
@WebServlet(name = "ParameterUpdateServlet", urlPatterns = {"/ParameterUpdateServlet"})
public class ParameterUpdateServlet extends HttpServlet {

    @Resource(name = "jdbc/POINTEX1")
    private DataSource ds;

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        /* establecer set de caracteres */
        request.setCharacterEncoding("UTF-8");

        /* definir conexion */
        Connection conexion = null;

        //////////////////////////
        // ESTABLECER CONEXION
        //////////////////////////
        try {
            conexion = ds.getConnection();

            ParameterDAO parameterDAO = new ParameterDAO();
            parameterDAO.setConexion(conexion);

            //////////////////////////
            // COMPROBAR SESSION
            //////////////////////////
            try {
                /* recuperar sesion */
                HttpSession session = request.getSession(false);

                /* obtener parametros de session */
                int access = Integer.parseInt((String) session.getAttribute("access"));
                String username = (String) session.getAttribute("username");

                /* comprobar permisos de usuario */
                if (access != 555 && access != 777) {
                    request.getRequestDispatcher("/ForbiddenServlet").forward(request, response);
                } else {

                    /* obtener los valores de session y asignar valores a la jsp */
                    request.setAttribute("userJsp", username);
                    request.setAttribute("access", access);

                    /////////////////////////////////////////
                    // RECIBIR Y COMPROBAR PARAMETROS
                    ////////////////////////////////////////

                    String swaitingCard = request.getParameter("waitingCard");
                    String snumberEvent = request.getParameter("numberEvent");
                    String snumberPromo = request.getParameter("numberPromo");
                    String sbanerCevent = request.getParameter("banerCentralEvent");
                    String sbanerCpromo = request.getParameter("banerCentralPromo");
                    String sbanerCexchange = request.getParameter("banerCentralExchange");
                    String sbanerCvip = request.getParameter("banerCentralVip");
                    String sbanerCabout = request.getParameter("banerCentralAboutUs");
                    String sbanerTevent = request.getParameter("banerTopEvent");
                    String sbanerTpromo = request.getParameter("banerTopPromo");
                    String sbanerTmyplace = request.getParameter("banerTopMyPlace");
                    String sbanerTfindplace = request.getParameter("banerTopFindPlace");
                    String sbanerTconfiguration = request.getParameter("banerTopConfiguration");
                    String sbanerTsocial = request.getParameter("banerTopSocialNetworks");

                    /* instanciar url */
                    String url = "?redirect=ok";
                    url += "&watingCard=" + swaitingCard;
                    url += "&numberEvent=" + snumberEvent;
                    url += "&numberPromo=" + snumberPromo;
                    url += "&banerCentralEvent=" + sbanerCevent;
                    url += "&banerCentralPromo=" + sbanerCpromo;
                    url += "&banerCentralExchange=" + sbanerCexchange;
                    url += "&banerCentralVip=" + sbanerCvip;
                    url += "&banerCentralAboutUs=" + sbanerCabout;
                    url += "&banerTopEvent=" + sbanerTevent;
                    url += "&banerTopPromo=" + sbanerTpromo;
                    url += "&banerTopMyPlace=" + sbanerTmyplace;
                    url += "&banerTopFindMyPlace=" + sbanerTfindplace;
                    url += "&banerTopConfiguration=" + sbanerTconfiguration;
                    url += "&banerTopSocialNetworks=" + sbanerTsocial;

                    /* instanciar parametros */
                    Parameter parameter = new Parameter();
                                        
                    Format format = new Format();

                    /* flag de error */
                    boolean error = false;

                    /* comprobar waiting card*/
                    if (swaitingCard == null || swaitingCard.trim().equals("")) {
                        url += "&msgErrorWaitingCard=Debe Ingresar día para solicitud de tarjeta.";
                        error = true;
                    } else {
                        try {
                            parameter.setWaitingCard(Integer.parseInt(swaitingCard));
                            if (parameter.getWaitingCard() < 0) {
                                url += "&msgErrorWaitingCard=Los días no pueden ser negativos.";
                                error = true;
                            }
                        } catch (NumberFormatException n) {
                            url += "&msgErrorWaitingCard=Error: Los días de solicitud deben ser numéricos.";
                            error = true;
                        }
                    }

                    /*comprobar numberEvent*/
                    if (snumberEvent == null || snumberEvent.trim().equals("")) {
                        url += "&msgErrorNumberEvent=Error: Debe Ingresar los días para evento.";
                        error = true;
                    } else {
                        try {
                            parameter.setNumberEvent(Integer.parseInt(snumberEvent));
                            if (parameter.getNumberEvent() < 0) {
                                url += "&msgErrorNumberEvent=Error: Los días no pueden ser negativos.";
                                error = true;
                            }
                        } catch (NumberFormatException n) {
                            url += "&msgErrorNumberEvent=Error: Los días deben ser numéricos.";
                            error = true;
                        }
                    }

                    /*comprobar numberPromo*/
                    if (snumberPromo == null || snumberPromo.trim().equals("")) {
                        url += "&msgErrorNumberPromo=Error: Debe Ingresar los días para promo.";
                        error = true;
                    } else {
                        try {
                            parameter.setNumberPromo(Integer.parseInt(snumberPromo));
                            if (parameter.getNumberPromo() < 0) {
                                url += "&msgErrorNumberPromo=Error: Los días no pueden ser negativos.";
                                error = true;
                            }
                        } catch (NumberFormatException n) {
                            url += "&msgErrorNumberPromo=Error: Los días deben ser numéricos.";
                            error = true;
                        }
                    }

                    /* comprobar baner central event*/
                    if (sbanerCevent == null || sbanerCevent.trim().equals("")) {
                        url += "&msgErrorBanerCentralEvent=Error: Debe ingresar url del baner central de event.";
                        error = true;
                    } else {
                        if (format.validarUrl(sbanerCevent)) {
                            parameter.setBanerCentralEvent(sbanerCevent);
                        } else {
                            url += "&msgErrorBanerCentralEvent=Error: Debe ingresar formato correcto para baner central de evento.";
                            error = true;
                        }
                    }
                    /* comprobar baner central promo*/
                    if (sbanerCpromo == null || sbanerCpromo.trim().equals("")) {
                        url += "&msgErrorBanerCentralPromo=Error: Debe ingresar url del baner central de promo.";
                        error = true;
                    } else {
                        if (format.validarUrl(sbanerCpromo)) {
                            parameter.setBanerCentralPromo(sbanerCpromo);
                        } else {
                            url += "&msgErrorBanerCentralPromo=Error: Debe ingresar formato correcto para baner central de promo.";
                            error = true;
                        }
                    }

                    /* comprobar baner central exchange*/
                    if (sbanerCexchange == null || sbanerCexchange.trim().equals("")) {
                        url += "&msgErrorBanerCentralExchange=Error: Debe ingresar url del baner central de productos canjeables.";
                        error = true;
                    } else {
                        if (format.validarUrl(sbanerCexchange)) {
                            parameter.setBanerCentralExchangeable(sbanerCexchange);
                        } else {
                            url += "&msgErrorBanerCentralExchange=Error: Debe ingresar formato correcto para baner central de productos canjeables.";
                            error = true;
                        }
                    }

                    /* comprobar baner central vip*/
                    if (sbanerCvip == null || sbanerCvip.trim().equals("")) {
                        url += "&msgErrorBanerCentralVip=Error: Debe ingresar url del baner central de vip.";
                        error = true;
                    } else {
                        if (format.validarUrl(sbanerCvip)) {
                            parameter.setBanerCentralVip(sbanerCvip);
                        } else {
                            url += "&msgErrorBanerCentralVip=Error: Debe ingresar formato correcto para baner central de vip.";
                            error = true;
                        }
                    }

                    /* comprobar baner central about us*/
                    if (sbanerCabout == null || sbanerCabout.trim().equals("")) {
                        url += "&msgErrorBanerCentralAboutUs=Error: Debe ingresar url del baner central de sobre nostros.";
                        error = true;
                    } else {
                        if (format.validarUrl(sbanerCabout)) {
                            parameter.setBanerCentralAboutUs(sbanerCabout);
                        } else {
                            url += "&msgErrorBanerCentralAboutUs=Error: Debe ingresar formato correcto para baner central de sobre nostros.";
                            error = true;
                        }
                    }

                    /* comprobar baner top event*/
                    if (sbanerTevent == null || sbanerTevent.trim().equals("")) {
                        url += "&msgErrorBanerTopEvent=Error: Debe ingresar url del baner top para evento.";
                        error = true;
                    } else {
                        if (format.validarUrl(sbanerTevent)) {
                            parameter.setBanerTopEvent(sbanerTevent);
                        } else {
                            url += "&msgErrorBanerTopEvent=Error: Debe ingresar formato correcto para baner de cabecera de evento.";
                            error = true;
                        }
                    }

                    /* comprobar baner top promo*/
                    if (sbanerTpromo == null || sbanerTpromo.trim().equals("")) {
                        url += "&msgErrorBanerTopPromo=Error: Debe ingresar url del baner top para promo.";
                        error = true;
                    } else {
                        if (format.validarUrl(sbanerTpromo)) {
                            parameter.setBanerTopPromo(sbanerTpromo);
                        } else {
                            url += "&msgErrorBanerTopPromo=Error: Debe ingresar formato correcto para baner de cabecera de promo.";
                            error = true;
                        }
                    }

                    /* comprobar baner top my place*/
                    if (sbanerTmyplace == null || sbanerTmyplace.trim().equals("")) {
                        url += "&msgErrorBanerTopMyPlace=Error: Debe ingresar url del baner cabecera para mis lugares.";
                        error = true;
                    } else {
                        if (format.validarUrl(sbanerTmyplace)) {
                            parameter.setBanerTopMyPlace(sbanerTmyplace);
                        } else {
                            url += "&msgErrorBanerTopMyPlace=Error: Debe ingresar formato correcto para baner de cabecera de mis lugares.";
                            error = true;
                        }

                    }/* comprobar baner top find place*/
                    if (sbanerTfindplace == null || sbanerTfindplace.trim().equals("")) {
                        url += "&msgErrorBanerTopFindPlace=Error: Debe ingresar url del baner top para buscar lugares.";
                        error = true;
                    } else {
                        if (format.validarUrl(sbanerTfindplace)) {
                            parameter.setBanerTopFindPlace(sbanerTfindplace);
                        } else {
                            url += "&msgErrorBanerTopFindPlace=Error: Debe ingresar formato correcto para baner de cabecera de buscar lugares.";
                            error = true;
                        }

                    }

                    /* comprobar baner top configuration*/
                    if (sbanerTconfiguration == null || sbanerTconfiguration.trim().equals("")) {
                        url += "&msgErrorBanerTopConfiguration=Error: Debe ingresar url del baner top para configuración.";
                        error = true;
                    } else {
                        if (format.validarUrl(sbanerTconfiguration)) {
                            parameter.setBanerTopConfiguration(sbanerTconfiguration);
                        } else {
                            url += "&msgErrorBanerTopConfiguration=Error: Debe ingresar formato correcto para baner de cabecera de configuración.";
                            error = true;
                        }
                    }

                    /* comprobar baner top configuration*/
                    if (sbanerTsocial == null || sbanerTsocial.trim().equals("")) {
                        url += "&msgErrorBanerTopSocialNetworks=Error: Debe ingresar url del baner top para redes sociales.";
                        error = true;
                    } else {
                        if (format.validarUrl(sbanerTsocial)) {
                            parameter.setBanerTopSocialNetworks(sbanerTsocial);
                        } else {
                            url += "&msgErrorBanerTopSocialNetworks=Error: Debe ingresar formato correcto para baner de cabecera de redes sociales.";
                            error = true;
                        }
                    }

                    /////////////////////////////
                    // LOGICA DE NEGOCIO
                    /////////////////////////////

                    if (!error) {
                        try {
                            parameterDAO.update(parameter);
                            url += "&msgOk=Registro actualizado exitosamente!";
                        } catch (Exception ex) {
                            url += "&msgErrorUpdate=Error: No se pudo actualizar, verifique los datos.";

                        }
                    }

                    /* send redirect */
                    response.sendRedirect("ParameterGetServlet" + url);
                }
            } catch (Exception sessionException) {
                /* enviar a la vista de login */
                System.out.println("no ha iniciado session");
                request.getRequestDispatcher("/login/login.jsp").forward(request, response);
            }
        } catch (Exception connectionException) {
            connectionException.printStackTrace();
        } finally {
            try {
                conexion.close();
            } catch (Exception noGestionar) {
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
