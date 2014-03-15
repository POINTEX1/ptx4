/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clientNews;

import Helpers.Format;
import Helpers.Rut;
import java.io.IOException;
import java.io.PrintWriter;
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
import userCard.UserCard;
import userCard.UserCardDAO;

/**
 *
 * @author alexander
 */
@WebServlet(name = "ClientNewsAddServlet", urlPatterns = {"/ClientNewsAddServlet"})
public class ClientNewsAddServlet extends HttpServlet {

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

            ClientNewsDAO cnewsDAO = new ClientNewsDAO();
            cnewsDAO.setConexion(conexion);

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

                    /////////////////////////////////////////
                    // RECIBIR PARAMETROS
                    //////////////////////////////////////// 
                    try {

                        String btnAdd = request.getParameter("add");
                        String stittle = request.getParameter("tittle");
                        String snewsType = request.getParameter("newsType");
                        String srut = request.getParameter("rut");
                        String sdateBegin = request.getParameter("dateBegin");
                        String sdateEnd = request.getParameter("dateEnd");

                        ClientNews cnews = new ClientNews();

                        boolean error = false;

                        /* comprobar si recibe formulario */
                        if (btnAdd == null) {
                            request.setAttribute("msg", "Ingrese Noticia.");
                        } else {

                            /* comprobar tittle */
                            if (stittle == null || stittle.trim().equals("")) {
                                request.setAttribute("msgErrorTittle", "Error al recibir titulo.");
                                error = true;
                            } else {
                                cnews.setTittle(stittle);
                            }

                            /* comprobar type news */
                            if (snewsType == null || snewsType.trim().equals("")) {
                                request.setAttribute("msgErrorNewsType", "Error al recibir tipo de noticia.");
                                error = true;
                            } else {
                                try {
                                    cnews.setNewsType(Integer.parseInt(snewsType));
                                } catch (NumberFormatException n) {
                                    request.setAttribute("msgErrorNewsType", "Error: Debe recibir un valor numérico en tipo de noticias.");
                                    error = true;
                                }
                            }

                            /* comprobar rut */
                            if (srut == null || srut.trim().equals("") || srut.length() < 3) {
                                request.setAttribute("msgErrorRut", "Error: Debe ingresar RUT.");
                                error = true;
                            } else {
                                request.setAttribute("rut", srut);
                                if (!Rut.validateRut(srut)) {
                                    request.setAttribute("msgErrorRut", "Error: RUT inválido.");
                                    error = true;
                                } else {
                                    cnews.setRut(Rut.getRut(srut));
                                    cnews.setDv(Rut.getDv(srut));
                                    try {
                                        UserCard aux = usercardDAO.findByRut(cnews.getRut());
                                        /* comprobar si existencia */
                                        if (aux == null) {
                                            request.setAttribute("msgErrorRut", "Error: No existe un usuario con ese rut. ");
                                            error = true;
                                        }
                                    } catch (Exception ex) {
                                        request.setAttribute("msgErrorRut", "Error: Rut inválido. ");
                                        error = true;
                                    }
                                }
                            }

                            /* comprobar date begin */
                            if (sdateBegin == null || sdateBegin.trim().equals("")) {
                                request.setAttribute("msgErrorDate", "Error al recibir fecha de inicio.");
                                error = true;
                            } else {
                                cnews.setDateBegin(sdateBegin);
                                /* comprobar date end */
                                if (sdateEnd == null || sdateEnd.trim().equals("")) {
                                    request.setAttribute("msgErrorDate", "Error al recibir fecha de término.");
                                    error = true;
                                } else {
                                    /* comparar fechas */
                                    cnews.setDateBegin(sdateBegin);
                                    cnews.setDateEnd(sdateEnd);
                                    /* comparar con fecha actual */
                                    if (cnews.getDateBegin().compareTo(Format.currentDate()) < 0) {
                                        request.setAttribute("msgErrorDate", "Error: La noticia no puede poseer una fecha de inicio anterior a la fecha actual.");
                                        error = true;
                                    } else {
                                        if (cnews.getDateBegin().compareTo(cnews.getDateEnd()) >= 0) {
                                            request.setAttribute("msgErrorDate", "Error: La fecha de término deber ser mayor que la fecha de inicio.");
                                            error = true;
                                        }
                                    }
                                }
                            }

                            if (!error) {
                                /* comprobar registros duplicados */
                                boolean find = cnewsDAO.validateDuplicate(cnews);
                                if (find) {
                                    error = true;
                                    request.setAttribute("msgErrorDup", "Error: ya existe esta noticia. Compruebe utilizando otro título u otro rango de fechas.");
                                } else {
                                    /* insertar nueva noticia */
                                    try {
                                        cnewsDAO.insert(cnews);
                                        request.setAttribute("msgOk", "Registro ingresado exitosamente! ");
                                    } catch (Exception insertException) {
                                        System.out.println("error no se pudo insertar news");
                                    }
                                }
                            }

                        }

                        /////////////////////////////////////////
                        // ESTABLECER ATRIBUTOS AL REQUEST
                        /////////////////////////////////////////

                        request.setAttribute("cnews", cnews);

                    } catch (Exception parameterException) {
                    } finally {
                        request.getRequestDispatcher("/clientNews/clientNewsAdd.jsp").forward(request, response);
                    }
                }
            } catch (Exception sessionException) {
                /* enviar a la vista de login */
                System.out.println("no ha iniciado session");
                request.getRequestDispatcher("/login/login.jsp").forward(request, response);
            }
        } catch (Exception connectionException) {
            connectionException.printStackTrace();
        } finally {
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
