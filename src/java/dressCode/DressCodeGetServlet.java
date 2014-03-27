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
@WebServlet(name = "DressCodeGetServlet", urlPatterns = {"/DressCodeGetServlet"})
public class DressCodeGetServlet extends HttpServlet {

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
            //////////////////////////////////////////
            // ESTABLECER CONEXION
            /////////////////////////////////////////

            conexion = ds.getConnection();

            DressCodeDAO dressCodeDAO = new DressCodeDAO();
            dressCodeDAO.setConexion(conexion);

            //////////////////////////////////////////
            // COMPROBAR SESSION
            /////////////////////////////////////////
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

                try {
                    /////////////////////////////////////////
                    // RECIBIR Y COMPROBAR PARAMETROS
                    //////////////////////////////////////// 

                    /* recibir parametros de PRG */
                    String nameDressCode = request.getParameter("nameDressCode");
                    String menDetails = request.getParameter("menDetails");
                    String womenDetails = request.getParameter("womenDetails");
                    String urlImage = request.getParameter("urlImage");

                    /* recibir mensaje de PRG */
                    String msgErrorNameDressCode = request.getParameter("msgErrorNameDressCode");
                    String msgErrorMenDetails = request.getParameter("msgErrorMenDetails");
                    String msgErrorWomenDetails = request.getParameter("msgErrorWomenDetails");
                    String msgErrorUrlImage = request.getParameter("msgErrorUrlImage");
                    String msgOk = request.getParameter("msgOk");

                    /* parametros de busqueda */
                    String sidDressCode = request.getParameter("idDressCode");

                    DressCode dressCode = new DressCode();

                    /* comprobar id dressCode */
                    if (sidDressCode == null || sidDressCode.trim().equals("")) {
                        request.setAttribute("msgErrorId", "Error al recibir parámetro");
                    } else {
                        try {
                            dressCode.setIdDressCode(Integer.parseInt(sidDressCode));
                        } catch (NumberFormatException n) {
                        }
                        /* buscar dressCode */
                        DressCode reg = dressCodeDAO.findById(dressCode.getIdDressCode());
                        if (reg != null) {

                            /* obtener atributos del dao */
                            request.setAttribute("idDressCode", reg.getIdDressCode());

                            /* comprobar nameDressCode */
                            if (msgErrorNameDressCode == null || msgErrorNameDressCode.trim().equals("")) {
                                request.setAttribute("nameDressCode", reg.getNameDressCode());
                            } else {
                                request.setAttribute("msgErrorNameDressCode", msgErrorNameDressCode);
                                request.setAttribute("nameDressCode", nameDressCode);
                            }

                            /* comprobar men Details */
                            if (msgErrorMenDetails == null || msgErrorMenDetails.trim().equals("")) {
                                request.setAttribute("menDetails", reg.getMenDetails());
                            } else {
                                request.setAttribute("msgErrorMenDetails", msgErrorMenDetails);
                                request.setAttribute("menDetails", menDetails);
                            }

                            /* comprobar woman details */
                            if (msgErrorWomenDetails == null || msgErrorWomenDetails.trim().equals("")) {
                                request.setAttribute("womenDetails", reg.getWomenDetails());
                            } else {
                                request.setAttribute("msgErrorWomenDetails", msgErrorWomenDetails);
                                request.setAttribute("womenDetails", womenDetails);
                            }

                            /* comprobar url */
                            if (msgErrorUrlImage == null || msgErrorUrlImage.trim().equals("")) {
                                request.setAttribute("urlImage", reg.getUrlImage());
                            } else {
                                request.setAttribute("msgErrorUrlImage", msgErrorUrlImage);
                                request.setAttribute("urlImage", urlImage);
                            }

                            if (msgOk == null || msgOk.trim().equals("")) {
                                request.setAttribute("msg", "Se encontró el registro!");
                            } else {
                                request.setAttribute("msgOk", msgOk);
                            }

                        } else {
                            request.setAttribute("msgErrorFound", "Error: No se encontró el registro.");
                        }
                    }
                } catch (Exception parameterException) {
                } finally {
                    request.getRequestDispatcher("/dressCode/dressCodeUpdate.jsp").forward(request, response);
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
