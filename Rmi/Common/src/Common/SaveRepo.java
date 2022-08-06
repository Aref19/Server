package Common;

import exception.NotFoundException;
import model.Artikel;
import model.Kunde;
import model.Person;
import model.WarenKorp;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface SaveRepo extends Remote {
    void closRead()throws RemoteException;

    void saveKunde(List<Kunde> kundeList) throws IOException,RemoteException;

    List<Kunde> loadKunde()throws RemoteException;

    public void saveWarenKorb(List<WarenKorp> warenKorpList)throws RemoteException;

    public List<WarenKorp> loadWaren(Person person) throws IOException,RemoteException;

    void closeWrite() throws IOException,RemoteException;

    void openForRead(String file)throws RemoteException;

    void openForWrite(String file) throws IOException,RemoteException;

    void creatFile(String fileName)throws RemoteException;

    void saveArtikel(List<Artikel> artikel) throws IOException,RemoteException;

    void saveListArtikels(List<Artikel> artikelList) throws IOException,RemoteException;

    List<Artikel> loadListArtikels()throws RemoteException;

    Artikel loadArtikel(String name) throws NotFoundException, RemoteException;

}
