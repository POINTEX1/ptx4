/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

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
@WebServlet(name = "AdminGetServlet", urlPatterns = {"/AdminGetServlet"})
public class AdminGetServlet extends HttpServlet {

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

        /////////////////////////////////////////
        // ESTABLECER CONEXION
        /////////////////////////////////////////
        try {
            conexion = ds.getConnection();

            AdminDAO adminDAO = new AdminDAO();
            adminDAO.setConexion(conexion);

            //////////////////////////////////////////
            // COMPROBAR SESSION
            /////////////////////////////////////////
            try {
                /* recuperar sesion */
                HttpSession session = request.getSession(false);

                /* obtener parametros de session */
                int access = Integer.parseInt((String) session.getAttribute("access"));
                String user = (String) session.getAttribute("username");

                /* comprobar permisos de usuario */
                if (access != 777) {
                    request.getRequestDispatcher("/ForbiddenServlet").forward(request, response);
                } else {
                    /* obtener los valores de session y asignar valores a la jsp */
                    request.setAttribute("userJsp", user);
                    request.setAttribute("access", access);


                    /////////////////////////////////////////
                    // RECIBIR Y COMPROBAR PARAMETROS
                    ////////////////////////////////////////

                    /* recibir parametro de busqueda */
                    String sid = request.getParameter("id");

                    /* recibir atributos por PRG */
                    String username = request.getParameter("username");
                    String email = request.getParameter("email");

                    /* recibir mensajes por PRG */
                    String msgErrorId = request.getParameter("msgErrorId");
                    String msgErrorUsername = request.getParameter("msgErrorUsername");
                    String msgErrorEmail = request.getParameter("msgErrorEmail");
                    String msgErrorPwd1 = request.getParameter("msgErrorPwd1");
                    String msgErrorPwd2 = request.getParameter("msgErrorPwd2");
                    String msgOk = request.getParameter("msgOk");


                    /* comprobar id admin */
                    if (sid == null || sid.trim().equals("")) {
                        request.setAttribute("msgErrorId", "Error al recibir id Admin.");
                    } else {
                        int id = Integer.parseInt(sid);
                        /* buscar admin por id */
                        try {
                            Admin reg = adminDAO.findById(id);
                            if (reg != null) {
                                /* obtener atributos */
                                request.setAttribute("id", id);

                                /* mensajes de error por id */
                                if (msgErrorId == null || msgErrorId.trim().equals("")) {
                                } else {
                                    request.setAttribute("msgErrorId", msgErrorId);
                                }

                                /* mensaje de error por username */
                                if (msgErrorUsername == null || msgErrorUsername.trim().equals("")) {
                                    request.setAttribute("username", reg.getUsername());
                                } else {
                                    request.setAttribute("username", username);
                                    request.setAttribute("msgErrorUsername", msgErrorUsername);
                                }

                                /* mensaje de error por email */
                                if (msgErrorEmail == null || msgErrorEmail.trim().equals("")) {
                                    request.setAttribute("email", reg.getEmail());
                                } else {
                                    request.setAttribute("email", email);
                                    request.setAttribute("msgErrorEmail", msgErrorEmail);
                                }

                                /* mensaje de error por pwd1 */
                                if (msgErrorPwd1 == null || msgErrorPwd1.trim().equals("")) {
                                } else {
                                    request.setAttribute("msgErrorPwd1", msgErrorPwd1);
                                }

                                /* mensaje de error pwd2 */
                                if (msgErrorPwd2 == null || msgErrorPwd2.trim().equals("")) {
                                } else {
                                    request.setAttribute("msgErrorPwd2", msgErrorPwd2);
                                }

                                /* mensaje de exito */
                                if (msgOk == null || msgOk.trim().equals("")) {
                                    request.setAttribute("msg", "Se encontró el registro!");
                                } else {
                                    request.setAttribute("msgOk", msgOk);
                                }
                            } else {
                                request.setAttribute("msgErrorFound", "Error: No se encontró el registro.");
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    /* despachar a la vista */
                    request.getRequestDispatcher("/admin/adminUpdate.jsp").forward(request, response);
                }
            } catch (Exception sessionException) {
                /* enviar a la vista de login */
                request.getRequestDispatcher("/login/login.jsp").forward(request, response);
                System.out.println("no ha iniciado session");
                sessionException.printStackTrace();
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
