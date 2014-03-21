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
            /////////////////////////////////////////
            // ESTABLECER CONEXION
            /////////////////////////////////////////

            conexion = ds.getConnection();

            ClientNewsDAO cnewsDAO = new ClientNewsDAO();
            cnewsDAO.setConexion(conexion);

            /////////////////////////////////////////
            // COMPROBAR SESSION
            /////////////////////////////////////////
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

                /////////////////////////////////////////
                // RECIBIR Y COMPROBAR PARAMETROS
                /////////////////////////////////////////

                String sidClientNews = request.getParameter("idClientNews");
                String srut = request.getParameter("rut");
                String stittle = request.getParameter("tittle");
                String snewsType = request.getParameter("newsType");
                String sdateBegin = request.getParameter("dateBegin");
                String sdateEnd = request.getParameter("dateEnd");

                ClientNews cnews = new ClientNews();

                boolean error = false;

                /* instanciar string url */
                String url = "?a=target";

                /* comprobar id news */
                url += "&idClientNews=" + sidClientNews;
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
                url += "&tittle=" + stittle;
                if (stittle == null || stittle.trim().equals("")) {
                    url += "&msgErrorTittle=Error: Debe ingresar un titulo para la noticia.";
                    error = true;
                } else {
                    cnews.setTittle(stittle);
                }

                /* comprobar type news */
                url += "&newsType=" + snewsType;
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
                url += "&rut=" + srut;
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

                /* comprobar dateBegin */
                url += "&dateBegin=" + sdateBegin;
                url += "&dateEnd=" + sdateEnd;
                if (sdateBegin == null || sdateBegin.trim().equals("")) {
                    url += "&msgErrorDate=Error al recibir fecha de inicio.";
                    error = true;
                } else {
                    /* comprobar dateEnd */
                    if (sdateEnd == null || sdateEnd.trim().equals("")) {
                        url += "&msgErrorDate=Error: Debe ingresar fecha de inicio.";
                        error = true;
                    } else {
                        /* comparar fechas */
                        cnews.setDateBegin(sdateBegin);
                        cnews.setDateEnd(sdateEnd);
                        //System.out.println("Comparar fecha 1 y fecha 2: " + event.getDateBegin().compareTo(event.getDateEnd()));
                        if (cnews.getDateBegin().compareTo(cnews.getDateEnd()) >= 0) {
                            url += "&msgErrorDate=Error: La fecha de térrmino debe ser mayor que la fecha de inicio.";
                            error = true;
                        }
                    }
                }
                if (!error) {
                    /* comprobar registros duplicados */
                    boolean find = cnewsDAO.validateDuplicate(cnews);
                    if (find) {
                        url += "&msgErrorDup=Error: ya existe esta noticia. Compruebe utilizando otro tÃ­tulo u otro rango de fechas.";
                    } else {
                        ClientNews aux = cnewsDAO.findByClientNews(cnews);
                        if (aux != null) {
                            cnewsDAO.update(cnews);
                            url += "&msgOk=Registro actualizado exitosamente!";
                        } else {
                            url += "&msgErrorFound=Error: El registro no existe o ha sido mientras se actualizaba.";
                        }
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
