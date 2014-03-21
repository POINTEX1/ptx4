/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientExchangeCheck;

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
 * @author Joseph
 */
public class ClientExchangeCheckDAO {

    private Connection conexion;

    public Connection getConnection() {
        return conexion;
    }

    public void setConnection(Connection conexion) {
        this.conexion = conexion;
    }

    public Collection<ClientExchangeCheck> getAll()  {
        Statement sentence = null;
        ResultSet result = null;
        Collection<ClientExchangeCheck> list = new ArrayList<ClientExchangeCheck>();
        try {
            sentence = conexion.createStatement();
            String sql = "select * from  client_exchange_check cec, user_card uc, exchangeable e, place pl  where uc.rut = cec.rut and e.id_exchangeable = cec.id_exchangeable and pl.id_place = e.id_place order by cec.id_exchangeable desc";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                /*Instanciar objeto*/
                ClientExchangeCheck reg = new ClientExchangeCheck();
                /*Obtener result set*/
                reg.setIdCheck(result.getInt("id_check"));
                reg.setRut(result.getInt("rut"));
                reg.setIdExchangeable(result.getInt("id_exchangeable"));
                reg.setDv(result.getString("dv"));
                reg.setIdPlace(result.getInt("id_place"));
                reg.setNamePlace(result.getString("name_place"));
                reg.setTittle(result.getString("tittle"));
                reg.setCreateTime(result.getString("create_time"));

                /*Agregar a la lista*/
                list.add(reg);
            }
        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en ClientExchangeCheckDAO, getAll() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en ClientExchangeCheckDAO, getAll() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en ClientExchangeCheckDAO, getAll() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en ClientExchangeCheckDAO, getAll() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en ClientExchangeCheckDAO, getAll() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en ClientExchangeCheckDAO, getAll() : " + ex);
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
    
    public ClientExchangeCheck findByRutIdCheck(ClientExchangeCheck reg){
        Statement sentence = null;
        ResultSet result = null;

        
     
        try{
         sentence=conexion.createStatement();
         String sql="select * from exchangeable e, user_card u, client_exchange_check cec where  cec.id_check="+reg.getIdCheck()+" and cec.rut="+reg.getRut()+"  and cec.id_exchangeable=e.id_exchangeable and cec.rut=u.rut ";
         result= sentence.executeQuery(sql);
         
         while(result.next()){
            /*Instanciar el objeto*/
            reg= new ClientExchangeCheck();
            /*obtener resultset*/
            reg.setIdCheck(result.getInt("id_check"));
            reg.setRut(result.getInt("rut"));
            reg.setDv(result.getString("dv"));
            reg.setIdExchangeable(result.getInt("id_exchangeable"));
            
         }
        }catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en ClientExchangeCheckDao, findbyRutIdPromo() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en ClientExchangeCheckDao, findbyRutIdPromo() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en ClientExchangeCheckDao, findbyRutIdPromo() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en ClientExchangeCheckDao, findbyRutIdPromo() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en ClientExchangeCheckDao, findbyRutIdPromo() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en ClientExchangeCheckDao, findbyRutIdPromo() : " + ex);
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
    
    public void insert(ClientExchangeCheck reg) {
        PreparedStatement sentence = null;

        try {
            String sql = "insert into client_exchange_check(id_exchangeable,rut,dv) value(?, ?, ?)";
            sentence = conexion.prepareStatement(sql);

            sentence.setInt(1, reg.getIdExchangeable());
            sentence.setInt(2, reg.getRut());
            sentence.setString(3, reg.getDv());

            sentence.executeUpdate();
        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en ClientExchangeCheckDAO, insert() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en ClientExchangeCheckDAO, insert() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en ClientExchangeCheckDAO, insert() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en ClientExchangeCheckDAO, insert() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en ClientExchangeCheckDAO, insert() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en ClientExchangeCheckDAO, insert() : " + ex);
        } finally {
            /* liberar recursos */
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }
    
    public void delete(int id){
      PreparedStatement sentence = null;
      
      try{
        String sql = "delete from client_exchange_check	 where id_check = ?";
        sentence = conexion.prepareStatement(sql);
        sentence.setInt(1, id);
        sentence.executeUpdate();
      }catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en ClientExchangeCheckDAO, delete() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en ClientExchangeCheckDAO, delete() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en ClientExchangeCheckDAO, delete() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en ClientExchangeCheckDAO, delete() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en ClientExchangeCheckDAO, delete() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en ClientExchangeCheckDAO, delete() : " + ex);
        } finally {
            /* liberar recursos */
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }
    
    
}
