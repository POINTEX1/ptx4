/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package news;

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
@WebServlet(name = "NewsUpdateServlet", urlPatterns = {"/NewsUpdateServlet"})
public class NewsUpdateServlet extends HttpServlet {

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

            NewsDAO newsDAO = new NewsDAO();
            newsDAO.setConexion(conexion);

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
                }

                /* obtener los valores de session y asignar valores a la jsp */
                request.setAttribute("userJsp", username);
                request.setAttribute("access", access);

                /////////////////////////////////////////
                // RECIBIR Y COMPROBAR PARAMETROS
                /////////////////////////////////////////

                String sidNews = request.getParameter("idNews");
                String stittle = request.getParameter("tittle");
                String sdetails = request.getParameter("details");
                String snewsType = request.getParameter("newsType");
                String surlImage = request.getParameter("urlImage");
                String sdateBegin = request.getParameter("dateBegin");
                String sdateEnd = request.getParameter("dateEnd");

                /* instanciar url */
                String url = "?redirect=ok";
                url += "&idNews=" + sidNews;
                url += "&tittle=" + stittle;
                url += "&details=" + sdetails;
                url += "&urlImage=" + surlImage;
                url += "&dateBegin=" + sdateBegin;
                url += "&dateEnd=" + sdateEnd;
                url += "&newsType=" + snewsType;

                /* instanciar news */
                News news = new News();

                /* flag de error */
                boolean error = false;

                /* comprobar id news */
                if (sidNews == null || sidNews.trim().equals("")) {
                    error = true;
                } else {
                    try {
                        news.setIdNews(Integer.parseInt(sidNews));
                    } catch (NumberFormatException n) {
                        error = true;
                    }
                }

                /* comprobar tittle */
                if (stittle == null || stittle.trim().equals("")) {
                    url += "&msgErrorTittle=Debe ingresar un titulo para la noticia.";
                    error = true;
                } else {
                    news.setTittle(stittle);
                }

                /* comprobar details */
                if (sdetails == null || sdetails.trim().equals("")) {
                    url += "&msgErrorDetails=Debe ingresar detalle de la noticia.";
                    error = true;
                } else {
                    news.setDetails(sdetails);
                }

                /* comprobar type news */
                if (snewsType == null || snewsType.trim().equals("")) {
                    error = true;
                } else {
                    try {
                        news.setNewsType(Integer.parseInt(snewsType));
                    } catch (NumberFormatException n) {
                        error = true;
                    }
                }

                /* comprobar url image */
                if (surlImage == null || surlImage.trim().equals("")) {
                    url += "&msgErrorUrlImage=Debe ingresar la url de la imagen.";
                    error = true;
                } else {
                    news.setUrlImage(surlImage);
                }

                /* comprobar dateBegin */
                news.setDateBegin(sdateBegin);
                if (sdateBegin == null || sdateBegin.trim().equals("")) {
                    url += "&msgErrorDateBegin=Debe ingresar fecha de inicio.";
                    error = true;
                }

                /* comprobar dateEnd */
                news.setDateEnd(sdateEnd);
                if (sdateEnd == null || sdateEnd.trim().equals("")) {
                    url += "&msgErrorDateEnd=Debe ingresar fecha de término.";
                    error = true;
                }

                /* comparar fechas */
                if (news.getDateBegin().compareTo(news.getDateEnd()) >= 0) {
                    url += "&msgErrorDate=La fecha de inicio no puede ser mayor o igual que la fecha de término.";
                    error = true;
                }

                ////////////////////////
                // LOGICA DE NEGOCIO
                ////////////////////////


                /* comprobar registros duplicados */
                try {
                    boolean find = newsDAO.validateDuplicate(news);
                    if (find) {
                        url += "&msgErrorDup=Ya existe esta noticia. Compruebe utilizando otro título u otro rango de fechas.";
                        error = true;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    error = true;
                }

                /* actualizar registro */
                if (!error) {
                    try {
                        newsDAO.update(news);
                        url += "&msgOk=Registro actualizado exitosamente.";
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                /* send redirect */
                response.sendRedirect("NewsGetServlet" + url);

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
