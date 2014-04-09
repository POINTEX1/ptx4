/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package userCard;

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
import univesity.University;
import univesity.UniversityDAO;

/**
 *
 * @author alexander
 * @editor patricio
 */
@WebServlet(name = "UserCardGetServlet", urlPatterns = {"/UserCardGetServlet"})
public class UserCardGetServlet extends HttpServlet {

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

        /////////////////////////
        // ESTABLECER CONEXION
        /////////////////////////
        try {
            conexion = ds.getConnection();

            UserCardDAO userCardDAO = new UserCardDAO();
            userCardDAO.setConexion(conexion);

            CityDAO cityDAO = new CityDAO();
            cityDAO.setConexion(conexion);

            UniversityDAO universityDAO = new UniversityDAO();
            universityDAO.setConexion(conexion);

            ////////////////////////
            // COMPROBAR SESSION
            ////////////////////////
            try {
                /* recuperar sesion */
                HttpSession session = request.getSession(false);

                /* obtener parametros de session */
                int access = Integer.parseInt((String) session.getAttribute("access"));
                String user = (String) session.getAttribute("username");

                /* obtener los valores de session y asignar valores a la jsp */
                request.setAttribute("userJsp", user);
                request.setAttribute("access", access);

                ////////////////////////////////////////
                // RECIBIR Y COMPROBAR PARAMETROS
                ////////////////////////////////////////

                /* obtener atributos de PRG */
                String redirect = request.getParameter("redirect");
                String srut = request.getParameter("rut");
                String firstName = request.getParameter("firstName");
                String lastName = request.getParameter("lastName");
                String gender = request.getParameter("gender");
                String sidCity = request.getParameter("idCity");
                String sidUniversity = request.getParameter("idUniversity");
                String telephone = request.getParameter("telephone");
                String email = request.getParameter("email");
                String facebook = request.getParameter("facebook");
                String dateBirth = request.getParameter("dateBirth");

                /* obtener mensajes de PRG */
                String msgErrorFirstName = request.getParameter("msgErrorFirstName");
                String msgErrorLastName = request.getParameter("msgErrorLastName");
                String msgErrorTelephone = request.getParameter("msgErrorTelephone");
                String msgErrorEmail = request.getParameter("msgErrorEmail");
                String msgErrorFacebook = request.getParameter("msgErrorFacebook");
                String msgErrorDateBirth = request.getParameter("msgErrorDateBirth");
                String msgErrorPwd1 = request.getParameter("msgErrorPwd1");
                String msgErrorPwd2 = request.getParameter("msgErrorPwd2");
                String msgOk = request.getParameter("msgOk");

                /* instanciar lista de mensajes */
                Collection<Message> msgList = new ArrayList<Message>();

                /* establecer rut */
                int rut = 0;
                try {
                    rut = Integer.parseInt(srut);
                } catch (NumberFormatException n) {
                }

                /* buscar cliente por rut */
                try {
                    UserCard reg = userCardDAO.findByRut(rut);

                    if (reg != null) {
                        /* obtener atributos de reg */
                        request.setAttribute("rut", reg.getRut());
                        request.setAttribute("dv", reg.getDv());

                        /* comprobar redirect */
                        if (redirect == null || redirect.trim().equals("")) {
                            /* establecer atributos de reg */
                            request.setAttribute("firstName", reg.getFirstName());
                            request.setAttribute("lastName", reg.getLastName());
                            request.setAttribute("gender", reg.getGender());
                            request.setAttribute("idCity", reg.getIdCity());
                            request.setAttribute("idUniversity", reg.getIdUniversity());
                            request.setAttribute("telephone", reg.getTelephone());
                            request.setAttribute("email", reg.getEmail());
                            request.setAttribute("facebook", reg.getFacebook());
                            request.setAttribute("dateBirth", reg.getDateBirth());
                        } else {
                            /* establecer atributos de PRG */
                            request.setAttribute("firstName", firstName);
                            request.setAttribute("lastName", lastName);
                            request.setAttribute("gender", Integer.parseInt(gender));
                            request.setAttribute("idCity", Integer.parseInt(sidCity));
                            request.setAttribute("idUniversity", Integer.parseInt(sidUniversity));
                            request.setAttribute("telephone", telephone);
                            request.setAttribute("email", email);
                            request.setAttribute("facebook", facebook);
                            request.setAttribute("dateBirth", dateBirth);
                        }

                        ///////////////////////////
                        // COMPROBAR ERRORES
                        ///////////////////////////

                        /* comprobar firstName */
                        if (msgErrorFirstName == null || msgErrorFirstName.trim().equals("")) {
                        } else {
                            request.setAttribute("msgErrorFirstName", true);
                            msgList.add(MessageList.addMessage(msgErrorFirstName));
                        }

                        /* comprobar lastName */
                        if (msgErrorLastName == null || msgErrorLastName.trim().equals("")) {
                        } else {
                            request.setAttribute("msgErrorLastName", true);
                            msgList.add(MessageList.addMessage(msgErrorLastName));
                        }

                        /* comprobar dateBirth */
                        if (msgErrorDateBirth == null || msgErrorDateBirth.trim().equals("")) {
                        } else {
                            request.setAttribute("msgErrorDateBirth", true);
                            msgList.add(MessageList.addMessage(msgErrorDateBirth));
                        }

                        /* comprobar email */
                        if (msgErrorEmail == null || msgErrorEmail.trim().equals("")) {
                        } else {
                            request.setAttribute("msgErrorEmail", true);
                            msgList.add(MessageList.addMessage(msgErrorEmail));
                        }

                        /* comprobar telephone */
                        if (msgErrorTelephone == null || msgErrorTelephone.trim().equals("")) {
                        } else {
                            request.setAttribute("msgErrorTelephone", true);
                            msgList.add(MessageList.addMessage(msgErrorTelephone));
                        }

                        /* comprobar facebook */
                        if (msgErrorFacebook == null || msgErrorFacebook.trim().equals("")) {
                        } else {
                            request.setAttribute("msgErrorFacebook", true);
                            msgList.add(MessageList.addMessage(msgErrorFacebook));
                        }

                        /* comprobar pwd1 */
                        if (msgErrorPwd1 == null || msgErrorPwd1.trim().equals("")) {
                        } else {
                            request.setAttribute("msgErrorPwd1", true);
                            msgList.add(MessageList.addMessage(msgErrorPwd1));
                        }

                        /* comprobar pwd2 */
                        if (msgErrorPwd2 == null || msgErrorPwd2.trim().equals("")) {
                        } else {
                            request.setAttribute("msgErrorPwd2", true);
                            msgList.add(MessageList.addMessage(msgErrorPwd2));
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


                /* obtener lista de ciudades */
                try {
                    Collection<City> listCity = cityDAO.getAll();
                    request.setAttribute("listCity", listCity);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                /* obtener lista de universidades */
                try {
                    Collection<University> listUniversity = universityDAO.getAll();
                    request.setAttribute("listUniversity", listUniversity);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                /* establecer lista de mensajes */
                if (!msgList.isEmpty()) {
                    request.setAttribute("msgList", msgList);
                }

                /* despachar a la vista */
                request.getRequestDispatcher("/userCard/userCardUpdate.jsp").forward(request, response);

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
