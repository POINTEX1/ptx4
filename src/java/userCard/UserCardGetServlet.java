/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package userCard;

import city.City;
import city.CityDAO;
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

        try {
            /////////////////////////////////////////
            // ESTABLECER CONEXION
            /////////////////////////////////////////

            conexion = ds.getConnection();

            UserCardDAO userCardDAO = new UserCardDAO();
            userCardDAO.setConexion(conexion);

            CityDAO cityDAO = new CityDAO();
            cityDAO.setConexion(conexion);

            UniversityDAO universityDAO = new UniversityDAO();
            universityDAO.setConexion(conexion);

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

                ////////////////////////////////////////
                // RECIBIR Y COMPROBAR PARAMETROS
                ////////////////////////////////////////
                try {
                    /* obtener atributos de PRG */
                    String firstName = request.getParameter("firstName");
                    String lastName = request.getParameter("lastName");
                    String telephone = request.getParameter("telephone");
                    String email = request.getParameter("email");
                    String facebook = request.getParameter("facebook");
                    String dateBirth = request.getParameter("dateBirth");
                    String pwd1 = request.getParameter("pwd1");
                    String pwd2 = request.getParameter("pwd2");

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

                    /* obtener parametros de busqueda */
                    String rut = request.getParameter("rut");

                    /* comprobar rut */
                    if (rut == null || rut.trim().equals("")) {
                    } else {
                        /* buscar cliente */
                        try {
                            UserCard reg = userCardDAO.findByRut(Integer.parseInt(rut));
                            if (reg != null) {
                                /* obtener atributos del dao */
                                request.setAttribute("rut", reg.getRut());
                                request.setAttribute("dv", reg.getDv());
                                request.setAttribute("facebook", reg.getFacebook());
                                request.setAttribute("gender", reg.getGender());
                                request.setAttribute("idCity", reg.getIdCity());
                                request.setAttribute("idUniversity", reg.getIdUniversity());
                                /* obtener password desde la vista */
                                request.setAttribute("pwd1", pwd1);
                                request.setAttribute("pwd2", pwd2);

                                ///////////////////////////
                                // COMPROBAR ERRORES
                                ///////////////////////////

                                /* comprobar firstName */
                                if (msgErrorFirstName == null || msgErrorFirstName.trim().equals("")) {
                                    request.setAttribute("firstName", reg.getFirstName());
                                } else {
                                    request.setAttribute("msgErrorFirstName", msgErrorFirstName);
                                }

                                /* comprobar lastName */
                                if (msgErrorLastName == null || msgErrorLastName.trim().equals("")) {
                                    request.setAttribute("lastName", reg.getLastName());
                                } else {
                                    request.setAttribute("msgErrorLastName", msgErrorLastName);
                                }

                                /* comprobar telephone */
                                if (msgErrorTelephone == null || msgErrorTelephone.trim().equals("")) {
                                    request.setAttribute("telephone", reg.getTelephone());
                                } else {
                                    request.setAttribute("msgErrorTelephone", msgErrorTelephone);
                                    request.setAttribute("telephone", telephone);
                                }

                                /* comprobar email */
                                if (msgErrorEmail == null || msgErrorEmail.trim().equals("")) {
                                    request.setAttribute("email", reg.getEmail());
                                } else {
                                    request.setAttribute("msgErrorEmail", msgErrorEmail);
                                }

                                /* comprobar mensajes de exito */
                                if (msgOk == null || msgOk.trim().equals("")) {
                                    request.setAttribute("msg", "Se encontró el registro!");
                                } else {
                                    request.setAttribute("msgOk", msgOk);
                                }

                                /* comprobar dateBirth */
                                if (msgErrorDateBirth == null || msgErrorDateBirth.trim().equals("")) {
                                    request.setAttribute("dateBirth", reg.getDateBirth());
                                } else {
                                    request.setAttribute("dateBirth", dateBirth);
                                    request.setAttribute("msgErrorDateBirth", msgErrorDateBirth);
                                }

                                if (msgErrorPwd1 == null || msgErrorPwd1.trim().equals("")) {
                                } else {
                                    request.setAttribute("msgErrorPwd1", msgErrorPwd1);
                                }

                                if (msgErrorPwd2 == null || msgErrorPwd2.trim().equals("")) {
                                } else {
                                    request.setAttribute("msgErrorPwd2", msgErrorPwd2);
                                }

                            } else {
                                request.setAttribute("msgError1", "Error: No se encontró el registro.");
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
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

                } catch (Exception parameterException) {
                    request.setAttribute("msgError1", "Error: No se recibió ningún parámetro.");
                } finally {
                    request.getRequestDispatcher("/userCard/userCardUpdate.jsp").forward(request, response);
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
