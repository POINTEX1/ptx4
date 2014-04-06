/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clientNews;

import java.io.IOException;
import java.sql.Connection;
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
 * @author alexander
 */
@WebServlet(name = "ClientNewsUpdateServlet", urlPatterns = {"/ClientNewsUpdateServlet"})
public class ClientNewsUpdateServlet extends HttpServlet {

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

            ClientNewsDAO cnewsDAO = new ClientNewsDAO();
            cnewsDAO.setConexion(conexion);

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
                if (access != 777) {
                    request.getRequestDispatcher("/ForbiddenServlet").forward(request, response);
                }

                ////////////////////////////////////
                // RECIBIR Y COMPROBAR PARAMETROS
                ////////////////////////////////////

                String sidClientNews = request.getParameter("idClientNews");
                String srut = request.getParameter("rut");
                String stittle = request.getParameter("tittle");
                String snewsType = request.getParameter("newsType");
                String sdateBegin = request.getParameter("dateBegin");
                String sdateEnd = request.getParameter("dateEnd");

                /* instanciar clientNews */
                ClientNews cnews = new ClientNews();

                boolean error = false;

                /* instanciar string url */
                String url = "?a=target";

                url += "&idClientNews=" + sidClientNews;
                url += "&tittle=" + stittle;
                url += "&newsType=" + snewsType;
                url += "&rut=" + srut;
                url += "&dateBegin=" + sdateBegin;
                url += "&dateEnd=" + sdateEnd;

                /* comprobar id news */
                if (sidClientNews == null || sidClientNews.trim().equals("")) {
                    url += "&msgErrorIdClientNews=Error al recibir id cliente noticias.";
                    error = true;
                } else {
                    try {
                        cnews.setIdClientNews(Integer.parseInt(sidClientNews));
                    } catch (NumberFormatException n) {
                        url += "&msgErrorIdClientNews=Error al recibir id cliente noticias.";
                        error = true;
                    }
                }

                /* comprobar tittle */
                if (stittle == null || stittle.trim().equals("")) {
                    url += "&msgErrorTittle=Error: Debe ingresar un titulo para la noticia.";
                    error = true;
                } else {
                    cnews.setTittle(stittle);
                }

                /* comprobar type news */
                if (snewsType == null || snewsType.trim().equals("")) {
                    url += "&msgErrorNewsType=Error al recibir tipo de noticia.";
                    error = true;
                } else {
                    try {
                        cnews.setNewsType(Integer.parseInt(snewsType));
                    } catch (NumberFormatException n) {
                        url += "&msgErrorNewsType=Error: Debe recibir un valor numérico en tipo de noticias.";
                        error = true;
                    }
                }

                /* comprobar rut */
                if (srut == null || srut.trim().equals("") || srut.length() < 2) {
                    url += "&msgErrorRut=Error al recibir RUT.";
                    error = true;
                } else {
                    try {
                        cnews.setRut(Integer.parseInt(srut));
                    } catch (NumberFormatException n) {
                        url += "&msgErrorRut=Error: RUT inválido.";
                        error = true;
                    }
                }

                /* comprobar date begin */
                cnews.setDateBegin(sdateBegin);
                if (sdateBegin == null || sdateBegin.trim().equals("")) {
                    url += "&msgErrorDateBegin=Debe ingresar fecha de inicio.";
                    error = true;
                }

                /* comprobar date end */
                cnews.setDateEnd(sdateEnd);
                if (sdateEnd == null || sdateEnd.trim().equals("")) {
                    url += "&msgErrorDateEnd=Debe ingresar fecha de término.";
                    error = true;
                }

                /* comparar fecha de inicio con fecha de término */
                if (cnews.getDateBegin().compareTo(cnews.getDateEnd()) >= 0) {
                    url += "&msgErrorDate=La fecha de inicio no puede ser mayor o igual que la fecha de término.";
                    error = true;
                }

                if (!error) {
                    /* comprobar registros duplicados */
                    try {
                        boolean find = cnewsDAO.validateDuplicate(cnews);
                        if (find) {
                            url += "&msgErrorDup=Ya existe esta noticia. Compruebe utilizando otro título u otro rango de fechas.";
                        } else {
                            try {
                                cnewsDAO.update(cnews);
                                url += "&msgOk=Registro actualizado exitosamente.";
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                /* send redirect */
                response.sendRedirect("ClientNewsGetServlet" + url);

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
