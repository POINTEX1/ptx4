/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clientPromoCheckout;

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
 * @author patricio
 */
public class ClientPromoCheckoutDAO {

    private Connection conexion;

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public Collection<ClientPromoCheckout> getAll() {

        Statement sentence = null;
        ResultSet result = null;

        Collection<ClientPromoCheckout> list = new ArrayList<ClientPromoCheckout>();

        try {
            sentence = conexion.createStatement();
            String sql = "select * from client_promo_check cpc, place pl, promo pr, user_card uc where uc.rut = cpc.rut and pr.id_promo = cpc.id_promo and pr.id_place = pl.id_place order by cpc.id_check desc";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                /* instanciar objeto */
                ClientPromoCheckout reg = new ClientPromoCheckout();
                /* obtener resultset */
                reg.setIdCheck(result.getInt("id_check"));
                reg.setIdPlace(result.getInt("id_place"));
                reg.setNamePlace(result.getString("name_place"));
                reg.setIdPromo(result.getInt("id_promo"));
                reg.setTittlePromo(result.getString("tittle"));
                reg.setRut(result.getInt("rut"));
                reg.setDv(result.getString("dv"));
                reg.setCreateTime(result.getString("create_time"));

                /* agregar a la lista */
                list.add(reg);
            }

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en ClientPromoCheckDAO, getAll() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en ClientPromoCheckDAO, getAll() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en ClientPromoCheckDAO, getAll() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en ClientPromoCheckDAO, getAll() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en ClientPromoCheckDAO, getAll() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en ClientPromoCheckDAO, getAll() : " + ex);
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

    public void insert(ClientPromoCheckout reg) {

        PreparedStatement sentence = null;

        try {

            String sql = "insert into client_promo_check (id_promo, rut, dv) values (?, ?, ?)";

            sentence = conexion.prepareStatement(sql);

            sentence.setInt(1, reg.getIdPromo());
            sentence.setInt(2, reg.getRut());
            sentence.setString(3, reg.getDv());

            sentence.executeUpdate();

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en ClientPromoCheckDAO, insert() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en ClientPromoCheckDAO, insert() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en ClientPromoCheckDAO, insert() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en ClientPromoCheckDAO, insert() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en ClientPromoCheckDAO, insert() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en ClientPromoCheckDAO, insert() : " + ex);
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
            String sql = "delete from client_promo_check where id_check = ?";

            sentence = conexion.prepareStatement(sql);

            sentence.setInt(1, id);

            sentence.executeUpdate();

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en ClientPromoCheckDAO, delete() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en ClientPromoCheckDAO, delete() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en ClientPromoCheckDAO, delete() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en ClientPromoCheckDAO, delete() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en ClientPromoCheckDAO, delete() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en ClientPromoCheckDAO, delete() : " + ex);
        } finally {
            /* liberar recursos */
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }
}
