/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package placeNews;

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
 * @author alexander
 */
public class PlaceNewsDAO {

    private Connection conexion;

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public PlaceNews findByIdPlaceNews(int id) {

        PreparedStatement sentence = null;
        ResultSet result = null;

        PlaceNews reg = null;

        try {
            String sql = "select * from place_news pn, place pl where pn.id_pnews = ? and pn.id_place = pl.id_place ";

            sentence = conexion.prepareStatement(sql);

            sentence.setInt(1, id);

            result = sentence.executeQuery();

            while (result.next()) {
                /* instanciar objeto */
                reg = new PlaceNews();
                /* obtener resultSet */
                reg.setIdPlaceNews(result.getInt("id_pnews"));
                reg.setIdPlace(result.getInt("id_place"));
                reg.setNamePlace(result.getString("name_place"));
                reg.setTittle(result.getString("tittle"));
                reg.setNewsType(result.getInt("news_type"));
                reg.setDetails(result.getString("details"));
                reg.setUrlImage(result.getString("url_image"));
                reg.setDateBegin(result.getString("date_begin"));
                reg.setDateEnd(result.getString("date_end"));
            }

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en PlaceNewsDAO, findByPlaceNews() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en PlaceNewsDAO, findByPlaceNews() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en PlaceNewsDAO, findByPlaceNews() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en PlaceNewsDAO, findByPlaceNews() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en PlaceNewsDAO, findByPlaceNews() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en PlaceNewsDAO, findByPlaceNews() : " + ex);
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

    public Collection<PlaceNews> getAll() {

        PreparedStatement sentence = null;
        ResultSet result = null;

        Collection<PlaceNews> list = new ArrayList<PlaceNews>();

        try {
            String sql = "select * from place_news pn, place pl where pn.id_place = pl.id_place order by pn.id_pnews desc";

            sentence = conexion.prepareStatement(sql);

            result = sentence.executeQuery();

            while (result.next()) {
                PlaceNews reg = new PlaceNews();

                reg.setIdPlaceNews(result.getInt("id_pnews"));
                reg.setIdPlace(result.getInt("id_place"));
                reg.setNamePlace(result.getString("name_place"));
                reg.setTittle(result.getString("tittle"));
                reg.setNewsType(result.getInt("news_type"));
                reg.setDetails(result.getString("details"));
                reg.setUrlImage(result.getString("url_image"));
                reg.setDateBegin(result.getString("date_begin"));
                reg.setDateEnd(result.getString("date_end"));
                /* agregar a la lista */
                list.add(reg);
            }

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en PlaceNewsDAO, getAll() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en PlaceNewsDAO, getAll() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en PlaceNewsDAO, getAll() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en PlaceNewsDAO, getAll() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en PlaceNewsDAO, getAll() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en PlaceNewsDAO, getAll() : " + ex);
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

    public boolean validateDuplicate(PlaceNews reg) {

        PreparedStatement sentence = null;
        ResultSet result = null;

        boolean find = false;

        try {
            String sql = "select * from place_news where id_place = ? and id_pnews <> ? and tittle = ? and date_end > ?";

            sentence = conexion.prepareStatement(sql);

            sentence.setInt(1, reg.getIdPlace());
            sentence.setInt(2, reg.getIdPlaceNews());
            sentence.setString(3, reg.getTittle());
            sentence.setString(4, reg.getDateBegin());

            result = sentence.executeQuery();

            while (result.next()) {
                /* obtener resultSet */
                find = true;
            }

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en PlaceNewsDAO, validateDuplicate : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en PlaceNewsDAO, validateDuplicate : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en PlaceNewsDAO, validateDuplicate : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en PlaceNewsDAO, validateDuplicate : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en PlaceNewsDAO, validateDuplicate : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en PlaceNewsDAO, validateDuplicate : " + ex);
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

    public void insert(PlaceNews reg) {

        PreparedStatement sentence = null;

        try {
            String sql = "insert into place_news (id_place, tittle, news_type, details, url_image, date_begin, date_end) values (?, ?, ?, ?, ?, ?, ?)";

            sentence = conexion.prepareStatement(sql);


            sentence.setInt(1, reg.getIdPlace());
            sentence.setString(2, reg.getTittle());
            sentence.setInt(3, reg.getNewsType());
            sentence.setString(4, reg.getDetails());
            sentence.setString(5, reg.getUrlImage());
            sentence.setString(6, reg.getDateBegin());
            sentence.setString(7, reg.getDateEnd());

            sentence.executeUpdate();

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en PlaceNewsDAO, insert() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en PlaceNewsDAO, insert() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en PlaceNewsDAO, insert() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en PlaceNewsDAO, insert() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en PlaceNewsDAO, insert() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en PlaceNewsDAO, insert() : " + ex);
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
            String sql = "delete from place_news where id_pnews = ?";

            sentence = conexion.prepareStatement(sql);

            sentence.setInt(1, id);

            sentence.executeUpdate();

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en PlaceNewsDAO, delete() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en PlaceNewsDAO, delete() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en PlaceNewsDAO, delete() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en PlaceNewsDAO, delete() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en PlaceNewsDAO, delete() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en PlaceNewsDAO, delete() : " + ex);
        } finally {
            /* liberar recursos */
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }

    public void update(PlaceNews reg) {
        PreparedStatement sentence = null;

        try {
            String sql = "update place_news set tittle = ?, news_type = ?, details = ?, url_image = ?, date_begin = ?, date_end = ? where id_pnews = ?";

            sentence = conexion.prepareStatement(sql);

            sentence.setString(1, reg.getTittle());
            sentence.setInt(2, reg.getNewsType());
            sentence.setString(3, reg.getDetails());
            sentence.setString(4, reg.getUrlImage());
            sentence.setString(5, reg.getDateBegin());
            sentence.setString(6, reg.getDateEnd());
            sentence.setInt(7, reg.getIdPlaceNews());

            sentence.executeUpdate();

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en PlaceNewsDAO, update() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en PlaceNewsDAO, update() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en PlaceNewsDAO, update() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en PlaceNewsDAO, update() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en PlaceNewsDAO, update() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en PlaceNewsDAO, update() : " + ex);
        } finally {
            /* liberar recursos */
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }
}
