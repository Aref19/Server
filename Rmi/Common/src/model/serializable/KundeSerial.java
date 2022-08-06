package model.serializable;

import model.Adresse;
import model.WarenKorp;

import java.io.Serializable;

public interface KundeSerial extends Serializable {

    public Adresse getAdresse();

    public void setAdresse(Adresse adresse);

    /**
     * Getter und Setter
     *
     * @return
     */
    public int getKundenHausNr();

    public void setKundenHausNr(int kundenHausNr);

    public int getKundenPlz();

    public void setKundenPlz(int kundenPlz);

    public WarenKorp getWarenKorp();

    public void setWarenKorp(WarenKorp warenKorp);
}
