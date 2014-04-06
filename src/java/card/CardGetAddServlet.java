/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package card;

import Helpers.Message;
import Helpers.MessageList;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author patricio
 */
@WebServlet(name = "CardGetAddServlet", urlPatterns = {"/CardGetAddServlet"})
public class CardGetAddServlet extends HttpServlet {

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

        /////////////////////////
        // COMPROBAR SESSION
        /////////////////////////
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

            String redirect = request.getParameter("redirect");

            if (redirect == null) {
                request.setAttribute("msg", "Ingrese una nueva tarjeta.");
            } else {
                /* obtener atributos de PRG */
                String srut = request.getParameter("rut");
                String sdv = request.getParameter("dv");
                String firstname = request.getParameter("firstname");
                String lastname = request.getParameter("lastname");
                String barcode = request.getParameter("barcode");
                String cardtype = request.getParameter("cardtype");

                /* obtener mensajes de PRG */
                String msgErrorBarcode = request.getParameter("msgErrorBarcode");

                /* establecer atributos a la vista */
                request.setAttribute("rut", srut);
                request.setAttribute("dv", sdv);
                request.setAttribute("firstname", firstname);
                request.setAttribute("lastname", lastname);
                request.setAttribute("barcode", barcode);
                request.setAttribute("cardtype", Integer.parseInt(cardtype));

                /* instanciar lista de mensajes */
                Collection<Message> msgList = new ArrayList<Message>();

                /* comprobar barcode */
                if (msgErrorBarcode == null || msgErrorBarcode.trim().equals("")) {
                } else {
                    request.setAttribute("msgErrorBarcode", true);
                    msgList.add(MessageList.addMessage(msgErrorBarcode));
                }

                /* establecer lista de mensajes a la peticion */
                if (!msgList.isEmpty()) {
                    request.setAttribute("msgList", msgList);
                }
            }

            /* despachar a la vista */
            request.getRequestDispatcher("/card/cardAdd.jsp").forward(request, response);

        } catch (Exception sessionException) {
            /* enviar a la vista de login */
            System.out.println("no ha iniciado session");
            request.getRequestDispatcher("/login/login.jsp").forward(request, response);
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
