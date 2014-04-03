/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parameter;

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
@WebServlet(name = "ParameterGetServlet", urlPatterns = {"/ParameterGetServlet"})
public class ParameterGetServlet extends HttpServlet {

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

        request.setCharacterEncoding("UTF-8");

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

                    ////////////////////////////////////
                    // RECIBIR Y COMPROBAR PARAMETOS
                    ////////////////////////////////////

                    /* recibir atributos por PRG */
                    String waitingCard = request.getParameter("waitingCard");
                    String numberEvent = request.getParameter("numberEvent");
                    String numberPromo = request.getParameter("numberPromo");
                    String banerCentralEvent = request.getParameter("banerCentralEvent");
                    String banerCentralPromo = request.getParameter("banerCentralPromo");
                    String banerCentralExchangeable = request.getParameter("banerCentralExchangeable");
                    String banerCentralVip = request.getParameter("banerCentralVip");
                    String banerCentralAboutUs = request.getParameter("banerCentralAboutUs");
                    String banerTopEvent = request.getParameter("banerTopEvent");
                    String banerTopPromo = request.getParameter("banerTopPromo");
                    String banerTopMyPlace = request.getParameter("banerTopMyPlace");
                    String banerTopFindPlace = request.getParameter("banerTopFindPlace");
                    String banerTopConfiguration = request.getParameter("banerTopConfiguration");
                    String banerTopSocialNetworks = request.getParameter("banerTopSocialNetworks");

                    /* recibir mensajes por PRG */
                    String msgErrorWaitingCard = request.getParameter("msgErrorWaitingCard");
                    String msgErrorNumberEvent = request.getParameter("msgErrorNumberEvent");
                    String msgErrorNumberPromo = request.getParameter("msgErrorNumberPromo");
                    String msgErrorBanerCentralEvent = request.getParameter("msgErrorBanerCentralEvent");
                    String msgErrorBanerCentralPromo = request.getParameter("msgErrorBanerCentralPromo");
                    String msgErrorBanerCentralExchange = request.getParameter("msgErrorBanerCentralExchange");
                    String msgErrorBanerCentralVip = request.getParameter("msgErrorBanerCentralVip");
                    String msgErrorBanerCentralAboutUs = request.getParameter("msgErrorBanerCentralAboutUs");
                    String msgErrorBanerTopEvent = request.getParameter("msgErrorBanerTopEvent");
                    String msgErrorBanerTopPromo = request.getParameter("msgErrorBanerTopPromo");
                    String msgErrorBanerTopMyPlace = request.getParameter("msgErrorBanerTopMyPlace");
                    String msgErrorBanerTopFindPlace = request.getParameter("msgErrorBanerTopFindPlace");
                    String msgErrorBanerTopConfiguration = request.getParameter("msgErrorBanerTopConfiguration");
                    String msgErrorBanerTopSocialNetworks = request.getParameter("msgErrorBanerTopSocialNetworks");
                    String msgOk = request.getParameter("msgOk");
                    String msgErrorUpdate = request.getParameter("msgErrorUpdate");


                    /* obtener parametros de configuracion */
                    Parameter reg = parameterDAO.getAll();

                    /* comprobar waiting card */
                    if (msgErrorWaitingCard == null || msgErrorWaitingCard.trim().equals("")) {
                        request.setAttribute("waitingCard", reg.getWaitingCard());
                    } else {
                        request.setAttribute("waitingCard", waitingCard);
                        request.setAttribute("msgErrorWaitingCard", msgErrorWaitingCard);
                    }

                    /* comprobar number event */
                    if (msgErrorNumberEvent == null || msgErrorNumberEvent.trim().equals("")) {
                        request.setAttribute("numberEvent", reg.getNumberEvent());
                    } else {
                        request.setAttribute("numberEvent", numberEvent);
                        request.setAttribute("msgErrorNumberEvent", msgErrorNumberEvent);
                    }

                    /* comprobar number promo */
                    if (msgErrorNumberPromo == null || msgErrorNumberPromo.trim().equals("")) {
                        request.setAttribute("numberPromo", reg.getNumberPromo());
                    } else {
                        request.setAttribute("numberPromo", numberPromo);
                        request.setAttribute("msgErrorNumberPromo", msgErrorNumberPromo);
                    }

                    /* comprobar baner central event */
                    if (msgErrorBanerCentralEvent == null || msgErrorBanerCentralEvent.trim().equals("")) {
                        request.setAttribute("banerCentralEvent", reg.getBanerCentralEvent());
                    } else {
                        request.setAttribute("banerCentralEvent", banerCentralEvent);
                        request.setAttribute("msgErrorBanerCentralEvent", msgErrorBanerCentralEvent);
                    }

                    /* comprobar baner central promo */
                    if (msgErrorBanerCentralPromo == null || msgErrorBanerCentralPromo.trim().equals("")) {
                        request.setAttribute("banerCentralPromo", reg.getBanerCentralPromo());
                    } else {
                        request.setAttribute("banerCentralPromo", banerCentralPromo);
                        request.setAttribute("msgErrorBanerCentralPromo", msgErrorBanerCentralPromo);
                    }

                    /* comprobar baner central exchangeable */
                    if (msgErrorBanerCentralExchange == null || msgErrorBanerCentralExchange.trim().equals("")) {
                        request.setAttribute("banerCentralExchange", reg.getBanerCentralExchangeable());
                    } else {
                        request.setAttribute("banerCentralExchange", banerCentralExchangeable);
                        request.setAttribute("msgErrorBanerCentralExchange", msgErrorBanerCentralExchange);
                    }

                    /* comprobar baner central vip */
                    if (msgErrorBanerCentralVip == null || msgErrorBanerCentralVip.trim().equals("")) {
                        request.setAttribute("banerCentralVip", reg.getBanerCentralVip());
                    } else {
                        request.setAttribute("banerCentralVip", banerCentralVip);
                        request.setAttribute("msgErrorBanerCentralVip", msgErrorBanerCentralVip);
                    }

                    /* comprobar baner central about us */
                    if (msgErrorBanerCentralAboutUs == null || msgErrorBanerCentralAboutUs.trim().equals("")) {
                        request.setAttribute("banerCentralAboutUs", reg.getBanerCentralAboutUs());
                    } else {
                        request.setAttribute("banerCentralAboutUs", banerCentralAboutUs);
                        request.setAttribute("msgErrorBanerCentralAboutUs", msgErrorBanerCentralAboutUs);
                    }

                    /* comprobar baner top event */
                    if (msgErrorBanerTopEvent == null || msgErrorBanerTopEvent.trim().equals("")) {
                        request.setAttribute("banerTopEvent", reg.getBanerTopEvent());
                    } else {
                        request.setAttribute("banerTopEvent", banerTopEvent);
                        request.setAttribute("msgErrorBanerTopEvent", msgErrorBanerTopEvent);
                    }

                    /* comprobar baner top promo */
                    if (msgErrorBanerTopPromo == null || msgErrorBanerTopPromo.trim().equals("")) {
                        request.setAttribute("banerTopPromo", reg.getBanerTopPromo());
                    } else {
                        request.setAttribute("banerTopPromo", banerTopPromo);
                        request.setAttribute("msgErrorBanerTopPromo", msgErrorBanerTopPromo);
                    }

                    /* comprobar baner top my place */
                    if (msgErrorBanerTopMyPlace == null || msgErrorBanerTopMyPlace.trim().equals("")) {
                        request.setAttribute("banerTopMyPlace", reg.getBanerTopMyPlace());
                    } else {
                        request.setAttribute("banerTopMyPlace", banerTopMyPlace);
                        request.setAttribute("msgErrorBanerTopMyPlace", msgErrorBanerTopMyPlace);
                    }

                    /* comprobar baner top find place */
                    if (msgErrorBanerTopFindPlace == null || msgErrorBanerTopFindPlace.trim().equals("")) {
                        request.setAttribute("banerTopFindPlace", reg.getBanerTopFindPlace());
                    } else {
                        request.setAttribute("banerTopFindPlace", banerTopFindPlace);
                        request.setAttribute("msgErrorBanerTopFindPlace", msgErrorBanerTopFindPlace);
                    }

                    /* comprobar baner top configuration */
                    if (msgErrorBanerTopConfiguration == null || msgErrorBanerTopConfiguration.trim().equals("")) {
                        request.setAttribute("banerTopConfiguration", reg.getBanerTopConfiguration());
                    } else {
                        request.setAttribute("banerTopConfiguration", banerTopConfiguration);
                        request.setAttribute("msgErrorBanerTopConfiguration", msgErrorBanerTopConfiguration);
                    }

                    /* comprobar baner top social networks */
                    if (msgErrorBanerTopSocialNetworks == null || msgErrorBanerTopSocialNetworks.trim().equals("")) {
                        request.setAttribute("banerTopSocialNetworks", reg.getBanerTopSocialNetworks());
                    } else {
                        request.setAttribute("banerTopSocialNetworks", banerTopSocialNetworks);
                        request.setAttribute("msgErrorBanerTopSocialNetworks", msgErrorBanerTopSocialNetworks);
                    }

                    /* comprobar error de actualizacion */
                    if (msgErrorUpdate == null || msgErrorUpdate.trim().equals("")) {
                    } else {
                        request.setAttribute("msgErrorUpdate", msgErrorUpdate);
                    }

                    /* comprobar mensaje de exito */
                    if (msgOk == null || msgOk.trim().equals("")) {
                        request.setAttribute("msg", "actualice los datos de configuración.");
                    } else {
                        request.setAttribute("msgOk", msgOk);
                    }

                    /* despachar a la vista */
                    request.getRequestDispatcher("/parameter/parameterUpdate.jsp").forward(request, response);

                }
            } catch (Exception sessionException) {
                /* enviar a la vista de login */
                System.out.println("no ha iniciado session");
                request.getRequestDispatcher("/login/login.jsp").forward(request, response);
            }
        } catch (Exception connectionException) {
            connectionException.printStackTrace();
        } finally {
            /* cerrar conexion */
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
