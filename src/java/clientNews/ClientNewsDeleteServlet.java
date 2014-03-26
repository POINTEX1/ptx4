/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clientNews;

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
@WebServlet(name = "ClientNewsDeleteServlet", urlPatterns = {"/ClientNewsDeleteServlet"})
public class ClientNewsDeleteServlet extends HttpServlet {

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

            ClientNewsDAO cnewsDAO = new ClientNewsDAO();
            cnewsDAO.setConexion(conexion);

            //////////////////////////////////////////
            // COMPROBAR SESSION
            /////////////////////////////////////////
            try {
                /* recuperar sesion */
                HttpSession session = request.getSession(false);

                /* obtener nivel de acceso acceso */
                int access = Integer.parseInt((String) session.getAttribute("access"));
                /* obtener nombre de usuario */
                String username = (String) session.getAttribute("username");

                /* comprobar permisos de usuario */
                if (access != 555 && access != 777) {
                    /* ACCESO PROHIBIDO */
                    request.getRequestDispatcher("/ForbiddenServlet").forward(request, response);
                } else {
                    /* ACCESO PERMITIDO */

                    /* asginar nombre de usuario */
                    request.setAttribute("userJsp", username);
                    /* asignar nivel de acceso */
                    request.setAttribute("access", access);

                    /////////////////////////////////////////
                    // RECIBIR Y COMPROBAR PARAMETOS
                    /////////////////////////////////////////

                    /* obtener parametro para eliminar única fila */
                    String btnDelRow = request.getParameter("btnDelRow");

                    /* obtener parametro para eliminar varias filas */
                    String btnDelCol = request.getParameter("btnDelCol");

                    /* instanciar point */
                    ClientNews cnews = new ClientNews();

                    /* instanciar url */
                    String url = "?target=main";

                    //////////////////////////////////////////
                    // ELIMINAR POR REGISTRO
                    //////////////////////////////////////////
                    if (btnDelRow != null) {
                        /* recibir parametros */
                        try {
                            cnews.setIdClientNews(Integer.parseInt(request.getParameter("idClientNews")));
                            try {
                                cnewsDAO.delete(cnews.getIdClientNews());
                                url += "&msgDel=Un registro ha sido eliminado.";
                            } catch (Exception referenceException) {
                                referenceException.printStackTrace();
                                url += "&msgErrorConstraint=Error de restricción: No puede eliminar el registro, existen dependencias asociadas.";
                            }
                        } catch (NumberFormatException n) {
                            n.printStackTrace();
                        }
                    }

                    //////////////////////////////////////////
                    // ELIMINAR VARIOS REGISTOS
                    //////////////////////////////////////////
                    if (btnDelCol != null) {
                        /* recibir parametros del array */
                        String[] outerArray = request.getParameterValues("chk");
                        int cont = 0; //contador de registros eliminados
                        int i = 0; //puntero del array
                        try {
                            while (outerArray[i] != null) {
                                try {
                                    cnewsDAO.delete(Integer.parseInt(outerArray[i]));
                                    cont++;
                                } catch (Exception referenceException) {
                                    url += "&msgErrorConstraint=Error de restricción: No puede eliminar el registro, existen dependencias asociadas.";
                                }
                                i++;
                            }
                        } catch (Exception ex) {
                        }
                        if (cont == 1) {
                            url += "&msgDel=Un registro ha sido eliminado.";
                        } else if (cont > 1) {
                            url += "&msgDel=" + cont + " registros han sido eliminados";
                        }
                    }

                    /* send redirect */
                    response.sendRedirect("ClientNewsMainServlet" + url);

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
