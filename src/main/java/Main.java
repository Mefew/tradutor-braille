/**
 * Created by Mefew on 13/02/2019.
 */

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

//import com.itextpdf.layout.hyphenation.Hyphenator;
//import com.sun.xml.internal.ws.api.config.management.policy.ManagementAssertion;
import nl.siegmann.epublib.domain.Author;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.domain.TOCReference;



public class Main {

    public static void main (String[] args) {

        Ebook ebook = new Ebook();
        Settings.fetchSettings();

        if (args.length == 0) {
            printHelp();
        }
        else if (args.length == 1) {
            if (args[0].equals("help") || args[0].equals("--help") || args[0].equals("-h")) {
                printHelp();
            }
            else {
                ebook.loadEbook(args[0], 0, 0);
                Usb.connect(ebook);
            }
        }
    }

    private static void printHelp() {
        System.out.println("Argument expected is the path to the file to be read. File name must include its correct file extension (txt, pdf or epub).");
    }
}
