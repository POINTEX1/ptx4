/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clientNews;

import Helpers.Format;
import Helpers.Message;
import Helpers.MessageList;
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
import userCard.UserCardDAO;

/**
 *
 * @author alexander
 */
@WebServlet(name = "ClientNewsGetServlet", urlPatterns = {"/ClientNewsGetServlet"})
public class ClientNewsGetServlet extends HttpServlet {

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

        ////////////////////////
        // ESTABLECER CONEXION
        ////////////////////////
        try {
            conexion = ds.getConnection();

            ClientNewsDAO cnewsDAO = new ClientNewsDAO();
            cnewsDAO.setConexion(conexion);

            UserCardDAO usercardDAO = new UserCardDAO();
            usercardDAO.setConexion(conexion);

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
                    request.setAttribute("su", 777); //superuser 

                    ////////////////////////////////////
                    // RECIBIR Y COMPROBAR PARAMETROS
                    ////////////////////////////////////

                    /* obtener atributos de PRG */
                    String redirect = request.getParameter("redirect");
                    String sidClientNews = request.getParameter("idClientNews");
                    String tittle = request.getParameter("tittle");
                    String dateBegin = request.getParameter("dateBegin");
                    String dateEnd = request.getParameter("dateEnd");
                    String newsType = request.getParameter("newsType");

                    /* obtener mensajes de PRG */
                    String msgErrorTittle = request.getParameter("msgErrorTittle");
                    String msgErrorDateBegin = request.getParameter("msgErrorDateBegin");
                    String msgErrorDateEnd = request.getParameter("msgErrorDateEnd");
                    String msgErrorDate = request.getParameter("msgErrorDate");
                    String msgOk = request.getParameter("msgOk");

                    /* instanciar lista de mensajes */
                    Collection<Message> msgList = new ArrayList<Message>();

                    /* establecer id client news */
                    int idCnews = 0;
                    try {
                        idCnews = Integer.parseInt(sidClientNews);
                    } catch (NumberFormatException n) {
                    }

                    /* buscar por idClientNews */
                    try {
                        ClientNews reg = cnewsDAO.findByIdClientNews(idCnews);

                        if (reg != null) {
                            /* establecer atributos de reg */
                            request.setAttribute("idClientNews", reg.getIdClientNews());
                            request.setAttribute("rut", reg.getRut());
                            request.setAttribute("dv", reg.getDv());
                            request.setAttribute("firstName", reg.getFirstName());
                            request.setAttribute("lastName", reg.getLastName());

                            /* comprobar redirect */
                            if (redirect == null || redirect.trim().equals("")) {
                                /* establecer atributos de reg */
                                request.setAttribute("tittle", reg.getTittle());

                                /* formatear fechas */
                                reg.setDateBegin(Format.dateYYYYMMDD(reg.getDateBegin()));
                                reg.setDateEnd(Format.dateYYYYMMDD(reg.getDateEnd()));

                                request.setAttribute("dateBegin", reg.getDateBegin());
                                request.setAttribute("dateEnd", reg.getDateEnd());
                            } else {
                                /* establecer atributos de PRG */
                                request.setAttribute("tittle", tittle);
                                request.setAttribute("dateBegin", dateBegin);
                                request.setAttribute("dateEnd", dateEnd);
                                try {
                                    request.setAttribute("newsType", Integer.parseInt(newsType));
                                } catch (NumberFormatException n) {
                                }
                            }

                            /*comprobar titulo */
                            if (msgErrorTittle == null || msgErrorTittle.trim().equals("")) {
                            } else {
                                request.setAttribute("msgErrorTittle", true);
                                msgList.add(MessageList.addMessage(msgErrorTittle));
                            }

                            /* comprobar fecha inicio */
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

                            /* comprobar date */
                            if (msgErrorDate == null || msgErrorDate.trim().equals("")) {
                            } else {
                                request.setAttribute("msgErrorDate", true);
                                msgList.add(MessageList.addMessage(msgErrorDate));
                            }

                            /* comprobar mensaje de exito */
                            if (msgOk == null || msgOk.trim().equals("")) {
                                request.setAttribute("msg", "El registro ha sido encontrado!");
                            } else {
                                request.setAttribute("msgOk", msgOk);
                            }
                        } else {
                            request.setAttribute("msgErrorFound", true);
                            msgList.add(MessageList.addMessage("No se ha encontrado el registro."));
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    /* establecer lista de mensajes */
                    if (!msgList.isEmpty()) {
                        request.setAttribute("msgList", msgList);
                    }

                    /* despachar a la vista */
                    request.getRequestDispatcher("/clientNews/clientNewsUpdate.jsp").forward(request, response);

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
