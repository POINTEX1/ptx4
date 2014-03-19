/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package placeNews;

import Helpers.Format;
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
import place.Place;
import place.PlaceDAO;

/**
 *
 * @author alexander
 */
@WebServlet(name = "PlaceNewsAddServlet", urlPatterns = {"/PlaceNewsAddServlet"})
public class PlaceNewsAddServlet extends HttpServlet {

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

            PlaceNewsDAO pnewsDAO = new PlaceNewsDAO();
            pnewsDAO.setConexion(conexion);

            PlaceDAO placeDAO = new PlaceDAO();
            placeDAO.setConexion(conexion);

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
                        String sidPlace = request.getParameter("idPlace");
                        String stittle = request.getParameter("tittle");
                        String snewsType = request.getParameter("newsType");
                        String sdetails = request.getParameter("details");
                        String surlImage = request.getParameter("urlImage");
                        String sdateBegin = request.getParameter("dateBegin");
                        String sdateEnd = request.getParameter("dateEnd");

                        PlaceNews pnews = new PlaceNews();
                        Format format = new Format();

                        boolean error = false;

                        /* comprobar si recibe formulario */
                        if (btnAdd == null) {
                            request.setAttribute("msg", "Ingrese Noticia.");
                        } else {

                            /* comprobar id place */
                            if (sidPlace == null || sidPlace.trim().equals("")) {
                                request.setAttribute("msgErrorIdPlace", "Error al recibir id Plaza.");
                                error = true;
                            } else {
                                pnews.setIdPlace(Integer.parseInt(sidPlace));
                            }

                            /* comprobar tittle */
                            if (stittle == null || stittle.trim().equals("")) {
                                request.setAttribute("msgErrorTittle", "Error al recibir titulo.");
                                error = true;
                            } else {
                                pnews.setTittle(stittle);
                            }

                            /* comprobar type news */
                            if (snewsType == null || snewsType.trim().equals("")) {
                                request.setAttribute("msgErrorTypeNews", "Error al recibir tipo de noticia.");
                                error = true;
                            } else {
                                try {
                                    pnews.setNewsType(Integer.parseInt(snewsType));
                                } catch (NumberFormatException n) {
                                    request.setAttribute("msgErrorTypeNews", "Error: Debe recibir un valor numérico en tipo de noticias.");
                                    error = true;
                                }
                            }

                            /*comprobar Url Image*/
                            if (surlImage == null || surlImage.trim().equals("")) {
                                request.setAttribute("msgErrorUrlImage", "Error al recibir url imagen.");
                                error = true;
                            } else {
                                pnews.setUrlImage(surlImage);
                                if (format.validarUrl(surlImage)) {
                                    pnews.setUrlImage(surlImage);
                                } else {
                                    request.setAttribute("msgErrorUrlImage", "Error: Debe ingresar formato correcto para url imagen.");
                                    error = true;
                                }
                            }

                            /* comprobar details */
                            if (sdetails == null || sdetails.trim().equals("")) {
                            } else {
                                pnews.setDetails(sdetails);
                            }

                            /* comprobar date begin */
                            if (sdateBegin == null || sdateBegin.trim().equals("")) {
                                request.setAttribute("msgErrorDateBegin", "Error al recibir fecha de inicio.");
                                error = true;
                            } else {
                                pnews.setDateBegin(sdateBegin);
                                /* comprobar date end */
                                if (sdateEnd == null || sdateEnd.trim().equals("")) {
                                    request.setAttribute("msgErrorDateEnd", "Error al recibir fecha de término.");
                                    error = true;
                                } else {
                                    /* comparar fechas */
                                    pnews.setDateBegin(sdateBegin);
                                    pnews.setDateEnd(sdateEnd);
                                    /* comparar con fecha actual */
                                    if (pnews.getDateBegin().compareTo(Format.currentDate()) < 0) {
                                        request.setAttribute("msgErrorDate", "Error: La noticia no puede poseer una fecha de inicio anterior a la fecha actual.");
                                        error = true;
                                    } else {
                                        if (pnews.getDateBegin().compareTo(pnews.getDateEnd()) >= 0) {
                                            request.setAttribute("msgErrorDate", "Error: La fecha de término deber ser mayor que la fecha de inicio.");
                                            error = true;
                                        }
                                    }
                                }
                            }

                            if (!error) {
                                /* comprobar registros duplicados */
                                boolean find = pnewsDAO.validateDuplicate(pnews);
                                if (find) {
                                    error = true;
                                    request.setAttribute("msgErrorDup", "Error: ya existe esta noticia. Compruebe utilizando otro título u otro rango de fechas.");
                                } else {
                                    /* insertar nueva noticia */
                                    try {
                                        pnewsDAO.insert(pnews);
                                        request.setAttribute("msgOk", "Registro ingresado exitosamente! ");
                                    } catch (Exception insertException) {
                                        System.out.println("error no se pudo insertar place news");
                                    }
                                }
                            }

                        }

                        /* obtener lista de lugares */
                        try {
                            Collection<Place> listPlace = placeDAO.getAll();
                            request.setAttribute("listPlace", listPlace);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                        /////////////////////////////////////////
                        // ESTABLECER ATRIBUTOS AL REQUEST
                        /////////////////////////////////////////

                        request.setAttribute("pnews", pnews);

                    } catch (Exception parameterException) {
                    } finally {
                        request.getRequestDispatcher("/placeNews/placeNewsAdd.jsp").forward(request, response);
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
