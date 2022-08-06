package Common;

import exception.BestandNichtAusreichendException;
import exception.LoginFailedException;
import exception.NotFoundException;
import model.*;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;

public interface EschopVerwaltungRemote extends Remote {

    Artikel findArtikel(String name) throws NotFoundException, RemoteException;

    void saveWaren(Person person, boolean buystatus) throws RemoteException;

    WarenKorp kundeWaren(Person person) throws NotFoundException, RemoteException;

    Person warenlegen(String name, int anzahl, Person person) throws BestandNichtAusreichendException, NotFoundException, RemoteException;

    void returnArikel(String artiekelName, String anzahl, Person person) throws NotFoundException, RemoteException;

    Einlogen kundenEinloggen(String na, String pas) throws LoginFailedException, RemoteException;

    List<Artikel> artikelZeigen() throws RemoteException;

    Rechnung getRec(Kunde kunde, HashMap<Artikel, Integer> artikels) throws IOException, RemoteException;

}
