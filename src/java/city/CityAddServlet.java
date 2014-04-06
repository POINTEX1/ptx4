/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package city;

import Helpers.Format;
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
@WebServlet(name = "CityAddServlet", urlPatterns = {"/CityAddServlet"})
public class CityAddServlet extends HttpServlet {

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
            /////////////////////////
            // ESTABLECER CONEXION
            /////////////////////////
            conexion = ds.getConnection();

            CityDAO cityDAO = new CityDAO();
            cityDAO.setConexion(conexion);

            ////////////////////////
            // COMPROBAR SESSION
            ////////////////////////
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

                String namecity = request.getParameter("nameCity");
                String btnAdd = request.getParameter("add");

                /* instanciar ciudad */
                City city = new City();

                /* instanciar lista de mensajes */
                Collection<Message> msgList = new ArrayList<Message>();

                if (btnAdd == null) {
                    request.setAttribute("msg", "Ingrese una ciudad.");
                } else {
                    if (namecity == null || namecity.trim().equals("")) {
                        request.setAttribute("msgErrorNameCity", true);
                        msgList.add(MessageList.addMessage("Debe ingresar nombre de ciudad."));
                    } else {
                        city.setNameCity(Format.capital(namecity));

                        /* comprobar ciudad duplicada */
                        boolean find = false;
                        try {
                            find = cityDAO.validateDuplicateName(city);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        if (find) {
                            request.setAttribute("msgErrorDup", true);
                            msgList.add(MessageList.addMessage("Ya existe una ciudad con este nombre."));
                        } else {
                            /* insertar registro en DB */
                            try {
                                cityDAO.insert(city);
                                request.setAttribute("msgOk", "Registro ingresado exitosamente.");
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                }

                /* establecer lista de mensajes a la peticion */
                if (!msgList.isEmpty()) {
                    request.setAttribute("msgList", msgList);
                }

                /* establecer ciudad a la peticion */
                request.setAttribute("city", city);

                /* despachar a la vista */
                request.getRequestDispatcher("/city/cityAdd.jsp").forward(request, response);

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
