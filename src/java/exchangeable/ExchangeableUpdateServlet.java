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
@WebServlet(name = "ExchangeableUpdateServlet", urlPatterns = {"/ExchangeableUpdateServlet"})
public class ExchangeableUpdateServlet extends HttpServlet {

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

            ExchangeableDAO exDAO = new ExchangeableDAO();
            exDAO.setConexion(conexion);

            /////////////////////////
            // COMPROBAR SESSION
            /////////////////////////
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

                    /////////////////////////////////////
                    // RECIBIR Y COMPROBAR PARAMETROS
                    /////////////////////////////////////

                    String sidExchangeable = request.getParameter("idExchangeable");
                    String tittle = request.getParameter("tittle");
                    String urlImage = request.getParameter("urlImage");
                    String spoints = request.getParameter("points");
                    String srequest = request.getParameter("exchangeRequest");
                    String reason = request.getParameter("reason");

                    String url = "?redirect=ok";
                    url += "&idExchangeable=" + sidExchangeable;
                    url += "&tittle=" + tittle;
                    url += "&urlImage=" + urlImage;
                    url += "&points=" + spoints;
                    url += "&reason=" + reason;
                    url += "&request=" + srequest;

                    /* instanciar exchangeable */
                    Exchangeable exchange = new Exchangeable();

                    /* flag de error */
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

                    /* comprobar tittle */
                    if (tittle == null || tittle.trim().equals("")) {
                        url += "&msgErrorTittle=Debe ingresar un título para el producto canjeable.";
                        error = true;
                    } else {
                        exchange.setTittle(tittle);
                    }

                    /* comprobar url image */
                    if (urlImage == null || urlImage.trim().equals("")) {
                        url += "&msgErrorUrlImage=Debe ingresar url de imagen.";
                        error = true;
                    } else {
                        exchange.setUrlImage(urlImage);
                    }

                    /* comprobar points */
                    if (spoints == null || spoints.trim().equals("")) {
                        url += "&msgErrorPoints=Debe ingresar puntos requeridos para este producto.";
                        error = true;
                    } else {
                        try {
                            exchange.setPoints(Integer.parseInt(spoints));
                        } catch (NumberFormatException n) {
                            url += "&msgErrorPoints=Los puntos deben ser numéricos.";
                            error = true;
                        }
                    }

                    /* comprobar request */
                    if (srequest == null || srequest.trim().equals("")) {
                        error = true;
                    } else {
                        try {
                            exchange.setRequest(Integer.parseInt(srequest));
                        } catch (NumberFormatException n) {
                            error = true;
                        }
                    }

                    /* comprobar reason */
                    if ((reason == null || reason.trim().equals("")) && exchange.getRequest() == 2) {
                        url += "&msgErrorReason=Debe ingresar razón de rechazo.";
                        error = true;
                    } else {
                        exchange.setReason(reason);
                    }

                    /////////////////////////
                    // LOGICA DE NEGOCIO
                    /////////////////////////

                    /* verificar registro duplicado */
                    try {
                        boolean find = exDAO.validateDuplicate(exchange);
                        if (find) {
                            url += "&msgErrorDup=Error: ya existe este producto canjeable.";
                            error = true;
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    /* actualizar registro */
                    if (!error) {
                        try {
                            exDAO.update(exchange);
                            url += "&msgOk=Registro actualizado exitosamente!";
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    /* send redirect */
                    response.sendRedirect("ExchangeableGetServlet" + url);
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
