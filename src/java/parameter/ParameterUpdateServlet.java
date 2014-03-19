/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parameter;

import Helpers.Format;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Collection;
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

        try {
            /////////////////////////////////////////
            // ESTABLECER CONEXION
            ///////////////////////////////////////// 

            conexion = ds.getConnection();

            ParameterDAO parameterDAO = new ParameterDAO();
            parameterDAO.setConexion(conexion);

            //////////////////////////////////////////
            // COMPROBAR SESSION
            /////////////////////////////////////////
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
                    // DECLARAR VARIABLES DE INSTANCIA
                    ////////////////////////////////////////

                    Parameter parameter = new Parameter();
                    Format format = new Format();

                    try {
                        /////////////////////////////////////////
                        // RECIBIR Y COMPROBAR PARAMETROS
                        ////////////////////////////////////////

                        String swaitingCard = request.getParameter("waitingCard");
                        String snumberEvent = request.getParameter("numberEvent");
                        String snumberPromo = request.getParameter("numberPromo");
                        String sbanerCevent = request.getParameter("banerCentralEvent");
                        String sbanerCpromo = request.getParameter("banerCentralPromo");
                        String sbanerCexchange = request.getParameter("banerCentralExchangeable");
                        String sbanerCvip = request.getParameter("banerCentralVip");
                        String sbanerCabout = request.getParameter("banerCentralAboutUs");
                        String sbanerTevent = request.getParameter("banerTopEvent");
                        String sbanerTpromo = request.getParameter("banerTopPromo");
                        String sbanerTmyplace = request.getParameter("banerTopMyPlace");
                        String sbanerTfindplace = request.getParameter("banerTopFindPlace");
                        String sbanerTconfiguration = request.getParameter("banerTopConfiguration");
                        String sbanerTsocial = request.getParameter("banerTopSocialNetworks");

                        boolean error = false;

                        /* comprobar waiting card*/
                        if (swaitingCard == null || swaitingCard.trim().equals("")) {
                            request.setAttribute("msgErrorWaitingCard", "Error: Debe Ingresar día para solicitud de tarjeta.");
                            error = true;
                        } else {
                            try {
                                request.setAttribute("waitingCard", swaitingCard);
                                parameter.setWaitingCard(Integer.parseInt(swaitingCard));
                                if (parameter.getWaitingCard() < 0) {
                                    request.setAttribute("msgErrorWaitingCard", "Error: Los días no pueden ser negativos.");
                                    error = true;
                                }
                            } catch (NumberFormatException n) {
                                request.setAttribute("msgErrorWaitingCard", "Error: Los días deben ser numéricos.");
                                error = true;
                            }
                        }

                        /*comprobar numberEvent*/
                        if (snumberEvent == null || snumberEvent.trim().equals("")) {
                            request.setAttribute("msgErrorNumberEvent", "Error: Debe Ingresar los días para evento.");
                            error = true;
                        } else {
                            try {
                                request.setAttribute("numberEvent", snumberEvent);
                                parameter.setNumberEvent(Integer.parseInt(snumberEvent));
                                if (parameter.getNumberEvent() < 0) {
                                    request.setAttribute("msgErrorNumberEvent", "Error: Los días no pueden ser negativos.");
                                    error = true;
                                }
                            } catch (NumberFormatException n) {
                                request.setAttribute("msgErrorNumberEvent", "Error: Los días deben ser numéricos.");
                                error = true;
                            }
                        }

                        /*comprobar numberPromo*/
                        if (snumberPromo == null || snumberPromo.trim().equals("")) {
                            request.setAttribute("msgErrorNumberPromo", "Error: Debe Ingresar los días para promo.");
                            error = true;
                        } else {
                            try {
                                request.setAttribute("numberPromo", snumberPromo);
                                parameter.setNumberPromo(Integer.parseInt(snumberPromo));
                                if (parameter.getNumberPromo() < 0) {
                                    request.setAttribute("msgErrorNumberPromo", "Error: Los días no pueden ser negativos.");
                                    error = true;
                                }
                            } catch (NumberFormatException n) {
                                request.setAttribute("msgErrorNumberEvent", "Error: Los días deben ser numéricos.");
                                error = true;
                            }
                        }

                        /* comprobar baner central event*/
                        if (sbanerCevent == null || sbanerCevent.trim().equals("")) {
                            request.setAttribute("msgErrorBanerCentralEvent", "Error: Debe ingresar url del baner central de event.");
                            error = true;
                        } else {
                            request.setAttribute("banerCentralEvent", sbanerCevent);
                            if (format.validarUrl(sbanerCevent)) {
                                parameter.setBanerCentralEvent(sbanerCevent);
                            } else {
                                request.setAttribute("msgErrorBanerCentralEvent", "Error: Debe ingresar formato correcto para baner central de evento.");
                                error = true;
                            }
                        }
                        /* comprobar baner central promo*/
                        if (sbanerCpromo == null || sbanerCpromo.trim().equals("")) {
                            request.setAttribute("msgErrorBanerCentralPromo", "Error: Debe ingresar url del baner central de promo.");
                            error = true;
                        } else {
                            request.setAttribute("banerCentralPromo", sbanerCpromo);
                            if (format.validarUrl(sbanerCpromo)) {
                                parameter.setBanerCentralPromo(sbanerCpromo);
                            } else {
                                request.setAttribute("msgErrorBanerCentralPromo", "Error: Debe ingresar formato correcto para baner central de promo.");
                                error = true;
                            }
                        }

                        /* comprobar baner central exchange*/
                        if (sbanerCexchange == null || sbanerCexchange.trim().equals("")) {
                            request.setAttribute("msgErrorBanerCentralExchange", "Error: Debe ingresar url del baner central de productos canjeables.");
                            error = true;
                        } else {
                            request.setAttribute("banerCentralExchangeable", sbanerCexchange);
                            if (format.validarUrl(sbanerCexchange)) {
                                parameter.setBanerCentralExchangeable(sbanerCexchange);
                            } else {
                                request.setAttribute("msgErrorBanerCentralExchange", "Error: Debe ingresar formato correcto para baner central de productos canjeables.");
                                error = true;
                            }
                        }

                        /* comprobar baner central vip*/
                        if (sbanerCvip == null || sbanerCvip.trim().equals("")) {
                            request.setAttribute("msgErrorBanerCentralVip", "Error: Debe ingresar url del baner central de vip.");
                            error = true;
                        } else {
                            request.setAttribute("banerCentralVip", sbanerCvip);
                            if (format.validarUrl(sbanerCvip)) {
                                parameter.setBanerCentralVip(sbanerCvip);
                            } else {
                                request.setAttribute("msgErrorBanerCentralVip", "Error: Debe ingresar formato correcto para baner central de vip.");
                                error = true;
                            }
                        }

                        /* comprobar baner central about us*/
                        if (sbanerCabout == null || sbanerCabout.trim().equals("")) {
                            request.setAttribute("msgErrorBanerCentralAboutUs", "Error: Debe ingresar url del baner central de sobre nostros.");
                            error = true;
                        } else {
                            request.setAttribute("banerCentralAboutUs", sbanerCabout);
                            if (format.validarUrl(sbanerCabout)) {
                                parameter.setBanerCentralAboutUs(sbanerCabout);
                            } else {
                                request.setAttribute("msgErrorBanerCentralAboutUs", "Error: Debe ingresar formato correcto para baner central de sobre nostros.");
                                error = true;
                            }
                        }

                        /* comprobar baner top event*/
                        if (sbanerTevent == null || sbanerTevent.trim().equals("")) {
                            request.setAttribute("msgErrorBanerTopEvent", "Error: Debe ingresar url del baner top para evento.");
                            error = true;
                        } else {
                            request.setAttribute("banerTopEvent", sbanerTevent);
                            if (format.validarUrl(sbanerTevent)) {
                                parameter.setBanerTopEvent(sbanerTevent);
                            } else {
                                request.setAttribute("msgErrorBanerTopEvent", "Error: Debe ingresar formato correcto para baner de cabecera de evento.");
                                error = true;
                            }
                        }

                        /* comprobar baner top promo*/
                        if (sbanerTpromo == null || sbanerTpromo.trim().equals("")) {
                            request.setAttribute("msgErrorBanerTopPromo", "Error: Debe ingresar url del baner top para promo.");
                            error = true;
                        } else {
                            request.setAttribute("banerTopPromo", sbanerTpromo);
                            if (format.validarUrl(sbanerTpromo)) {
                                parameter.setBanerTopPromo(sbanerTpromo);
                            } else {
                                request.setAttribute("msgErrorBanerTopPromo", "Error: Debe ingresar formato correcto para baner de cabecera de promo.");
                                error = true;
                            }
                        }

                        /* comprobar baner top my place*/
                        if (sbanerTmyplace == null || sbanerTmyplace.trim().equals("")) {
                            request.setAttribute("msgErrorBanerTopMyPlace", "Error: Debe ingresar url del baner cabecera para mis lugares.");
                            error = true;
                        } else {
                            request.setAttribute("banerTopMyPlace", sbanerTmyplace);
                            if (format.validarUrl(sbanerTmyplace)) {
                                parameter.setBanerTopMyPlace(sbanerTmyplace);
                            } else {
                                request.setAttribute("msgErrorBanerTopMyPlace", "Error: Debe ingresar formato correcto para baner de cabecera de mis lugares.");
                                error = true;
                            }

                        }/* comprobar baner top find place*/
                        if (sbanerTfindplace == null || sbanerTfindplace.trim().equals("")) {
                            request.setAttribute("msgErrorBanerTopFindPlace", "Error: Debe ingresar url del baner top para buscar lugares.");
                            error = true;
                        } else {
                            request.setAttribute("banerTopFindPlace", sbanerTfindplace);
                            if (format.validarUrl(sbanerTfindplace)) {
                                parameter.setBanerTopFindPlace(sbanerTfindplace);
                            } else {
                                request.setAttribute("msgErrorBanerTopFindPlace", "Error: Debe ingresar formato correcto para baner de cabecera de buscar lugares.");
                                error = true;
                            }

                        }

                        /* comprobar baner top configuration*/
                        if (sbanerTconfiguration == null || sbanerTconfiguration.trim().equals("")) {
                            request.setAttribute("msgErrorBanerTopConfiguration", "Error: Debe ingresar url del baner top para configuración.");
                            error = true;
                        } else {
                            request.setAttribute("banerTopConfiguration", sbanerTconfiguration);
                            if (format.validarUrl(sbanerTconfiguration)) {
                                parameter.setBanerTopConfiguration(sbanerTconfiguration);
                            } else {
                                request.setAttribute("msgErrorBanerTopConfiguration", "Error: Debe ingresar formato correcto para baner de cabecera de configuración.");
                                error = true;
                            }
                        }

                        /* comprobar baner top configuration*/
                        if (sbanerTsocial == null || sbanerTsocial.trim().equals("")) {
                            request.setAttribute("msgErrorBanerTopSocialNetworks", "Error: Debe ingresar url del baner top para redes sociales.");
                            error = true;
                        } else {
                            request.setAttribute("banerTopSocialNetworks", sbanerTsocial);
                            if (format.validarUrl(sbanerTsocial)) {
                                parameter.setBanerTopSocialNetworks(sbanerTsocial);
                            } else {
                                request.setAttribute("msgErrorBanerTopSocialNetworks", "Error: Debe ingresar formato correcto para baner de cabecera de redes sociales.");
                                error = true;
                            }
                        }

                        if (!error) {
                            try {
                                parameter.setIdParameter(1);
                                parameterDAO.update(parameter);
                                request.setAttribute("msgOk", "Registro actualizado exitosamente! ");
                            } catch (Exception ex) {
                                request.setAttribute("msgErrorFound", "Error: no existe el evento o ha sido eliminado mientras se actualizaba.");

                            }
                        }

                        /////////////////////////////////////////
                        // ESTABLECER ATRIBUTOS AL REQUEST
                        /////////////////////////////////////////


                    } catch (Exception parameterException) {
                    } finally {
                        request.getRequestDispatcher("/parameter/parameterUpdate.jsp").forward(request, response);
                    }
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
