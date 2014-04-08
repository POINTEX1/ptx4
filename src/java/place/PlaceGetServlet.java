/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package place;

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
 * @author patricio alberto
 */
@WebServlet(name = "PlaceGetServlet", urlPatterns = {"/PlaceGetServlet"})
public class PlaceGetServlet extends HttpServlet {

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

            PlaceDAO placeDAO = new PlaceDAO();
            placeDAO.setConexion(conexion);

            CityDAO cityDAO = new CityDAO();
            cityDAO.setConexion(conexion);

            ////////////////////////
            // COMPROBAR SESSION
            ////////////////////////
            try {
                /* recuperar sesion */
                HttpSession session = request.getSession(false);

                /* obtener parametros de session */
                int access = Integer.parseInt((String) session.getAttribute("access"));
                String username = (String) session.getAttribute("username");

                /* comprobar permisos de usuario */
                if (access != 777) {
                    request.getRequestDispatcher("/ForbiddenServlet").forward(request, response);
                } else {

                    /* obtener los valores de session y asignar valores a la jsp */
                    request.setAttribute("userJsp", username);
                    request.setAttribute("access", access);
                    request.setAttribute("su", 777); //superuser                    

                    /////////////////////////////////////////
                    // RECIBIR Y COMPROBAR PARAMETROS
                    /////////////////////////////////////////

                    /* obtener atributos de PRG */
                    String redirect = request.getParameter("redirect");
                    String sidPlace = request.getParameter("idPlace");
                    String sidCity = request.getParameter("idCity");
                    String namePlace = request.getParameter("namePlace");
                    String address = request.getParameter("address");
                    String contact = request.getParameter("contact");
                    String description = request.getParameter("description");
                    String status = request.getParameter("status");
                    String urlImage = request.getParameter("urlImage");
                    String urlLogo = request.getParameter("urlLogo");

                    /* obtener mensajes de PRG */
                    String msgOk = request.getParameter("msgOk");
                    String msgErrorNamePlace = request.getParameter("msgErrorNamePlace");
                    String msgErrorAddress = request.getParameter("msgErrorAddress");
                    String msgErrorContact = request.getParameter("msgErrorContact");
                    String msgErrorDes = request.getParameter("msgErrorDes");
                    String msgErrorUrlImage = request.getParameter("msgErrorUrlImage");
                    String msgErrorUrlLogo = request.getParameter("msgErrorUrlLogo");
                    String msgErrorDup = request.getParameter("msgErrorDup");

                    /* instanciar lista de mensajes */
                    Collection<Message> msgList = new ArrayList<Message>();

                    /* establecer id place */
                    int id = 0;
                    try {
                        id = Integer.parseInt(sidPlace);
                    } catch (NumberFormatException n) {
                    }

                    /* buscar place por id */
                    try {
                        Place reg = placeDAO.findById(id);

                        if (reg != null) {
                            /* obtener atributos de reg */
                            request.setAttribute("idPlace", reg.getIdPlace());

                            /* comprobar redirect */
                            if (redirect == null || redirect.trim().equals("")) {
                                /* establecer atributos de reg */
                                request.setAttribute("idCity", reg.getIdCity());
                                request.setAttribute("status", reg.getStatus());
                                request.setAttribute("namePlace", reg.getNamePlace());
                                request.setAttribute("address", reg.getAddress());
                                request.setAttribute("contact", reg.getContact());
                                request.setAttribute("description", reg.getDescription());
                                request.setAttribute("urlImage", reg.getUrlImage());
                                request.setAttribute("urlLogo", reg.getUrlLogo());
                            } else {
                                /* estblecer atributos de PRG */
                                request.setAttribute("idCity", Integer.parseInt(sidCity));
                                request.setAttribute("status", Integer.parseInt(status));
                                request.setAttribute("namePlace", namePlace);
                                request.setAttribute("address", address);
                                request.setAttribute("contact", contact);
                                request.setAttribute("description", description);
                                request.setAttribute("urlImage", urlImage);
                                request.setAttribute("urlLogo", urlLogo);
                            }

                            /* comprobar name place */
                            if (msgErrorNamePlace == null || msgErrorNamePlace.trim().equals("")) {
                            } else {
                                request.setAttribute("msgErrorNamePlace", true);
                                msgList.add(MessageList.addMessage(msgErrorNamePlace));
                            }

                            /* comprobar address */
                            if (msgErrorAddress == null || msgErrorAddress.trim().equals("")) {
                            } else {
                                request.setAttribute("msgErrorAddress", true);
                                msgList.add(MessageList.addMessage(msgErrorAddress));
                            }

                            /* comprobar contact */
                            if (msgErrorContact == null || msgErrorContact.trim().equals("")) {
                            } else {
                                request.setAttribute("msgErrorContact", true);
                                msgList.add(MessageList.addMessage(msgErrorContact));
                            }

                            /* comprobar description */
                            if (msgErrorDes == null || msgErrorDes.trim().equals("")) {
                            } else {
                                request.setAttribute("msgErrorDes", true);
                                msgList.add(MessageList.addMessage(msgErrorDes));
                            }

                            /* comprobar urlImage */
                            if (msgErrorUrlImage == null || msgErrorUrlImage.trim().equals("")) {
                            } else {
                                request.setAttribute("msgErrorUrlImage", true);
                                msgList.add(MessageList.addMessage(msgErrorUrlImage));
                            }

                            /* comprobar url Logo */
                            if (msgErrorUrlLogo == null || msgErrorUrlLogo.trim().equals("")) {
                            } else {
                                request.setAttribute("msgErrorUrlLogo", true);
                                msgList.add(MessageList.addMessage(msgErrorUrlLogo));
                            }

                            /* comprobar duplicados */
                            if (msgErrorDup == null || msgErrorDup.trim().equals("")) {
                            } else {
                                request.setAttribute("msgErrorDup", true);
                                msgList.add(MessageList.addMessage(msgErrorDup));
                            }

                            /* comprobar msgOk y msg */
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

                    /* obtener listado de ciudades */
                    try {
                        Collection<City> listCity = cityDAO.getAll();
                        request.setAttribute("listCity", listCity);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    /* despachar a la vista */
                    request.getRequestDispatcher("/place/placeUpdate.jsp").forward(request, response);
                }
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
