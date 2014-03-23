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
                    // RECIBIR Y COMPROBAR PARAMETROS
                    /////////////////////////////////////////

                    /* obtener atributos de PRG */
                    String tittle = request.getParameter("tittle");
                    String urlImage = request.getParameter("urlImage");
                    String dateBegin = request.getParameter("dateBegin");
                    String dateEnd = request.getParameter("dateEnd");

                    /* obtener mensajes de PRG */
                    String msgOk = request.getParameter("msgOk");
                    String msgErrorTittle = request.getParameter("msgErrorTittle");
                    String msgErrorDetails = request.getParameter("msgErrorDetails");
                    String msgErrorUrlImage = request.getParameter("msgErrorUrlImage");
                    String msgErrorDate = request.getParameter("msgErrorDate");
                    String msgErrorDup = request.getParameter("msgErrorDup");

                    /* obtener parametros de busqueda */
                    String sidPlaceNews = request.getParameter("idPlaceNews");

                    boolean error = false;

                    PlaceNews pnews = new PlaceNews();

                    /* comprobar id place */
                    if (sidPlaceNews == null || sidPlaceNews.trim().equals("")) {
                        error = true;
                    } else {
                        try {
                            pnews.setIdPlaceNews(Integer.parseInt(sidPlaceNews));
                        } catch (NumberFormatException n) {
                            error = true;
                        }
                    }

                    if (!error) {
                        /* buscar registro */
                        try {
                            PlaceNews reg = pnewsDAO.findByPlaceNews(pnews.getIdPlaceNews());
                            if (reg != null) {
                                /* obtener registros del dao */
                                request.setAttribute("idPlaceNews", reg.getIdPlaceNews());
                                request.setAttribute("namePlace", reg.getNamePlace());
                                request.setAttribute("newsType", reg.getNewsType());

                                reg.setDateBegin(Format.dateYYYYMMDD(reg.getDateBegin()));
                                reg.setDateEnd(Format.dateYYYYMMDD(reg.getDateEnd()));

                                ///////////////////////////
                                // COMPROBAR ERRORES
                                ///////////////////////////

                                /* comprobar tittle */
                                if (msgErrorTittle == null || msgErrorTittle.trim().equals("")) {
                                    request.setAttribute("tittle", reg.getTittle());
                                } else {
                                    request.setAttribute("MsgErrorTittle", msgErrorTittle);
                                }

                                /* comprobar details */
                                if (msgErrorDetails == null || msgErrorDetails.trim().equals("")) {
                                    request.setAttribute("details", reg.getDetails());
                                } else {
                                    request.setAttribute("msgErrorDetails", msgErrorDetails);
                                }

                                /* comprobar urlImage */
                                if (msgErrorUrlImage == null || msgErrorUrlImage.trim().equals("")) {
                                    request.setAttribute("urlImage", reg.getUrlImage());
                                } else {
                                    request.setAttribute("msgErrorUrlImage", urlImage);
                                }

                                /* comprobar fecha */
                                if (msgErrorDate == null || msgErrorDate.trim().equals("")) {
                                    request.setAttribute("dateBegin", reg.getDateBegin());
                                    request.setAttribute("dateEnd", reg.getDateEnd());
                                } else {
                                    request.setAttribute("msgErrorDate", msgErrorDate);
                                    request.setAttribute("dateBegin", dateBegin);
                                    request.setAttribute("dateEnd", dateEnd);
                                }

                                /* comprobar duplicaciones */
                                if (msgErrorDup == null || msgErrorDup.trim().equals("")) {
                                } else {
                                    request.setAttribute("msgErrorDup", msgErrorDup);
                                    request.setAttribute("tittle", tittle);
                                    request.setAttribute("dateBegin", dateBegin);
                                    request.setAttribute("dateEnd", dateEnd);
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
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    /* obtener lista de lugares */
                    try {
                        Collection<Place> listPlace = placeDAO.getAll();
                        request.setAttribute("listPlace", listPlace);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    request.getRequestDispatcher("/placeNews/placeNewsUpdate.jsp").forward(request, response);
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
