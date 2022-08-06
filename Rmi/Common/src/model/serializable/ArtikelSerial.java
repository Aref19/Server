package model.serializable;

import java.io.Serializable;

public interface ArtikelSerial extends Serializable {
    int getArtikelNr();

    void setArtikelNr(int artikelNr);

    double getPreis();

    void setPreis(double preis);

    String getArtikelBezeichnung();

    void setArtikelBezeichnung(String artikelBezeichnung);

    int getArtikelBestand();

    void setArtikelBestand(int artikelBestand);

    int creatId();

}
