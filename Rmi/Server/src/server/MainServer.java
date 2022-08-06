package server;

import Common.EschopVerwaltungRemote;
import domain.EshopVerwaltung;
import exception.BestandNichtAusreichendException;
import exception.NotFoundException;
import model.Adresse;
import model.Kunde;
import model.Person;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MainServer {

    public static void main(String[] args) {
        Registry registry;
        EschopVerwaltungRemote eshopverwaltungRemote;

        try {
            eshopverwaltungRemote = new EshopVerwaltung();
            // Dann versuchen, selber eine Registry zu starten:
            // (Achtung, funktioniert nur, wenn die Registry auch alle
            // Klassen aus Common finden kann (CLASSPATH)!)
            registry = LocateRegistry.createRegistry(1099);
            System.out.println("Registry erzeugt.");

            registry.rebind("kunde", eshopverwaltungRemote);
            try {
                Kunde person=new Kunde("d","w",new Adresse(1,2,"","d"),"a1","a@gmail.com");
                eshopverwaltungRemote.warenlegen("Fanta",1,person);
                eshopverwaltungRemote.kundeWaren(person);
                System.out.println(  eshopverwaltungRemote.kundeWaren(person).getAnzahl());
            } catch (BestandNichtAusreichendException e) {
                e.printStackTrace();
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
            System.out.println("Adressbuch-Server läuft...");

        } catch (RemoteException e) {
            System.out.println(e.getMessage());
            // tritt z.B. auf, wenn Stub-Klasse nicht vorhanden ist
            e.printStackTrace();
        }
    }
}
