/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parameter;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
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
 * @author alexander
 */
@WebServlet(name = "ParameterGetServlet", urlPatterns = {"/ParameterGetServlet"})
public class ParameterGetServlet extends HttpServlet {

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

            ParameterDAO parameterDAO = new ParameterDAO();
            parameterDAO.setConexion(conexion);


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


                    Parameter reg = null;
                    try {


                        ////////////////////////////////////////
                        // OBTENER REGISTROS DE DAO's
                        ////////////////////////////////////////

                        Collection<Parameter> listParameter = parameterDAO.getAll();

                        if (listParameter.size() > 0) {
                            for (Parameter aux : listParameter) {
                                reg = aux;
                                request.setAttribute("waitingCard", reg.getWaitingCard());
                                request.setAttribute("numberEvent", reg.getNumberEvent());
                                request.setAttribute("numberPromo", reg.getNumberPromo());
                                request.setAttribute("banerCentralEvent", reg.getBanerCentralEvent());
                                request.setAttribute("banerCentralPromo", reg.getBanerCentralPromo());
                                request.setAttribute("banerCentralExchangeable", reg.getBanerCentralExchangeable());
                                request.setAttribute("banerCentralVip", reg.getBanerCentralVip());
                                request.setAttribute("banerCentralAboutUs", reg.getBanerCentralAboutUs());
                                request.setAttribute("banerTopEvent", reg.getBanerTopEvent());
                                request.setAttribute("banerTopPromo", reg.getBanerTopPromo());
                                request.setAttribute("banerTopMyPlace", reg.getBanerTopMyPlace());
                                request.setAttribute("banerTopFindPlace", reg.getBanerTopFindPlace());
                                request.setAttribute("banerTopConfiguration", reg.getBanerTopConfiguration());
                                request.setAttribute("banerTopSocialNetworks", reg.getBanerTopSocialNetworks());
                                request.setAttribute("msg", "registros encontrados en la base de datos.");
                            }
                        } else {
                            request.setAttribute("msg", "No hay registros encontrado en la base de datos.");
                        }



                    } catch (Exception parameterException) {
                    } finally {
                        request.getRequestDispatcher("/parameter/parameterUpdate.jsp").forward(request, response);
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
