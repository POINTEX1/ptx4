/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dressCode;

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
 * @author patricio
 */
@WebServlet(name = "DressCodeUpdateServlet", urlPatterns = {"/DressCodeUpdateServlet"})
public class DressCodeUpdateServlet extends HttpServlet {

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

            DressCodeDAO dressCodeDAO = new DressCodeDAO();
            dressCodeDAO.setConexion(conexion);

            ///////////////////////
            // COMPROBAR SESSION
            ///////////////////////
            try {
                /* recuperar sesion */
                HttpSession session = request.getSession(false);

                /* obtener los valores de session */
                String userJsp = (String) session.getAttribute("username");
                String sAccess = (String) session.getAttribute("access");
                int access = Integer.parseInt(sAccess);

                /* asignar valores a la jsp */
                request.setAttribute("userJsp", userJsp);
                request.setAttribute("access", access);

                ////////////////////////////////////
                // RECIBIR Y COMPROBAR PARAMETROS
                ////////////////////////////////////

                String sidDressCode = request.getParameter("idDressCode");
                String nameDressCode = request.getParameter("nameDressCode");
                String menDetails = request.getParameter("menDetails");
                String womenDetails = request.getParameter("womenDetails");
                String urlImage = request.getParameter("urlImage");

                /* instanciar string url */
                String url = "?a=target";

                url += "&idDressCode=" + sidDressCode;
                url += "&nameDressCode=" + nameDressCode;
                url += "&menDetails=" + menDetails;
                url += "&womenDetails=" + womenDetails;
                url += "&urlImage" + urlImage;

                /* instanciar codigo vestir */
                DressCode dressCode = new DressCode();

                boolean error = false;

                /* comprobar idDressCode */
                if (sidDressCode == null || sidDressCode.trim().equals("")) {
                    error = true;
                } else {
                    try {
                        dressCode.setIdDressCode(Integer.parseInt(sidDressCode));
                    } catch (NumberFormatException n) {
                        error = true;
                    }
                }

                /* comprobar name dress code */
                if (nameDressCode == null || nameDressCode.trim().equals("")) {
                    url += "&msgErrorNameDressCode=Debe ingresar un título de código de vestir.";
                    error = true;
                } else {
                    dressCode.setNameDressCode(nameDressCode);
                    /* comprobar duplicaciones */
                    boolean find = dressCodeDAO.findByIdName(dressCode.getIdDressCode(), dressCode.getNameDressCode());
                    if (find) {
                        url += "&msgErrorNameDressCode=Ya existe un código de vestir con ese título.";
                        error = true;
                    }
                }

                /* comprobar men details */
                if (menDetails == null || menDetails.trim().equals("")) {
                    url += "&msgErrorMenDetails=Debe ingresar detalles para hombres.";
                    error = true;
                } else {
                    dressCode.setMenDetails(menDetails);
                }

                /* comprobar women details */
                if (womenDetails == null || womenDetails.trim().equals("")) {
                    url += "&msgErrorWomenDetails=Debe ingresar detalles para mujeres.";
                    error = true;
                } else {
                    dressCode.setWomenDetails(womenDetails);
                }

                /* comprobar url image */
                if (urlImage == null || urlImage.trim().equals("")) {
                    url += "&msgErrorUrlImage=Debe ingresar url de imagen.";
                    error = true;
                } else {
                    dressCode.setUrlImage(urlImage);
                }

                if (!error) {
                    try {
                        /* actualizar datos */
                        dressCodeDAO.update(dressCode);
                        url += "&msgOk=Registro actualizado exitosamente.";
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                /* send redirect */
                response.sendRedirect("DressCodeGetServlet" + url);

            } catch (Exception sessionException) {
                /* enviar a la vista de login */
                System.out.println("no ha iniciado session");
                request.getRequestDispatcher("/login/login.jsp").forward(request, response);
            }
        } catch (Exception connectionException) {
            connectionException.printStackTrace();
        } finally {
            /* cerrar sesion */
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
