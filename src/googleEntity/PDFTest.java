package googleEntity;

/**
 * Created by jyotirmay.d on 04/06/18.
 */
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.apache.pdfbox.util.*;

public class PDFTest {

    public static void main(String[] args){
        PDDocument pd;
        BufferedWriter wr;
        try {
            File input = new File("/Users/jyotirmay.d/Downloads/easier_english_student_dictionary_upper-int.pdf");  // The PDF file from where you would like to extract
            File output = new File("/Users/jyotirmay.d/Downloads/SampleText.txt"); // The text file where you are going to store the extracted data
            pd = PDDocument.load(input);
            System.out.println(pd.getNumberOfPages());
            System.out.println(pd.isEncrypted());
            pd.save("CopyOfInvoice.pdf"); // Creates a copy called "CopyOfInvoice.pdf"
            PDFTextStripper stripper = new PDFTextStripper();
            wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output)));
            stripper.writeText(pd, wr);
            if (pd != null) {
                pd.close();
            }
            // I use close() to flush the stream.
            wr.close();

            readPDF(input);
            //System.out.println(readPDF(input));
        } catch (Exception e){
            e.printStackTrace();
        }
    }



    private static String readPDF(File pdf) throws InvalidPasswordException, IOException {

            PDDocument document = PDDocument.load(pdf);
            document.getClass();

            if (!document.isEncrypted()) {

                PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                stripper.setSortByPosition(true);

                PDFTextStripper tStripper = new PDFTextStripper();

                String pdfFileInText = tStripper.getText(document);
                // System.out.println("Text:" + st);

                // split by whitespace
                String lines[] = pdfFileInText.split("\\/");

                List<String> pdfLines = new ArrayList<String>();
                StringBuilder sb = new StringBuilder();
                for (String line : lines) {
                    System.out.println(line);
                    pdfLines.add(line);
                    sb.append(line + "\n");
                }
                System.out.print(lines.length);
                return sb.toString();
            }

        return null;
    }
}