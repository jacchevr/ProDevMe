package edu.cnm.deepdive.prodevme;


import android.net.Uri;
import com.tom_roush.pdfbox.pdmodel.PDDocument;
import com.tom_roush.pdfbox.text.PDFTextStripper;
import com.tom_roush.pdfbox.text.PDFTextStripperByArea;
import com.tom_roush.pdfbox.util.PDFBoxResourceLoader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class PdfStripper {

  public static String[] strip(InputStream doc) throws IOException {

    try (PDDocument document = PDDocument.load(doc)) {

      document.getClass();

      if (!document.isEncrypted()) {

        PDFTextStripperByArea stripper = new PDFTextStripperByArea();
        stripper.setSortByPosition(true);

        PDFTextStripper tStripper = new PDFTextStripper();

        String pdfFileInText = tStripper.getText(document);
        //System.out.println("Text:" + st);

        // split by whitespace
        String lines[] = pdfFileInText.split("\\r?\\n");
        return lines;

      }

    }

    return new String[0];
  }
}