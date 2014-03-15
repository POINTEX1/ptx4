/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package place_news;

/**
 *
 * @author alexander
 */
public class PlaceNews {

    public int idPlaceNews;
    public int idPlace;
    public int newsType;
    public String namePlace;
    public String tittle;
    public String details;
    public String urlImage;
    public String dateBegin;
    public String dateEnd;

    public PlaceNews() {
    }

    public int getIdPlaceNews() {
        return idPlaceNews;
    }

    public void setIdPlaceNews(int idPlaceNews) {
        this.idPlaceNews = idPlaceNews;
    }

    public int getIdPlace() {
        return idPlace;
    }

    public void setIdPlace(int idPlace) {
        this.idPlace = idPlace;
    }

    public int getNewsType() {
        return newsType;
    }

    public void setNewsType(int newsType) {
        this.newsType = newsType;
    }

    public String getNamePlace() {
        return namePlace;
    }

    public void setNamePlace(String namePlace) {
        this.namePlace = namePlace;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(String dateBegin) {
        this.dateBegin = dateBegin;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }
}
