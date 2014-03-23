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
import place.PlaceDAO;

/**
 *
 * @author patricio alberto
 */
@WebServlet(name = "PointGetServlet", urlPatterns = {"/PointGetServlet"})
public class PointGetServlet extends HttpServlet {

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
            /////////////////////////////////////////

            conexion = ds.getConnection();

            PointDAO pointDAO = new PointDAO();
            pointDAO.setConexion(conexion);

            PlaceDAO placeDAO = new PlaceDAO();
            placeDAO.setConexion(conexion);

            //////////////////////////////////////////
            // COMPROBAR SESSION
            /////////////////////////////////////////
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

                    try {
                        ///////////////////////////////////////
                        // RECIBIR Y COMPROBAR PARAMETROS
                        ///////////////////////////////////////

                        /* obtener atributos de PRG */
                        String points = request.getParameter("points");

                        /* obtner mensajes de PRG */
                        String msgErrorPoints = request.getParameter("msgErrorPoints");
                        String msgOk = request.getParameter("msgOk");

                        /* obtener parametros de busqueda */
                        String sidPlace = request.getParameter("idPlace");
                        String srut = request.getParameter("rut");

                        boolean error = false;

                        Point point = new Point();

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

                        if (!error) {
                            /* obtener valores de point */
                            try {
                                Point reg = pointDAO.findByPoint(point);
                                if (reg != null) {
                                    /* obtener atributos del dao */
                                    request.setAttribute("idPlace", reg.getIdPlace());
                                    request.setAttribute("namePlace", reg.getNamePlace());
                                    request.setAttribute("rut", reg.getRut());
                                    request.setAttribute("dv", reg.getDv());

                                    //////////////////////////////
                                    // COMPROBAR VALIDACIONES
                                    //////////////////////////////

                                    /* comprobar points */
                                    if (msgErrorPoints == null || msgErrorPoints.trim().equals("")) {
                                        request.setAttribute("points", reg.getPoints());
                                    } else {
                                        request.setAttribute("msgErrorPoints", msgErrorPoints);
                                        request.setAttribute("points", points);
                                    }

                                    /* comprobar mensaje de exito */
                                    if (msgOk == null || msgOk.trim().equals("")) {
                                        request.setAttribute("msg", "Se encontró el registro!");
                                    } else {
                                        request.setAttribute("msgOk", msgOk);
                                    }

                                } else {
                                    request.setAttribute("msgErrorFound", "Error: No se encontró el Evento.");
                                }
                            } catch (Exception ex) {
                                request.setAttribute("msgErrorFound", "Ocurrió un error inesperado.");
                            }
                        }
                    } catch (Exception parameterException) {
                        parameterException.printStackTrace();
                    } finally {
                        request.getRequestDispatcher("/point/pointUpdate.jsp").forward(request, response);
                    }
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
