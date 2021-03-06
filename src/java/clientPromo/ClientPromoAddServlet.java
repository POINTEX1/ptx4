/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clientPromo;

import Helpers.Rut;
import java.io.IOException;
import java.sql.Connection;
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
import promo.Promo;
import promo.PromoDAO;
import userCard.UserCard;
import userCard.UserCardDAO;

/**
 *
 * @author patricio alberto
 */
@WebServlet(name = "ClientPromoAddServlet", urlPatterns = {"/ClientPromoAddServlet"})
public class ClientPromoAddServlet extends HttpServlet {

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

            ClientPromoDAO pglDAO = new ClientPromoDAO();
            pglDAO.setConexion(conexion);

            PlaceDAO placeDAO = new PlaceDAO();
            placeDAO.setConexion(conexion);

            PromoDAO promoDAO = new PromoDAO();
            promoDAO.setConexion(conexion);

            UserCardDAO usercardDAO = new UserCardDAO();
            usercardDAO.setConexion(conexion);

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

                    try {
                        /////////////////////////////////////////
                        // RECIBIR Y COMPROBAR PARAMETROS
                        /////////////////////////////////////////

                        String btnAdd = request.getParameter("add");
                        String sidPlace = request.getParameter("idPlace");
                        String sidPromo = request.getParameter("idPromo");
                        String srut = request.getParameter("rut");

                        ClientPromo pglReg = new ClientPromo();

                        boolean error = false;

                        if (btnAdd == null) {
                            request.setAttribute("msg", "Ingrese una promoción para un cliente.");
                        } else {
                            /* comprobar id place */
                            if (sidPlace == null || sidPlace.trim().equals("")) {
                                error = true;
                            } else {
                                try {
                                    pglReg.setIdPlace(Integer.parseInt(sidPlace));
                                } catch (NumberFormatException n) {
                                    error = true;
                                }
                            }

                            /* comprobar id promo */
                            if (sidPromo == null || sidPromo.trim().equals("")) {
                                error = true;
                            } else {
                                try {
                                    pglReg.setIdPromo(Integer.parseInt(sidPromo));
                                    if (pglReg.getIdPromo() == 0) {
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
                                        request.setAttribute("msgErrorRut", "Error: Rut inválido. ");
                                        error = true;
                                    } else {
                                        pglReg.setRut(Rut.getRut(srut));
                                        pglReg.setDv(Rut.getDv(srut));
                                    }
                                } catch (Exception ex) {
                                    request.setAttribute("msgErrorRut", "Error: Rut inválido. ");
                                    error = true;
                                }
                            }

                            if (!error) {
                                /* obtener promo_gift de place */
                                Promo promoReg = new Promo();

                                promoReg.setIdPlace(pglReg.getIdPlace());
                                promoReg.setIdPromo(pglReg.getIdPromo());

                                /* verificar si existe el user_card */
                                UserCard reg = usercardDAO.findByRut(pglReg.getRut());
                                if (reg == null) {
                                    request.setAttribute("msgErrorUserFound", "Error: No existe este usuario. ");
                                    error = true;
                                }

                                /* verificar si existe promo */
                                Promo pgReg = promoDAO.findbyIdPromo(promoReg.getIdPromo());
                                if (pgReg != null) {
                                    /* verificar si esta duplicada en tabla client_promo */
                                    ClientPromo aux = pglDAO.findbyRutIdPromo(pglReg.getRut(), pglReg.getIdPromo());
                                    if (aux != null) {
                                        request.setAttribute("msgErrorDup", "Error: ya existe esta Promoción para el cliente.");
                                        error = true;
                                    }
                                } else {
                                    request.setAttribute("msgErrorFound", "Error: No existe esta promoción en este lugar.");
                                    error = true;
                                }
                            }
                            if (!error) {
                                try {
                                    pglDAO.insert(pglReg);
                                    request.setAttribute("msgOk", "Registro ingresado exitosamente! ");
                                } catch (Exception sqlException) {
                                    request.setAttribute("msgErrorDup", "Error de inserción: ya existe esta Promoción para el cliente.");
                                }
                            }
                        }

                        /* obtener lista de lugares */
                        try {
                            Collection<Place> listPlace = placeDAO.getAll();
                            request.setAttribute("listPlace", listPlace);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                        request.setAttribute("pglReg", pglReg);

                    } catch (Exception parameterException) {
                    } finally {
                        request.getRequestDispatcher("/clientPromo/clientPromoAdd.jsp").forward(request, response);
                    }
                }
            } catch (Exception sessionException) {
                /* enviar a la vista de login */
                System.out.println("no ha iniciado session");
                request.getRequestDispatcher("/login/login.jsp").forward(request, response);
                sessionException.printStackTrace();
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
