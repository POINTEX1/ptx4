/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package city;

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
@WebServlet(name = "CityGetServlet", urlPatterns = {"/CityGetServlet"})
public class CityGetServlet extends HttpServlet {

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
            //////////////////////////
            // ESTABLECER CONEXION
            //////////////////////////

            conexion = ds.getConnection();

            CityDAO cityDAO = new CityDAO();
            cityDAO.setConexion(conexion);

            //////////////////////////
            // COMPROBAR SESSION
            //////////////////////////
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

                /////////////////////////////////////
                // RECIBIR Y COMPROBAR PARAMETROS
                /////////////////////////////////////

                /* obtener atributos de PRG */
                String nameCity = request.getParameter("nameCity");

                /* obtener mensajes de PRG */
                String msgErrorNameCity = request.getParameter("msgErrorNameCity");
                String msgErrorDup = request.getParameter("msgErrorDup");
                String msgOk = request.getParameter("msgOk");

                /* obtener atributos de busqueda */
                String sidCity = request.getParameter("idCity");

                /* instanciar ciudad */
                City city = new City();

                /* intanciar lista de mensajes */
                Collection<Message> msgList = new ArrayList<Message>();

                /* comprobar id city */
                if (sidCity == null || sidCity.trim().equals("")) {
                } else {
                    /* establecer id */
                    int idCity = 0;
                    try {
                        idCity = Integer.parseInt(sidCity);
                    } catch (NumberFormatException n) {
                    }
                    city.setIdCity(idCity);

                    /* comprobar existencia */
                    City aux = null;
                    try {
                        aux = cityDAO.findbyIdCity(city);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    if (aux != null) {
                        /* obtener atributos del dao */
                        request.setAttribute("idCity", aux.getIdCity());

                        /* comprobar msgErrorNameCity */
                        if (msgErrorNameCity == null || msgErrorNameCity.trim().equals("")) {
                            request.setAttribute("nameCity", aux.getNameCity());
                        } else {
                            request.setAttribute("nameCity", nameCity);
                            request.setAttribute("msgErrorNameCity", true);
                            msgList.add(MessageList.addMessage(msgErrorNameCity));
                        }

                        /* comprobar msgErrorDup */
                        if (msgErrorDup == null || msgErrorDup.trim().equals("")) {
                        } else {
                            request.setAttribute("msgErrorDup", msgErrorDup);
                            msgList.add(MessageList.addMessage(msgErrorDup));
                        }

                        /* comprobar mensaje de exito */
                        if (msgOk == null || msgOk.trim().equals("")) {
                            request.setAttribute("msg", "Se encontró el registro!");
                        } else {
                            request.setAttribute("msgOk", msgOk);
                        }

                    } else {
                        request.setAttribute("msgErrorFound", true);
                        msgList.add(MessageList.addMessage("El registro no ha sido encotrado."));
                    }
                }

                /* establecer lista de mensaje a la peticion */
                if (!msgList.isEmpty()) {
                    request.setAttribute("msgList", msgList);
                }

                /* despachar a la vista */
                request.getRequestDispatcher("/city/cityUpdate.jsp").forward(request, response);

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
