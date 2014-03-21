/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package category;

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
 * @author patricio alberto
 */
@WebServlet(name = "CategoryUpdateServlet", urlPatterns = {"/CategoryUpdateServlet"})
public class CategoryUpdateServlet extends HttpServlet {

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
            //////////////////////////////////////// 

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
                /////////////////////////////////////////

                String sidCategory = request.getParameter("idCategory");
                String nameCategory = request.getParameter("nameCategory");

                Category category = new Category();

                boolean error = false;

                String url = "?a=target";

                /* comprobar ID */
                url += "&idCategory=" + sidCategory;
                if (sidCategory == null || sidCategory.trim().equals("")) {
                    url += "&msgErrorId=Error al recibir ID.";
                    error = true;
                } else {
                    try {
                        category.setIdCategory(Integer.parseInt(sidCategory));
                    } catch (NumberFormatException n) {
                        url += "&msgErrorId=Error al recibir ID.";
                        error = true;
                    }
                }
                /* comprobar name category */
                url += "&nameCategory=" + nameCategory;
                if (nameCategory == null || nameCategory.trim().equals("")) {
                    url += "&msgErrorNameCategory= Error: Debe ingresar nombre de categoría.";
                    error = true;
                } else {
                    category.setNameCategory(Format.capital(nameCategory));
                }

                if (!error) {
                    /* comprobar duplicados */
                    if (categoryDAO.findByName(category.getIdCategory(), category.getNameCategory())) {
                        url += "&msgErrorDup=Error: ya existe una categoría con ese nombre.";
                    } else {
                        /* comprobar existencia */
                        Category aux = categoryDAO.findById(category.getIdCategory());
                        if (aux != null) {
                            try {
                                categoryDAO.update(category);
                                url += "msgOk=Registro actualizado exitosamente!";
                            } catch (Exception ex) {
                            }
                        } else {
                            url += "msgErrorFound=Error: La ciudad no existe o ha sido eliminada mientras se actualizaba.";
                        }
                    }
                }
                /* send redirect */
                response.sendRedirect("CategoryGetServlet" + url);

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
