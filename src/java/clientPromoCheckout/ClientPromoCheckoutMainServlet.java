/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clientPromoCheckout;

import clientPromo.ClientPromoDAO;
import java.io.IOException;
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
import place.PlaceDAO;
import promo.PromoDAO;

/**
 *
 * @author patricio
 */
@WebServlet(name = "ClientPromoCheckoutMainServlet", urlPatterns = {"/ClientPromoCheckoutMainServlet"})
public class ClientPromoCheckoutMainServlet extends HttpServlet {

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

            ClientPromoDAO pglDAO = new ClientPromoDAO();
            pglDAO.setConexion(conexion);

            PlaceDAO placeDAO = new PlaceDAO();
            placeDAO.setConexion(conexion);

            PromoDAO promoDAO = new PromoDAO();
            promoDAO.setConexion(conexion);

            ClientPromoCheckoutDAO clientPromoCheckoutDAO = new ClientPromoCheckoutDAO();
            clientPromoCheckoutDAO.setConexion(conexion);

            ///////////////////////
            // COMPROBAR SESSION
            ///////////////////////
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

                    //////////////////////////////////////
                    // RECIBIR Y COMPROBAR PARAMETROS
                    //////////////////////////////////////

                    /* obtener mensajes */
                    String msgDel = request.getParameter("msgDel");
                    String msgErrorConstraint = request.getParameter("msgErrorConstraint");

                    /* comprobar eliminacion */
                    if (msgDel == null || msgDel.trim().equals("")) {
                    } else {
                        request.setAttribute("msgDel", msgDel);
                    }

                    /* comprobar error de eliminacion */
                    if (msgErrorConstraint == null || msgErrorConstraint.trim().equals("")) {
                    } else {
                        request.setAttribute("msgErrorConstraint", msgErrorConstraint);
                    }

                    /* obtener promo de clientes cobradas */
                    try {
                        Collection<ClientPromoCheckout> list = clientPromoCheckoutDAO.getAll();
                        request.setAttribute("list", list);

                        /* contador de registros */
                        request.setAttribute("regCount", list.size());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    /* despachar a la vista */
                    request.getRequestDispatcher("/clientPromoCheckout/clientPromoCheckout.jsp").forward(request, response);

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
