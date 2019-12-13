/**
 * Created by Mefew on 19/02/2019.
 */
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Properties;
import java.io.PrintWriter;

public class Settings {
    private static Properties properties = new Properties();
    private static final String filePath = "./app.properties";
    private static final int DEFAULT_LINE_SIZE = 1, DEFAULT_BAUD_RATE = 9600, DEFAULT_PARITY = 0, DEFAULT_STOP_BITS = 0;
    private static final boolean DEFAULT_DEBUG_MODE = true, DEFAULT_RS485_MODE = false;
    private static int lineSize = DEFAULT_LINE_SIZE, baudRate = DEFAULT_BAUD_RATE, parity = DEFAULT_PARITY, stopBits = DEFAULT_STOP_BITS;
    private static boolean debugMode = DEFAULT_DEBUG_MODE, RS485_Mode = DEFAULT_RS485_MODE;
    private static String portDescriptor = "";

    public static int getLineSize () {
        return lineSize;
    }
    public static int getBaudRate() { return baudRate; }
    public static int getParity() { return parity; }
    public static int getStopBits() { return stopBits; }
    public static boolean isRS485_Mode() { return RS485_Mode; }
    public static String getPortDescriptor() { return portDescriptor; }
    public static boolean isDebugMode () {
        return debugMode;
    }

    public static void fetchSettings () {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            properties.load(br);

            if (properties.getProperty("NumeroDeCelulasBraille") != null && !properties.getProperty("NumeroDeCelulasBraille").isBlank())
                lineSize = Integer.parseInt(properties.getProperty("NumeroDeCelulasBraille").trim());
            if (lineSize > 254) {
                System.out.println("Line sizes of more than 254 characters are not supported by this implementation currently. Instead using the default value of " + DEFAULT_LINE_SIZE + ".");
                lineSize = DEFAULT_LINE_SIZE;
            }
            else if (lineSize <= 0) {
                System.out.println("Line sizes must be between 1 and 254. Please edit the configuration file. Execution will continue using the default value of " + DEFAULT_LINE_SIZE + ".");
                lineSize = DEFAULT_LINE_SIZE;
            }

            if (properties.getProperty("IdentificadorDaPortaSerial")!=null && !properties.getProperty("IdentificadorDaPortaSerial").isBlank())
                portDescriptor = properties.getProperty("IdentificadorDaPortaSerial");

            if (properties.getProperty("BaudRate")!=null && !properties.getProperty("BaudRate").isBlank())
                baudRate = Integer.parseInt(properties.getProperty("BaudRate").trim());

            if (properties.getProperty("Paridade")!=null && !properties.getProperty("Paridade").isBlank())
                parity = Integer.parseInt(properties.getProperty("Paridade").trim());
            if (parity < 0 || parity > 4) {
                System.out.println("Invalid value found for parity, using default of \"No Parity\". Values should be: \"No Parity\" = 0, \"Odd Parity\" = 1, \"Even Parity\" = 2, \"Mark Parity = 3\", \"Space Parity = 4\"");
                parity = DEFAULT_PARITY;
            }

            if (properties.getProperty("StopBits")!=null && !properties.getProperty("StopBits").isBlank()) {
                stopBits = Integer.parseInt(properties.getProperty("StopBits").trim());
            }
            if (stopBits != 0 && stopBits != 1 && stopBits != 2) {
                System.out.println("Invalid StopBits value found, using default option, which means 1 stop bit. Possible options for the configuration file are: \"1 stop bit\" = 0, \"1.5 stop bits\" = 1, \"2 stop bits\" = 2. Note that 1.5 stop bits might not be available on non-Windows systems.");
                stopBits = DEFAULT_STOP_BITS;
            }

            if (properties.getProperty("ModoRS-485")!=null && !properties.getProperty("ModoRS-485").isBlank()) {
                String value = properties.getProperty("ModoRS-485").trim();
                if (value.equals("Y") || value.equals("Yes")
                        || value.equals("S") || value.equals("Sim"))
                    RS485_Mode = true;
                else if (!value.equals("N") && !value.equals("No")
                            && !value.equals("Não") && !value.equals("Nao")) {
                        System.out.println("Invalid value found for RS-485 Mode, using default value of NO. Values should be \"Yes (or Y)\" or \"No (or N)\".");
                }
            }
        }
        catch (FileNotFoundException fnfe) {
            System.out.println("Configuration file \"app.properties\" not found. A default configuration file is being created...");
            createDefaultConfigFile();
        }
        catch (IOException ioe) {

        }
        catch (NumberFormatException nfe) {
            System.out.println("One or more properties were expected to be numbers, but something else was found. Please fix the configuration file. Aborting execution.");
            System.exit(0);
        }
    }

    private static void createDefaultConfigFile () {
        try (PrintWriter writer = new PrintWriter(filePath, "UTF-8")) {
            //TODO escrever igual o config da GRUB. Cheio de explicação e comentario
            writer.println("NumeroDeCelulasBraille=" + DEFAULT_LINE_SIZE);
            writer.println("IdentificadorDaPortaSerial=");
            writer.println("BaudRate=9600");
            writer.println("Paridade=" + DEFAULT_PARITY);
            writer.println("StopBits=" + DEFAULT_STOP_BITS);
            writer.println("ModoRS-485=" + (DEFAULT_RS485_MODE ? "Yes" : "No"));
        }
        catch (IOException ioe) {
            System.out.println("Default configuration file could not be created. Please check your permissions for this directory. Or download the default configuration file from the software repository.");
            System.out.println("Starting program using default configurations...");
        }
    }
}
