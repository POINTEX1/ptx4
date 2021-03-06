/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package point;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import list.List;

/**
 *
 * @author patricio alberto
 */
public class PointDAO {

    private Connection conexion;

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public Collection<Point> getAll() {

        PreparedStatement sentence = null;
        ResultSet result = null;

        Collection<Point> list = new ArrayList<Point>();

        try {
            String sql = "select * from point pt, user_card uc, place pl, city ci where pt.rut = uc.rut and pt.id_place = pl.id_place and pl.id_city = ci.id_city ";

            sentence = conexion.prepareStatement(sql);

            result = sentence.executeQuery();

            while (result.next()) {
                /* instanciar objeto */
                Point reg = new Point();
                /* obtener resultset */
                reg.setIdPlace(result.getInt("id_place"));
                reg.setNamePlace(result.getString("name_place"));
                reg.setNameCity(result.getString("name_city"));
                reg.setRut(result.getInt("rut"));
                reg.setDv(result.getString("dv"));
                reg.setFirstName(result.getString("first_name"));
                reg.setLastName(result.getString("last_name"));
                reg.setPoints(result.getInt("points"));
                reg.setTotalPoint(result.getInt("total_point"));
                /* agregar a la lista */
                list.add(reg);
            }

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en PointDAO, getAll() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en PointDAO, getAll() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en PointDAO, getAll() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad enPointDAO, getAll() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en PointDAO, getAll() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en PointDAO, getAll() : " + ex);
        } finally {
            /* liberar los recursos */
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

    public Point findByPoint(int idPlace, int rut) {

        PreparedStatement sentence = null;
        ResultSet result = null;

        Point reg = null;

        try {
            String sql = "select * from point pt, place pl where pt.id_place = ? and pt.rut = ? and pt.id_place = pl.id_place ";

            sentence = conexion.prepareStatement(sql);

            sentence.setInt(1, idPlace);
            sentence.setInt(2, rut);

            result = sentence.executeQuery();

            while (result.next()) {
                /* liberar recursos */
                reg = new Point();
                /* obtener resultset */
                reg.setIdPlace(result.getInt("id_place"));
                reg.setNamePlace(result.getString("name_place"));
                reg.setRut(result.getInt("rut"));
                reg.setDv(result.getString("dv"));
                reg.setPoints(result.getInt("points"));
            }

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en PointDAO, findByPoint() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en PointDAO, findByPoint() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en PointDAO, findByPoint() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad enPointDAO, findByPoint() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en PointDAO, findByPoint() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en PointDAO, findByPoint() : " + ex);
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

    public void delete(Point point) {

        PreparedStatement sentence = null;

        try {
            String sql = "delete from point where id_place = ? and rut = ? ";

            sentence = conexion.prepareStatement(sql);

            sentence.setInt(1, point.getIdPlace());
            sentence.setInt(2, point.getRut());

            sentence.executeUpdate();

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en PointDAO, delete() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en PointDAO, delete() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en PointDAO, delete() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad enPointDAO, delete() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en PointDAO, delete() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en PointDAO, delete() : " + ex);
        } finally {
            /* liberar recursos */
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }

    public void insert(Point point) {

        PreparedStatement sentence = null;

        try {
            String sql = "insert into point (id_place, rut, dv, points) values (?, ?, ?, ?)";

            sentence = conexion.prepareStatement(sql);

            sentence.setInt(1, point.getIdPlace());
            sentence.setInt(2, point.getRut());
            sentence.setString(3, point.getDv());
            sentence.setInt(4, point.getPoints());

            sentence.executeUpdate();

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en PointDAO, insert() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en PointDAO, insert() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en PointDAO, insert() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad enPointDAO, insert() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en PointDAO, insert() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en PointDAO, insert() : " + ex);
        } finally {
            /* liberar recursos */
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }

    public void insertAllUsersByLastPlace() {

        PreparedStatement sentence = null;

        try {
            String sql = "INSERT INTO point(id_place, rut, dv) SELECT (SELECT max(id_place) from place) as max_place, rut, dv from user_card";

            sentence = conexion.prepareStatement(sql);

            sentence.executeUpdate();

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en PointDAO, insertAllUsersByLastPlace() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en PointDAO, insertAllUsersByLastPlace() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en PointDAO, insertAllUsersByLastPlace() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad enPointDAO, insertAllUsersByLastPlace() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en PointDAO, insertAllUsersByLastPlace() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en PointDAO, insertAllUsersByLastPlace() : " + ex);
        } finally {
            /* liberar recursos */
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }

    public void update(Point point) {

        PreparedStatement sentence = null;

        try {
            String sql = "update point set points = ? where id_place = ? and rut = ? ";

            sentence = conexion.prepareStatement(sql);

            sentence.setInt(1, point.getPoints());
            sentence.setInt(2, point.getIdPlace());
            sentence.setInt(3, point.getRut());

            sentence.executeUpdate();

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en PointDAO, update() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en PointDAO, update() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en PointDAO, update() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad enPointDAO, update() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en PointDAO, update() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en PointDAO, update() : " + ex);
        } finally {
            /* liberar recursos */
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }

    public void updatePointUp(List reg, int points) {

        PreparedStatement sentence = null;

        try {
            String sql = "update point set points = points + ?, total_point = total_point + ? where id_place = ? and rut = ? ";

            sentence = conexion.prepareStatement(sql);

            sentence.setInt(1, points);
            sentence.setInt(2, points);
            sentence.setInt(3, reg.getIdPlace());
            sentence.setInt(4, reg.getRut());

            sentence.executeUpdate();

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en PointDAO, updatePointUp() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en PointDAO, updatePointUp() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en PointDAO, updatePointUp() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad enPointDAO, updatePointUp() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en PointDAO, updatePointUp() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en PointDAO, updatePointUp() : " + ex);
        } finally {
            /* liberar recursos */
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }
}
