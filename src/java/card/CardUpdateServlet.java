/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package card;

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
 * @author alexander
 */
@WebServlet(name = "CardUpdateServlet", urlPatterns = {"/CardUpdateServlet"})
public class CardUpdateServlet extends HttpServlet {

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

            //////////////////////////
            // COMPROBAR SESSION
            //////////////////////////
            try {
                /* recuperar sesion */
                HttpSession session = request.getSession(false);

                /* obtener parametros de session */
                int access = Integer.parseInt((String) session.getAttribute("access"));
                String user = (String) session.getAttribute("username");

                /* obtener los valores de session y asignar valores a la jsp */
                request.setAttribute("userJsp", user);
                request.setAttribute("access", access);

                /////////////////////////////////////
                // RECIBIR Y COMPROBAR PARAMETROS
                /////////////////////////////////////

                String srut = request.getParameter("rut");
                String sdv = request.getParameter("dv");
                String firstname = request.getParameter("firstName");
                String lastname = request.getParameter("lastName");
                String sbarcode = request.getParameter("barCode");
                String scardtype = request.getParameter("cardType");
                String dateBegin = request.getParameter("dateBeginCard");
                String dateEnd = request.getParameter("dateEndCard");

                /* instanciar string url */
                String url = "?a=target";

                /* establecer parametros para PRG */
                url += "&rut=" + srut;
                url += "&dv=" + sdv;
                url += "&firstName=" + firstname;
                url += "&lastName=" + lastname;
                url += "&barCode=" + sbarcode;
                url += "&cardType=" + scardtype;
                url += "&dateBegin=" + dateBegin;
                url += "&dateEnd=" + dateEnd;

                Card card = new Card();

                boolean error = false;

                /* comprobar rut */
                if (srut == null || srut.trim().equals("")) {
                    error = true;
                } else {
                    try {
                        card.setRut(Integer.parseInt(srut));
                    } catch (NumberFormatException n) {
                        error = true;
                    }
                }

                /* comprobar dv */
                if (sdv == null || sdv.trim().equals("")) {
                    error = true;
                } else {
                    card.setDv(sdv);
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

                /* comprobar barcode */
                if (sbarcode == null || sbarcode.trim().equals("")) {
                    error = true;
                } else {
                    try {
                        card.setBarCode(Integer.parseInt(sbarcode));
                    } catch (NumberFormatException n) {
                        error = true;
                    }
                }

                /* comprobar type */
                if (scardtype == null || scardtype.trim().equals("")) {
                    error = true;
                } else {
                    try {
                        card.setCardType(Integer.parseInt(scardtype));
                    } catch (NumberFormatException n) {
                        error = true;
                    }
                }

                /* comprobar date begin */
                if (dateBegin == null || dateBegin.trim().equals("")) {
                    url = url + "&msgErrorDateBegin=Debe ingresar fecha de inicio.";
                    error = true;
                } else {
                    card.setDateBeginCard(dateBegin);
                    /* comprobar date end */
                    if (dateEnd == null || dateEnd.trim().equals("")) {
                        url = url + "&msgErrorDateEnd=Debe ingresar fecha de término.";
                        error = true;
                    } else {
                        /* comparar fechas */
                        card.setDateBeginCard(dateBegin);
                        card.setDateEndCard(dateEnd);
                        //System.out.println("comparar fecha inicio fecha fin: " + card.getDateBeginCard().compareTo(card.getDateEndCard()));
                        if (card.getDateBeginCard().compareTo(card.getDateEndCard()) >= 0) {
                            url = url + "&msgErrorDateBegin=La fecha de término deber ser mayor que la fecha de inicio.";
                            error = true;
                        }
                    }
                }

                ////////////////////////
                // INSERTAR REGISTRO
                ////////////////////////

                if (!error) {
                    try {
                        cardDAO.update(card);
                        url = url + "&msgOk=Registro actualizado exitosamente.";
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                response.sendRedirect("/POINTEX1/CardGetServlet" + url);

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
