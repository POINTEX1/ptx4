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
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import Services.CardService;
import java.util.ArrayList;
import java.util.Collection;
import javax.servlet.http.HttpSession;

/**
 *
 * @author alexander
 */
@WebServlet(name = "CardAddServlet", urlPatterns = {"/CardAddServlet"})
public class CardAddServlet extends HttpServlet {

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
            //////////////////////////
            // ESTABLECER CONEXION
            //////////////////////////

            conexion = ds.getConnection();

            CardDAO cardDAO = new CardDAO();
            cardDAO.setConexion(conexion);

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

                //Recibir rut de verify.jsp
                String srut = request.getParameter("rut");
                String sdv = request.getParameter("dv");
                String firstname = request.getParameter("firstname");
                String lastname = request.getParameter("lastname");
                String barcode = request.getParameter("barCode");
                String cardtype = request.getParameter("cardType");
                String btnAdd = request.getParameter("add");

                /* instanciar url */
                String url = "?redirect=ok";

                url += "&rut=" + srut;
                url += "&dv=" + sdv;
                url += "&firstname=" + firstname;
                url += "&lastname=" + lastname;
                url += "&barcode=" + barcode;
                url += "&cardtype=" + cardtype;

                /* instanciar tarjeta */
                Card card = new Card();

                boolean error = false;

                if (btnAdd != null) {
                    /* comprobar rut */
                    if (srut == null || srut.trim().equals("")) {
                        url += "&msgErrorRut=Fallo al recibir Rut.";
                        error = true;
                    } else {
                        try {
                            card.setRut(Integer.parseInt(srut));
                        } catch (NumberFormatException n) {
                            url += "&msgErrorRut=Rut inválido.";
                            error = true;
                        }
                    }

                    /* comprobar dv */
                    if (sdv == null || sdv.trim().equals("")) {
                        url += "&msgErrorDv=Fallo al recibir dv.";
                        error = true;
                    } else {
                        card.setDv(sdv);
                    }

                    /* comprobar barcode */
                    if (barcode == null || barcode.trim().equals("")) {
                        url += "&msgErrorBarcode=Debe ingresar código de barras.";
                        error = true;
                    } else {
                        try {
                            card.setBarCode(Integer.parseInt(barcode));
                            if (card.getBarCode() < 10000000) {
                                url += "&msgErrorBarcode=El código de barras debe poseer 8 dígitos.";
                                error = true;
                            } else {
                                /* comprobar duplicados */
                                try {
                                    Card aux = cardDAO.findByBarCode(card.getBarCode());
                                    if (aux != null) {
                                        url += "&msgErrorBarcode=Ya existe esta tarjeta asociada a un cliente.";
                                        error = true;
                                    }
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        } catch (NumberFormatException n) {
                            url += "&msgErroBarcode=Fallo al recibir Codigo de Barra, los valores deben ser numéricos.";
                            error = true;
                        }
                    }

                    /* comprobar firstname */
                    if (firstname == null || firstname.trim().equals("")) {
                        error = true;
                    } else {
                        card.setFirstName(firstname);
                    }

                    /* comprobar lastname */
                    if (lastname == null || lastname.trim().equals("")) {
                        error = true;
                    } else {
                        card.setLastName(lastname);
                    }

                    /* comprobar cardtype */
                    if (cardtype == null || cardtype.trim().equals("")) {
                        error = true;
                    } else {
                        try {
                            int ct = Integer.parseInt(cardtype);
                            switch (ct) {
                                case 1:
                                    card.setCardType(ct);
                                    card.setDateBeginCard(Format.currentDate());
                                    card.setDateEndCard(CardService.currentDateCardType(card.getBasic()));
                                    break;
                                case 2:
                                    card.setCardType(ct);
                                    card.setDateBeginCard(Format.currentDate());
                                    card.setDateEndCard(CardService.currentDateCardType(card.getSilver()));
                                    break;
                                case 3:
                                    card.setCardType(ct);
                                    card.setDateBeginCard(Format.currentDate());
                                    card.setDateEndCard(CardService.currentDateCardType(card.getGold()));
                                    break;
                            }
                        } catch (NumberFormatException n) {
                            error = true;
                        }
                    }

                    ///////////////////////
                    // INSERTAR REGISTRO
                    ///////////////////////
                    if (!error) {
                        try {
                            cardDAO.insert(card);
                            url += "&msgOk=Registro ingresado exitosamente.";
                        } catch (Exception sqlException) {
                            sqlException.printStackTrace();
                        }
                    }
                }

                /* send redirect */
                response.sendRedirect("CardGetAddServlet" + url);

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
