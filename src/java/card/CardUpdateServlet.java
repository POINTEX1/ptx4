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
            ///////////////////////////////////////
            // ESTABLECER CONEXION
            ////////////////////////////////////////
            conexion = ds.getConnection();

            CardDAO cardDAO = new CardDAO();
            cardDAO.setConexion(conexion);

            //////////////////////////////////////////
            // COMPROBAR SESSION
            /////////////////////////////////////////
            try {
                /* recuperar sesion */
                HttpSession session = request.getSession(false);

                /* obtener parametros de session */
                int access = Integer.parseInt((String) session.getAttribute("access"));
                String user = (String) session.getAttribute("username");

                /* obtener los valores de session y asignar valores a la jsp */
                request.setAttribute("userJsp", user);
                request.setAttribute("access", access);

                //////////////////////////////////////////
                // RECIBIR Y COMPROBAR PARAMETROS
                /////////////////////////////////////////    

                String srut = request.getParameter("rut");
                String sdv = request.getParameter("dv");
                String firstname = request.getParameter("firstName");
                String lastname = request.getParameter("lastName");
                String sbarcode = request.getParameter("barCode");
                String scardtype = request.getParameter("cardType");
                String dateBegin = request.getParameter("dateBeginCard");
                String dateEnd = request.getParameter("dateEndCard");

                Card card = new Card();

                boolean error = false;

                /* instanciar string url */
                String url = "?a=target";

                /* comprobar rut */
                url = url + "&rut=" + srut;
                if (srut == null || srut.trim().equals("")) {
                    url = url + "&msgErrorRut=Error al recibir RUT.";
                    error = true;
                } else {
                    try {
                        card.setRut(Integer.parseInt(srut));
                    } catch (NumberFormatException n) {
                        url = url + "&msgErrorRut=Error al recibir RUT.";
                        error = true;
                    }
                }

                /* comprobar dv */
                url = url + "&dv=" + sdv;
                if (sdv == null || sdv.trim().equals("")) {
                    url = url + "&msgErrorDv=Error al recibir DV.";
                    error = true;
                } else {
                    card.setDv(sdv);
                }

                /* comprobar firstname */
                url = url + "&firstName=" + firstname;
                if (firstname == null || firstname.trim().equals("")) {
                    url = url + "&msgErrorFirstName=Error al recibir nombres.";
                    error = true;
                } else {
                    card.setFirstName(firstname);
                }

                /* comprobar lastname */
                url = url + "&lastName=" + lastname;
                if (lastname == null || lastname.trim().equals("")) {
                    url = url + "&msgErrorLastName=Error al recibir apellidos .";
                    error = true;
                } else {
                    card.setLastName(lastname);
                }

                /* comprobar barcode */
                url = url + "&barCode=" + sbarcode;
                if (sbarcode == null || sbarcode.trim().equals("")) {
                    url = url + "&msgErrorBarCode=Error al recibir código de barras.";
                    error = true;
                } else {
                    try {
                        card.setBarCode(Integer.parseInt(sbarcode));
                    } catch (NumberFormatException n) {
                        url = url + "&msgErrorBarCode=Error al recibir código de barras, debe ser numérico.";
                        error = true;
                    }
                }

                /* comprobar type */
                url = url + "&cardType=" + scardtype;
                if (scardtype == null || scardtype.trim().equals("")) {
                    url = url + "&msgErrorType=Error al recibir tipo tarjeta.";
                    error = true;
                } else {
                    try {
                        card.setCardType(Integer.parseInt(scardtype));
                    } catch (NumberFormatException n) {
                        url = url + "&msgErrorType=Error al recibir tipo tarjeta.";
                        error = true;
                    }
                }

                /* comprobar date begin */
                url = url + "&dateBegin=" + dateBegin;
                if (dateBegin == null || dateBegin.trim().equals("")) {
                    url = url + "&msgErrorDateBegin=Error al recibir fecha de inicio.";
                    error = true;
                } else {
                    card.setDateBeginCard(dateBegin);
                    /* comprobar date end */
                    url = url + "&dateEnd=" + dateEnd;
                    if (dateEnd == null || dateEnd.trim().equals("")) {
                        url = url + "&msgErrorDateEnd=Error al recibir fecha de término.";
                        error = true;
                    } else {
                        /* comparar fechas */
                        card.setDateBeginCard(dateBegin);
                        card.setDateEndCard(dateEnd);
                        //System.out.println("comparar fecha inicio fecha fin: " + card.getDateBeginCard().compareTo(card.getDateEndCard()));
                        if (card.getDateBeginCard().compareTo(card.getDateEndCard()) >= 0) {
                            url = url + "&msgErrorDateBegin=Error: La fecha de término deber ser mayor que la fecha de inicio.";
                            error = true;
                        }
                    }
                }

                ////////////////////////////////////////
                // INSERTAR REGISTRO
                ////////////////////////////////////////

                if (!error) {
                    /* comprobar existencia */
                    Card aux = cardDAO.findByBarCode(card.getBarCode());
                    if (aux != null) {
                        try {
                            cardDAO.update(card);
                            url = url + "&msgOk=Registro actualizado exitosamente!";
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        url = url + "msgErrorFound=Error: La tarjeta no ha sido encontrada o ha sido eliminada mientras actualizaba.";
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
