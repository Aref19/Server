package GUI.services;

import Common.EschopVerwaltungRemote;
import GUI.until.PdfGenerator;
import exception.BestandNichtAusreichendException;
import exception.LoginFailedException;
import exception.NotFoundException;
import model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.List;

public class KundenService {


    private static Person person;
    DefaultListModel<String> model;
    private DefaultTableModel defaultTableModel;
    private EschopVerwaltungRemote eschopVerwaltungRemote;

    public KundenService() {
        defaultTableModel = new DefaultTableModel();
        model = new DefaultListModel<>();
        registry();
    }


    public void sacheKaufen() throws RemoteException {
        saveWarenWarenKorb(true);
    }

    public void saveWarenWarenKorb(boolean buyStatus) throws RemoteException {
        eschopVerwaltungRemote.saveWaren(person, buyStatus);
    }

    public DefaultListModel kasse() {
        List<Artikel> artikelList = null;
        HashMap<Artikel, Integer> artikels = null;
        WarenKorp warenKorp;
        try {
            model = new DefaultListModel<>();
            warenKorp = eschopVerwaltungRemote.kundeWaren(person);
            artikels = warenKorp.get();
            artikelList = warenKorp.hashtoList();
        } catch (NotFoundException e) {
            e.getMessage();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        double gesamtpreis = 0;
        for (int i = 0; i < artikelList.size(); i++) {
            Artikel artikel = artikelList.get(i);
            model.addElement(artikel.getArtikelBezeichnung() + "     " + artikels.get(artikel) + "    " + (artikel.getPreis() * artikels.get(artikel)));
            gesamtpreis += (artikel.getPreis() * artikels.get(artikel));
        }
        model.addElement("--------------------------------------------");
        model.addElement("gesamt Preis :  " + gesamtpreis);
        return model;
    }

    public DefaultTableModel entfernenFromKorp(int index) throws NotFoundException, RemoteException {
        String artikel[] = model.get(index).split("    ");
        model.remove(index);
        eschopVerwaltungRemote.returnArikel(artikel[0], artikel[1], person);
        return putArtikel();


    }

    public DefaultListModel artikelEinfugen(String name, String bestand, String preis, int anzahl) throws BestandNichtAusreichendException, NotFoundException, RemoteException {
        if (anzahl > 0) {
          person= eschopVerwaltungRemote.warenlegen(name, anzahl, person);
            System.out.println(((Kunde) person).getWarenKorp().get().size());
            saveModel(model, name, bestand, preis, anzahl);
        }
        return model;
    }


    public Person login(String email, String pass) throws LoginFailedException, RemoteException {
        person = eschopVerwaltungRemote.kundenEinloggen(email, pass).person;
        return person;

    }

    public DefaultTableModel putArtikel() throws RemoteException {
        List<Artikel> artikels = eschopVerwaltungRemote.artikelZeigen();
        defaultTableModel = new DefaultTableModel();
        defaultTableModel.addColumn("name");
        defaultTableModel.addColumn("betsand");
        defaultTableModel.addColumn("preis");
        defaultTableModel.addColumn("Masse");
        for (Artikel ar : artikels) {
            int masse = ar instanceof Massengutartikel ? ((Massengutartikel) ar).getMasse() : 1;
            if (ar.getArtikelBestand() != 0) {
                String[] artikleArray = {ar.getArtikelBezeichnung(), String.valueOf(ar.getArtikelBestand()), String.valueOf(ar.getPreis()), String.valueOf(masse)};
                defaultTableModel.addRow(artikleArray);
            }
        }
        return defaultTableModel;
    }

    public Rechnung creatPdf() {
        PdfGenerator pdfGenerator = null;
        Rechnung rechnung = null;
        try {
            rechnung = eschopVerwaltungRemote.getRec(((Kunde) person), ((Kunde) person).getWarenKorp().get());
            pdfGenerator = new PdfGenerator(rechnung);
        } catch (IOException e) {
            e.printStackTrace();
        }
        pdfGenerator.creatPdf();
        return rechnung;
    }

    public void kill(JFrame jFrame) {
        jFrame.dispose();
    }

  /*  public void kundRegisteren(Kunde kunde, JFrame parent) {
        Alert alert = null;
        try {
            EschopClient.eshopVerwaltungRemote.kundenRegistrieren(kunde);
            return;
        } catch (INcorrectEmailException ex) {
            alert = new Alert(parent, ex.getMessage(), "Email!");
        } catch (NumberFormatException ne) {
            alert = new Alert(parent, "das ist keine Nr\n" + ne.getMessage(), "Nummer");
        } catch (RegisitierungException regex) {
            alert = new Alert(parent, regex.getMessage(), "Registeren");
        }
        alert.showInfoMassage();

    }*/

    private void saveModel(DefaultListModel defaultListModel,
                           String name, String bestand, String preis, int anzahl) {
        double gesamtpreis = Double.parseDouble(preis) * anzahl;

        for (int i = 0; i < defaultListModel.size(); i++) {
            String content[] = defaultListModel.getElementAt(i).toString().split("    ");
            if (content[0].equals(name)) {
                defaultListModel.remove(i);
                anzahl = Integer.parseInt(content[1]) + anzahl;
                gesamtpreis = Double.parseDouble(preis) * anzahl;
                defaultListModel.addElement(content[0] + "    " + anzahl + "    " + gesamtpreis);
                return;
            }
        }
        model.addElement(name + "    " + anzahl + "    " + gesamtpreis);

    }

    private void registry() {
        final String serviceName = "kunde";
        String host = "localhost";
        try {
            Registry registry1 = LocateRegistry.getRegistry(host, 1099);
            eschopVerwaltungRemote = (EschopVerwaltungRemote) registry1.lookup(serviceName);
            List<Artikel> artikels = eschopVerwaltungRemote.artikelZeigen();

            System.out.println("läuft");
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }

}





