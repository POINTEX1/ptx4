/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clientNews;

import Helpers.Format;
import Helpers.Message;
import Helpers.MessageList;
import Helpers.Rut;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import userCard.UserCard;
import userCard.UserCardDAO;

/**
 *
 * @author alexander
 */
@WebServlet(name = "ClientNewsAddServlet", urlPatterns = {"/ClientNewsAddServlet"})
public class ClientNewsAddServlet extends HttpServlet {

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

            ClientNewsDAO cnewsDAO = new ClientNewsDAO();
            cnewsDAO.setConexion(conexion);

            UserCardDAO usercardDAO = new UserCardDAO();
            usercardDAO.setConexion(conexion);

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
                if (access != 555 && access != 777) {
                    request.getRequestDispatcher("/ForbiddenServlet").forward(request, response);
                } else {
                    /* obtener los valores de session y asignar valores a la jsp */
                    request.setAttribute("userJsp", username);
                    request.setAttribute("access", access);

                    ///////////////////////////////////
                    // RECIBIR Y COMPROBAR PARAMETROS
                    ///////////////////////////////////

                    /* obtener atributos de la vista */
                    String btnAdd = request.getParameter("add");
                    String stittle = request.getParameter("tittle");
                    String snewsType = request.getParameter("newsType");
                    String srut = request.getParameter("rut");
                    String sdateBegin = request.getParameter("dateBegin");
                    String sdateEnd = request.getParameter("dateEnd");

                    /* instanciar ClientNews */
                    ClientNews cnews = new ClientNews();

                    /* instanciar lista de mensajes */
                    Collection<Message> msgList = new ArrayList<Message>();

                    boolean error = false;

                    /* comprobar si recibe formulario */
                    if (btnAdd == null) {
                        request.setAttribute("msg", "Ingrese Noticia.");
                    } else {
                        /* establecer atributos a la vista */
                        request.setAttribute("tittle", stittle);
                        request.setAttribute("newsType", Integer.parseInt(snewsType));
                        request.setAttribute("rut", srut);
                        request.setAttribute("dateBegin", sdateBegin);
                        request.setAttribute("dateEnd", sdateEnd);

                        /* comprobar tittle */
                        if (stittle == null || stittle.trim().equals("")) {
                            request.setAttribute("msgErrorTittle", true);
                            msgList.add(MessageList.addMessage("Debe ingresar título de la noticia."));
                            error = true;
                        } else {
                            cnews.setTittle(stittle);
                        }

                        /* comprobar type news */
                        if (snewsType == null || snewsType.trim().equals("")) {
                            error = true;
                        } else {
                            try {
                                cnews.setNewsType(Integer.parseInt(snewsType));
                            } catch (NumberFormatException n) {
                                error = true;
                            }
                        }

                        /* comprobar rut */
                        if (srut == null || srut.trim().equals("") || srut.length() < 3) {
                            request.setAttribute("msgErrorRut", true);
                            msgList.add(MessageList.addMessage("Debe ingresar RUT."));
                            error = true;
                        } else {
                            try {
                                if (!Rut.validateRut(srut)) {
                                    request.setAttribute("msgErrorRut", true);
                                    msgList.add(MessageList.addMessage("RUT inválido."));
                                    error = true;
                                } else {
                                    cnews.setRut(Rut.getRut(srut));
                                    cnews.setDv(Rut.getDv(srut));

                                    /* buscar cliente por rut */
                                    try {
                                        UserCard aux = usercardDAO.findByRut(cnews.getRut());
                                        /* comprobar si existencia */
                                        if (aux == null) {
                                            request.setAttribute("msgErrorRut", true);
                                            msgList.add(MessageList.addMessage("No existe un usuario con ese RUT."));
                                            error = true;
                                        }
                                    } catch (Exception ex) {
                                        request.setAttribute("msgErrorRut", true);
                                        msgList.add(MessageList.addMessage("RUT inválido."));
                                        error = true;
                                    }
                                }
                            } catch (Exception ex) {
                                request.setAttribute("msgErrorRut", true);
                                msgList.add(MessageList.addMessage("RUT inválido."));
                                error = true;
                            }
                        }

                        /* comprobar date begin */
                        cnews.setDateBegin(sdateBegin);
                        if (sdateBegin == null || sdateBegin.trim().equals("")) {
                            request.setAttribute("errorDateBegin", true);
                            msgList.add(MessageList.addMessage("Debe ingresar fecha de inicio."));
                            error = true;
                        }

                        /* comprobar date end */
                        cnews.setDateEnd(sdateEnd);
                        if (sdateEnd == null || sdateEnd.trim().equals("")) {
                            request.setAttribute("errorDateEnd", true);
                            msgList.add(MessageList.addMessage("Debe ingresar fecha de término."));
                            error = true;
                        }

                        /* comparar con fecha actual */
                        if (cnews.getDateBegin().compareTo(Format.currentDate()) < 0) {
                            request.setAttribute("errorDateBegin", true);
                            msgList.add(MessageList.addMessage("La fecha de inicio no puede ser menor o igual que la fecha actual."));
                            error = true;
                        }

                        /* comparar fecha de inicio con fecha de término */
                        if (cnews.getDateBegin().compareTo(cnews.getDateEnd()) >= 0) {
                            request.setAttribute("errorDateBegin", true);
                            request.setAttribute("errorDateEnd", true);
                            msgList.add(MessageList.addMessage("La fecha de inicio no puede ser mayor o igual que la fecha de término."));
                            error = true;
                        }

                        ////////////////////////
                        // LOGICA DE NEGOCIO
                        ////////////////////////
                        if (!error) {
                            /* comprobar registros duplicados */
                            try {
                                boolean find = cnewsDAO.validateDuplicate(cnews);
                                if (find) {
                                    request.setAttribute("msgErrorDup", true);
                                    msgList.add(MessageList.addMessage("Ya existe esta noticia. Compruebe utilizando otro título u otro rango de fechas."));
                                } else {
                                    /* insertar nueva noticia */
                                    try {
                                        cnewsDAO.insert(cnews);
                                        request.setAttribute("msgOk", "Registro ingresado exitosamente! ");
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }

                    /* establecer lista de mensajes */
                    if (!msgList.isEmpty()) {
                        request.setAttribute("msgList", msgList);
                    }

                    /* despachar a la vista */
                    request.getRequestDispatcher("/clientNews/clientNewsAdd.jsp").forward(request, response);
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
