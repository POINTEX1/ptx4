/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package city;

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
            //////////////////////////////////////////
            // ESTABLECER CONEXION
            /////////////////////////////////////////

            conexion = ds.getConnection();

            CityDAO cityDAO = new CityDAO();
            cityDAO.setConexion(conexion);

            //////////////////////////////////////////
            // COMPROBAR SESSION
            /////////////////////////////////////////
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

                /////////////////////////////////////////
                // RECIBIR Y COMPROBAR PARAMETROS
                //////////////////////////////////////// 

                /* obtener atributos de PRG */
                String nameCity = request.getParameter("nameCity");

                /* obtener mensajes de PRG */
                String msgErrorId = request.getParameter("msgErroId");
                String msgErrorNameCity = request.getParameter("msgErrorNameCity");
                String msgErrorDup = request.getParameter("msgErrorDup");
                String msgOk = request.getParameter("msgOk");

                /* obtener atributos de busqueda */
                String sidCity = request.getParameter("idCity");

                City city = new City();

                /* comprobar id city */
                if (sidCity == null || sidCity.trim().equals("")) {
                    request.setAttribute("msgErrorId", "Error al recibir id Ciudad.");
                } else {
                    city.setIdCity(Integer.parseInt(sidCity));
                    /* comprobar existencia */
                    City aux = cityDAO.findbyIdCity(city);
                    if (aux != null) {
                        /* obtener atributos del dao */
                        request.setAttribute("idCity", aux.getIdCity());

                        /* comprobar msgErrorNameCity */
                        if (msgErrorNameCity == null || msgErrorNameCity.trim().equals("")) {
                            request.setAttribute("nameCity", aux.getNameCity());
                        } else {
                            request.setAttribute("nameCity", nameCity);
                            request.setAttribute("msgErrorNameCity", msgErrorNameCity);
                        }

                        /* comprobar msgErrorDup */
                        if (msgErrorDup == null || msgErrorDup.trim().equals("")) {
                        } else {
                            request.setAttribute("msgErrorDup", msgErrorDup);
                        }

                        if (msgOk == null || msgOk.trim().equals("")) {
                            request.setAttribute("msg", "Se encontró el registro!");
                        } else {
                            request.setAttribute("msgOk", msgOk);
                        }

                    } else {
                        request.setAttribute("msgErrorFound", "Error: No se encontró el registro.");
                    }
                }

                request.getRequestDispatcher("/city/cityUpdate.jsp").forward(request, response);

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
