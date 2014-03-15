/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parameter;

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
public class ParameterDAO {

    private Connection conexion;

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public Collection<Parameter> getAll() {

        Statement sentence = null;
        ResultSet result = null;

        Collection<Parameter> list = new ArrayList<Parameter>();

        try {
            sentence = conexion.createStatement();
            String sql = "select * from parameter";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                Parameter reg = new Parameter();
                reg.setWaitingCard(result.getInt("waiting_card"));
                reg.setNumberEvent(result.getInt("number_event"));
                reg.setNumberPromo(result.getInt("number_promo"));
                reg.setBanerCentralEvent(result.getString("baner_central_event"));
                reg.setBanerCentralPromo(result.getString("baner_central_promo"));
                reg.setBanerCentralExchange(result.getString("baner_central_exchangeable"));
                reg.setBanerTopEvent(result.getString("baner_top_event"));
                reg.setBanerTopPromo(result.getString("baner_top_promo"));
                reg.setBanerTopMyPlace(result.getString("baner_top_mplace"));
                reg.setBanerTopFindPlace(result.getString("baner_top_fplace"));
                reg.setBanerTopConfiguration(result.getString("baner_top_configuration"));

                list.add(reg);
            }

            //5 let free resources        
        } catch (SQLException ex) {
            // Gestionar mejor esta exception
            ex.printStackTrace();
        } finally {
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

    public void update(Parameter reg) {

        PreparedStatement sentence = null;

        try {
            String sql = "update parameter set waiting_card = ?, number_event = ?, number_promo = ?, baner_central_event = ?, baner_central_promo = ?, baner_central_exchange = ?, baner_top_event = ?, baner_top_promo = ?, baner_top_mplace = ?, baner_top_fplace = ?, baner_top_configuration = ? ";

            sentence = conexion.prepareStatement(sql);

            sentence.setInt(1, reg.getWaitingCard());
            sentence.setInt(2, reg.getNumberEvent());
            sentence.setInt(3, reg.getNumberPromo());
            sentence.setString(4, reg.getBanerCentralEvent());
            sentence.setString(5, reg.getBanerCentralPromo());
            sentence.setString(6, reg.getBanerCentralExchange());
            sentence.setString(7, reg.getBanerTopEvent());
            sentence.setString(8, reg.getBanerTopPromo());
            sentence.setString(9, reg.getBanerTopMyPlace());
            sentence.setString(10, reg.getBanerTopFindPlace());
            sentence.setString(11, reg.getBanerTopConfiguration());

            sentence.executeUpdate();

        } catch (SQLException ex) {
            // todo Gestionar mejor esta exception
            ex.printStackTrace();
            throw new RuntimeException("RegDAO.update: " + reg, ex);
        } finally {
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }
}
