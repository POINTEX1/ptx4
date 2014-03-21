/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package category;

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
 * @author patricio alberto
 */
@WebServlet(name = "CategoryGetServlet", urlPatterns = {"/CategoryGetServlet"})
public class CategoryGetServlet extends HttpServlet {

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
            //////////////////////////////////////////
            // ESTABLECER CONEXION
            /////////////////////////////////////////

            conexion = ds.getConnection();

            CategoryDAO categoryDAO = new CategoryDAO();
            categoryDAO.setConexion(conexion);

            //////////////////////////////////////////
            // COMPROBAR SESSION
            /////////////////////////////////////////
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

                /////////////////////////////////////////
                // RECIBIR Y COMPROBAR PARAMETROS
                //////////////////////////////////////// 
                try {

                    /* recibir atributos por PRG */
                    String nameCategory = request.getParameter("nameCategory");

                    /* recibir mensajes por PRG */
                    String msgErrorNameCategory = request.getParameter("msgErrorNameCategory");
                    String msgErrorDup = request.getParameter("msgErrorDup");
                    String msgOk = request.getParameter("msgOk");

                    /* obtener atributos para busqueda */
                    String sidCategory = request.getParameter("idCategory");

                    Category category = new Category();

                    /* comprobar id category */
                    if (sidCategory == null || sidCategory.trim().equals("")) {
                        request.setAttribute("msgErrorId", "Error al recibir ID.");
                    } else {
                        category.setIdCategory(Integer.parseInt(sidCategory));
                        /* buscar registro */
                        Category aux = categoryDAO.findById(category.getIdCategory());

                        if (aux != null) {
                            /* obtener atributos del dao */
                            request.setAttribute("idCategory", aux.getIdCategory());

                            /* comprobar msgErrorNameCategory */
                            if (msgErrorNameCategory == null || msgErrorNameCategory.trim().equals("")) {
                                request.setAttribute("nameCategory", aux.getNameCategory());
                            } else {
                                request.setAttribute("msgErrorNameCategory", msgErrorNameCategory);
                                request.setAttribute("nameCategory", nameCategory);
                            }

                            /* comprobar registro duplicado */
                            if (msgErrorDup == null || msgErrorDup.trim().equals("")) {
                            } else {
                                request.setAttribute("msgErrorDup", msgErrorDup);
                            }

                            /* comprobar msgOk */
                            if (msgOk == null || msgOk.trim().equals("")) {
                                request.setAttribute("msgOk", "Se encontró el registro!");
                            } else {
                                request.setAttribute("msgOk", msgOk);
                            }
                        } else {
                            request.setAttribute("msgErrorFound", "Error: No se encontró el registro.");
                        }
                    }
                } catch (Exception parameterException) {
                } finally {
                    request.getRequestDispatcher("category/categoryUpdate.jsp").forward(request, response);
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
