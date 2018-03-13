package maciejtrudnos.sklep;

/**
 * Created by Maciej on 21.12.2017.
 */

public class DataAdapter {

    public String ImageURL;
    public String ImageTitle;
    public String Cena;

    public String getImageUrl() {

        return ImageURL;
    }

    public void setImageUrl(String imageServerUrl) {

        this.ImageURL = imageServerUrl;
    }

    public String getImageTitle() {

        return ImageTitle;
    }

    public void setImageTitle(String Imagetitlename) {

        this.ImageTitle = Imagetitlename;
    }

    public String getCenaTitle() {

        return Cena;
    }

    public void setCena(String CenaName) {

        this.Cena = CenaName;
    }


}
