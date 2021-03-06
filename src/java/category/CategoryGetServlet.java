/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package category;

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

        ///////////////////////////
        // ESTABLECER CONEXION
        ///////////////////////////
        try {
            conexion = ds.getConnection();

            CategoryDAO categoryDAO = new CategoryDAO();
            categoryDAO.setConexion(conexion);

            /////////////////////////
            // COMPROBAR SESSION
            /////////////////////////
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

                ////////////////////////////////////
                // RECIBIR Y COMPROBAR PARAMETROS
                ////////////////////////////////////

                /* recibir parametros por PRG */
                String redirect = request.getParameter("redirect");
                String sidCategory = request.getParameter("idCategory");
                String nameCategory = request.getParameter("nameCategory");

                /* recibir mensajes por PRG */
                String msgErrorNameCategory = request.getParameter("msgErrorNameCategory");
                String msgErrorDup = request.getParameter("msgErrorDup");
                String msgOk = request.getParameter("msgOk");

                /* instanciar lista de mensajes */
                Collection<Message> msgList = new ArrayList<Message>();

                /* comprobar id */
                int idCategory = 0;
                try {
                    idCategory = Integer.parseInt(sidCategory);
                } catch (NumberFormatException n) {
                }

                /* buscar registro por id */
                try {
                    Category aux = categoryDAO.findById(idCategory);

                    if (aux != null) {
                        /* establecer atributos */
                        request.setAttribute("idCategory", idCategory);

                        /* comprobar redirect */
                        if (redirect == null || redirect.trim().equals("")) {
                            /* establecer atributos de reg */
                            request.setAttribute("nameCategory", aux.getNameCategory());
                        } else {
                            /* establecer atributos de PRG */
                            request.setAttribute("nameCategory", nameCategory);
                        }

                        /* comprobar msgErrorNameCategory */
                        if (msgErrorNameCategory == null || msgErrorNameCategory.trim().equals("")) {
                        } else {
                            request.setAttribute("msgErrorNameCategory", true);
                            msgList.add(MessageList.addMessage(msgErrorNameCategory));
                        }

                        /* comprobar registro duplicado */
                        if (msgErrorDup == null || msgErrorDup.trim().equals("")) {
                        } else {
                            request.setAttribute("msgErrorDup", true);
                            msgList.add(MessageList.addMessage(msgErrorDup));
                        }

                        /* comprobar msgOk */
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

                /* establecer lista de mensajes a la peticion */
                if (!msgList.isEmpty()) {
                    request.setAttribute("msgList", msgList);
                }

                /* despachar a la vista */
                request.getRequestDispatcher("category/categoryUpdate.jsp").forward(request, response);

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
