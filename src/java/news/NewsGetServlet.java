/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package news;

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

/**
 *
 * @author alexander
 */
@WebServlet(name = "NewsGetServlet", urlPatterns = {"/NewsGetServlet"})
public class NewsGetServlet extends HttpServlet {
    
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
            
            NewsDAO newsDAO = new NewsDAO();
            newsDAO.setConexion(conexion);

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
                }

                /* obtener los valores de session y asignar valores a la jsp */
                request.setAttribute("userJsp", username);
                request.setAttribute("access", access);

                /////////////////////////////////////
                // RECIBIR Y COMPROBAR PARAMETROS
                /////////////////////////////////////

                /* obtener atributos de PRG */
                String redirect = request.getParameter("redirect");
                String sidNews = request.getParameter("idNews");
                String tittle = request.getParameter("tittle");
                String details = request.getParameter("details");
                String urlImage = request.getParameter("urlImage");
                String dateBegin = request.getParameter("dateBegin");
                String dateEnd = request.getParameter("dateEnd");
                String newsType = request.getParameter("newsType");

                /* obtener mensajes de PRG */
                String msgOk = request.getParameter("msgOk");
                String msgErrorTittle = request.getParameter("msgErrorTittle");
                String msgErrorDetails = request.getParameter("msgErrorDetails");
                String msgErrorUrlImage = request.getParameter("msgErrorUrlImage");
                String msgErrorDate = request.getParameter("msgErrorDate");
                String msgErrorDateBegin = request.getParameter("msgErrorDateBegin");
                String msgErrorDateEnd = request.getParameter("msgErrorDateEnd");
                String msgErrorDup = request.getParameter("msgErrorDup");

                /* instanciar lista de mensajes */
                Collection<Message> msgList = new ArrayList<Message>();

                /* establecer id */
                int idNews = 0;
                try {
                    idNews = Integer.parseInt(sidNews);
                } catch (NumberFormatException n) {
                }

                /* buscar news */
                try {
                    News reg = newsDAO.findByIdNews(idNews);
                    
                    if (reg != null) {
                        /* obtener atributos de reg */
                        request.setAttribute("idNews", reg.getIdNews());

                        /* comprobar redirect */
                        if (redirect == null || redirect.trim().equals("")) {
                            /* establecer atributos de reg */
                            request.setAttribute("tittle", reg.getTittle());
                            request.setAttribute("details", reg.getDetails());
                            request.setAttribute("urlImage", reg.getUrlImage());
                            request.setAttribute("dateBegin", Format.dateYYYYMMDD(reg.getDateBegin()));
                            request.setAttribute("dateEnd", Format.dateYYYYMMDD(reg.getDateEnd()));
                            request.setAttribute("newsType", reg.getNewsType());
                        } else {
                            /* establecer atributos de PRG */
                            request.setAttribute("tittle", tittle);
                            request.setAttribute("details", details);
                            request.setAttribute("urlImage", urlImage);
                            request.setAttribute("dateBegin", dateBegin);
                            request.setAttribute("dateEnd", dateEnd);
                            try {
                                request.setAttribute("newsType", Integer.parseInt(newsType));
                            } catch (NumberFormatException n) {
                            }
                        }

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

                        /* comproba date begin */
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

                        /* comprobar fechas */
                        if (msgErrorDate == null || msgErrorDate.trim().equals("")) {
                        } else {
                            request.setAttribute("msgErrorDate", true);
                            msgList.add(MessageList.addMessage(msgErrorDate));
                        }

                        /* comprobar duplicados */
                        if (msgErrorDup == null || msgErrorDup.trim().equals("")) {
                        } else {
                            request.setAttribute("msgErrorDup", true);
                            msgList.add(MessageList.addMessage(msgErrorDup));
                        }

                        /* comprobar actualizacion */
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

                /* despachar a la vista */
                request.getRequestDispatcher("/news/newsUpdate.jsp").forward(request, response);
                
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
