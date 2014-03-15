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

                    try {
                        /////////////////////////////////////////
                        // RECIBIR Y COMPROBAR PARAMETROS
                        ////////////////////////////////////////

                        String swaitingCard = request.getParameter("waitingCard");
                        String snumberEvent = request.getParameter("numberEvent");
                        String snumberPromo = request.getParameter("numberPromo");
                        String sbanerCevent = request.getParameter("banerCentralEvent");
                        String sbanerCpromo = request.getParameter("banerCentralPromo");
                        String sbanerCexchange = request.getParameter("banerCentralExchange");
                        String sbanerTevent = request.getParameter("banerTopEvent");
                        String sbanerTpromo = request.getParameter("banerTopPromo");
                        String sbanerTmyplace = request.getParameter("banerTopMyPlace");
                        String sbanerTfindplace = request.getParameter("banerTopFindPlace");
                        String sbanerTconfiguration = request.getParameter("banerTopConfiguration");

                        boolean error = false;

                        /* comprobar waiting card*/
                        if (swaitingCard == null || swaitingCard.trim().equals("")) {
                            request.setAttribute("msgErrorWaitingCard", "Error: Debe Ingresar día para solicitud de tarjeta.");
                            error = true;
                        } else {
                            try {
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
                            parameter.setBanerCentralPromo(sbanerCevent);
                        }

                        /* comprobar baner central promo*/
                        if (sbanerCpromo == null || sbanerCpromo.trim().equals("")) {
                            request.setAttribute("msgErrorBanerCentralPromo", "Error: Debe ingresar url del baner central de promo.");
                            error = true;
                        } else {
                            parameter.setBanerCentralPromo(sbanerCpromo);
                        }

                        /* comprobar baner central exchange*/
                        if (sbanerCexchange == null || sbanerCexchange.trim().equals("")) {
                            request.setAttribute("msgErrorBanerCentralExchange", "Error: Debe ingresar url del baner central de productos canjeables.");
                            error = true;
                        } else {
                            parameter.setBanerCentralExchange(sbanerCexchange);
                        }

                        /* comprobar baner top event*/
                        if (sbanerTevent == null || sbanerTevent.trim().equals("")) {
                            request.setAttribute("msgErrorBanerTopEvent", "Error: Debe ingresar url del baner top para evento.");
                            error = true;
                        } else {
                            parameter.setBanerTopEvent(sbanerTevent);
                        }

                        /* comprobar baner top promo*/
                        if (sbanerTpromo == null || sbanerTpromo.trim().equals("")) {
                            request.setAttribute("msgErrorBanerTopPromo", "Error: Debe ingresar url del baner top para promo.");
                            error = true;
                        } else {
                            parameter.setBanerTopPromo(sbanerTpromo);
                        }

                        /* comprobar baner top my place*/
                        if (sbanerTmyplace == null || sbanerTmyplace.trim().equals("")) {
                            request.setAttribute("msgErrorBanerTopMyPlace", "Error: Debe ingresar url del baner top para mis lugares.");
                            error = true;
                        } else {
                            parameter.setBanerTopMyPlace(sbanerTmyplace);

                        }/* comprobar baner top find place*/
                        if (sbanerTfindplace == null || sbanerTfindplace.trim().equals("")) {
                            request.setAttribute("msgErrorBanerTopFindPlace", "Error: Debe ingresar url del baner top para buscar lugares.");
                            error = true;
                        } else {
                            parameter.setBanerTopFindPlace(sbanerTfindplace);

                        }

                        /* comprobar baner top configuration*/
                        if (sbanerTconfiguration == null || sbanerTconfiguration.trim().equals("")) {
                            request.setAttribute("msgErrorBanerTopConfiguration", "Error: Debe ingresar url del baner top para configuración.");
                            error = true;
                        } else {
                            parameter.setBanerTopConfiguration(sbanerTconfiguration);

                        }

                        if (!error) {
                            parameterDAO.update(parameter);
                            request.setAttribute("msgOk", "Registro actualizado exitosamente! ");
                        }

                        /////////////////////////////////////////
                        // ESTABLECER ATRIBUTOS AL REQUEST
                        /////////////////////////////////////////
                        request.setAttribute("parameter", parameter);

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
