/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package event;

import dressCode.DressCodeDAO;
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
@WebServlet(name = "EventUpdateServlet", urlPatterns = {"/EventUpdateServlet"})
public class EventUpdateServlet extends HttpServlet {

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

            EventDAO eventDAO = new EventDAO();
            eventDAO.setConexion(conexion);

            DressCodeDAO dressCodeDAO = new DressCodeDAO();
            dressCodeDAO.setConexion(conexion);

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

                    String sidPlace = request.getParameter("idPlace");
                    String sidEvent = request.getParameter("idEvent");
                    String tittle = request.getParameter("tittle");
                    String details = request.getParameter("details");
                    String dateBegin = request.getParameter("dateBegin");
                    String dateEnd = request.getParameter("dateEnd");
                    String urlImage = request.getParameter("urlImage");
                    String spoints = request.getParameter("points");
                    String srequest = request.getParameter("eventRequest");
                    String sidDressCode = request.getParameter("idDressCode");
                    String reason = request.getParameter("reason");

                    String url = "?redirect=ok";
                    url += "&idPlace=" + sidPlace;
                    url += "&idEvent=" + sidEvent;
                    url += "&tittle=" + tittle;
                    url += "&details=" + details;
                    url += "&points=" + spoints;
                    url += "&urlImage=" + urlImage;
                    url += "&dateBegin=" + dateBegin;
                    url += "&dateEnd=" + dateEnd;
                    url += "&idDressCode=" + sidDressCode;
                    url += "&srequest=" + srequest;
                    url += "&reason=" + reason;

                    /* instanciar evento */
                    Event event = new Event();

                    /* flag de error */
                    boolean error = false;

                    /* comprobar id place */
                    if (sidPlace == null || sidPlace.trim().equals("")) {
                        error = true;
                    } else {
                        event.setIdPlace(Integer.parseInt(sidPlace));
                    }

                    /* comprobar id event */
                    if (sidEvent == null || sidEvent.trim().equals("")) {
                        error = true;
                    } else {
                        event.setIdEvent(Integer.parseInt(sidEvent));
                    }

                    /* comprobar tittle */
                    if (tittle == null || tittle.trim().equals("")) {
                        url += "&msgErrorTittle=Debe ingresar un título para el evento.";
                        error = true;
                    } else {
                        event.setTittle(tittle);
                    }

                    /* comprobar details */
                    if (details == null || details.trim().equals("")) {
                        url += "&msgErrorDetails=Debe ingresar detalles del evento.";
                        error = true;
                    } else {
                        event.setDetails(details);
                    }

                    /* comprobar points */
                    if (spoints == null || spoints.trim().equals("")) {
                        url += "&msgErrorPoints=Debe ingresar puntos.";
                        error = true;
                    } else {
                        try {
                            event.setPoints(Integer.parseInt(spoints));
                            if (event.getPoints() < 0) {
                                url += "&msgErrorPoints=Los puntos no pueden ser negativos.";
                                error = true;
                            }
                        } catch (NumberFormatException n) {
                            url += "&msgErrorPoints=Los puntos deben ser numéricos.";
                            error = true;
                        }
                    }

                    /* comprobar url image*/
                    if (urlImage == null || urlImage.trim().equals("")) {
                        url += "&msgErrorUrlImage=Debe ingresar imagen.";
                        error = true;
                    } else {
                        event.setUrlImage(urlImage);
                    }

                    /* comprobar date begin */
                    event.setDateBegin(dateBegin);
                    if (dateBegin == null || dateBegin.trim().equals("")) {
                        url += "&msgErrorDateBegin=Debe ingresar fecha de inicio.";
                        error = true;
                    }

                    /* comprobar date end */
                    event.setDateEnd(dateEnd);
                    if (dateEnd == null || dateEnd.trim().equals("")) {
                        url += "&msgErrorDateEnd=Debe ingresar fecha de término.";
                        error = true;
                    }

                    /* comparar fecha de inicio con fecha de término */
                    if (event.getDateBegin().compareTo(event.getDateEnd()) >= 0) {
                        url += "&msgErrorDate=La fecha de inicio no puede ser mayor o igual que la fecha de término.";
                        error = true;
                    }

                    /* comprobar id dress code */
                    if (sidDressCode == null || sidDressCode.trim().equals("")) {
                        error = true;
                    } else {
                        try {
                            event.setIdDressCode(Integer.parseInt(sidDressCode));
                        } catch (NumberFormatException n) {
                            error = true;
                        }
                    }

                    /*comprobar request */
                    if (srequest == null || srequest.trim().equals("")) {
                        error = true;
                    } else {
                        try {
                            event.setRequest(Integer.parseInt(srequest));
                        } catch (NumberFormatException n) {
                            error = true;
                        }
                    }

                    /* comprobar reason */
                    if ((reason == null || reason.trim().equals("")) && event.getRequest() == 2) {
                        url += "&msgErrorReason=Debe ingresar razón de rechazo.";
                        error = true;
                    } else {
                        event.setReason(reason);
                    }

                    ////////////////////////
                    // LOGICA DE NEGOCIO
                    ////////////////////////

                    /* comprobar registros duplicados */
                    try {
                        boolean find = eventDAO.findDuplicate(event);
                        if (find) {
                            url += "&msgErrorEvent=Ya existe este evento. Compruebe utilizando otro título u otro rango de fechas.";
                            error = true;
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        error = true;
                    }

                    /* actualizar el evento */
                    if (!error) {
                        try {
                            eventDAO.update(event);
                            url += "&msgOk=Registro actualizado exitosamente.";
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    /* send redirect */
                    response.sendRedirect("EventGetServlet" + url);
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
