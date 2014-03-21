/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package clientExchangeCheck;

/**
 *
 * @author Joseph
 */
public class ClientExchangeCheck implements java.io.Serializable{
    public int idCheck;
    public int rut;
    public int idExchangeable;
    public String dv;
    public int idPlace;
    public String namePlace;
    public String tittle;
    public String createTime;
    
    public ClientExchangeCheck(){
    }

    public int getIdCheck() {
        return idCheck;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }
    
    public int getRut() {
        return rut;
    }

    public int getIdExchangeable() {
        return idExchangeable;
    }

    public int getIdPlace() {
        return idPlace;
    }

    public String getNamePlace() {
        return namePlace;
    }
    
    
    
    public String getDv() {
        return dv;
    }

    public void setIdCheck(int idCheck) {
        this.idCheck = idCheck;
    }

    public void setRut(int rut) {
        this.rut = rut;
    }

    public void setIdExchangeable(int idExchangeable) {
        this.idExchangeable = idExchangeable;
    }

    public void setDv(String dv) {
        this.dv = dv;
    }

    public void setIdPlace(int idPlace) {
        this.idPlace = idPlace;
    }

    public void setNamePlace(String namePlace) {
        this.namePlace = namePlace;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
   
    
    
}




