/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package list;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author patricio alberto
 */
public class ListDAO {

    private Connection conexion;

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public Collection<List> getAll() {

        PreparedStatement sentence = null;
        ResultSet result = null;

        Collection<List> list = new ArrayList<List>();

        try {
            String sql = "select * from list l, user_card uc, place pl, event e, card c where l.rut = uc.rut and l.rut = c.rut and pl.id_place = e.id_place and l.id_event = e.id_event group by l.id_event, l.rut order by l.create_time desc";

            sentence = conexion.prepareStatement(sql);

            result = sentence.executeQuery();

            while (result.next()) {
                /* instanciar objeto */
                List reg = new List();
                /* obtener resultset */
                reg.setIdPlace(result.getInt("id_place"));
                reg.setNamePlace(result.getString("name_place"));
                reg.setIdEvent(result.getInt("id_event"));
                reg.setNameEvent(result.getString("tittle"));
                reg.setRut(result.getInt("rut"));
                reg.setDv(result.getString("dv"));
                reg.setBarCode(result.getInt("bar_code"));
                reg.setFirstName(result.getString("first_name"));
                reg.setLastName(result.getString("last_name"));
                reg.setDateBegin(result.getString("date_begin"));
                reg.setDateEnd(result.getString("date_end"));
                reg.setCreateTime(result.getString("create_time"));
                /* agregar a la lista */
                list.add(reg);
            }

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en ListDAO, getAll() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en ListDAO, getAll() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en ListDAO, getAll() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en ListDAO, getAll() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en ListDAO, getAll() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en ListDAO, getAll() : " + ex);
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

    public void insert(List reg) {

        PreparedStatement sentence = null;

        try {
            String sql = "insert into list (id_event, rut, dv, bar_code) values (?, ?, ?, ?)";

            sentence = conexion.prepareStatement(sql);

            sentence.setInt(1, reg.getIdEvent());
            sentence.setInt(2, reg.getRut());
            sentence.setString(3, reg.getDv());
            sentence.setInt(4, reg.getBarCode());

            sentence.executeUpdate();

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en ListDAO, insert() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en ListDAO, insert() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en ListDAO, insert() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en ListDAO, insert() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en ListDAO, insert() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en ListDAO, insert() : " + ex);
        } finally {
            /* liberar recursos */
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }

    public void delete(List reg) {

        PreparedStatement sentence = null;

        try {
            String sql = "delete from list where id_event = ? and rut = ? and bar_code = ? ";

            sentence = conexion.prepareStatement(sql);

            sentence.setInt(1, reg.getIdEvent());
            sentence.setInt(2, reg.getRut());
            sentence.setInt(3, reg.getBarCode());

            sentence.executeUpdate();

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en ListDAO, delete() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en ListDAO, delete() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en ListDAO, delete() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en ListDAO, delete() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en ListDAO, delete() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en ListDAO, delete() : " + ex);
        } finally {
            /* liberar recursos */
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }
}
