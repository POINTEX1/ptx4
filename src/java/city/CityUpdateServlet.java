/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package city;

import Helpers.Format;
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
@WebServlet(name = "CityUpdateServlet", urlPatterns = {"/CityUpdateServlet"})
public class CityUpdateServlet extends HttpServlet {

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

                //////////////////////////////////////
                // RECIBIR Y COMPROBAR PARAMETROS
                //////////////////////////////////////

                String sidCity = request.getParameter("idCity");
                String nameCity = request.getParameter("nameCity");

                /* instanciar ciudad */
                City city = new City();

                boolean error = false;

                /* instanciar url */
                String url = "?a=target";

                /* comprobar id city */
                url += "&idCity=" + sidCity;
                if (sidCity == null || sidCity.trim().equals("")) {
                    error = true;
                } else {
                    try {
                        city.setIdCity(Integer.parseInt(sidCity));
                    } catch (NumberFormatException n) {
                        error = true;
                    }

                }
                /* comprobar name city */
                url += "&nameCity=" + nameCity;
                if (nameCity == null || nameCity.trim().equals("")) {
                    url += "&msgErrorNameCity=Debe ingresar nombre Ciudad.";
                    error = true;
                } else {
                    city.setNameCity(Format.capital(nameCity));
                }

                if (!error) {
                    /* comprobar ciudad duplicada */
                    boolean find = cityDAO.validateDuplicateName(city);
                    if (find) {
                        url += "&msgErrorDup=El nombre de la ciudad ingresada ya existe.";
                    } else {
                        /* actualizar */
                        try {
                            cityDAO.update(city);
                            url += "&msgOk=Registro actualizado exitosamente!";
                        } catch (Exception ex) {
                        }
                    }
                }
                /* send redirect */
                response.sendRedirect("CityGetServlet" + url);

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
