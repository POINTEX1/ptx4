/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientExchangeCheck;

import clientPromoCheckout.ClientPromoCheckout;
import exchangeable.ExchangeableDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import place.PlaceDAO;
import userCard.UserCardDAO;

/**
 *
 * @author Joseph
 */
@WebServlet(name = "ClientExchangeCheckMainServlet", urlPatterns = {"/ClientExchangeCheckMainServlet"})
public class ClientExchangeCheckMainServlet extends HttpServlet {

    @Resource(name = "jdbc/POINTEX1")
    private DataSource ds;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        request.setCharacterEncoding("UTF-8");
        Connection conexion = null;
        try {
            /////////////////////////////////////////
            // ESTABLECER CONEXION
            /////////////////////////////////////////

            conexion = ds.getConnection();
            /*IMPORTANTE!!! : Continuar cuando DAO este completo*/

            ClientExchangeCheckDAO cecDAO = new ClientExchangeCheckDAO();
            cecDAO.setConnection(conexion);

            ExchangeableDAO eDAO = new ExchangeableDAO();
            eDAO.setConexion(conexion);

            UserCardDAO ucDAO = new UserCardDAO();
            ucDAO.setConexion(conexion);

            PlaceDAO pDAO = new PlaceDAO();
            pDAO.setConexion(conexion);

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
                        //////////////////////////////////////
                        // RECIBIR Y COMPROBAR PARAMETROS
                        //////////////////////////////////////

                        String btnDelRow = request.getParameter("btnDelRow");
                        String btnDelCol = request.getParameter("btnDelCol");

                        ClientExchangeCheck clientCheck = new ClientExchangeCheck();

                        //////////////////////////////////////////
                        // ELIMINAR POR REGISTRO
                        //////////////////////////////////////////
                        if (btnDelRow != null) {
                            /* recibir parametros */
                            clientCheck.setIdCheck(Integer.parseInt(request.getParameter("idCheck")));

                            try {
                                cecDAO.delete(clientCheck.getIdCheck());
                                request.setAttribute("msgDel", "Un Registro ha sido eliminado.");
                            } catch (Exception ex) {
                                request.setAttribute("msgErrorReference", "Error: No puede eliminar, existen clientes asociados.");
                            }
                        }

                        //////////////////////////////////////////
                        // ELIMINAR VARIOS REGISTROS
                        //////////////////////////////////////////
                        if (btnDelCol != null) {
                          try {
                                /* recibir parametros */
                                String[] outerArray = request.getParameterValues("chk");
                                int cont = 0;
                                int i = 0;
                                while (outerArray[i] != null) {
                                    try {
                                        cecDAO.delete(Integer.parseInt(outerArray[i]));
                                        cont++;
                                        if (cont == 1) {
                                            request.setAttribute("msgDel", "Un registro ha sido eliminado.");
                                        } else if (cont > 1) {
                                            request.setAttribute("msgDel", cont + " registros han sido eliminados.");
                                        }
                                    } catch (Exception deleteException) {
                                        request.setAttribute("msgErrorReference", "Error: No puede eliminar, existen clientes asociados.");
                                    }
                                    i++;
                                }
                          } catch (Exception parameterException) {
                          }
                       
                        }
                        
                        
                    } catch (Exception connectionException) {
                        connectionException.printStackTrace();
                    } finally {
                        try {
                      
                        } catch (Exception noGestionar) {
                        }
                    }
                }

                try {
                    Collection<ClientExchangeCheck> list = cecDAO.getAll();
                    request.setAttribute("list", list);

                    if (list.size() == 1) {
                        request.setAttribute("msg", "1 registro encontrado en la base de datos.");
                    } else if (list.size() > 1) {
                        request.setAttribute("msg", list.size() + " registros encontrados en la base de datos.");
                    } else if (list.isEmpty()) {
                        request.setAttribute("msg", "No hay registros encontrado en la base de datos.");
                    }
                } catch (Exception ex) {
                }

            } catch (Exception parameterException) {
            } finally {
                request.getRequestDispatcher("/clientExchangeCheck/clientExchangeCheck.jsp").forward(request, response);
            }

        } catch (Exception sessionException) {
            /* enviar a la vista de login */
            System.out.println("no ha iniciado session");
            request.getRequestDispatcher("/login/login.jsp").forward(request, response);
        }finally {
            try {
               conexion.close();
            } catch (Exception noGestionar) {
            }
    }
}

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
/**
 * Handles the HTTP <code>GET</code> method.
 *
 * @param request servlet request
 * @param response servlet response
 * @throws ServletException if a servlet-specific error occurs
 * @throws IOException if an I/O error occurs
 */
@Override
        protected void doGet
                (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                    try {
                        processRequest(request, response);
                    



} catch (SQLException ex) {
                        Logger.getLogger(ClientExchangeCheckMainServlet.class  

.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                /**
                 * Handles the HTTP <code>POST</code> method.
                 *
                 * @param request servlet request
                 * @param response servlet response
                 * @throws ServletException if a servlet-specific error occurs
                 * @throws IOException if an I/O error occurs
                 */
                @Override
        protected void doPost
                (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                    try {
                        processRequest(request, response);
                    



} catch (SQLException ex) {
                        Logger.getLogger(ClientExchangeCheckMainServlet.class  

.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                /**
                 * Returns a short description of the servlet.
                 *
                 * @return a String containing servlet description
                 */
                @Override
        public String getServletInfo() {
               return "Short description";
        }
}
