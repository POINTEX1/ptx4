/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientExchangeCheck;

import Helpers.Message;
import Helpers.MessageList;
import Helpers.Rut;
import exchangeable.Exchangeable;
import exchangeable.ExchangeableDAO;
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
import userCard.UserCard;
import userCard.UserCardDAO;

/**
 *
 * @author Joseph
 */
@WebServlet(name = "ClientExchangeAddServlet", urlPatterns = {"/ClientExchangeAddServlet"})
public class ClientExchangeAddServlet extends HttpServlet {

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
            /////////////////////////
            // ESTABLECER CONEXION
            /////////////////////////

            conexion = ds.getConnection();

            ClientExchangeCheckDAO cecDAO = new ClientExchangeCheckDAO();
            cecDAO.setConnection(conexion);

            ExchangeableDAO eDAO = new ExchangeableDAO();
            eDAO.setConexion(conexion);

            UserCardDAO ucDAO = new UserCardDAO();
            ucDAO.setConexion(conexion);

            /////////////////////////
            // COMPROBAR SESSION
            /////////////////////////
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

                    ////////////////////////////////////
                    // RECIBIR Y COMPROBAR PARAMETROS
                    ////////////////////////////////////

                    String btnAdd = request.getParameter("add");
                    String sidExchange = request.getParameter("idExchange");
                    String srut = request.getParameter("rut");

                    /* instanciar clientExchangeCheck*/
                    ClientExchangeCheck exchangeCheck = new ClientExchangeCheck();

                    /* instanciar lista de mensajes */
                    Collection<Message> msgList = new ArrayList<Message>();

                    boolean error = false;

                    /* comprobar add button */
                    if (btnAdd == null) {
                        request.setAttribute("msg", "Ingrese una promoción para un cliente.");
                    } else {

                        /* comprobar id Exchangeable */
                        if (sidExchange == null || sidExchange.trim().equals("")) {
                            error = true;
                        } else {
                            request.setAttribute("idExchangeable", sidExchange);
                            try {
                                exchangeCheck.setIdExchangeable(Integer.parseInt(sidExchange));
                            } catch (NumberFormatException n) {
                                error = true;
                            }
                        }

                        /* comprobar rut */
                        if (srut == null || srut.trim().equals("")) {
                            request.setAttribute("msgErrorRut", true);
                            msgList.add(MessageList.addMessage("Debe ingresar RUT."));
                            error = true;
                        } else {
                            request.setAttribute("rut", srut);
                            try {
                                if (!Rut.validateRut(srut)) {
                                    error = true;
                                    request.setAttribute("msgErrorRut", true);
                                    msgList.add(MessageList.addMessage("Rut inválido."));
                                } else {
                                    exchangeCheck.setRut(Rut.getRut(srut));
                                    exchangeCheck.setDv(Rut.getDv(srut));
                                }
                            } catch (Exception ex) {
                                request.setAttribute("msgErrorRut", true);
                                msgList.add(MessageList.addMessage("Rut inválido."));
                                error = true;
                            }
                        }

                        if (!error) {
                            /* comprobar si existe cliente */
                            UserCard user = ucDAO.findByRut(exchangeCheck.getRut());
                            if (user == null) {
                                request.setAttribute("msgErrorUserFound", true);
                                msgList.add(MessageList.addMessage("Cliente no encontrado."));
                            } else {
                                /* verificar si existe registro en ClientExchangeCheck */
                                ClientExchangeCheck aux = null;
                                try {
                                    aux = cecDAO.findByRutIdCheck(exchangeCheck);
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                                if (aux == null) {
                                    request.setAttribute("msgErrorUserFound", true);
                                    msgList.add(MessageList.addMessage("El cliente no posee asociada esta promoción."));
                                } else {
                                    /* insertar registro */
                                    try {
                                        cecDAO.insert(exchangeCheck);
                                        request.setAttribute("msgOk", "Registro ingresado exitosamente! ");
                                    } catch (Exception sqlException) {
                                        sqlException.printStackTrace();
                                        request.setAttribute("msgErrorDup", true);
                                        msgList.add(MessageList.addMessage("Error de inserción, verifique los campos."));
                                    }
                                }
                            }
                        }

                    }

                    /* establecer lista de mensajes a la peticion */
                    if (!msgList.isEmpty()) {
                        request.setAttribute("msgList", msgList);
                    }

                    /* obtener lista de canjeables */
                    try {
                        Collection<Exchangeable> listE = eDAO.getAll();
                        request.setAttribute("list", listE);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    /* despachar a la vista */
                    request.getRequestDispatcher("/clientExchangeCheck/clientExchangeCheckAdd.jsp").forward(request, response);
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
