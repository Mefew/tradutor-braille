/**
 * Created by Mefew on 13/03/2019.
 */
//import com.sun.xml.internal.ws.api.config.management.policy.ManagementAssertion;
//import gnu.io.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.fazecast.jSerialComm.*;

public class Usb {

    public static SerialPort serialPort;
    public static OutputStream outStream;
    public static InputStream inStream;
    private static boolean isConnected = false;
    private static final int HELLO = 0,
            SENDING_SYMBOLS = 1,
            SENDING_SETTINGS = 2,
            RECEIVED = 3,
            ADVANCE = 4,
            GO_BACK = 5,
            END_OF_BOOK = 6,
            DEBUG_MESSAGE = 7;

    public static void connect(Ebook ebook) {
        try {
            serialPort = SerialPort.getCommPorts()[0];
            serialPort.setBaudRate(9600);
            if (!Settings.getPortDescriptor().equals(""))
                SerialPort.getCommPort(Settings.getPortDescriptor());     //portDescriptor should be similar to "COM[*]" on Windows machines, and "/dev/tty[*]" on Linux machines
            serialPort.openPort();

            inStream = serialPort.getInputStream();
            outStream = serialPort.getOutputStream();
            if (inStream == null || outStream == null) {
                System.out.println("Could not open port for communications. Please try again");
                System.exit(0);
            }

            while (!isConnected) {
                if (waitForOrder(8000) == HELLO) {
                    System.out.println("recebemos HELLO");
                    isConnected = true;
                    outStream.write(HELLO);
                    sendSettings();
                } else {
                    outStream.write(HELLO);
                }
            }
            System.out.println("entrou no loop principal");
            while (true) {
                processOrder(waitForOrder(0), ebook);
            }
        }
        catch (SerialPortInvalidPortException spipe) {
            System.out.println("Provided Port Descriptor didn't match any connected serial ports. Exiting...");
            spipe.printStackTrace();
            System.exit(0);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (inStream != null)
                    inStream.close();
                if (outStream != null)
                    outStream.close();
            }
            catch (IOException ioe) {
                ioe.printStackTrace();
            }
            if (serialPort != null && serialPort.isOpen()) {
                serialPort.closePort();
            }
        }
    }

    public static void sendSymbols (BrailleCell[] brailleLine) {
        try {
            for (int i = 0; i < brailleLine.length; i++) {
                Debug.printBrailleSymbolToConsole(brailleLine[i]);
            }
            outStream.write(brailleLine.length);
            for (int i = 0; i < brailleLine.length; i++) {
                if (waitForOrder(8000) == RECEIVED) {
                    outStream.write(brailleLine[i].getByteRepresentationOfBrailleSymbol());
                }
                else {
                    System.out.println("While sending a Braille line, did not get confirmation for symbol number " + i + ". Instead, received something else.");
                }
            }
            if (waitForOrder(8000) != RECEIVED) {
                System.out.println("");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendSettings() {
        try {
            outStream.write(SENDING_SETTINGS);

            for (int i = 0; i < 2; i++) {
                if (waitForOrder(8000) == RECEIVED) {
                    switch (i) {
                        case 0:
                            outStream.write(Settings.getLineSize());
                            break;
                        /*case 1:
                            outStream.write(Settings.getBaudRate());
                            break;
                        case 2:
                            outStream.write(Settings.getParity());
                            break;
                        case 3:
                            outStream.write(Settings.getStopBits());
                            break;
                        case 4:
                            outStream.write(Settings.isRS485_Mode() ? 1 : 0);
                            break;*/
                    }
                }
                else {
                    System.out.println("Error while sending settings to device. Expected a RECEIVED order but got something else. Exiting program.");
                    System.exit(0);
                }
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void notifyEndOfBook () {
        try {
            outStream.write(END_OF_BOOK);
            if (waitForOrder(8000) != RECEIVED) {
                System.out.println("Expected confirmation after sending END_OF_BOOK, but received something else instead. Exiting.");
                System.exit(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static int waitForOrder (long timeout) {
        try {
            long start = System.currentTimeMillis();
            while (inStream.available() == 0) {
                if (System.currentTimeMillis() - start >= timeout && timeout != 0) {
                    System.out.println("Reached timeout while waiting for an order. Exiting program.");
                    System.exit(0);
                }
            }
            int order = inStream.read();

            if (order == DEBUG_MESSAGE) {
                char charLeft = (char) inStream.read(), charRight = (char) inStream.read();
                System.out.print("Microcontroller sent an error message: \"" + charLeft);
                while (true) {
                    System.out.print(charRight);
                    charLeft = charRight;
                    charRight = (char) inStream.read();
                    if (charLeft == '\r' && charRight == '\n')
                        break;
                }
            }

            return order;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    private static void processOrder (int order, Ebook ebook) {
        try {
            switch (order) {
                case ADVANCE:
                    outStream.write(SENDING_SYMBOLS);
                    if (waitForOrder(8000) == RECEIVED)
                        sendSymbols(ebook.nextLine());
                    else {
                        System.out.println("Reached timeout while waiting for a confirmation. Exiting program.");
                        System.exit(0);
                    }
                    break;
                case GO_BACK:
                    outStream.write(SENDING_SYMBOLS);
                    if (waitForOrder(8000) == RECEIVED)
                        sendSymbols(ebook.previousLine());
                    else {
                        System.out.println("Reached timeout while waiting for a confirmation. Exiting program.");
                        System.exit(0);
                    }
                    break;
                default:
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
