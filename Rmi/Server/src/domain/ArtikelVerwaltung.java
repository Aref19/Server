package domain;

import Common.SaveRepo;
import db.Persistent.SaveFile;
import exception.BestandNichtAusreichendException;
import exception.NotFoundException;
import model.Artikel;
import model.Massengutartikel;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ArtikelVerwaltung {

    final String filename = "Rmi/Server/Artikel.txt";
    SaveRepo saveRepo;
    /**
     * Arraylist für Artikel
     */
    private List<Artikel> artikelList;

    public ArtikelVerwaltung() throws RemoteException {
        saveRepo = new SaveFile();
        saveRepo.creatFile(filename);
        saveRepo.openForRead(filename);
        artikelList = saveRepo.loadListArtikels();
        saveRepo.closRead();
    }

    /**
     * Methode um eine Artikel an zu legen und in der Arraylist zu speichern.
     *
     * @param artikelBestand
     * @param artikelBezeichnung
     */

    public void artikelAnlegen(int artikelBestand, String artikelBezeichnung, double preis, int masse) throws IOException { //Artikelerschaffen
        Artikel artikel;
        if (masse > 1) {
            artikel = new Massengutartikel(artikelBezeichnung, artikelBestand, preis, masse);
        } else {
            artikel = new Artikel(artikelBezeichnung, artikelBestand, preis);
        }
        if (!checkArikel(artikelBezeichnung, artikelBestand)) {
            artikelList.add(artikel);
        }
        saveAtrikel(artikelList);

    }

    public void saveAtrikel(List<Artikel> artikelList) {
        try {
            saveRepo.openForWrite(filename);
            saveRepo.saveArtikel(artikelList);
            saveRepo.closeWrite();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Methode ArtikelLöschen entfernt artikel von der Artikel Liste
     *
     * @param artikel
     * @return
     */
    public boolean artikelLoeschen(Artikel artikel) {
        return artikelList.remove(artikel);
    }

    /**
     * Es wird Artikel gesucht nach ihrer Bezeichnung und wird Arraylist zurückgegeben.
     *
     * @param artikelBezeichnung
     * @return
     */
    public ArrayList<Artikel> artikelSuchen(String artikelBezeichnung) {
        ArrayList<Artikel> gefundenArtikelList = new ArrayList<>();
        for (Artikel artikel : artikelList) {
            if (
                    artikel.getArtikelBezeichnung().toLowerCase()
                            .contains(artikelBezeichnung.toLowerCase())
            ) {
                gefundenArtikelList.add(artikel);
            }
        }
        return gefundenArtikelList;
    }

    public Artikel findArtikel(String name) throws NotFoundException {
        for (Artikel artikel : artikelList) {
            if (artikel.getArtikelBezeichnung().equals(name)) {
                return artikel;
            }
        }
        throw new NotFoundException("Eingegeben Artikel Existiert leider nicht");
    }


    /**
     * Beim ArtikelBestandErhöhen Methode wird nur den Bestand erhöht durch ausgleich vom Artikel Nr.
     * Diese Methode gibt es keine Werte zurück, erkennt man auch durch Void.
     * @param artikelNr
     * @param artikelBestand
     */


    /**
     * Die ArrayList wird wieder zurückgegeben.
     *
     * @return
     */
    public List<Artikel> getArtikelList() {
        return artikelList;
    }

    public ArrayList<Artikel> artikelSortieren(boolean sort) {
        ArrayList<Artikel> sortertlist = new ArrayList<>(artikelList);
        if (sort) {
            Collections.sort(sortertlist, new Comparator<Artikel>() {
                @Override
                public int compare(Artikel artikel1, Artikel artikel2) {
                    return Integer.valueOf(artikel1.getArtikelNr()).compareTo(artikel2.getArtikelNr());
                }
            });
        } else {
            Collections.sort(sortertlist, new Comparator<Artikel>() {
                @Override
                public int compare(Artikel o1, Artikel o2) {
                    return o1.getArtikelBezeichnung().compareTo(o2.getArtikelBezeichnung());
                }

            });
        }
        return sortertlist;
    }


    public void artikelBestandReduzieren(Artikel artikel, int anzahl) throws BestandNichtAusreichendException {

        for (Artikel artikelSuchen : artikelList) {
            if (artikelSuchen.equals(artikel)) {
                if (artikelSuchen.getArtikelBestand() - anzahl >= 0) {
                    artikelSuchen.setArtikelBestand(artikelSuchen.getArtikelBestand() - anzahl);
                    return;
                }

            }
        }
        throw new BestandNichtAusreichendException(artikel);
    }

    private boolean checkArikel(String name, int anzahl) {
        Artikel artikel = null;
        for (Artikel a : artikelList) {
            if (a.getArtikelBezeichnung().equals(name)) {
                artikel = a;

            } else
                return false;
        }
        if (artikel != null) {
            artikelList.remove(artikel);
            artikel.setArtikelBestand(artikel.getArtikelBestand() + anzahl);
            artikelList.add(artikel);
            System.out.println("add");
        }

        return true;
    }

    public void returnWare(Artikel artikel, String anzah) {
        for (int i = 0; i < artikelList.size(); i++) {
            if (artikel.getArtikelBezeichnung().equals(artikelList.get(i).getArtikelBezeichnung())) {
                artikelList.get(i).setArtikelBestand((artikelList.get(i).getArtikelBestand() + Integer.parseInt(anzah)));
                return;
            }
        }
        artikelList.add(artikel);
    }
}
