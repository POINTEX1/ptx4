/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exchangeable;

import Helpers.Message;
import Helpers.MessageList;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
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
 * @author patricio alberto
 */
@WebServlet(name = "ExchangeableGetServlet", urlPatterns = {"/ExchangeableGetServlet"})
public class ExchangeableGetServlet extends HttpServlet {

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
            //////////////////////////
            // ESTABLECER CONEXION
            //////////////////////////

            conexion = ds.getConnection();

            ExchangeableDAO exDAO = new ExchangeableDAO();
            exDAO.setConexion(conexion);

            ////////////////////////
            // COMPROBAR SESSION
            ////////////////////////
            try {
                /* recuperar sesion */
                HttpSession session = request.getSession(false);

                /* obtener parametros de session */
                int access = Integer.parseInt((String) session.getAttribute("access"));
                String username = (String) session.getAttribute("username");

                /* comprobar permisos de usuario */
                if (access != 555 && access != 777) {
                    request.getRequestDispatcher("/ForbiddenServlet").forward(request, response);
                }

                /* obtener los valores de session y asignar valores a la jsp */
                request.setAttribute("userJsp", username);
                request.setAttribute("access", access);

                /////////////////////////////////////////
                // RECIBIR Y COMPROBAR PARAMETROS
                /////////////////////////////////////////

                /* obtener atributos de PRG */
                String redirect = request.getParameter("redirect");
                String sidExchangeable = request.getParameter("idExchangeable");
                String tittle = request.getParameter("tittle");
                String urlImage = request.getParameter("urlImage");
                String points = request.getParameter("points");
                String srequest = request.getParameter("request");
                String reason = request.getParameter("reason");

                /* obtener mensajes de PRG */
                String msgErrorTittle = request.getParameter("msgErrorTittle");
                String msgErrorUrlImage = request.getParameter("msgErrorUrlImage");
                String msgErrorPoints = request.getParameter("msgErrorPoints");
                String msgErrorReason = request.getParameter("msgErrorReason");
                String msgErrorDup = request.getParameter("msgErrorDup");
                String msgOk = request.getParameter("msgOk");

                /* instanciar lista de mensajes */
                Collection<Message> msgList = new ArrayList<Message>();

                /* establecer id exchangeable */
                int id = 0;
                try {
                    id = Integer.parseInt(sidExchangeable);
                } catch (NumberFormatException n) {
                }

                /* buscar exchangeable por id */
                try {
                    Exchangeable reg = exDAO.findByIdExchange(id);

                    if (reg != null) {
                        /* obtener atributos de reg */
                        request.setAttribute("idExchangeable", reg.getIdExchangeable());
                        request.setAttribute("namePlace", reg.getNamePlace());

                        /* comprobar redirect */
                        if (redirect == null || redirect.trim().equals("")) {
                            /* establecer atributos de reg */
                            request.setAttribute("tittle", reg.getTittle());
                            request.setAttribute("urlImage", reg.getUrlImage());
                            request.setAttribute("points", reg.getPoints());
                            request.setAttribute("reason", reg.getReason());
                            request.setAttribute("request", reg.getRequest());
                        } else {
                            /* estableccer atributos de PRG */
                            request.setAttribute("tittle", tittle);
                            request.setAttribute("urlImage", urlImage);
                            request.setAttribute("points", points);
                            request.setAttribute("reason", reason);
                            try {
                                request.setAttribute("request", Integer.parseInt(srequest));
                            } catch (NumberFormatException n) {
                            }
                        }

                        /////////////////////////////////
                        // COMPROBAR MENSAJES DE ERROR
                        /////////////////////////////////

                        /* comprobar tittle */
                        if (msgErrorTittle == null || msgErrorTittle.trim().equals("")) {
                        } else {
                            request.setAttribute("msgErrorTittle", true);
                            msgList.add(MessageList.addMessage(msgErrorTittle));
                        }

                        /* comprobar url image */
                        if (msgErrorUrlImage == null || msgErrorUrlImage.trim().equals("")) {
                        } else {
                            request.setAttribute("msgErrorUrlImage", true);
                            msgList.add(MessageList.addMessage(msgErrorUrlImage));
                        }

                        /* comprobar points */
                        if (msgErrorPoints == null || msgErrorPoints.trim().equals("")) {
                        } else {
                            request.setAttribute("msgErrorPoints", true);
                            msgList.add(MessageList.addMessage(msgErrorPoints));
                        }

                        /* comprobar reason */
                        if (msgErrorReason == null || msgErrorReason.trim().equals("")) {
                        } else {
                            request.setAttribute("msgErrorReason", true);
                            msgList.add(MessageList.addMessage(msgErrorReason));
                        }

                        /* comprobar registro duplicado */
                        if (msgErrorDup == null || msgErrorDup.trim().equals("")) {
                        } else {
                            request.setAttribute("msgErrorDup", true);
                            msgList.add(MessageList.addMessage(msgErrorDup));
                        }

                        /* comprobar mensaje de exito */
                        if (msgOk == null || msgOk.trim().equals("")) {
                            request.setAttribute("msg", "Se encontró el registro!");
                        } else {
                            request.setAttribute("msgOk", msgOk);
                        }
                    } else {
                        request.setAttribute("msgErrorFound", true);
                        msgList.add(MessageList.addMessage("El registro no ha sido encontrado."));
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                /* establecer lista de mensajes */
                if (!msgList.isEmpty()) {
                    request.setAttribute("msgList", msgList);
                }

                /* despachar a la vista */
                request.getRequestDispatcher("/exchangeable/exchangeableUpdate.jsp").forward(request, response);

            } catch (Exception sesionException) {
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
