/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package card;

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
@WebServlet(name = "CardGetServlet", urlPatterns = {"/CardGetServlet"})
public class CardGetServlet extends HttpServlet {

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
            ///////////////////////////
            // ESTABLECER CONEXION
            ///////////////////////////

            conexion = ds.getConnection();

            CardDAO cardDAO = new CardDAO();
            cardDAO.setConexion(conexion);

            UserCardDAO userDAO = new UserCardDAO();
            userDAO.setConexion(conexion);

            ////////////////////////
            // COMPROBAR SESSION
            ////////////////////////
            try {
                /* recuperar sesion */
                HttpSession session = request.getSession(false);

                /* obtener parametros de session */
                int access = Integer.parseInt((String) session.getAttribute("access"));
                String user = (String) session.getAttribute("username");

                /* obtener los valores de session y asignar valores a la jsp */
                request.setAttribute("userJsp", user);
                request.setAttribute("access", access);

                ////////////////////////////////////
                // RECIBIR Y COMPROBAR PARAMETROS
                ////////////////////////////////////

                /* recibir atributos por PRG */
                String redirect = request.getParameter("redirect");
                String sbarcode = request.getParameter("barCode");
                String srut = request.getParameter("rut");
                String dateBegin = request.getParameter("dateBegin");
                String dateEnd = request.getParameter("dateEnd");

                /* recibir mensajes de PRG */
                String msgOk = request.getParameter("msgOk");
                String msgErrorDateBegin = request.getParameter("msgErrorDateBegin");
                String msgErrorDateEnd = request.getParameter("msgErrorDateEnd");
                String msgErrorDate = request.getParameter("msgErrorDate");


                /* instanciar lista de mensajes */
                Collection<Message> msgList = new ArrayList<Message>();

                /* comprobar rut */
                int rut = 0;
                try {
                    rut = Integer.parseInt(srut);
                } catch (NumberFormatException n) {
                }

                /* comprobar barcode */
                int barcode = 0;
                try {
                    barcode = Integer.parseInt(sbarcode);
                } catch (NumberFormatException n) {
                }

                /* buscar registro */
                try {
                    Card reg = cardDAO.findbyRutBarcode(barcode, rut);

                    if (reg != null) {
                        /* establecer atributos de reg */
                        request.setAttribute("barCode", reg.getBarCode());
                        request.setAttribute("rut", reg.getRut());
                        request.setAttribute("dv", reg.getDv());
                        request.setAttribute("firstName", reg.getFirstName());
                        request.setAttribute("lastName", reg.getLastName());
                        request.setAttribute("cardType", reg.getCardType());

                        /* comprobar redirect */
                        if (redirect == null || redirect.trim().equals("")) {
                            /* establecer atributos de reg */
                            request.setAttribute("dateBegin", Format.dateYYYYMMDD(reg.getDateBeginCard()));
                            request.setAttribute("dateEnd", Format.dateYYYYMMDD(reg.getDateEndCard()));
                        } else {
                            /* establecer atributos de PRG */
                            request.setAttribute("dateBegin", dateBegin);
                            request.setAttribute("dateEnd", dateEnd);
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

                        /* comprobar date */
                        if (msgErrorDate == null || msgErrorDate.trim().equals("")) {
                        } else {
                            request.setAttribute("msgErrorDate", true);
                            msgList.add(MessageList.addMessage(msgErrorDate));
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

                /* establecer lista de mensajes a la petición */
                if (!msgList.isEmpty()) {
                    request.setAttribute("msgList", msgList);
                }

                /* despachar a la vista */
                request.getRequestDispatcher("/card/cardUpdate.jsp").forward(request, response);

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
