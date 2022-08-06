package model.serializable;

import model.Artikel;
import model.Kunde;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashMap;

public interface RechnungSerial extends Serializable {

    public Instant getInstant();

    public void setInstant(Instant instant);

    public Kunde getKunden();

    public void setKunden(Kunde kunde);

    public HashMap<Artikel, Integer> getArtikels();

    public void setArtikels(HashMap<Artikel, Integer> artikels);
}
