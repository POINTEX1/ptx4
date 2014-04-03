/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parameter;

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
public class ParameterDAO {

    private Connection conexion;

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public Parameter getAll() {

        PreparedStatement sentence = null;
        ResultSet result = null;

        Parameter reg = null;

        try {
            String sql = "select * from parameter where id_parameter = 1";

            sentence = conexion.prepareStatement(sql);

            result = sentence.executeQuery();

            while (result.next()) {
                reg = new Parameter();
                reg.setWaitingCard(result.getInt("id_parameter"));
                reg.setWaitingCard(result.getInt("waiting_card"));
                reg.setNumberEvent(result.getInt("number_event"));
                reg.setNumberPromo(result.getInt("number_promo"));
                reg.setBanerCentralEvent(result.getString("baner_central_event"));
                reg.setBanerCentralPromo(result.getString("baner_central_promo"));
                reg.setBanerCentralExchangeable(result.getString("baner_central_exchangeable"));
                reg.setBanerCentralVip(result.getString("baner_central_vip"));
                reg.setBanerCentralAboutUs(result.getString("baner_central_about_us"));
                reg.setBanerTopEvent(result.getString("baner_top_event"));
                reg.setBanerTopPromo(result.getString("baner_top_promo"));
                reg.setBanerTopMyPlace(result.getString("baner_top_mplace"));
                reg.setBanerTopFindPlace(result.getString("baner_top_fplace"));
                reg.setBanerTopConfiguration(result.getString("baner_top_configuration"));
                reg.setBanerTopSocialNetworks(result.getString("baner_top_social_networks"));
            }

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en ParameterDAO, getAll() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en ParameterDAO, getAll() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en ParameterDAO, getAll() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en ParameterDAO, getAll() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en ParameterDAO, getAll() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en ParameterDAO, getAll() : " + ex);
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

    public void update(Parameter reg) {

        PreparedStatement sentence = null;

        try {
            String sql = "update parameter set waiting_card = ?, number_event = ?, number_promo = ?, baner_central_event = ?, baner_central_promo = ?, baner_central_exchangeable = ?, baner_central_vip = ?, baner_central_about_us = ?, baner_top_event = ?, baner_top_promo = ?, baner_top_mplace = ?, baner_top_fplace = ?, baner_top_configuration = ?, baner_top_social_networks = ? where id_parameter = 1";

            sentence = conexion.prepareStatement(sql);

            sentence.setInt(1, reg.getWaitingCard());
            sentence.setInt(2, reg.getNumberEvent());
            sentence.setInt(3, reg.getNumberPromo());
            sentence.setString(4, reg.getBanerCentralEvent());
            sentence.setString(5, reg.getBanerCentralPromo());
            sentence.setString(6, reg.getBanerCentralExchangeable());
            sentence.setString(7, reg.getBanerCentralVip());
            sentence.setString(8, reg.getBanerCentralAboutUs());
            sentence.setString(9, reg.getBanerTopEvent());
            sentence.setString(10, reg.getBanerTopPromo());
            sentence.setString(11, reg.getBanerTopMyPlace());
            sentence.setString(12, reg.getBanerTopFindPlace());
            sentence.setString(13, reg.getBanerTopConfiguration());
            sentence.setString(14, reg.getBanerTopSocialNetworks());

            sentence.executeUpdate();

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en ParameterDAO, update() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en ParameterDAO, update() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en ParameterDAO, update() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en ParameterDAO, update() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en ParameterDAO, update() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en ParameterDAO, update() : " + ex);
        } finally {
            /* liberar recursos */
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }
}
