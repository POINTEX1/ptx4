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

        try {
            /////////////////////////////////////////
            // ESTABLECER CONEXION
            ///////////////////////////////////////// 

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

                    /////////////////////////////////////////
                    // DECLARAR VARIABLES DE INSTANCIA
                    ////////////////////////////////////////

                    PlaceNews pnews = new PlaceNews();

                    try {
                        /////////////////////////////////////////
                        // RECIBIR Y COMPROBAR PARAMETROS
                        ////////////////////////////////////////

                        String sidPlaceNews = request.getParameter("idPlaceNews");
                        String sidPlace = request.getParameter("idPlace");
                        String namePlace = request.getParameter("namePlace");
                        String snewsType = request.getParameter("newsType");
                        String tittle = request.getParameter("tittle");
                        String details = request.getParameter("details");
                        String dateBegin = request.getParameter("dateBegin");
                        String dateEnd = request.getParameter("dateEnd");
                        String urlImage = request.getParameter("urlImage");

                        boolean error = false;

                        /* comprobar id place news */
                        if (sidPlaceNews == null || sidPlaceNews.trim().equals("")) {
                            request.setAttribute("msgErrorIdPlaceNews", "Error al recibir id de noticia.");
                            error = true;
                        } else {
                            pnews.setIdPlaceNews(Integer.parseInt(sidPlaceNews));
                        }


                        /* comprobar id place */
                        if (sidPlace == null || sidPlace.trim().equals("")) {
                            request.setAttribute("msgErrorIdPlace", "Error al recibir id de plaza.");
                            error = true;
                        } else {
                            pnews.setIdPlace(Integer.parseInt(sidPlace));
                        }

                        /* comprobar namePlace */
                        if (namePlace == null || namePlace.trim().equals("")) {
                            request.setAttribute("msgErrorNamePlace", "Error al recibir nombre de plaza.");
                            error = true;
                        } else {
                            pnews.setNamePlace(namePlace);
                        }

                        /*comprobar typeNews*/
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

                        /* comprobar tittle*/
                        if (tittle == null || tittle.trim().equals("")) {
                            request.setAttribute("msgErrorTittle", "Error al recibir título.");
                            error = true;
                        } else {
                            pnews.setTittle(tittle);
                        }

                        /* comprobar details */
                        if (details == null || details.trim().equals("")) {
                            request.setAttribute("msgErrorDetails", "Error al recibir detalles.");
                            error = true;
                        } else {
                            pnews.setDetails(details);
                        }

                        /* comprobar url image*/
                        if (urlImage == null || urlImage.trim().equals("")) {
                            request.setAttribute("msgErrorUrlImage", "Error: Debe ingresar url de imagen.");
                            error = true;
                        } else {
                            pnews.setUrlImage(urlImage);
                        }

                        /* comprobar dateBegin */
                        if (dateBegin == null || dateBegin.trim().equals("")) {
                            request.setAttribute("msgErrorDateBegin", "Error al recibir feha de inicio.");
                            error = true;
                        } else {
                            /* comprobar dateEnd */
                            if (dateEnd == null || dateEnd.trim().equals("")) {
                                request.setAttribute("msgErrorDateEnd", "Error al recibir feha de término.");
                                error = true;
                            } else {
                                /* comparar fechas */
                                pnews.setDateBegin(dateBegin);
                                pnews.setDateEnd(dateEnd);
                                //System.out.println("Comparar fecha 1 y fecha 2: " + event.getDateBegin().compareTo(event.getDateEnd()));
                                if (pnews.getDateBegin().compareTo(pnews.getDateEnd()) >= 0) {
                                    request.setAttribute("msgErrorDate", "Error: La fecha de término debe ser mayor que la fecha de inicio.");
                                    error = true;
                                }
                            }
                        }


                        if (!error) {
                            /* comprobar registros duplicados */
                            boolean find = pnewsDAO.validateDuplicate(pnews);
                            if (find) {
                                request.setAttribute("msgErrorDup", "Error: ya existe esta noticia. Compruebe utilizando otro título u otro rango de fechas.");
                            } else {
                                /* comprobar existencia */
                                PlaceNews aux = pnewsDAO.findByPlaceNews(pnews);
                                if (aux != null) {
                                    pnewsDAO.update(pnews);
                                    request.setAttribute("msgOk", "Registro actualizado exitosamente! ");
                                } else {
                                    request.setAttribute("msgErrorFound", "Error: no existe el evento o ha sido eliminado mientras se actualizaba.");
                                }
                            }
                        }

                        request.setAttribute("pnews", pnews);

                    } catch (Exception parameterException) {
                    } finally {
                        request.getRequestDispatcher("/placeNews/placeNewsUpdate.jsp").forward(request, response);
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
