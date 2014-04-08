/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package event;

import Helpers.Format;
import Helpers.Message;
import Helpers.MessageList;
import dressCode.DressCode;
import dressCode.DressCodeDAO;
import java.io.IOException;
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
 * @author patricio alberto
 */
@WebServlet(name = "EventGetServlet", urlPatterns = {"/EventGetServlet"})
public class EventGetServlet extends HttpServlet {

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

            EventDAO eventDAO = new EventDAO();
            eventDAO.setConexion(conexion);

            PlaceDAO placeDAO = new PlaceDAO();
            placeDAO.setConexion(conexion);

            DressCodeDAO dressCodeDAO = new DressCodeDAO();
            dressCodeDAO.setConexion(conexion);

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
                    String sidPlace = request.getParameter("idPlace");
                    String sidEvent = request.getParameter("idEvent");
                    String tittle = request.getParameter("tittle");
                    String details = request.getParameter("details");
                    String points = request.getParameter("points");
                    String dateBegin = request.getParameter("dateBegin");
                    String dateEnd = request.getParameter("dateEnd");
                    String urlImage = request.getParameter("urlImage");
                    String reason = request.getParameter("reason");
                    String srequest = request.getParameter("srequest");

                    /* obtener mensajes de PRG */
                    String msgOk = request.getParameter("msgOk");
                    String msgErrorTittle = request.getParameter("msgErrorTittle");
                    String msgErrorDetails = request.getParameter("msgErrorDetails");
                    String msgErrorPoints = request.getParameter("msgErrorPoints");
                    String msgErrorDate = request.getParameter("msgErrorDate");
                    String msgErrorDateBegin = request.getParameter("msgErrorDateBegin");
                    String msgErrorDateEnd = request.getParameter("msgErrorDateEnd");
                    String msgErrorUrlImage = request.getParameter("msgErrorUrlImage");
                    String msgErrorEvent = request.getParameter("msgErrorEvent");
                    String msgErrorReason = request.getParameter("msgErrorReason");

                    /* instanciar lista de mensajes */
                    Collection<Message> msgList = new ArrayList<Message>();

                    /* establecer idPlace */
                    int idPlace = 0;
                    try {
                        idPlace = Integer.parseInt(sidPlace);
                    } catch (NumberFormatException n) {
                        request.setAttribute("msgErrorIdPlace", true);
                        msgList.add(MessageList.addMessage("Fallo al recibir id de lugar."));
                    }

                    /* establecer idEvent */
                    int idEvent = 0;
                    try {
                        idEvent = Integer.parseInt(sidEvent);
                    } catch (NumberFormatException n) {
                        request.setAttribute("msgErrorIdPlace", true);
                        msgList.add(MessageList.addMessage("Fallo al recibir id de lugar."));
                    }

                    /* buscar Evento */
                    try {
                        Event reg = eventDAO.findByPlaceEvent(idPlace, idEvent);

                        if (reg != null) {
                            /* obtener atributos de reg */
                            request.setAttribute("idPlace", reg.getIdPlace());
                            request.setAttribute("namePlace", reg.getNamePlace());
                            request.setAttribute("idEvent", reg.getIdEvent());
                            request.setAttribute("idDressCode", reg.getIdDressCode());

                            /* comprobar redirect */
                            if (redirect == null || redirect.trim().equals("")) {
                                /* establecer atributos de reg */
                                request.setAttribute("tittle", reg.getTittle());
                                request.setAttribute("details", reg.getDetails());
                                request.setAttribute("points", reg.getPoints());
                                request.setAttribute("dateBegin", Format.dateYYYYMMDD(reg.getDateBegin()));
                                request.setAttribute("dateEnd", Format.dateYYYYMMDD(reg.getDateEnd()));
                                request.setAttribute("urlImage", reg.getUrlImage());
                                request.setAttribute("reason", reg.getReason());
                                request.setAttribute("request", reg.getRequest());
                            } else {
                                /* establecer atributos de PRG */
                                request.setAttribute("tittle", tittle);
                                request.setAttribute("details", details);
                                request.setAttribute("points", points);
                                request.setAttribute("dateBegin", dateBegin);
                                request.setAttribute("dateEnd", dateEnd);
                                request.setAttribute("urlImage", urlImage);
                                request.setAttribute("reason", reason);
                                try {
                                    int rqst = Integer.parseInt(srequest);
                                    request.setAttribute("request", rqst);
                                } catch (NumberFormatException n) {
                                }
                            }

                            /* comprobar titulo evento */
                            if (msgErrorTittle == null || msgErrorTittle.trim().equals("")) {
                            } else {
                                request.setAttribute("msgErrorTittle", true);
                                msgList.add(MessageList.addMessage(msgErrorTittle));
                            }

                            /* comprobar detalles */
                            if (msgErrorDetails == null || msgErrorDetails.trim().equals("")) {
                            } else {
                                request.setAttribute("msgErrorDetails", true);
                                msgList.add(MessageList.addMessage(msgErrorDetails));
                            }

                            /* comprobar puntos */
                            if (msgErrorPoints == null || msgErrorPoints.trim().equals("")) {
                            } else {
                                request.setAttribute("msgErrorPoints", true);
                                msgList.add(MessageList.addMessage(msgErrorPoints));
                            }

                            /* comprobar fechas */
                            if (msgErrorDate == null || msgErrorDate.trim().equals("")) {
                            } else {
                                request.setAttribute("msgErrorDate", true);
                                msgList.add(MessageList.addMessage(msgErrorDate));
                            }

                            /* comprobar date begin */
                            if (msgErrorDateBegin == null || msgErrorDateBegin.trim().equals("")) {
                            } else {
                                request.setAttribute("msgErrorDateBegin", true);
                                msgList.add(MessageList.addMessage(msgErrorDateBegin));
                            }

                            /* comprobar date end */
                            if (msgErrorDateEnd == null || msgErrorDateEnd.trim().equals("")) {
                            } else {
                                request.setAttribute("msgErrorDateEnd", true);
                                msgList.add(MessageList.addMessage(msgErrorDateEnd));
                            }

                            /* comprobar url image */
                            if (msgErrorUrlImage == null || msgErrorUrlImage.trim().equals("")) {
                            } else {
                                request.setAttribute("msgErrorUrlImage", true);
                                msgList.add(MessageList.addMessage(msgErrorUrlImage));
                            }

                            /* comprobar reason */
                            if (msgErrorReason == null || msgErrorReason.trim().equals("")) {
                            } else {
                                request.setAttribute("msgErrorReason", true);
                                msgList.add(MessageList.addMessage(msgErrorReason));
                            }

                            /* comprobar error event */
                            if (msgErrorEvent == null || msgErrorEvent.trim().equals("")) {
                            } else {
                                request.setAttribute("msgErrorEvent", true);
                                msgList.add(MessageList.addMessage(msgErrorEvent));
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

                    /* obtener lista de lugares */
                    try {
                        Collection<Place> listPlace = placeDAO.getAll();
                        request.setAttribute("listPlace", listPlace);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    /* obtener lista de codigos de vestir */
                    try {
                        Collection<DressCode> listDressCode = dressCodeDAO.getAll();
                        request.setAttribute("listDressCode", listDressCode);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    /* establecer lista de mensajes */
                    if (!msgList.isEmpty()) {
                        request.setAttribute("msgList", msgList);
                    }

                    /* despachar a la vista */
                    request.getRequestDispatcher("/event/eventUpdate.jsp").forward(request, response);
                }
            } catch (Exception sessionException) {
                /* enviar a la vista de login */
                System.out.println("no ha iniciado session");
                request.getRequestDispatcher("/login/login.jsp").forward(request, response);
            }
        } catch (Exception connectionException) {
            connectionException.printStackTrace();
        } finally {
            /* cerar conexion */
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
