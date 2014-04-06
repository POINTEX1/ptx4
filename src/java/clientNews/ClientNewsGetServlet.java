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
                    String tittle = request.getParameter("tittle");
                    String dateBegin = request.getParameter("dateBegin");
                    String dateEnd = request.getParameter("dateEnd");

                    /* obtener mensajes de PRG */
                    String msgErrorTittle = request.getParameter("msgErrorTittle");
                    String msgErrorDateBegin = request.getParameter("msgErrorDateBegin");
                    String msgErrorDateEnd = request.getParameter("msgErrorDateEnd");
                    String msgErrorDate = request.getParameter("msgErrorDate");
                    String msgOk = request.getParameter("msgOk");

                    /* parametros de busqueda */
                    String sidClientNews = request.getParameter("idClientNews");
                    String srut = request.getParameter("rut");

                    /* instanciar client news */
                    ClientNews cnews = new ClientNews();

                    /* instanciar lista de mensajes */
                    Collection<Message> msgList = new ArrayList<Message>();

                    boolean error = false;

                    /* comprobar id client news */
                    if (sidClientNews == null || sidClientNews.trim().equals("")) {
                        error = true;
                    } else {
                        try {
                            cnews.setIdClientNews(Integer.parseInt(sidClientNews));
                        } catch (NumberFormatException n) {
                        }
                    }

                    /* comprobar rut */
                    if (srut == null || srut.trim().equals("")) {
                        error = true;
                    } else {
                        try {
                            cnews.setRut(Integer.parseInt(srut));
                        } catch (NumberFormatException n) {
                            error = true;
                        }
                    }

                    if (!error) {
                        /* buscar noticia cliente */
                        ClientNews reg = cnewsDAO.findByClientNews(cnews);
                        if (reg != null) {
                            reg.setDateBegin(Format.dateYYYYMMDD(reg.getDateBegin()));
                            reg.setDateEnd(Format.dateYYYYMMDD(reg.getDateEnd()));

                            /* obtener atributos del dao */
                            request.setAttribute("rut", reg.getRut());
                            request.setAttribute("dv", reg.getDv());
                            request.setAttribute("idClientNews", reg.getIdClientNews());
                            request.setAttribute("firstName", reg.getFirstName());
                            request.setAttribute("lastName", reg.getLastName());
                            request.setAttribute("newsType", reg.getNewsType());

                            /*comprobar titulo */
                            if (msgErrorTittle == null || msgErrorTittle.trim().equals("")) {
                                request.setAttribute("tittle", reg.getTittle());
                            } else {
                                request.setAttribute("msgErrorTittle", true);
                                msgList.add(MessageList.addMessage(msgErrorTittle));
                                request.setAttribute("tittle", tittle);
                            }

                            /* comprobar fecha inicio */
                            if (msgErrorDateBegin == null || msgErrorDateBegin.trim().equals("")) {
                                request.setAttribute("dateBegin", reg.getDateBegin());
                            } else {
                                request.setAttribute("msgErrorDateBegin", true);
                                msgList.add(MessageList.addMessage(msgErrorDateBegin));
                                request.setAttribute("dateBegin", dateBegin);
                            }

                            /* comprobar fecha de termino */
                            if (msgErrorDateEnd == null || msgErrorDateEnd.trim().equals("")) {
                                request.setAttribute("dateEnd", reg.getDateEnd());
                            } else {
                                request.setAttribute("msgErrorDateEnd", true);
                                msgList.add(MessageList.addMessage(msgErrorDateEnd));
                                request.setAttribute("dateEnd", dateEnd);
                            }

                            /* comprobar date */
                            if (msgErrorDate == null || msgErrorDate.trim().equals("")) {
                            } else {
                                request.setAttribute("dateBegin", dateBegin);
                                request.setAttribute("dateEnd", reg.getDateEnd());
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
                            request.setAttribute("msgErrorFound", "Error: No se ha encontrado el registro.");
                        }
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
