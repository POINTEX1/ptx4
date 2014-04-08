/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package point;

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

        //////////////////////////
        // ESTABLECER CONEXION
        //////////////////////////
        try {
            conexion = ds.getConnection();

            PointDAO pointDAO = new PointDAO();
            pointDAO.setConexion(conexion);

            PlaceDAO placeDAO = new PlaceDAO();
            placeDAO.setConexion(conexion);

            ///////////////////////
            // COMPROBAR SESSION
            ///////////////////////
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

                    ///////////////////////////////////
                    // RECIBIR Y COMPROBAR PARAMETROS
                    ///////////////////////////////////

                    /* obtener atributos de PRG */
                    String redirect = request.getParameter("redirect");
                    String sidPlace = request.getParameter("idPlace");
                    String srut = request.getParameter("rut");
                    String points = request.getParameter("points");

                    /* obtner mensajes de PRG */
                    String msgErrorPoints = request.getParameter("msgErrorPoints");
                    String msgOk = request.getParameter("msgOk");

                    /* instanciar lista de mensajes */
                    Collection<Message> msgList = new ArrayList<Message>();

                    /* establecer id place */
                    int idPlace = 0;
                    try {
                        idPlace = Integer.parseInt(sidPlace);
                    } catch (NumberFormatException n) {
                    }

                    /* establecer rut */
                    int rut = 0;
                    try {
                        rut = Integer.parseInt(srut);
                    } catch (NumberFormatException n) {
                    }

                    /* obtener valores de point */
                    try {
                        Point reg = pointDAO.findByPoint(idPlace, rut);

                        if (reg != null) {
                            /* establecer atributos de reg */
                            request.setAttribute("idPlace", reg.getIdPlace());
                            request.setAttribute("namePlace", reg.getNamePlace());
                            request.setAttribute("rut", reg.getRut());
                            request.setAttribute("dv", reg.getDv());

                            /* comprobar redirect */
                            if (redirect == null || redirect.trim().equals("")) {
                                /* establecer atributos de reg */
                                request.setAttribute("points", reg.getPoints());
                            } else {
                                /* establecer atributos de PRG */
                                request.setAttribute("points", points);
                            }

                            ////////////////////////
                            // COMPROBAR ERRORES
                            ////////////////////////

                            /* comprobar points */
                            if (msgErrorPoints == null || msgErrorPoints.trim().equals("")) {
                            } else {
                                request.setAttribute("msgErrorPoints", true);
                                msgList.add(MessageList.addMessage(msgErrorPoints));
                            }

                            /* comprobar mensaje de exito */
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

                    /* establecer lista de mensajes */
                    if (!msgList.isEmpty()) {
                        request.setAttribute("msgList", msgList);
                    }

                    /* despachar a la vista */
                    request.getRequestDispatcher("/point/pointUpdate.jsp").forward(request, response);
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
