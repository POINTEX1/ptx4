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
@WebServlet(name = "AdminAddServlet", urlPatterns = {"/AdminAddServlet"})
public class AdminAddServlet extends HttpServlet {

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

            AdminDAO adminDAO = new AdminDAO();
            adminDAO.setConexion(conexion);

            ///////////////////////
            // COMPROBAR SESSION
            ///////////////////////
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
                    request.setAttribute("userJsp", user); // username
                    request.setAttribute("access", access); // nivel de acceso

                    ////////////////////////////////////
                    // RECIBIR Y COMPROBAR PARAMETROS
                    ////////////////////////////////////

                    /* obtener parametros de la vista */
                    String username = request.getParameter("username");
                    String email = request.getParameter("email");
                    String type = request.getParameter("type_admin");
                    String pwd1 = request.getParameter("pwd1");
                    String pwd2 = request.getParameter("pwd2");

                    /* instanciar url */
                    String url = "?target=get";

                    url += "&redirect=ok";
                    url += "&username=" + username;
                    url += "&email=" + email;
                    url += "&type=" + type;

                    /* instanciar admin */
                    Admin admin = new Admin();

                    boolean error = false;

                    /* establecer atributos a la vista */
                    request.setAttribute("username", username);
                    request.setAttribute("email", email);
                    request.setAttribute("type", Integer.parseInt(type));
                    request.setAttribute("pwd1", pwd1);
                    request.setAttribute("pwd2", pwd2);

                    /* comprobar username */
                    if (username == null || username.trim().equals("")) {
                        url += "&msgErrorUsername=Debe ingresar username.";
                        error = true;
                    } else {
                        /* comprobar username duplicado */
                        admin.setUsername(username);
                        try {
                            boolean find = adminDAO.validateDuplicateUsername(admin);
                            if (find) {
                                url += "&msgErrorUsername=El Username ingresado ya se encuentra registrado.";
                                error = true;
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            error = true;
                        }
                    }
                    /* comprobar email */
                    if (email == null || email.trim().equals("")) {
                        url += "&msgErrorEmail=Debe ingresar Email.";
                        error = true;
                    } else {
                        /* comprobar email duplicado */
                        admin.setEmail(email);
                        try {
                            boolean find = adminDAO.validateDuplicateEmail(admin);
                            if (find) {
                                url += "&msgErrorEmail=El Email ingresado ya se encuentra registrado.";
                                error = true;
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            error = true;
                        }
                    }

                    /* comprobar type */
                    if (type == null || type.trim().equals("")) {
                        error = true;
                    } else {
                        admin.setTypeAdmin(Integer.parseInt(type));
                    }
                    /* comprobar pwd1 */
                    if (pwd1 == null || pwd1.trim().equals("")) {
                        url += "&msgErrorPwd1=Debe ingresar password.";
                        error = true;
                    } else {
                        admin.setPwd1(pwd1);
                        /* comprobar pwd2 */
                        if (pwd2 == null || pwd2.trim().equals("")) {
                            url += "&msgErrorPwd1=Debe ingresar password.";
                            error = true;
                        } else {
                            admin.setPwd2(pwd2);
                            /* comprobar coincidencias */
                            if (!pwd1.equals(pwd2)) {
                                url += "&msgErrorPwd1=Las passwords no coinciden.";
                                error = true;
                            }
                            /* comprobar largo de caracteres */
                            if (pwd1.length() < 6 || pwd2.length() < 6) {
                                url += "&msgErrorPwd2=La password debe contener al menos 6 caracteres.";
                                error = true;
                            }
                            /*encriptar password en hash MD5 */
                            if (!error) {
                                admin.setPassword(StringMD.getStringMessageDigest(pwd1, StringMD.MD5));
                            }
                        }
                    }

                    ////////////////////////
                    // LOGICA DE NEGOCIO
                    ////////////////////////
                    if (!error) {
                        try {
                            /* insertar registro */
                            adminDAO.insert(admin);
                            url += "&msgOk=Registro ingresado exitosamente.";
                        } catch (Exception duplicateException) {
                            url += "&msgErrorDup=Registro duplicado, verifique campos ingresados.";
                        }
                    }

                    /* send redirect */
                    response.sendRedirect("AdminGetAddServlet" + url);
                }
            } catch (Exception sesionException) {
                /* enviar a la vista de login */
                System.err.println("no ha iniciado session");
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
