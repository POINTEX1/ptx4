/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package univesity;

import Helpers.Message;
import Helpers.MessageList;
import city.City;
import city.CityDAO;
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
@WebServlet(name = "UniversityGetServlet", urlPatterns = {"/UniversityGetServlet"})
public class UniversityGetServlet extends HttpServlet {

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

        ///////////////////////////
        // ESTABLECER CONEXION
        ///////////////////////////
        try {
            conexion = ds.getConnection();

            UniversityDAO universityDAO = new UniversityDAO();
            universityDAO.setConexion(conexion);

            ////////////////////////
            // COMPROBAR SESSION
            ////////////////////////
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

                ////////////////////////////////////////
                // RECIBIR Y COMPROBAR PARAMETROS
                //////////////////////////////////////// 

                /* obtener atributos del PRG */
                String redirect = request.getParameter("redirect");
                String sidUniversity = request.getParameter("idUniversity");
                String nameUniversity = request.getParameter("nameUniversity");

                /* obtener mensajes de PRG */
                String msgErrorNameUniversity = request.getParameter("msgErrorNameUniversity");
                String msgErrorDup = request.getParameter("msgErrorDup");
                String msgOk = request.getParameter("msgOk");

                /* instanciar lista de mensajes */
                Collection<Message> msgList = new ArrayList<Message>();

                /* establecer id ciudad */
                int idUniversity = 0;
                try {
                    idUniversity = Integer.parseInt(sidUniversity);
                } catch (NumberFormatException n) {
                }

                /* buscar university por id */
                try {
                    University reg = universityDAO.findById(idUniversity);

                    if (reg != null) {
                        /* obtener atributos de reg */
                        request.setAttribute("idUniversity", reg.getIdUniversity());

                        /* comprobar redirect */
                        if (redirect == null || redirect.trim().equals("")) {
                            /* establecer atributos por reg */
                            request.setAttribute("nameUniversity", reg.getNameUniversity());
                        } else {
                            /* establecer atributos por PRG */
                            request.setAttribute("nameUniversity", nameUniversity);
                        }

                        ///////////////////////////////
                        // COMPROBAR ERRORES
                        ///////////////////////////////                                                

                        /* comprobar nameUniversity */
                        if (msgErrorNameUniversity == null || msgErrorNameUniversity.trim().equals("")) {
                        } else {
                            request.setAttribute("msgErrorNameUniversity", true);
                            msgList.add(MessageList.addMessage(msgErrorNameUniversity));
                        }

                        /* comprobar duplicaciones */
                        if (msgErrorDup == null || msgErrorDup.trim().equals("")) {
                        } else {
                            request.setAttribute("msgErrorDup", true);
                            msgList.add(MessageList.addMessage(msgErrorDup));
                        }

                        /* comprobar mensajes de exito */
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

                /* establecer lista de mensajes */
                if (!msgList.isEmpty()) {
                    request.setAttribute("msgList", msgList);
                }

                /* despachar a la vista */
                request.getRequestDispatcher("/university/universityUpdate.jsp").forward(request, response);

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
