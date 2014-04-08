/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package placeNews;

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

/**
 *
 * @author alexander
 */
@WebServlet(name = "PlaceNewsUpdateServlet", urlPatterns = {"/PlaceNewsUpdateServlet"})
public class PlaceNewsUpdateServlet extends HttpServlet {

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

            PlaceNewsDAO pnewsDAO = new PlaceNewsDAO();
            pnewsDAO.setConexion(conexion);

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
                if (access != 555 && access != 777) {
                    request.getRequestDispatcher("/ForbiddenServlet").forward(request, response);
                } else {

                    /* obtener los valores de session y asignar valores a la jsp */
                    request.setAttribute("userJsp", username);
                    request.setAttribute("access", access);

                    //////////////////////////////////////
                    // RECIBIR Y COMPROBAR PARAMETROS
                    //////////////////////////////////////

                    String sidPlaceNews = request.getParameter("idPlaceNews");
                    String sidPlace = request.getParameter("idPlace");
                    String snewsType = request.getParameter("newsType");
                    String tittle = request.getParameter("tittle");
                    String details = request.getParameter("details");
                    String dateBegin = request.getParameter("dateBegin");
                    String dateEnd = request.getParameter("dateEnd");
                    String urlImage = request.getParameter("urlImage");

                    /* instanciar string url */
                    String url = "?redirect=ok";
                    url += "&idPlaceNews=" + sidPlaceNews;
                    url += "&idPlace=" + sidPlace;
                    url += "&newsType=" + snewsType;
                    url += "&tittle=" + tittle;
                    url += "&details=" + details;
                    url += "&dateBegin=" + dateBegin;
                    url += "&dateEnd=" + dateEnd;
                    url += "&urlImage=" + urlImage;

                    /* instanciar place news */
                    PlaceNews pnews = new PlaceNews();

                    /* flag de error */
                    boolean error = false;

                    /* comprobar id place news */
                    if (sidPlaceNews == null || sidPlaceNews.trim().equals("")) {
                        error = true;
                    } else {
                        try {
                            pnews.setIdPlaceNews(Integer.parseInt(sidPlaceNews));
                        } catch (NumberFormatException n) {
                            error = true;
                        }
                    }

                    /* comprobar id Place */
                    if (sidPlace == null || sidPlace.trim().equals("")) {
                        error = true;
                    } else {
                        try {
                            pnews.setIdPlace(Integer.parseInt(sidPlace));
                        } catch (NumberFormatException n) {
                            error = true;
                        }
                    }

                    /*comprobar typeNews*/
                    if (snewsType == null || snewsType.trim().equals("")) {
                        error = true;
                    } else {
                        try {
                            pnews.setNewsType(Integer.parseInt(snewsType));
                        } catch (NumberFormatException n) {
                            error = true;
                        }
                    }

                    /* comprobar tittle */
                    if (tittle == null || tittle.trim().equals("")) {
                        url += "&msgErrorTittle=Debe ingresar título.";
                        error = true;
                    } else {
                        pnews.setTittle(tittle);
                    }

                    /* comprobar details */
                    if (details == null || details.trim().equals("")) {
                        url += "&msgErrorDetails=Debe ingresar detalles.";
                        error = true;
                    } else {
                        pnews.setDetails(details);
                    }

                    /* comprobar url image */
                    if (urlImage == null || urlImage.trim().equals("")) {
                        url += "&msgErrorUrlImage=Debe ingresar url de imagen.";
                        error = true;
                    } else {
                        pnews.setUrlImage(urlImage);
                    }

                    /* comprobar dateBegin */
                    pnews.setDateBegin(dateBegin);
                    if (dateBegin == null || dateBegin.trim().equals("")) {
                        url += "&msgErrorDateBegin=Debe ingresar fecha de inicio.";
                        error = true;
                    }

                    /* comprobar dateEnd */
                    pnews.setDateEnd(dateEnd);
                    if (dateEnd == null || dateEnd.trim().equals("")) {
                        url += "&msgErrorDateEnd=Debe ingresar fecha de término.";
                        error = true;
                    }
                    /* comparar fechas */
                    if (pnews.getDateBegin().compareTo(pnews.getDateEnd()) >= 0) {
                        url += "&msgErrorDate=Error: La fecha de inicio no puede ser mayor o igual que la fecha de término.";
                        error = true;
                    }

                    ///////////////////////
                    // LOGICA DE NEGOCIO
                    ///////////////////////

                    /* comprobar registros duplicados */
                    try {
                        boolean find = pnewsDAO.validateDuplicate(pnews);
                        if (find) {
                            url += "&msgErrorDup=Error: ya existe esta noticia. Compruebe utilizando otro título u otro rango de fechas.";
                            error = true;
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        error = true;
                    }

                    /* actualizar registro */
                    if (!error) {
                        try {
                            pnewsDAO.update(pnews);
                            url += "&msgOk=Registro actualizado exitosamente!";
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    /* send redirect */
                    response.sendRedirect("PlaceNewsGetServlet" + url);
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