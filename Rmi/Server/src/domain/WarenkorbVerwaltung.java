package domain;

import Common.SaveRepo;
import db.Persistent.SaveFile;
import exception.NotFoundException;
import model.Artikel;
import model.Kunde;
import model.Person;
import model.WarenKorp;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class WarenkorbVerwaltung {
    //ToDo
    private final String filename = "Rmi/Server/warenkorb.txt";
    private SaveRepo saveRepo;

    private List<WarenKorp> allkundWaren;

    public WarenkorbVerwaltung() throws RemoteException {
        saveRepo = new SaveFile();
        saveRepo.creatFile(filename);
        allkundWaren = new ArrayList<>();
    }

    public Person addArikel(Person person, Artikel artikel, int anzahl)  {
        ((Kunde) person).getWarenKorp().addArtikle(artikel, anzahl);
        return person;
    }

    public void returnArtikel(Person person, Artikel artikel) throws NotFoundException {
        ((Kunde) person).getWarenKorp().loschArtikle(artikel);
    }

    public WarenKorp getWarenKorb(Person kunde) {
        return ((Kunde) kunde).getWarenKorp();
    }

    public void saveWarenKorb(List<WarenKorp> warenKorp) {
        try {
            saveRepo.openForWrite(filename);
            saveRepo.saveWarenKorb(warenKorp);
            saveRepo.closeWrite();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void loadWaren(Person person) {
        try {
            saveRepo.openForRead(filename);
            allkundWaren = saveRepo.loadWaren(person);

            saveRepo.closRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<WarenKorp> getSavedWaren() {
        return allkundWaren;
    }
}
