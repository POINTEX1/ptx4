/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package userCard;

import Helpers.Format;
import Helpers.Rut;
import Helpers.StringMD;
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
 */
@WebServlet(name = "UserCardUpdateServlet", urlPatterns = {"/UserCardUpdateServlet"})
public class UserCardUpdateServlet extends HttpServlet {

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

            UserCardDAO usercardDAO = new UserCardDAO();
            usercardDAO.setConexion(conexion);

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

                /////////////////////////////////////////
                // RECIBIR Y COMPROBAR PARAMETROS
                ////////////////////////////////////////

                String srut = request.getParameter("rut");
                String firstname = request.getParameter("firstName");
                String lastname = request.getParameter("lastName");
                String telephone = request.getParameter("telephone");
                String sidCity = request.getParameter("idCity");
                String email = request.getParameter("email");
                String gender = request.getParameter("gender");
                String facebook = request.getParameter("facebook");
                String dateBirth = request.getParameter("dateBirth");
                String sidUniversity = request.getParameter("idUniversity");

                /* parametros para actualizar password */
                String chkPwd = request.getParameter("chk");
                String pwd1 = request.getParameter("pwd1");
                String pwd2 = request.getParameter("pwd2");

                /* instanciar string url */
                String url = "?redirect=ok";
                url += "&rut=" + srut;
                url += "&firstName=" + firstname;
                url += "&lastName=" + lastname;
                url += "&telephone=" + telephone;
                url += "&idCity=" + sidCity;
                url += "&email=" + email;
                url += "&gender=" + gender;
                url += "&facebook=" + facebook;
                url += "&dateBirth=" + dateBirth;
                url += "&idUniversity=" + sidUniversity;

                /* instanciar usercard */
                UserCard userCardReg = new UserCard();

                /* flag de error */
                boolean error = false;

                /* comprobar rut */
                if (srut == null || srut.trim().equals("")) {
                    error = true;
                } else {
                    try {
                        userCardReg.setRut(Integer.parseInt(srut));
                    } catch (NumberFormatException n) {
                        error = true;
                    }
                }

                /* comprobar firstname */
                if (firstname == null || firstname.trim().equals("")) {
                    url += "&msgErrorFirstName=Debe ingresar nombres.";
                    error = true;
                } else {
                    userCardReg.setFirstName(Format.capital(firstname));
                }

                /* comprobar lastname */
                if (lastname == null || lastname.trim().equals("")) {
                    url += "&msgErrorLastName=Debe ingresar apellidos.";
                    error = true;
                } else {
                    userCardReg.setLastName(Format.capital(lastname));
                }

                /* comprobar email */
                if (email == null || email.trim().equals("")) {
                    url += "&msgErrorEmail=Debe ingresar email.";
                    error = true;
                } else {
                    userCardReg.setEmail(email);
                    boolean find = usercardDAO.validateDuplicateEmail(userCardReg);
                    if (find) {
                        url += "&msgErrorEmail=Ya existe un usuario con ese email.";
                        error = true;
                    }
                }

                /* comprobar genero */
                if (gender == null || gender.trim().equals("")) {
                    error = true;
                } else {
                    try {
                        userCardReg.setGender(Integer.parseInt(gender));
                    } catch (NumberFormatException n) {
                        error = true;
                    }
                }

                /* comprobar id city */
                if (sidCity == null || sidCity.trim().equals("")) {
                    error = true;
                } else {
                    try {
                        userCardReg.setIdCity(Integer.parseInt(sidCity));
                    } catch (NumberFormatException n) {
                        error = true;
                    }
                }

                /* comprobar telefono */
                if (telephone == null || telephone.trim().equals("")) {
                    url += "&msgErrorTelephone=Debe ingresar teléfono celular.";
                    error = true;
                } else {
                    try {
                        userCardReg.setTelephone(Integer.parseInt(telephone));
                    } catch (NumberFormatException n) {
                        url += "&msgErrorTelephone=El teléfono celular debe contener valores numéricos.";
                        error = true;
                    }
                }

                /* comprobar facebook */
                if (facebook == null || facebook.trim().equals("")) {
                    url += "&msgErrorFacebook=Debe ingresar facebook.";
                    error = true;
                } else {
                    userCardReg.setFacebook(facebook);
                }

                /* comprobar fecha de nacimiento */
                if (dateBirth == null || dateBirth.trim().equals("")) {
                    url += "&msgErrorDateBirth=Debe ingresar fecha de nacimiento.";
                    error = true;
                } else {
                    userCardReg.setDateBirth(dateBirth);
                    /* validar que la fecha de nacimiento no sea mayor que la fecha actual */
                    if (dateBirth.compareTo(Format.currentDate()) >= 0) {
                        url += "&msgErrorDateBirth=La fecha de nacimiento no puede ser mayor o igual que la fecha actual.";
                        error = true;
                    }
                }

                /* comprobar id university */
                if (sidUniversity == null || sidUniversity.trim().equals("")) {
                    error = true;
                } else {
                    try {
                        userCardReg.setIdUniversity(Integer.parseInt(sidUniversity));
                    } catch (NumberFormatException n) {
                        error = true;
                    }
                }

                //////////////////////
                // LOGICA DE NEGOCIO
                //////////////////////

                /* comprobar checkbox password */
                if (chkPwd != null) {
                    /* comprobar pwd1 */
                    if (pwd1 == null || pwd1.trim().equals("")) {
                        url += "&msgErrorPwd1=Debe ingresar password.";
                    } else {
                        userCardReg.setPwd1(pwd1);
                        /* comprobar pwd2 */
                        if (pwd2 == null || pwd2.trim().equals("")) {
                            url += "&msgErrorPwd1=Debe ingresar password.";
                        } else {
                            userCardReg.setPwd2(pwd2);
                            /* comprobar coincidencias */
                            if (!pwd1.equals(pwd2)) {
                                url += "&msgErrorPwd1=Las passwords no coinciden.";
                                error = true;
                            }
                            if (pwd1.length() < 6 || pwd2.length() < 6) {
                                url += "&msgErrorPwd2= La password debe poseer al menos 6 caracteres.";
                                error = true;
                            }

                            if (!error) {
                                /* encriptar password */
                                userCardReg.setPassword(StringMD.getStringMessageDigest(pwd1, StringMD.MD5));
                                usercardDAO.updatePassword(userCardReg);
                                url += "&msgOk=Registro actualizado exitosamente.";
                            }
                        }
                    }
                } else {
                    if (!error) {
                        usercardDAO.update(userCardReg);
                        url += "&msgOk=Registro actualizado exitosamente.";
                    }
                }

                /* obtener list city */
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
                }

                /* send redirect */
                response.sendRedirect("UserCardGetServlet" + url);

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
