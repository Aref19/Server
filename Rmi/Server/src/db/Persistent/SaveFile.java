package db.Persistent;

import Common.SaveRepo;
import model.*;
import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class SaveFile extends UnicastRemoteObject implements SaveRepo {
    BufferedReader bufferedReader = null;
    BufferedWriter bufferedWriter = null;
    File file = null;

    public SaveFile() throws RemoteException {
        super();
    }

    @Override
    public void creatFile(String fileName) {
        file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void closeWrite() throws IOException {
        bufferedWriter.close();
    }

    @Override
    public void openForRead(String file) {
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void openForWrite(String file) throws IOException {
        if (file.equals("Ereignis.txt")) {
            bufferedWriter = new BufferedWriter(new FileWriter(file, true));
        } else
            bufferedWriter = new BufferedWriter(new FileWriter(file, false));
    }

    @Override
    public void saveArtikel(List<Artikel> artikel) throws IOException {
        try {
            for (Artikel artikel1 : artikel) {
                bufferedWriter.append((artikel1.toSaveInFile() + System.lineSeparator()));
            }
        } catch (IOException e) {
            throw e;
        }
    }

    @Override
    public void saveListArtikels(List<Artikel> artikelList) throws IOException {
        try {
            for (Artikel artikel : artikelList) {
                bufferedWriter.write(artikel.toSaveInFile());
            }
        } catch (IOException e) {
            throw e;
        }
    }

    @Override
    public List<Artikel> loadListArtikels() {
        List<Artikel> artikels = new ArrayList<>();
        try {
            String data = bufferedReader.readLine();
            System.out.println(data);
            while (data != null) {
                String[] content = data.split(";");
                Artikel newArtikel;
                int content0 = Integer.parseInt(content[0]);
                String content1 = content[1];
                int content2 = Integer.parseInt(content[2]);
                double content3 = Double.parseDouble(content[3]);

                if (content.length >= 5) {
                    newArtikel = new Massengutartikel(content0, content1, content2, content3,
                            Integer.parseInt(content[4]));
                } else {
                    newArtikel = new Artikel(content0, content1, content2, content3);
                }
                artikels.add(newArtikel);

                data = bufferedReader.readLine();
                System.out.println(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return artikels;
    }

    @Override
    public Artikel loadArtikel(String name) throws RemoteException {
        List<Artikel> artikels = loadListArtikels();

        for (Artikel artikel1 : artikels) {
            if (artikel1.getArtikelBezeichnung().equals(name)) {
                return artikel1;
            }
        }
        return null;
    }

    @Override
    public List<Kunde> loadKunde() {
        List<Kunde> kundeList = new ArrayList<>();
        try {
            String data = bufferedReader.readLine();
            while (data != null && !data.isEmpty()) {
                String[] content = data.split(";");
                kundeList.add(new Kunde(content[0], content[1], new Adresse(
                        Integer.parseInt(content[2])
                        , Integer.parseInt(content[3])
                        , content[4]
                        , content[5]
                ), content[6], content[7]));
                data = bufferedReader.readLine();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return kundeList;
    }

    @Override
    public void saveWarenKorb(List<WarenKorp> warenKorpList) {
        try {
            for (WarenKorp warenkorp : warenKorpList) {
                WarenKorp warenKorp1 = warenkorp;
                bufferedWriter.write((warenKorp1 + System.lineSeparator()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<WarenKorp> loadWaren(Person person) throws IOException {
        List<WarenKorp> WarenKorp = new ArrayList<>();
        try {
            String data = bufferedReader.readLine();
            while (data != null && !data.isEmpty()) {
                String[] content = data.split(";");
                WarenKorp.add(new WarenKorp(content[0], content[1], Double.parseDouble(content[2]), Integer.parseInt(content[3])));
                data = bufferedReader.readLine();

            }
        } catch (IOException e) {
            throw e;
        }
        return WarenKorp;
    }

    @Override
    public void closRead() {
        try {
            bufferedReader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void saveKunde(List<Kunde> kundeList) throws IOException {
        try {
            for (Kunde kunde : kundeList) {
                bufferedWriter.append((kunde + System.lineSeparator()));
            }
        } catch (IOException e) {
            throw e;
        }

    }

}
