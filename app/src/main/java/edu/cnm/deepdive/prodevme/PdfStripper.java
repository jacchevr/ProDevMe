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

  /**
   * This method is used to extract plain
   * text from a pdf document. The method
   * takes an InputStream as a parameter,
   * and returns a String[], or plain text.
   * @param doc is the pdf being used
   * @return lines are the lines of plain text.
   * @throws IOException
   */
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