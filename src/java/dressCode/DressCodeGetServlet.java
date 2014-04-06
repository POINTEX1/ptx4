/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dressCode;

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
 * @author patricio
 */
@WebServlet(name = "DressCodeGetServlet", urlPatterns = {"/DressCodeGetServlet"})
public class DressCodeGetServlet extends HttpServlet {

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

            DressCodeDAO dressCodeDAO = new DressCodeDAO();
            dressCodeDAO.setConexion(conexion);

            /////////////////////////
            // COMPROBAR SESSION
            /////////////////////////
            try {
                /* recuperar sesion */
                HttpSession session = request.getSession(false);

                /* obtener los valores de session */
                String userJsp = (String) session.getAttribute("username");
                String sAccess = (String) session.getAttribute("access");
                int access = Integer.parseInt(sAccess);

                /* asignar valores a la jsp */
                request.setAttribute("userJsp", userJsp);
                request.setAttribute("access", access);

                ////////////////////////////////////
                // RECIBIR Y COMPROBAR PARAMETROS
                ////////////////////////////////////

                /* recibir parametros de PRG */
                String nameDressCode = request.getParameter("nameDressCode");
                String menDetails = request.getParameter("menDetails");
                String womenDetails = request.getParameter("womenDetails");
                String urlImage = request.getParameter("urlImage");

                /* recibir mensaje de PRG */
                String msgErrorNameDressCode = request.getParameter("msgErrorNameDressCode");
                String msgErrorMenDetails = request.getParameter("msgErrorMenDetails");
                String msgErrorWomenDetails = request.getParameter("msgErrorWomenDetails");
                String msgErrorUrlImage = request.getParameter("msgErrorUrlImage");
                String msgOk = request.getParameter("msgOk");

                /* parametros de busqueda */
                String sidDressCode = request.getParameter("idDressCode");

                /* instanciar codigo de vestir */
                DressCode dressCode = new DressCode();

                /* instanciar lista de mensajes */
                Collection<Message> msgList = new ArrayList<Message>();

                /* comprobar id dressCode */
                if (sidDressCode == null || sidDressCode.trim().equals("")) {
                } else {
                    /*establecer id */
                    try {
                        dressCode.setIdDressCode(Integer.parseInt(sidDressCode));
                    } catch (NumberFormatException n) {
                    }
                    /* buscar dressCode */
                    try {
                        DressCode reg = dressCodeDAO.findById(dressCode.getIdDressCode());

                        if (reg != null) {
                            /* obtener atributos del dao */
                            request.setAttribute("idDressCode", reg.getIdDressCode());

                            /* comprobar nameDressCode */
                            if (msgErrorNameDressCode == null || msgErrorNameDressCode.trim().equals("")) {
                                request.setAttribute("nameDressCode", reg.getNameDressCode());
                            } else {
                                request.setAttribute("msgErrorNameDressCode", true);
                                msgList.add(MessageList.addMessage(msgErrorNameDressCode));
                                request.setAttribute("nameDressCode", nameDressCode);
                            }

                            /* comprobar men Details */
                            if (msgErrorMenDetails == null || msgErrorMenDetails.trim().equals("")) {
                                request.setAttribute("menDetails", reg.getMenDetails());
                            } else {
                                request.setAttribute("msgErrorMenDetails", true);
                                msgList.add(MessageList.addMessage(msgErrorMenDetails));
                                request.setAttribute("menDetails", menDetails);
                            }

                            /* comprobar woman details */
                            if (msgErrorWomenDetails == null || msgErrorWomenDetails.trim().equals("")) {
                                request.setAttribute("womenDetails", reg.getWomenDetails());
                            } else {
                                request.setAttribute("msgErrorWomenDetails", true);
                                msgList.add(MessageList.addMessage(msgErrorWomenDetails));
                                request.setAttribute("womenDetails", womenDetails);
                            }

                            /* comprobar url */
                            if (msgErrorUrlImage == null || msgErrorUrlImage.trim().equals("")) {
                                request.setAttribute("urlImage", reg.getUrlImage());
                            } else {
                                request.setAttribute("msgErrorUrlImage", true);
                                msgList.add(MessageList.addMessage(msgErrorUrlImage));
                                request.setAttribute("urlImage", urlImage);
                            }

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
                }

                /* establecer lista de mensajes a la peticion */
                if (!msgList.isEmpty()) {
                    request.setAttribute("msgList", msgList);
                }

                /* despachar a la vista */
                request.getRequestDispatcher("/dressCode/dressCodeUpdate.jsp").forward(request, response);

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
