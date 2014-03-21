/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exchangeable;

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
@WebServlet(name = "ExchangeableGetServlet", urlPatterns = {"/ExchangeableGetServlet"})
public class ExchangeableGetServlet extends HttpServlet {

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

            ExchangeableDAO exDAO = new ExchangeableDAO();
            exDAO.setConexion(conexion);

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
                }

                /* obtener los valores de session y asignar valores a la jsp */
                request.setAttribute("userJsp", username);
                request.setAttribute("access", access);

                /////////////////////////////////////////
                // RECIBIR Y COMPROBAR PARAMETROS
                /////////////////////////////////////////
                try {
                    /* obtener atributos de PRG */
                    String tittle = request.getParameter("tittle");
                    String urlImage = request.getParameter("urlImage");
                    String points = request.getParameter("points");
                    String srequest = request.getParameter("request");
                    String reason = request.getParameter("reason");

                    /* obtener mensajes de PRG */
                    String msgErrorTittle = request.getParameter("msgErrorTittle");
                    String msgErrorUrlImage = request.getParameter("msgErrorUrlImage");
                    String msgErrorPoints = request.getParameter("msgErrorPoints");
                    String msgErrorReason = request.getParameter("msgErrorReason");
                    String msgErrorDup = request.getParameter("msgErrorDup");
                    String msgOk = request.getParameter("msgOk");

                    /* parametros de busqueda */
                    String sidExchangeable = request.getParameter("idExchangeable");

                    Exchangeable exchange = new Exchangeable();

                    boolean error = false;

                    /* comprobar id exchangeable */
                    if (sidExchangeable == null || sidExchangeable.trim().equals("")) {
                        error = true;
                    } else {
                        try {
                            exchange.setIdExchangeable(Integer.parseInt(sidExchangeable));
                        } catch (NumberFormatException n) {
                            error = true;
                        }
                    }

                    if (!error) {
                        /* buscar producto */
                        Exchangeable reg = exDAO.findByExchange(exchange);
                        if (reg != null) {
                            /* obtener atributos del dao */
                            request.setAttribute("idExchangeable", reg.getIdExchangeable());
                            request.setAttribute("namePlace", reg.getNamePlace());

                            //////////////////////////
                            /// COMPROBAR ATRIBUTOS
                            //////////////////////////

                            /* comprobar tittle */
                            if (msgErrorTittle == null || msgErrorTittle.trim().equals("")) {
                                request.setAttribute("tittle", reg.getTittle());
                            } else {
                                request.setAttribute("msgErrorTittle", msgErrorTittle);
                                request.setAttribute("tittle", tittle);
                            }

                            /* comprobar url image */
                            if (msgErrorUrlImage == null || msgErrorUrlImage.trim().equals("")) {
                                request.setAttribute("urlImage", reg.getUrlImage());
                            } else {
                                request.setAttribute("msgErrorUrlImage", msgErrorUrlImage);
                                request.setAttribute("urlImage", urlImage);
                            }

                            /* comprobar points */
                            if (msgErrorPoints == null || msgErrorPoints.trim().equals("")) {
                                request.setAttribute("points", reg.getPoints());
                            } else {
                                request.setAttribute("msgErrorPoints", msgErrorPoints);
                                request.setAttribute("points", points);
                            }

                            /* comprobar reason */
                            if (msgErrorReason == null || msgErrorReason.trim().equals("")) {
                                request.setAttribute("reason", reg.getReason());
                                request.setAttribute("request", reg.getRequest());
                            } else {
                                request.setAttribute("msgErrorReason", msgErrorReason);
                                request.setAttribute("reason", reason);
                                request.setAttribute("request", Integer.parseInt(srequest));
                            }

                            /* comprobar registro duplicado */
                            if (msgErrorDup == null || msgErrorDup.trim().equals("")) {
                            } else {
                                request.setAttribute("msgErrorDup", msgErrorDup);
                            }

                            /* comprobar mensaje de exito */
                            if (msgOk == null || msgOk.trim().equals("")) {
                                request.setAttribute("msg", "Se encontró el registro!");
                            } else {
                                request.setAttribute("msgOk", msgOk);
                            }
                        } else {
                            request.setAttribute("msgErrorFound", "Error: No se encontró el registro.");
                        }
                    }
                } catch (Exception parameterException) {
                } finally {
                    request.getRequestDispatcher("/exchangeable/exchangeableUpdate.jsp").forward(request, response);
                }
            } catch (Exception sesionException) {
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
