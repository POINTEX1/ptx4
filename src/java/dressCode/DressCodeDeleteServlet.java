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
@WebServlet(name = "DressCodeDeleteServlet", urlPatterns = {"/DressCodeDeleteServlet"})
public class DressCodeDeleteServlet extends HttpServlet {

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
            ////////////////////////////////////
            // ESTABLECER CONEXION
            ////////////////////////////////////

            conexion = ds.getConnection();

            DressCodeDAO dressCodeDAO = new DressCodeDAO();
            dressCodeDAO.setConexion(conexion);

            ////////////////////////////////////
            // COMPROBAR SESSION
            ////////////////////////////////////
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

                //////////////////////////////////////
                // RECIBIR Y COMPROBAR PARAMETROS
                //////////////////////////////////////

                String btnDelRow = request.getParameter("btnDelRow");
                String btnDelCol = request.getParameter("btnDelCol");

                DressCode dressCode = new DressCode();

                /* instanciar url */
                String url = "?target=main";

                ////////////////////////////////
                // ELIMINAR POR REGISTRO
                ////////////////////////////////
                if (btnDelRow != null) {
                    /* recibir parametros */
                    String id = request.getParameter("idDressCode");
                    try {
                        dressCode.setIdDressCode(Integer.parseInt(id));
                        /* eliminar registro */
                        try {
                            dressCodeDAO.delete(dressCode.getIdDressCode());
                            url += "&msgDel=Un registro ha sido eliminado.";
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            url += "&msgErrorConstraint=Error de restricción: No puede eliminar el registro, existen dependencias asociadas.";
                        }
                    } catch (NumberFormatException n) {
                    }
                }

                ///////////////////////////////////
                // ELIMINAR VARIOS REGISTROS
                ///////////////////////////////////
                if (btnDelCol != null) {
                    /* recibir parametros*/
                    String[] outerArray = request.getParameterValues("chk");
                    int i = 0;
                    int cont = 0;
                    try {
                        while (outerArray[i] != null) {
                            try {
                                dressCodeDAO.delete(Integer.parseInt(outerArray[i]));
                                cont++;
                            } catch (Exception ex) {
                                url += "&msgErrorConstraint=Error: No puede eliminar registro con ID: " + outerArray[i] + ", existen referencias asociadas.";
                            }
                            i++;
                        }
                    } catch (Exception ex) {
                    }
                    if (cont == 1) {
                        url += "&msgDel=Un registro ha sido eliminado.";
                    } else if (cont > 1) {
                        url += "&msgDel=" + cont + " registros han sido eliminados.";
                    }
                }

                /* send redirect */
                response.sendRedirect("DressCodeMainServlet" + url);

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
