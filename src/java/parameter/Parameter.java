/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parameter;

/**
 *
 * @author alexander
 */
public class Parameter implements java.io.Serializable {

    public int waitingCard;
    public int numberEvent;
    public int numberPromo;
    public String banerCentralEvent;
    public String banerCentralPromo;
    public String banerCentralExchange;
    public String banerTopEvent;
    public String banerTopPromo;
    public String banerTopMyPlace;
    public String banerTopFindPlace;
    public String banerTopConfiguration;

    public Parameter() {
    }

    public String getBanerCentralEvent() {
        return banerCentralEvent;
    }

    public void setBanerCentralEvent(String banerCentralEvent) {
        this.banerCentralEvent = banerCentralEvent;
    }

    public String getBanerCentralPromo() {
        return banerCentralPromo;
    }

    public void setBanerCentralPromo(String banerCentralPromo) {
        this.banerCentralPromo = banerCentralPromo;
    }

    public String getBanerCentralExchange() {
        return banerCentralExchange;
    }

    public void setBanerCentralExchange(String banerCentralExchange) {
        this.banerCentralExchange = banerCentralExchange;
    }

    public String getBanerTopEvent() {
        return banerTopEvent;
    }

    public void setBanerTopEvent(String banerTopEvent) {
        this.banerTopEvent = banerTopEvent;
    }

    public String getBanerTopPromo() {
        return banerTopPromo;
    }

    public void setBanerTopPromo(String banerTopPromo) {
        this.banerTopPromo = banerTopPromo;
    }

    public String getBanerTopMyPlace() {
        return banerTopMyPlace;
    }

    public void setBanerTopMyPlace(String banerTopMyPlace) {
        this.banerTopMyPlace = banerTopMyPlace;
    }

    public String getBanerTopFindPlace() {
        return banerTopFindPlace;
    }

    public void setBanerTopFindPlace(String banerTopFindPlace) {
        this.banerTopFindPlace = banerTopFindPlace;
    }

    public String getBanerTopConfiguration() {
        return banerTopConfiguration;
    }

    public void setBanerTopConfiguration(String banerTopConfiguration) {
        this.banerTopConfiguration = banerTopConfiguration;
    }

    public int getWaitingCard() {
        return waitingCard;
    }

    public void setWaitingCard(int waitingCard) {
        this.waitingCard = waitingCard;
    }

    public int getNumberEvent() {
        return numberEvent;
    }

    public void setNumberEvent(int numberEvent) {
        this.numberEvent = numberEvent;
    }

    public int getNumberPromo() {
        return numberPromo;
    }

    public void setNumberPromo(int numberPromo) {
        this.numberPromo = numberPromo;
    }
}
