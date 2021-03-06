/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package place;

import city.CityDAO;
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
@WebServlet(name = "PlaceUpdateServlet", urlPatterns = {"/PlaceUpdateServlet"})
public class PlaceUpdateServlet extends HttpServlet {

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

            PlaceDAO placeDAO = new PlaceDAO();
            placeDAO.setConexion(conexion);

            CityDAO cityDAO = new CityDAO();
            cityDAO.setConexion(conexion);

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
                if (access != 777) {
                    request.getRequestDispatcher("/ForbiddenServlet").forward(request, response);
                }

                /* obtener los valores de session y asignar valores a la jsp */
                request.setAttribute("userJsp", username);
                request.setAttribute("access", access);

                ////////////////////////////////////
                // RECIBIR Y COMPROBAR PARAMETROS
                ////////////////////////////////////

                String sidPlace = request.getParameter("idPlace");
                String sidCity = request.getParameter("idCity");
                String snamePlace = request.getParameter("namePlace");
                String sadress = request.getParameter("address");
                String status = request.getParameter("status");
                String contact = request.getParameter("contact");
                String description = request.getParameter("description");
                String urlImage = request.getParameter("urlImage");
                String urlLogo = request.getParameter("urlLogo");

                /* instanciar place */
                Place place = new Place();

                /* flag de error */
                boolean error = false;

                /* instanciar url */
                String url = "?redirect=ok";
                url += "&idPlace=" + sidPlace;
                url += "&idCity=" + sidCity;
                url += "&namePlace=" + snamePlace;
                url += "&address=" + sadress;
                url += "&status=" + status;
                url += "&contact=" + contact;
                url += "&description=" + description;
                url += "&urlImage=" + urlImage;
                url += "&urlLogo=" + urlLogo;

                /* comprobar id place */
                if (sidPlace == null || sidPlace.trim().equals("")) {
                    error = true;
                } else {
                    try {
                        place.setIdPlace(Integer.parseInt(sidPlace));
                    } catch (NumberFormatException n) {
                        error = true;
                    }
                }

                /* comprobar id city */
                if (sidCity == null || sidCity.trim().equals("")) {
                    error = true;
                } else {
                    try {
                        place.setIdCity(Integer.parseInt(sidCity));
                    } catch (NumberFormatException n) {
                        error = true;
                    }
                }

                /* comprobar name place */
                if (snamePlace == null || snamePlace.trim().equals("")) {
                    url += "&msgErrorNamePlace=Debe ingresar un nombre para el lugar.";
                    error = true;
                } else {
                    place.setNamePlace(snamePlace);
                }

                /* comprobar address */
                if (sadress == null || sadress.trim().equals("")) {
                    url += "&msgErrorAddress=Debe ingresar dirección del lugar.";
                    error = true;
                } else {
                    place.setAddress(sadress);
                }

                /* comprobar status */
                if (status == null || status.trim().equals("")) {
                    error = true;
                } else {
                    try {
                        place.setStatus(Integer.parseInt(status));
                    } catch (NumberFormatException n) {
                        error = true;
                    }
                }

                /* comprobar contact */
                if (contact == null || contact.trim().equals("")) {
                } else {
                    try {
                        place.setContact(Integer.parseInt(contact));
                    } catch (NumberFormatException n) {
                        url += "&msgErrorContact=El número de contacto debe ser numérico.";
                        error = true;
                    }
                }

                /* comprobar description */
                if (description == null || description.trim().equals("")) {
                    url += "&msgErrorDes=Debe ingresar descripción del lugar.";
                    error = true;
                } else {
                    place.setDescription(description);
                }

                /* comprobar url image */
                if (urlImage == null || urlImage.trim().equals("")) {
                    url += "&msgErrorUrlImage=Debe ingresar la url de la imagen.";
                    error = true;
                } else {
                    place.setUrlImage(urlImage);
                }

                /* comprobar url logo */
                if (urlLogo == null || urlLogo.trim().equals("")) {
                    url += "&msgErrorUrlLogo=Error: Debe ingresar la url del logo.";
                    error = true;
                } else {
                    place.setUrlLogo(urlLogo);
                }

                ///////////////////////
                // LOGICA DE NEGOCIO
                ///////////////////////

                /* comprobar duplicaciones */
                try {
                    boolean find = placeDAO.validateDuplicate(place);
                    if (find) {
                        url += "&msgErrorDup=Ya existe este registro.";
                        error = true;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    error = true;
                }

                /* actualizar registro */
                if (!error) {
                    try {
                        placeDAO.update(place);
                        url += "&msgOk=Registro actualizado exitosamente.";
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                /* send redirect */
                response.sendRedirect("PlaceGetServlet" + url);

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
