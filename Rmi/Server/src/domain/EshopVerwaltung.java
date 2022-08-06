package domain;


import Common.EschopVerwaltungRemote;
import exception.*;
import model.*;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EshopVerwaltung
        extends UnicastRemoteObject
        implements EschopVerwaltungRemote {
    private ArtikelVerwaltung artikelVerwaltung;
    private KundeVerwaltung kundeVerwaltung;
    private RechnungWarenkorb rechnungWarenkorb;
    private WarenkorbVerwaltung warenkorbVerwaltung;

    /**
     * TODO pdf Datei laden
     **/
    public EshopVerwaltung() throws RemoteException {
        artikelVerwaltung = new ArtikelVerwaltung();
        kundeVerwaltung = new KundeVerwaltung();
        rechnungWarenkorb = new RechnungWarenkorb();
        warenkorbVerwaltung = new WarenkorbVerwaltung();
    }

    public ArrayList<Artikel> artikelSortieren(boolean artsort) {
        return artikelVerwaltung.artikelSortieren(artsort);
    }

    /**
     * Die übertragene Daten von Methode KundeRegistrieren werden hier im KundenRegistrieren über Parameter kunde weiter gegeben.
     * Die daten werden im Methode registrieren verarbeitet.
     *
     * @param kunde
     */
    public void kundenRegistrieren(Kunde kunde) throws INcorrectEmailException, RegisitierungException {
        Person.checkEmail(kunde.getEmail());
        kundeVerwaltung.registrieren(kunde);
    }

    public Einlogen kundenEinloggen(String na, String pas) throws LoginFailedException {
        Einlogen einlogen = kundeVerwaltung.einlogen(na, pas);
        warenkorbVerwaltung.loadWaren(einlogen.person);
        return einlogen;
    }

    public void returnArikel(String artiekelName, String anzahl, Person person) throws NotFoundException {
        Artikel artikel = findArtikel(artiekelName);
        artikelVerwaltung.returnWare(artikel, anzahl);
        warenkorbVerwaltung.returnArtikel(person, artikel);
    }

    public Person warenlegen(String name, int anzahl, Person person) throws BestandNichtAusreichendException, NotFoundException {
        Artikel artikel = findArtikel(name);
        artikelVerwaltung.artikelBestandReduzieren(artikel, anzahl);
       return warenkorbVerwaltung.addArikel(person, artikel, anzahl);
    }

    public Artikel findArtikel(String name) throws NotFoundException {
        return artikelVerwaltung.findArtikel(name);
    }

    public void artikelAnlegen(Mitarbeiter mitarbeiter, Artikel artikel) throws IOException {
        try {
            if (artikel instanceof Massengutartikel) {
                artikelVerwaltung.artikelAnlegen(artikel.getArtikelBestand(), artikel.getArtikelBezeichnung(), artikel.getPreis(), ((Massengutartikel) artikel).getMasse());
            } else {
                artikelVerwaltung.artikelAnlegen(artikel.getArtikelBestand(), artikel.getArtikelBezeichnung(), artikel.getPreis(), 1);
            }
        } catch (IOException e) {
            throw e;
        }
    }

    public List<Artikel> artikelZeigen() {
        return artikelVerwaltung.getArtikelList();
    }

    public Rechnung getRec(Kunde kunde, HashMap<Artikel, Integer> artikels) throws IOException {
        return rechnungWarenkorb.creatRec(kunde, artikels);
    }

    public WarenKorp kundeWaren(Person person) throws NotFoundException {
        List<WarenKorp> warenKorpList = warenkorbVerwaltung.getSavedWaren();
        for (int i = 0; i < warenKorpList.size(); i++) {
            if (warenKorpList.get(i).getEmail().equals(person.getEmail())) {// nur waren von kunde
                Artikel artikel = findArtikel(warenKorpList.get(i).getNameArtikel());
                warenkorbVerwaltung.getWarenKorb(person).addArtikle(artikel, warenKorpList.get(i).getAnzahl());
            }
        }
        WarenKorp warenKorp=warenkorbVerwaltung.getWarenKorb(person);
        return warenkorbVerwaltung.getWarenKorb(person);
    }

    public void saveWaren(Person person, boolean buystatus) {
        List<Artikel> artikels = ((Kunde) person).getWarenKorp().hashtoList();
        System.out.println(artikels.size());
        List<WarenKorp> warenKorpList = warenkorbVerwaltung.getSavedWaren();
        boolean save = false;
        for (int i = 0; i < artikels.size(); i++) {
            if (warenKorpList.size() > 0) {
                save = false;
                for (int j = 0; j < warenKorpList.size(); j++) {
                    if (warenKorpList.get(j).getEmail().equals(person.getEmail())) {
                        if (artikels.get(i).getArtikelBezeichnung().equals(warenKorpList.get(j).getNameArtikel())) {
                            int anzahl = ((Kunde) person).getWarenKorp().get().get(artikels.get(i));
                            warenKorpList.add(new WarenKorp(person.getEmail(), warenKorpList.get(j).getNameArtikel(),
                                    anzahl * artikels.get(i).getPreis(), anzahl));
                            warenKorpList.remove(j);
                            save = true;
                        }
                    }
                }
                if (!save) { // if email nicht vorhanden oder Artikel andere Name
                    int anzahl = ((Kunde) person).getWarenKorp().get().get(artikels.get(i));
                    warenKorpList.add(new WarenKorp(person.getEmail(), artikels.get(i).getArtikelBezeichnung(),
                            anzahl * artikels.get(i).getPreis(), anzahl));
                    save = false;
                }

            } else {// if data ist leer
                int anzahl = ((Kunde) person).getWarenKorp().get().get(artikels.get(i));
                warenKorpList.add(new WarenKorp(person.getEmail(), artikels.get(i).getArtikelBezeichnung(),
                        anzahl * artikels.get(i).getPreis(), anzahl));
            }
        }
        artikelVerwaltung.saveAtrikel(artikelVerwaltung.getArtikelList());
        warenkorbVerwaltung.saveWarenKorb(warenKorpList);
        if (buystatus) {
            try {
                checkIfBuy(person, warenKorpList);
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    private void checkIfBuy(Person person, List<WarenKorp> warenKorpList) throws NotFoundException {

        for (int i = 0; i < warenKorpList.size(); i++) {
            WarenKorp warenKorp = warenKorpList.get(i);
            if (warenKorp.getEmail().equals(person.getEmail())) {
                warenKorpList.remove(warenKorp);
                Artikel artikel = findArtikel(warenKorp.getNameArtikel());
                i--;
            }

        }
        warenkorbVerwaltung.saveWarenKorb(warenKorpList);
    }
}
