package until;

import model.Rechnung;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PdfGenerator {
    Rechnung rechnung;

    public PdfGenerator(Rechnung rechnung) {
        this.rechnung = rechnung;
    }

    public void creatPdf() {
        File file = new File("Rechnung");
        File pdfFile;
        if (!file.exists()) {
            file.mkdir();
        }
        try {
            pdfFile = new File("Rechnung/rechnung.txt");
            pdfFile.createNewFile();
            FileWriter writer = new FileWriter(pdfFile);
            String rec = rechnung.toString();
            System.out.println("dasdas");
            writer.write(rechnung.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
