/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import Helpers.StringMD;
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
@WebServlet(name = "AdminUpdateServlet", urlPatterns = {"/AdminUpdateServlet"})
public class AdminUpdateServlet extends HttpServlet {

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

                    String chk = request.getParameter("chk");
                    String sid = request.getParameter("id");
                    String username = request.getParameter("username");
                    String email = request.getParameter("email");
                    String pwd1 = request.getParameter("pwd1");
                    String pwd2 = request.getParameter("pwd2");

                    Admin admin = new Admin();

                    boolean error = false;

                    /* instanciar string url */
                    String url = "";

                    /* comprobar id admin */
                    if (sid == null || sid.trim().equals("")) {
                        url = url + "&msgErrorId=Error al recibir id Admin.";
                        error = true;
                    } else {
                        admin.setIdAdmin(Integer.parseInt(sid));
                    }

                    /* comprobar username */
                    url = url + "&username=" + username;
                    if (username == null || username.trim().equals("")) {
                        url = url + "&msgErrorUsername=Error: Debe ingresar username.";
                        error = true;
                    } else {
                        admin.setUsername(username);
                        /* comprobar username duplicado */
                        try {
                            boolean find = adminDAO.validateDuplicateUsername(admin);
                            if (find) {
                                url = url + "&msgErrorUsername=Error: ya existe un administrador con ese username.";
                                error = true;
                            }
                        } catch (Exception ex) {
                            url = url + "msgErrorUsername=Error: no se pudo llevar a cabo la instrucción.";
                            error = true;
                        }
                    }

                    /* comprobar email */
                    url = url + "&email=" + email;
                    if (email == null || email.trim().equals("")) {
                        url = url + "&msgErrorEmail=Error: Debe ingresar email.";
                        error = true;
                    } else {
                        /* comprobar email duplicado */
                        admin.setEmail(email);
                        try {
                            boolean find = adminDAO.validateDuplicateEmail(admin);
                            if (find) {
                                url = url + "&msgErrorEmail=Error: ya existe un administrador con ese email.";
                                error = true;
                            }
                        } catch (Exception ex) {
                            url = url + "&msgErrorEmail=Error: No se pudo llevar a cabo la instrucción.";
                            error = true;
                        }
                    }
                    /////////////////////////////////////
                    // EJECUTAR LÓGICA DE NEGOCIO
                    /////////////////////////////////////

                    /* buscar admin */
                    Admin reg = null;
                    try {
                        reg = adminDAO.findById(admin.getIdAdmin());
                    } catch (Exception ex) {
                        url = url + "&msgErrorId=Error: No se pudo llevar a cabo la instrucción findById).";
                        error = true;
                    }

                    if (chk != null) {
                        /* comprobar pwd1 */
                        if (pwd1 == null || pwd1.trim().equals("")) {
                            url = url + "&msgErrorPwd1=Error: Debe ingresar password.";
                        } else {
                            admin.setPwd1(pwd1);
                            /* comprobar pwd2 */
                            if (pwd2 == null || pwd2.trim().equals("")) {
                                url = url + "&msgErrorPwd2=Error: Debe ingresar password.";
                            } else {
                                admin.setPwd2(pwd2);
                                /* comprobar coincidencias */
                                if (!pwd1.equals(pwd2)) {
                                    url = url + "&msgErrorPwd1=Error: Las password's no coinciden.";
                                    error = true;
                                }
                                if (pwd1.length() < 6 || pwd2.length() < 6) {
                                    url = url + "&msgErrorPwd2=Error: La password debe poseer al menos 6 caracteres.";
                                    error = true;
                                }

                                if (!error) {
                                    admin.setPassword(StringMD.getStringMessageDigest(pwd1, StringMD.MD5));
                                    /* si admin fue encontrado */
                                    if (reg != null) {
                                        /* actualizar password */
                                        adminDAO.updatePassword(admin);
                                        url = url + "&msgOk=Registro actualizado exitosamente!";
                                    } else {
                                        request.setAttribute("msgErrorId", "Error: No se encontró el registro o ha sido eliminado mientras se actualizaba.");

                                    }
                                }
                            }
                        }
                    } else {
                        if (!error) {
                            /* si admin fue encontrado */
                            if (reg != null) {
                                /* no actualizar password */
                                try {
                                    adminDAO.update(admin);
                                    url = url + "&msgOk=Registro actualizado exitosamente!";
                                } catch (Exception ex) {
                                    url = url + "&msgErrorId=Error: No se pudo llevar a cabo la instrucción update.";
                                }
                            } else {
                                url = url + "&msgErrorId=Error: No se ha encontrado el registro.";
                            }
                        }
                    }

                    response.sendRedirect("/POINTEX1/AdminGetServlet?id=" + admin.getIdAdmin() + url);
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
