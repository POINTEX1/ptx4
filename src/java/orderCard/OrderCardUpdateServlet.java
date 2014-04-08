/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orderCard;

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
 * @author patricio
 */
@WebServlet(name = "OrderCardUpdateServlet", urlPatterns = {"/OrderCardUpdateServlet"})
public class OrderCardUpdateServlet extends HttpServlet {

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

        /////////////////////////
        // ESTABLECER CONEXION
        /////////////////////////
        try {
            conexion = ds.getConnection();

            OrderCardDAO orderCardDAO = new OrderCardDAO();
            orderCardDAO.setConexion(conexion);

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
                if (access != 777) {
                    request.getRequestDispatcher("/ForbiddenServlet").forward(request, response);
                }

                /* obtener los valores de session y asignar valores a la jsp */
                request.setAttribute("userJsp", username);
                request.setAttribute("access", access);

                /////////////////////////////////////
                // RECIBIR Y COMPROBAR PARAMETROS
                /////////////////////////////////////

                String sidOrderCard = request.getParameter("idOrder");
                String sCardType = request.getParameter("cardType");
                String srequest = request.getParameter("orderCardRequest");
                String reason = request.getParameter("reason");

                /* instanciar orderCard */
                OrderCard orderCard = new OrderCard();

                /* flag de error */
                boolean error = false;

                /* instanciar string url */
                String url = "?redirect=ok";
                url += "&idOrder=" + sidOrderCard;
                url += "&cardType=" + sCardType;
                url += "&request=" + srequest;
                url += "&reason=" + reason;


                /* comprobar idOrder */
                if (sidOrderCard == null || sidOrderCard.trim().equals("")) {
                    error = true;
                } else {
                    try {
                        orderCard.setIdOrder(Integer.parseInt(sidOrderCard));
                    } catch (NumberFormatException n) {
                        error = true;
                    }
                }

                /* comprobar card type */
                if (sCardType == null || sCardType.trim().equals("")) {
                    error = true;
                } else {
                    try {
                        orderCard.setCardType(Integer.parseInt(sCardType));
                    } catch (NumberFormatException n) {
                        error = true;
                    }
                }

                /* comprobar request */
                if (srequest == null || srequest.trim().equals("")) {
                    error = true;
                } else {
                    try {
                        orderCard.setRequest(Integer.parseInt(srequest));
                    } catch (NumberFormatException n) {
                        error = true;
                    }
                }

                /* comprobar reason*/
                if ((reason == null || reason.trim().equals("")) && orderCard.getRequest() == 2) {
                    url += "&msgErrorReason=Debe ingresar razón de rechazo.";
                    error = true;
                } else {
                    orderCard.setReason(reason);
                }

                ///////////////////////
                // LOGICA DE NEGOCIO
                ///////////////////////

                if (!error) {
                    /* actualizar datos */
                    try {
                        orderCardDAO.update(orderCard);
                        url += "&msgOk=Registro actualizado exitosamente.";
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                /* send redirect */
                response.sendRedirect("OrderCardGetServlet" + url);

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
