/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package point;

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
@WebServlet(name = "PointUpdateServlet", urlPatterns = {"/PointUpdateServlet"})
public class PointUpdateServlet extends HttpServlet {

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

        /* establecer set de caracteres */
        request.setCharacterEncoding("UTF-8");

        /* definir conexion */
        Connection conexion = null;

        //////////////////////////
        // ESTABLECER CONEXION
        //////////////////////////
        try {
            conexion = ds.getConnection();

            PointDAO pointDAO = new PointDAO();
            pointDAO.setConexion(conexion);

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
                if (access != 555 && access != 777) {
                    request.getRequestDispatcher("/ForbiddenServlet").forward(request, response);
                } else {

                    /* obtener los valores de session y asignar valores a la jsp */
                    request.setAttribute("userJsp", username);
                    request.setAttribute("access", access);

                    /////////////////////////////////////////
                    // RECIBIR Y COMPROBAR PARAMETROS
                    ////////////////////////////////////////

                    String sidPlace = request.getParameter("idPlace");
                    String namePlace = request.getParameter("namePlace");
                    String srut = request.getParameter("rut");
                    String spoints = request.getParameter("points");

                    /* instanciar puntos */
                    Point point = new Point();

                    /* flag de error */
                    boolean error = false;

                    /* instanciar string url */
                    String url = "?redirect=ok";
                    url += "&idPlace=" + sidPlace;
                    url += "&namePlace=" + namePlace;
                    url += "&rut=" + srut;
                    url += "&points=" + spoints;

                    /* comprobar id place */
                    if (sidPlace == null || sidPlace.trim().equals("")) {
                        error = true;
                    } else {
                        try {
                            point.setIdPlace(Integer.parseInt(sidPlace));
                        } catch (NumberFormatException n) {
                            error = true;
                        }
                    }

                    /* comprobar rut */
                    if (srut == null || srut.trim().equals("")) {
                        error = true;
                    } else {
                        try {
                            point.setRut(Integer.parseInt(srut));
                        } catch (NumberFormatException n) {
                            error = true;
                        }
                    }

                    /* comprobar points */
                    if (spoints == null || spoints.trim().equals("")) {
                        url += "&msgErrorPoints=Debe ingresar puntos.";
                        error = true;
                    } else {
                        try {
                            point.setPoints(Integer.parseInt(spoints));
                            if (point.getPoints() < 0) {
                                url += "&msgErrorPoints=Los puntos no pueden ser negativos.";
                                error = true;
                            }
                        } catch (NumberFormatException n) {
                            url += "&msgErrorPoints=Los puntos deben ser numéricos.";
                            error = true;
                        }
                    }

                    ///////////////////////
                    // LOGICA DE NEGOCIO
                    ///////////////////////

                    if (!error) {
                        /* comprobar existencia */
                        try {
                            pointDAO.update(point);
                            url += "&msgOk=Registro actualizado exitosamente!";
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    
                    /* send redirect */
                    response.sendRedirect("PointGetServlet" + url);
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
