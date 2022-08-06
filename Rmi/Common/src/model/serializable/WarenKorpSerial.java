package model.serializable;

import exception.NotFoundException;
import model.Artikel;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public interface WarenKorpSerial extends Serializable {
    public String getEmail();

    public void setEmail(String email);

    public String getNameArtikel();

    public void setNameArtikel(String nameArtikel);

    public double getGesamtPreis();

    public void setGesamtPreis(double gesamtPreis);

    public int getAnzahl();

    public void setAnzahl(int anzahl);

    public void addArtikle(Artikel artikel, int anzahl);

    public void loschArtikle(Artikel artikel) throws NotFoundException;

    public HashMap<Artikel, Integer> get();

    public void removeAll();

    public List<Artikel> hashtoList();

    public String toString();

}
