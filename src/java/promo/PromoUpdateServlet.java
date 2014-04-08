/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package promo;

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
 * @author patricio alberto
 */
@WebServlet(name = "PromoUpdateServlet", urlPatterns = {"/PromoUpdateServlet"})
public class PromoUpdateServlet extends HttpServlet {

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

        /////////////////////////
        // ESTABLECER CONEXION
        /////////////////////////
        try {
            conexion = ds.getConnection();

            PromoDAO promoDAO = new PromoDAO();
            promoDAO.setConexion(conexion);

            ///////////////////////
            // COMPROBAR SESSION
            ///////////////////////
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

                    ///////////////////////////////////////
                    // RECIBIR Y COMPROBAR PARAMETROS
                    ///////////////////////////////////////

                    String sidPlace = request.getParameter("idPlace");
                    String sidPromo = request.getParameter("idPromo");
                    String tittle = request.getParameter("tittle");
                    String details = request.getParameter("details");
                    String date1 = request.getParameter("dateBegin");
                    String date2 = request.getParameter("dateEnd");
                    String urlImage = request.getParameter("urlImage");
                    String spoints = request.getParameter("points");
                    String srequest = request.getParameter("promoRequest");
                    String reason = request.getParameter("reason");

                    /* instanciar string url */
                    String url = "?redirect=ok";
                    url += "&idPromo=" + sidPromo;
                    url += "&tittle=" + tittle;
                    url += "&details=" + details;
                    url += "&date1=" + date1;
                    url += "&date2=" + date2;
                    url += "&urlImage=" + urlImage;
                    url += "&points=" + spoints;
                    url += "&request=" + srequest;
                    url += "&reason=" + reason;

                    /* instanciar promo */
                    Promo promo = new Promo();

                    /* flag de error */
                    boolean error = false;

                    /* comprobar id promo */
                    if (sidPromo == null || sidPromo.trim().equals("")) {
                        error = true;
                    } else {
                        try {
                            promo.setIdPromo(Integer.parseInt(sidPromo));
                        } catch (NumberFormatException n) {
                            error = true;
                        }
                    }

                    /* comprobar id Place */
                    if (sidPlace == null || sidPlace.trim().equals("")) {
                        error = true;
                    } else {
                        try {
                            promo.setIdPlace(Integer.parseInt(sidPlace));
                        } catch (NumberFormatException n) {
                            error = true;
                        }
                    }

                    /* comprobar tittle */
                    if (tittle == null || tittle.trim().equals("")) {
                        url += "&msgErrorTittle=Debe ingresar un título para la promoción.";
                        error = true;
                    } else {
                        promo.setTittle(tittle);
                    }

                    /* comprobar details */
                    if (details == null || details.trim().equals("")) {
                        url += "&msgErrorDetails=Debe ingresar detalle para la promoción.";
                        error = true;
                    } else {
                        promo.setDetails(details);
                    }

                    /* comprobar url image */
                    if (urlImage == null || urlImage.trim().equals("")) {
                        url += "&msgErrorUrlImage=Debe ingresar url de imagen.";
                        error = true;
                    } else {
                        promo.setUrlImage(urlImage);
                    }

                    /* comprobar date begin */
                    promo.setDateBegin(date1);
                    if (date1 == null || date1.trim().equals("")) {
                        url += "&msgErrorDateBegin=Debe ingresar fecha de inicio.";
                        error = true;
                    }

                    /* comprobar date end */
                    promo.setDateEnd(date2);
                    if (date2 == null || date2.trim().equals("")) {
                        url += "&msgErrorDateEnd=Debe ingresar fecha de término.";
                        error = true;
                    }

                    /* comparar fechas */
                    if (promo.getDateBegin().compareTo(promo.getDateEnd()) >= 0) {
                        url += "&msgErrorDate=La fecha de inicio no puede ser mayor o igual que la fecha de término.";
                        error = true;
                    }

                    /* comprobar points */
                    if (spoints == null || spoints.trim().equals("")) {
                        url += "&msgErrorPoints=Debe ingresar puntos que acumula esta promoción.";
                        error = true;
                    } else {
                        try {
                            promo.setPoints(Integer.parseInt(spoints));
                        } catch (NumberFormatException n) {
                            url += "&msgErrorPoints=Los puntos deben ser numéricos.";
                            error = true;
                        }
                    }

                    /* comprobar request */
                    if (srequest == null || srequest.trim().equals("")) {
                        error = true;
                    } else {
                        try {
                            promo.setRequest(Integer.parseInt(srequest));
                        } catch (NumberFormatException n) {
                            error = true;
                        }
                    }

                    /* comprobar reason */
                    if ((reason == null || reason.trim().equals("")) && promo.getRequest() == 2) {
                        url += "&msgErrorReason=Debe ingresar razón de rechazo.";
                        error = true;
                    } else {
                        promo.setReason(reason);
                    }

                    if (!error) {
                        /* comprobar registros duplicados */
                        try {
                            boolean find = promoDAO.validateDuplicate(promo);
                            if (find) {
                                url += "&msgErrorDup=Ya existe esta promoción. Compruebe utilizando otro título u otro rango de fechas.";
                            } else {
                                /* actualizar registro */
                                try {
                                    promoDAO.update(promo);
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
                    response.sendRedirect("PromoGetServlet" + url);

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
