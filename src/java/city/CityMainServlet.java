/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package city;

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

/**
 *
 * @author patricio alberto
 */
@WebServlet(name = "CityMainServlet", urlPatterns = {"/CityMainServlet"})
public class CityMainServlet extends HttpServlet {

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

        ///////////////////////////
        // ESTABLECER CONEXION
        ///////////////////////////
        try {
            conexion = ds.getConnection();

            CityDAO cityDAO = new CityDAO();
            cityDAO.setConexion(conexion);

            ////////////////////////
            // COMPROBAR SESSION
            ////////////////////////
            try {
                /* recuperar sesion */
                HttpSession session = request.getSession(false);

                /* obtener los valores de session */
                String userJsp = (String) session.getAttribute("username");
                String sAccess = (String) session.getAttribute("access");
                int access = Integer.parseInt(sAccess);

                /* asignar valores a la jsp */
                request.setAttribute("userJsp", userJsp);
                request.setAttribute("access", access);

                //////////////////////////////////////
                // RECIBIR Y COMPROBAR PARAMETROS
                //////////////////////////////////////
                try {
                    String msgDel = request.getParameter("msgDel");
                    String msgErrorConstraint = request.getParameter("msgErrorContraint");

                    /* comprobar eliminacion */
                    if (msgDel == null || msgDel.trim().equals("")) {
                    } else {
                        request.setAttribute("msgDel", msgDel);
                    }

                    /* comprobar error de referencia */
                    if (msgErrorConstraint == null || msgErrorConstraint.trim().equals("")) {
                    } else {
                        request.setAttribute("msgErrorConstraint", msgErrorConstraint);
                    }

                    //////////////////////////////////////////
                    // OBTENER TOTAL DE REGISTROS
                    //////////////////////////////////////////
                    try {
                        Collection<City> list = cityDAO.getAll();
                        request.setAttribute("list", list);

                        request.setAttribute("regCount", list.size());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                } catch (Exception parameterException) {
                } finally {
                    request.getRequestDispatcher("/city/city.jsp").forward(request, response);
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
