/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clientPromoCheckout;

import Helpers.Rut;
import clientPromo.ClientPromo;
import clientPromo.ClientPromoDAO;
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
import userCard.UserCard;
import userCard.UserCardDAO;

/**
 *
 * @author patricio
 */
@WebServlet(name = "ClientPromoCheckoutAddServlet", urlPatterns = {"/ClientPromoCheckoutAddServlet"})
public class ClientPromoCheckoutAddServlet extends HttpServlet {

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
            //////////////////////////////////////////
            // ESTABLECER CONEXION
            //////////////////////////////////////////

            conexion = ds.getConnection();

            UserCardDAO userCardDAO = new UserCardDAO();
            userCardDAO.setConexion(conexion);

            ClientPromoDAO pglDAO = new ClientPromoDAO();
            pglDAO.setConexion(conexion);

            ClientPromoCheckoutDAO clientPromoCheckoutDAO = new ClientPromoCheckoutDAO();
            clientPromoCheckoutDAO.setConexion(conexion);

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

                    /////////////////////////////////////////
                    // RECIBIR Y COMPROBAR PARAMETROS
                    /////////////////////////////////////////
                    try {
                        String btnAdd = request.getParameter("add");
                        String sidPromo = request.getParameter("idPromo");
                        String srut = request.getParameter("rut");

                        ClientPromoCheckout checkout = new ClientPromoCheckout();

                        boolean error = false;

                        /* comprobar add button */
                        if (btnAdd == null) {
                            request.setAttribute("msg", "Ingrese una promoción para un cliente.");
                        } else {

                            /* comprobar id promo */
                            if (sidPromo == null || sidPromo.trim().equals("")) {
                                error = true;
                            } else {
                                request.setAttribute("idPromo", sidPromo);
                                try {
                                    checkout.setIdPromo(Integer.parseInt(sidPromo));
                                    if (checkout.getIdPromo() == 0) {
                                        request.setAttribute("msgErrorPromo", "Error: El id Promo debe ser mayor a cero. ");
                                        error = true;
                                    }
                                } catch (NumberFormatException n) {
                                    request.setAttribute("msgErrorPromo", "Error: El id Promo debe ser numérico. ");
                                    error = true;
                                }
                            }

                            /* comprobar rut */
                            if (srut == null || srut.trim().equals("")) {
                                request.setAttribute("msgErrorRut", "Error: Rut inválido. ");
                                error = true;
                            } else {
                                request.setAttribute("rut", srut);
                                try {
                                    if (!Rut.validateRut(srut)) {
                                        error = true;
                                        request.setAttribute("msgErrorRut", "Error: Rut inválido. ");
                                    } else {
                                        checkout.setRut(Rut.getRut(srut));
                                        checkout.setDv(Rut.getDv(srut));
                                    }
                                } catch (Exception ex) {
                                    request.setAttribute("msgErrorRut", "Error: Rut inválido. ");
                                    error = true;
                                }
                            }

                            if (!error) {
                                /* comprobar si existe cliente */
                                UserCard user = userCardDAO.findByRut(checkout.getRut());
                                if (user == null) {
                                    request.setAttribute("msgErrorRut", "Error: Cliente no encontrado.");
                                } else {
                                    /* verificar si existe registro client_promo */
                                    ClientPromo aux = pglDAO.findbyRutIdPromo(checkout.getRut(), checkout.getIdPromo());
                                    if (aux == null) {
                                        request.setAttribute("msgErrorFound", "Error: El cliente no posee asociada esta promoción.");
                                    } else {
                                        /* insertar registro */
                                        try {
                                            clientPromoCheckoutDAO.insert(checkout);
                                            request.setAttribute("msgOk", "Registro ingresado exitosamente! ");
                                        } catch (Exception sqlException) {
                                            sqlException.printStackTrace();
                                            request.setAttribute("msgErrorDup", "Error de inserción, verifique los campos.");
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception parameterException) {
                    } finally {
                        request.getRequestDispatcher("/clientPromoCheckout/clientPromoCheckoutAdd.jsp").forward(request, response);
                    }
                }
            } catch (Exception sessionException) {
                sessionException.printStackTrace();
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
