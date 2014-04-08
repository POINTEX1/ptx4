/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package placeNews;

import Helpers.Format;
import Helpers.Message;
import Helpers.MessageList;
import java.io.IOException;
import java.io.PrintWriter;
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

        /////////////////////////
        // ESTABLECER CONEXION
        /////////////////////////
        try {
            conexion = ds.getConnection();

            PlaceNewsDAO pnewsDAO = new PlaceNewsDAO();
            pnewsDAO.setConexion(conexion);

            PlaceDAO placeDAO = new PlaceDAO();
            placeDAO.setConexion(conexion);

            ////////////////////////
            // COMPROBAR SESSION
            ////////////////////////
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
                    String redirect = request.getParameter("redirect");
                    String sidPlaceNews = request.getParameter("idPlaceNews");
                    String tittle = request.getParameter("tittle");
                    String urlImage = request.getParameter("urlImage");
                    String newsType = request.getParameter("newsType");
                    String details = request.getParameter("details");
                    String dateBegin = request.getParameter("dateBegin");
                    String dateEnd = request.getParameter("dateEnd");

                    /* obtener mensajes de PRG */
                    String msgOk = request.getParameter("msgOk");
                    String msgErrorTittle = request.getParameter("msgErrorTittle");
                    String msgErrorDetails = request.getParameter("msgErrorDetails");
                    String msgErrorUrlImage = request.getParameter("msgErrorUrlImage");
                    String msgErrorDateBegin = request.getParameter("msgErrorDateBegin");
                    String msgErrorDateEnd = request.getParameter("msgErrorDateEnd");
                    String msgErrorDate = request.getParameter("msgErrorDate");
                    String msgErrorDup = request.getParameter("msgErrorDup");

                    /* instanciar lista de mensajes */
                    Collection<Message> msgList = new ArrayList<Message>();

                    /* establecer id place */
                    int idPlaceNews = 0;
                    try {
                        idPlaceNews = Integer.parseInt(sidPlaceNews);
                    } catch (NumberFormatException n) {
                    }

                    /* buscar registro */
                    try {
                        PlaceNews reg = pnewsDAO.findByIdPlaceNews(idPlaceNews);
                        if (reg != null) {
                            /* establecer registros de reg */
                            request.setAttribute("idPlaceNews", reg.getIdPlaceNews());
                            request.setAttribute("idPlace", reg.getIdPlace());
                            request.setAttribute("namePlace", reg.getNamePlace());

                            /* comprobar redirect */
                            if (redirect == null || redirect.trim().equals("")) {
                                /* establecer atributos de reg */
                                request.setAttribute("newsType", reg.getNewsType());
                                request.setAttribute("tittle", reg.getTittle());
                                request.setAttribute("details", reg.getDetails());
                                request.setAttribute("urlImage", reg.getUrlImage());
                                request.setAttribute("dateBegin", Format.dateYYYYMMDD(reg.getDateBegin()));
                                request.setAttribute("dateEnd", Format.dateYYYYMMDD(reg.getDateEnd()));
                            } else {
                                /* establecer atributos de PRG */
                                request.setAttribute("newsType", Integer.parseInt(newsType));
                                request.setAttribute("tittle", tittle);
                                request.setAttribute("details", details);
                                request.setAttribute("urlImage", urlImage);
                                request.setAttribute("dateBegin", dateBegin);
                                request.setAttribute("dateEnd", dateEnd);
                            }

                            ///////////////////////////
                            // COMPROBAR ERRORES
                            ///////////////////////////

                            /* comprobar tittle */
                            if (msgErrorTittle == null || msgErrorTittle.trim().equals("")) {
                            } else {
                                request.setAttribute("msgErrorTittle", true);
                                msgList.add(MessageList.addMessage(msgErrorTittle));
                            }

                            /* comprobar details */
                            if (msgErrorDetails == null || msgErrorDetails.trim().equals("")) {
                            } else {
                                request.setAttribute("msgErrorDetails", true);
                                msgList.add(MessageList.addMessage(msgErrorDetails));
                            }

                            /* comprobar urlImage */
                            if (msgErrorUrlImage == null || msgErrorUrlImage.trim().equals("")) {
                            } else {
                                request.setAttribute("msgErrorUrlImage", true);
                                msgList.add(MessageList.addMessage(msgErrorUrlImage));
                            }

                            /* comprobar fecha de inicio */
                            if (msgErrorDateBegin == null || msgErrorDateBegin.trim().equals("")) {
                            } else {
                                request.setAttribute("msgErrorDateBegin", true);
                                msgList.add(MessageList.addMessage(msgErrorDateBegin));
                            }

                            /* comprobar fecha de termino */
                            if (msgErrorDateEnd == null || msgErrorDateEnd.trim().equals("")) {
                            } else {
                                request.setAttribute("msgErrorDateEnd", true);
                                msgList.add(MessageList.addMessage(msgErrorDateEnd));
                            }

                            /* comprobar fecha */
                            if (msgErrorDate == null || msgErrorDate.trim().equals("")) {
                            } else {
                                request.setAttribute("msgErrorDate", true);
                                msgList.add(MessageList.addMessage(msgErrorDate));
                            }

                            /* comprobar duplicaciones */
                            if (msgErrorDup == null || msgErrorDup.trim().equals("")) {
                            } else {
                                request.setAttribute("msgErrorDup", true);
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
                            msgList.add(MessageList.addMessage("El registro no ha sido encontrado."));
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    /* establecer lista de mensajes */
                    if (!msgList.isEmpty()) {
                        request.setAttribute("msgList", msgList);
                    }

                    /* obtener lista de lugares */
                    try {
                        Collection<Place> listPlace = placeDAO.getAll();
                        request.setAttribute("listPlace", listPlace);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    /* despachar a la vista */
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
