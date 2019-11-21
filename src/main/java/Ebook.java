import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.*;
import java.util.ArrayList;

import nl.siegmann.epublib.epub.EpubReader;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Spine;
import nl.siegmann.epublib.domain.SpineReference;
import nl.siegmann.epublib.domain.Resource;

import org.attoparser.ParseException;
import org.attoparser.config.ParseConfiguration;
import org.attoparser.simple.SimpleMarkupParser;

/**
 * Created by Mefew on 13/02/2019.
 */
public class Ebook {

    private int currentParagraph = 0, currentPosition = 0;
    private boolean isFirstLoop = true;
    private BrailleCell[] brailleParagraph;
    String[] paragraphs;

    public Ebook() {
    }

    public void loadEbook (String filePath, int initialPageNumber, int initialLineNumber) {
        int i = filePath.lastIndexOf('.');
        String extension = "";
        if (i > 0) {
            extension = filePath.substring(i+1);
            if (!(extension.equals("txt") || extension.equals("pdf") || extension.equals("epub") || extension.equals("mobi"))) {
                System.out.println("File extension not supported. File format needs to be a pdf, txt, epub or mobi.");
                System.exit(0);
            }
        }
        else {
            System.out.println("File name doesn't have an extension. File extension should be pdf, txt or epub.");
            System.exit(0);
        }

        switch (extension) {
            case "txt":
                try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                    ArrayList<String> lines = new ArrayList<>();
                    String line = br.readLine();

                    while (line != null) {
                        lines.add(line);
                        line = br.readLine();
                    }

                    paragraphs = lines.toArray(new String[lines.size()]);
                }
                catch (FileNotFoundException fnfe) {
                    System.out.println("File not found.");
                    System.exit(0);
                }
                catch (IOException ioe) {
                    System.out.println("An unexpected error occurred while reading file.");
                }
                break;

            case "pdf":
                try (PDDocument document = PDDocument.load(new File(filePath))) {
                    AccessPermission ap = document.getCurrentAccessPermission();
                    String text = "";
                    if (!ap.canExtractContent()) {
                        System.out.println("You do not have permission to extract text from this file");
                        System.exit(0);
                    }
                    PDFTextStripper stripper = new PDFTextStripper();
                    stripper.setSortByPosition(true);

                    text = stripper.getText(document);
                    paragraphs = text.split("\r\n|\r|\n");
                    text = null;
                }
                catch (Exception excp) {
                    System.out.println("Could not open file. Please check if the path and name are correct.");
                    System.exit(0);
                }
                break;
            case "epub":
                EpubReader epubReader = new EpubReader();
                try {
                    Book book = epubReader.readEpub(new FileInputStream(filePath));
                    Spine spine = book.getSpine();
                    String line = "";
                    StringBuilder page = new StringBuilder();
                    ArrayList<String> allLines = new ArrayList<>();

                    for (SpineReference bookSection : spine.getSpineReferences()) {
                        Resource res = bookSection.getResource();
                        InputStream is = res.getInputStream();
                        BufferedReader br = new BufferedReader(new InputStreamReader(is));
                        line = br.readLine();
                        page.setLength(0);

                        while (line != null) {
                            page.append(line + System.lineSeparator());
                            line = br.readLine();
                        }

                        final TagStrippingAttoHandler handler = new TagStrippingAttoHandler();
                        ParseConfiguration config = ParseConfiguration.htmlConfiguration();
                        SimpleMarkupParser parser = new SimpleMarkupParser(config);
                        parser.parse(page.toString().toCharArray(), handler);
                        String[] temp = handler.getTagStrippedText().split("\r\n|\r|\n");
                        for (String str : temp) {
                            if (!str.trim().isEmpty()) {
                                allLines.add(str);
                                //System.out.println(str);
                            }
                        }
                    }
                    paragraphs = allLines.toArray(new String[0]);
                    break;
                }
                catch (FileNotFoundException fnfe) {
                    System.out.println("File not found.");
                    System.exit(0);
                }
                catch (IOException ioe) {

                }
                catch (ParseException pe) {
                    System.out.println("Exception generated while parsing.");
                }
                finally {

                }
            default:
                System.out.println("File extension might not have been processed correctly. This is a bug in Ebook.java file, loadEbook(), line " + new Exception().getStackTrace()[0].getLineNumber());

        }

    }

    public BrailleCell[] nextLine() {
        if (isFirstLoop) {
            brailleParagraph = Decoder.decodeLineToBraille(paragraphs[0]);
            isFirstLoop = false;
        }
        else if (currentPosition + Settings.getLineSize() >= brailleParagraph.length) {
            if (currentParagraph == paragraphs.length - 1) {
                Usb.notifyEndOfBook();
                //continue;
            }
            else {
                currentParagraph++;
                currentPosition = 0;
                brailleParagraph = Decoder.decodeLineToBraille(paragraphs[currentParagraph]);
            }
        }
        else {
            currentPosition += Settings.getLineSize();
        }

        int lastPosition = Math.min(brailleParagraph.length - 1, currentPosition + Settings.getLineSize() - 1);
        BrailleCell[] brailleLineToDisplay = new BrailleCell[lastPosition - currentPosition + 1];
        for (int i=currentPosition ; i <= lastPosition; i++) {
            brailleLineToDisplay[i-currentPosition] = brailleParagraph[i];
        }
        return brailleLineToDisplay;
    }

    public BrailleCell[] previousLine() {
        if (currentPosition - Settings.getLineSize() < 0) {
            if (currentParagraph == 0) {
                Usb.notifyEndOfBook();
            }
            else {
                currentParagraph--;
                brailleParagraph = Decoder.decodeLineToBraille(paragraphs[currentParagraph]);
                int remainder =  brailleParagraph.length % Settings.getLineSize();
                currentPosition = brailleParagraph.length - remainder;
                //Getting currentPosition this way means that a paragraph is read the same way either going forward or going backwards
            }
        }
        else {
            currentPosition -= Settings.getLineSize();
        }

        int lastPosition = Math.min(brailleParagraph.length - 1, currentPosition + Settings.getLineSize() - 1);
        BrailleCell[] brailleLineToDisplay = new BrailleCell[lastPosition - currentPosition + 1];
        for (int i=currentPosition ; i <= lastPosition; i++) {
            brailleLineToDisplay[i-currentPosition] = brailleParagraph[i];
        }
        return brailleLineToDisplay;
    }

}
