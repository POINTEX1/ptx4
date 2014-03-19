/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package placeNews;

import Helpers.Format;
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
import place.Place;
import place.PlaceDAO;

/**
 *
 * @author alexander
 */
@WebServlet(name = "PlaceNewsGetServlet", urlPatterns = {"/PlaceNewsGetServlet"})
public class PlaceNewsGetServlet extends HttpServlet {

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

            PlaceNewsDAO pnewsDAO = new PlaceNewsDAO();
            pnewsDAO.setConexion(conexion);

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

                    /////////////////////////////////////////
                    // DECLARAR VARIABLES DE INSTANCIA
                    /////////////////////////////////////////

                    PlaceNews reg = null;

                    try {
                        /////////////////////////////////////////
                        // RECIBIR Y COMPROBAR PARAMETROS
                        /////////////////////////////////////////

                        String sidPlaceNews = request.getParameter("idPlaceNews");
                        String sidPlace = request.getParameter("idPlace");

                        boolean error = false;

                        PlaceNews pnews = new PlaceNews();

                        /* comprobar id place */
                        if (sidPlaceNews == null || sidPlaceNews.trim().equals("")) {
                            request.setAttribute("msgErrorIdPlaceNews", "Error al recibir id Noticia.");
                            error = true;
                        } else {
                            try {
                                pnews.setIdPlaceNews(Integer.parseInt(sidPlaceNews));
                            } catch (NumberFormatException n) {
                                request.setAttribute("msgErrorIdPlaceNews", "Error: El id de noticia no es numérico.");
                            }
                        }

                        /* comprobar id place */
                        if (sidPlace == null || sidPlace.trim().equals("")) {
                            request.setAttribute("msgErrorIdPlace", "Error al recibir id Plaza.");
                            error = true;
                        } else {
                            try {
                                pnews.setIdPlace(Integer.parseInt(sidPlace));
                            } catch (NumberFormatException n) {
                                request.setAttribute("msgErrorIdPlace", "Error: El id de plaza no es numérico.");
                            }
                        }

                        if (!error) {
                            PlaceNews aux = pnewsDAO.findByPlaceNews(pnews);
                            if (aux != null) {
                                reg = aux;
                                reg.setDateBegin(Format.dateYYYYMMDD(reg.getDateBegin()));
                                reg.setDateEnd(Format.dateYYYYMMDD(reg.getDateEnd()));
                                request.setAttribute("msgOk", "Se encontró el registro!");
                            } else {
                                request.setAttribute("msgErrorFound", "Error: No se encontró el registro.");
                            }
                        }

                        /* obtener lista de lugares */
                        try {
                            Collection<Place> listPlace = placeDAO.getAll();
                            request.setAttribute("listPlace", listPlace);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                        /* enviar datos del objeto a la vista */
                        request.setAttribute("pnews", reg);

                    } catch (Exception parameterException) {
                    } finally {
                        request.getRequestDispatcher("/placeNews/placeNewsUpdate.jsp").forward(request, response);
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
