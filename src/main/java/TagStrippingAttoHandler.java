import org.attoparser.simple.AbstractSimpleMarkupHandler;
import org.attoparser.ParseException;

public class TagStrippingAttoHandler extends AbstractSimpleMarkupHandler {

    private final StringBuilder strBuilder;
    //private int counter = 0;

    public TagStrippingAttoHandler() {
        super();
        this.strBuilder = new StringBuilder();
    }

    public String getTagStrippedText() {
        return this.strBuilder.toString();
    }

    @Override
    public void handleText(
            final char[] buffer, final int offset, final int len,
            final int line, final int col)
            throws ParseException {
        this.strBuilder.append(buffer, offset, len);
        this.strBuilder.append('\n');
    }


}
