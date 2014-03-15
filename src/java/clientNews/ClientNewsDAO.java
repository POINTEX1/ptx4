/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clientNews;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;


/**
 *
 * @author alexander
 */
public class ClientNewsDAO {

    private Connection conexion;

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

   public ClientNews findByClientNews(ClientNews cnews) {

        Statement sentence = null;
        ResultSet result = null;

        ClientNews reg = null;

        try {
            sentence = conexion.createStatement();
            String sql = "select * from client_news cn, user_card uc where cn.id_cnews = " + cnews.getIdClientNews() + " and cn.rut = uc.rut ";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                /* instanciar objeto */
                reg = new ClientNews();
                /* obtener resultSet */
                reg.setIdClientNews(result.getInt("id_cnews"));
                reg.setTittle(result.getString("tittle"));
                reg.setNewsType(result.getInt("news_type"));
                reg.setRut(result.getInt("rut"));
                reg.setDv(result.getString("dv"));
                reg.setFirstName(result.getString("first_name"));
                reg.setLastName(result.getString("last_name"));
                reg.setDateBegin(result.getString("date_begin"));
                reg.setDateEnd(result.getString("date_end"));

            }

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en ClientNewsDAO, findByClientNews() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en ClientNewsDAO, findByClientNews() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en ClientNewsDAO, findbyClientNews() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en ClientNewsDAO, findByClientNews() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en ClientNewsDAO, findbyClientNews() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en ClientNewsDAO, findByClientNews() : " + ex);
        } finally {
            /* liberar recursos */
            try {
                result.close();
            } catch (Exception noGestionar) {
            }
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
        return reg;
    }

    public Collection<ClientNews> getAll() {

        Statement sentence = null;
        ResultSet result = null;

        Collection<ClientNews> list = new ArrayList<ClientNews>();

        try {
            sentence = conexion.createStatement();
            String sql = "select * from client_news cn, user_card uc where cn.rut = uc.rut order by cn.id_cnews desc";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                ClientNews reg = new ClientNews();

                reg.setIdClientNews(result.getInt("id_cnews"));
                reg.setTittle(result.getString("tittle"));
                reg.setNewsType(result.getInt("news_type"));
                reg.setRut(result.getInt("rut"));
                reg.setDv(result.getString("dv"));
                reg.setFirstName(result.getString("first_name"));
                reg.setLastName(result.getString("last_name"));
                reg.setDateBegin(result.getString("date_begin"));
                reg.setDateEnd(result.getString("date_end"));
                /* agregar a la lista */
                list.add(reg);
            }

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en ClientNewsDAO, getAll() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en ClientNewsDAO, getAll() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL ExcepciÃ³n de integridad en ClientNewsDAO, getAll() : " + ex);
            throw new RuntimeException("MySQL ExcepciÃ³n de integridad en ClientNewsDAO, getAll() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL ExcepciÃ³n inesperada en ClientNewsDAO, getAll() : " + ex);
            throw new RuntimeException("MySQL ExcepciÃ³n inesperada en ClientNewsDAO, getAll() : " + ex);
        } finally {
            /* liberar recursos */
            try {
                result.close();
            } catch (Exception noGestionar) {
            }
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
        return list;
    }

    public boolean validateDuplicate(ClientNews reg) {

        Statement sentence = null;
        ResultSet result = null;

        boolean find = false;

        try {
            sentence = conexion.createStatement();
            String sql = "select * from client_news where id_cnews <> " + reg.getIdClientNews() + " and rut = " + reg.getRut() + " and tittle = '" + reg.getTittle() + "' and date_end > '" + reg.getDateBegin() + "' ";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                /* obtener resultSet */
                find = true;
            }

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en ClientNewsDAO, validateDuplicate : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en ClientNewsDAO, validateDuplicate : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL ExcepciÃ³n de integridad en ClientNewsDAO, validateDuplicate : " + ex);
            throw new RuntimeException("MySQL ExcepciÃ³n de integridad en ClientNewsDAO, validateDuplicate : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL ExcepciÃ³n inesperada en ClientNewsDAO, validateDuplicate : " + ex);
            throw new RuntimeException("MySQL ExcepciÃ³n inesperada en ClientNewsDAO, validateDuplicate : " + ex);
        } finally {
            /* liberar recursos */
            try {
                result.close();
            } catch (Exception noGestionar) {
            }
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
        return find;
    }

    public void insert(ClientNews reg) {

        PreparedStatement sentence = null;

        try {
            String sql = "insert into client_news (tittle, news_type, rut, dv, date_begin, date_end) values (?, ?, ?, ?, ?, ?)";

            sentence = conexion.prepareStatement(sql);


            sentence.setString(1, reg.getTittle());
            sentence.setInt(2, reg.getNewsType());
            sentence.setInt(3, reg.getRut());
            sentence.setString(4, reg.getDv());
            sentence.setString(5, reg.getDateBegin());
            sentence.setString(6, reg.getDateEnd());

            sentence.executeUpdate();

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en ClientNewsDAO, insert() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en ClientNewsDAO, insert() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL ExcepciÃ³n de integridad en ClientNewsDAO, insert() : " + ex);
            throw new RuntimeException("MySQL ExcepciÃ³n de integridad en ClientNewsDAO, insert() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL ExcepciÃ³n inesperada en ClientNewsDAO, insert() : " + ex);
            throw new RuntimeException("MySQL ExcepciÃ³n inesperada en ClientNewsDAO, insert() : " + ex);
        } finally {
            /* liberar recursos */
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }

    public void delete(int id) {

        PreparedStatement sentence = null;

        try {
            String sql = "delete from client_news where id_cnews = ?";

            sentence = conexion.prepareStatement(sql);

            sentence.setInt(1, id);

            sentence.executeUpdate();

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en ClientNewsDAO, delete() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en ClientNewsDAO, delete() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL ExcepciÃ³n de integridad en ClientNewsDAO, delete() : " + ex);
            throw new RuntimeException("MySQL ExcepciÃ³n de integridad en ClientNewsDAO, delete() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL ExcepciÃ³n inesperada en ClientNewsDAO, delete() : " + ex);
            throw new RuntimeException("MySQL ExcepciÃ³n inesperada en ClientNewsDAO, delete() : " + ex);
        } finally {
            /* liberar recursos */
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }

    public void update(ClientNews reg) {
        PreparedStatement sentence = null;

        try {
            String sql = "update client_news set tittle = ?, news_type = ?, date_begin = ?, date_end = ? where id_cnews = ? and rut = ? ";

            sentence = conexion.prepareStatement(sql);

            sentence.setString(1, reg.getTittle());
            sentence.setInt(2, reg.getNewsType());
            sentence.setString(3, reg.getDateBegin());
            sentence.setString(4, reg.getDateEnd());
            sentence.setInt(5, reg.getIdClientNews());
            sentence.setInt(6, reg.getRut());

            sentence.executeUpdate();

         } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en ClientNewsDAO, update() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en ClientNewsDAO, update() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en ClientNewsDAO, update() : " + ex);
            throw new RuntimeException("MySQL ExcepciÃ³n de integridad en ClientNewsDAO, update() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en ClientNewsDAO, update() : " + ex);
            throw new RuntimeException("MySQL ExcepciÃ³n inesperada en ClientNewsDAO, update() : " + ex);
        } finally {
            /* liberar recursos */
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }
}

