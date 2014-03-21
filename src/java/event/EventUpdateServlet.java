/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package event;

import dressCode.DressCode;
import dressCode.DressCodeDAO;
import java.io.IOException;
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

        /* establecer set de caracteres */
        request.setCharacterEncoding("UTF-8");

        /* definir conexion */
        Connection conexion = null;

        try {
            /////////////////////////////////////////
            // ESTABLECER CONEXION
            ///////////////////////////////////////// 

            conexion = ds.getConnection();

            EventDAO eventDAO = new EventDAO();
            eventDAO.setConexion(conexion);

            DressCodeDAO dressCodeDAO = new DressCodeDAO();
            dressCodeDAO.setConexion(conexion);

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

                    ////////////////////////////////////////
                    // RECIBIR Y COMPROBAR PARAMETROS
                    ////////////////////////////////////////

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

                    Event event = new Event();

                    boolean error = false;

                    String url = "?a=target";

                    /* comprobar id place */
                    url += "&idPlace=" + sidPlace;
                    if (sidPlace == null || sidPlace.trim().equals("")) {
                        url += "&msgErrorIdPlace=Error al recibir id de lugar.";
                        error = true;
                    } else {
                        event.setIdPlace(Integer.parseInt(sidPlace));
                    }

                    /* comprobar id event */
                    url += "&idEvent=" + sidEvent;
                    if (sidEvent == null || sidEvent.trim().equals("")) {
                        url += "&msgErrorIdEvent=Error al recibir id de evento.";
                        error = true;
                    } else {
                        event.setIdEvent(Integer.parseInt(sidEvent));
                    }

                    /* comprobar tittle */
                    url += "&tittle=" + tittle;
                    if (tittle == null || tittle.trim().equals("")) {
                        url += "&msgErrorTittle=Error al recibir título.";
                        error = true;
                    } else {
                        event.setTittle(tittle);
                    }

                    /* comprobar details */
                    url += "&details=" + details;
                    if (details == null || details.trim().equals("")) {
                        url += "&msgErrorDetails=Error al recibir detalles.";
                        error = true;
                    } else {
                        event.setDetails(details);
                    }

                    /* comprobar points */
                    url += "&points=" + spoints;
                    if (spoints == null || spoints.trim().equals("")) {
                        url += "&msgErrorPoints=Error: Debe ingresar puntos.";
                        error = true;
                    } else {
                        try {
                            event.setPoints(Integer.parseInt(spoints));
                            if (event.getPoints() < 0) {
                                url += "&msgErrorPoints=Error: Los puntos no pueden ser negativos.";
                                error = true;
                            }
                        } catch (NumberFormatException n) {
                            url += "&msgErrorPoints=Error: Los puntos deben ser numéricos.";
                            error = true;
                        }
                    }

                    /* comprobar url image*/
                    url += "&urlImage=" + urlImage;
                    if (urlImage == null || urlImage.trim().equals("")) {
                        url += "&msgErrorUrlImage=Error: Debe ingresar url de imagen.";
                        error = true;
                    } else {
                        event.setUrlImage(urlImage);
                    }

                    /* comprobar dateBegin */
                    url += "&dateBegin=" + dateBegin;
                    url += "&dateEnd=" + dateEnd;
                    if (dateBegin == null || dateBegin.trim().equals("")) {
                        url += "&msgErrorDate=Error al recibir feha de inicio.";
                        error = true;
                    } else {
                        /* comprobar dateEnd */
                        if (dateEnd == null || dateEnd.trim().equals("")) {
                            url += "&msgErrorDate=Error al recibir feha de término.";
                            error = true;
                        } else {
                            /* comparar fechas */
                            event.setDateBegin(dateBegin);
                            event.setDateEnd(dateEnd);
                            //System.out.println("Comparar fecha 1 y fecha 2: " + event.getDateBegin().compareTo(event.getDateEnd()));
                            if (event.getDateBegin().compareTo(event.getDateEnd()) >= 0) {
                                url += "&msgErrorDate=Error: La fecha de término debe ser mayor que la fecha de inicio.";
                                error = true;
                            }
                        }
                    }

                    /* comprobar id dress code */
                    url += "&idDressCode=" + sidDressCode;
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
                    url += "&srequest=" + srequest;
                    if (srequest == null || srequest.trim().equals("")) {
                    } else {
                        try {
                            event.setRequest(Integer.parseInt(srequest));
                        } catch (NumberFormatException n) {
                        }
                    }

                    /* comprobar reason */
                    url += "&reason=" + reason;
                    if ((reason == null || reason.trim().equals("")) && event.getRequest() == 2) {
                        url += "&msgErrorReason=Error: Debe ingresar razón de rechazo.";
                        error = true;
                    } else {
                        event.setReason(reason);
                    }

                    if (!error) {
                        /* comprobar registros duplicados */
                        boolean find = eventDAO.findDuplicate(event);
                        if (find) {
                            url += "&msgErrorEvent=Error: ya existe este evento. Compruebe utilizando otro título u otro rango de fechas.";
                        } else {
                            /* comprobar existencia */
                            Event aux = eventDAO.findByPlaceEvent(event);
                            if (aux != null) {
                                eventDAO.update(event);
                                url += "&msgOk=Registro actualizado exitosamente!";
                            } else {
                                url += "&msgErrorFound=Error: no existe el evento o ha sido eliminado mientras se actualizaba.";
                            }
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
