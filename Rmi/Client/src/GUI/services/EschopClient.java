package GUI.services;

import Common.EschopVerwaltungRemote;
import model.Artikel;


import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class EschopClient {

    public static EschopVerwaltungRemote eshopVerwaltungRemote;

    public static void main(String[] args) {
        final String serviceName = "kunde";
        String host = "localhost";
        try {
            Registry registry1 = LocateRegistry.getRegistry(host, 1099);
             eshopVerwaltungRemote = (EschopVerwaltungRemote) registry1.lookup(serviceName);
           List<Artikel> artikels= eshopVerwaltungRemote.artikelZeigen();

            System.out.println("läuft");
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }

}
