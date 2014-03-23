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

        /* establecer set de caracteres */
        request.setCharacterEncoding("UTF-8");

        /* definir conexion */
        Connection conexion = null;

        /////////////////////////////////////////
        // ESTABLECER CONEXION
        /////////////////////////////////////////
        try {
            conexion = ds.getConnection();

            PlaceNewsDAO pnewsDAO = new PlaceNewsDAO();
            pnewsDAO.setConexion(conexion);


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
                    String url = "?a=target";

                    /* retornar atributos por PRG */
                    url += "&idPlaceNews=" + sidPlaceNews;
                    url += "&tittle=" + tittle;
                    url += "&details=" + details;
                    url += "&urlImage=" + urlImage;
                    url += "&dateBegin=" + dateBegin;
                    url += "&dateEnd=" + dateEnd;

                    PlaceNews pnews = new PlaceNews();

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
                        url += "&msgErrorTittle=Error al recibir título.";
                        error = true;
                    } else {
                        pnews.setTittle(tittle);
                    }

                    /* comprobar details */
                    if (details == null || details.trim().equals("")) {
                        url += "&msgErrorDetails=Error al recibir detalles.";
                        error = true;
                    } else {
                        pnews.setDetails(details);
                    }

                    /* comprobar url image*/
                    if (urlImage == null || urlImage.trim().equals("")) {
                        url += "&msgErrorUrlImage=Error: Debe ingresar url de imagen.";
                        error = true;
                    } else {
                        pnews.setUrlImage(urlImage);
                    }

                    /* comprobar dateBegin */
                    if (dateBegin == null || dateBegin.trim().equals("")) {
                        url += "&msgErrorDate=Error: Debe ingresar fecha de inicio.";
                        error = true;
                    } else {
                        /* comprobar dateEnd */
                        if (dateEnd == null || dateEnd.trim().equals("")) {
                            url += "&msgErrorDate=Error: Debe ingresar fecha de término.";
                            error = true;
                        } else {
                            /* comparar fechas */
                            pnews.setDateBegin(dateBegin);
                            pnews.setDateEnd(dateEnd);
                            //System.out.println("Comparar fecha 1 y fecha 2: " + event.getDateBegin().compareTo(event.getDateEnd()));
                            if (pnews.getDateBegin().compareTo(pnews.getDateEnd()) >= 0) {
                                url += "&msgErrorDate=Error: La fecha de inicio no puede ser mayo que la de término.";
                                error = true;
                            }
                        }
                    }

                    /////////////////////////////////////
                    // EJECUTAR LOGICA DE NEGOCIO
                    /////////////////////////////////////

                    if (!error) {
                        /* comprobar registros duplicados */
                        boolean find = pnewsDAO.validateDuplicate(pnews);
                        if (find) {
                            url += "&msgErrorDup=Error: ya existe esta noticia. Compruebe utilizando otro título u otro rango de fechas.";
                        } else {
                            /* actualizar registro */
                            try {
                                pnewsDAO.update(pnews);
                                url += "&msgOk=Registro actualizado exitosamente!";
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
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
