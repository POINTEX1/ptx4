/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clientPromoCheckout;

/**
 *
 * @author patricio
 */
public class ClientPromoCheckout implements java.io.Serializable {

    public int idCheck;
    public int idPromo;
    public int rut;
    public String dv;
    public int idPlace;
    public String namePlace;
    public String tittlePromo;
    public String createTime;
    public String firstName;
    public String lastName;

    public ClientPromoCheckout() {
    }

    public int getIdCheck() {
        return idCheck;
    }

    public void setIdCheck(int idCheck) {
        this.idCheck = idCheck;
    }

    public int getIdPromo() {
        return idPromo;
    }

    public void setIdPromo(int idPromo) {
        this.idPromo = idPromo;
    }

    public int getRut() {
        return rut;
    }

    public void setRut(int rut) {
        this.rut = rut;
    }

    public String getDv() {
        return dv;
    }

    public void setDv(String dv) {
        this.dv = dv;
    }

    public int getIdPlace() {
        return idPlace;
    }

    public void setIdPlace(int idPlace) {
        this.idPlace = idPlace;
    }

    public String getNamePlace() {
        return namePlace;
    }

    public void setNamePlace(String namePlace) {
        this.namePlace = namePlace;
    }

    public String getTittlePromo() {
        return tittlePromo;
    }

    public void setTittlePromo(String tittlePromo) {
        this.tittlePromo = tittlePromo;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
