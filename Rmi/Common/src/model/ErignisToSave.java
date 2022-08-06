package model;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

public class ErignisToSave implements Serializable {

    Instant datum;
    private int artikelNummer;
    private UUID mitarbeiterNummer;
    private int anzahl;
    private Ereignis.STATUS status;
    private int serialNummer = 1000;

    public ErignisToSave(UUID mitarbeiterNummer, int artikelNummer, Instant datum, Ereignis.STATUS status, int anzahl) {
        this.datum = datum;
        this.status = status;
        this.anzahl = anzahl;
        this.artikelNummer = artikelNummer;
        this.mitarbeiterNummer = mitarbeiterNummer;

    }

    public static Instant convertStringToInstat(String time) {
        Instant result = Instant.parse(time);
        return result;
    }

    public static Ereignis.STATUS statusFromString(String status) {
        if (status.equals("Neu")) {
            return Ereignis.STATUS.Neu;
        } else if (status.equals("Einlagerung")) {
            return Ereignis.STATUS.Einlagerung;
        } else if (status.equals("Kauf")) {
            return Ereignis.STATUS.Kauf;
        } else
            return Ereignis.STATUS.Auslagerung;
    }

    @Override
    public String toString() {
        return datum + ";" + mitarbeiterNummer + ";" + artikelNummer + ";" + anzahl + ";" + status;
    }

}
