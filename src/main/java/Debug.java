/**
 * Created by Mefew on 27/03/2019.
 */
public class Debug {

    public Debug () {

    }

    public static void printBrailleSymbolToConsole(BrailleCell brailleSymbol) {
        boolean dot1=brailleSymbol.getDot1(), dot2=brailleSymbol.getDot2(), dot3=brailleSymbol.getDot3(),
                dot4=brailleSymbol.getDot4(), dot5=brailleSymbol.getDot5(), dot6=brailleSymbol.getDot6(),
                dot7=brailleSymbol.getDot7(), dot8=brailleSymbol.getDot8();
        StringBuilder dotValues = new StringBuilder("900000000");       //number 9 serves only for zeroes on the left to not be dismissed


        if (dot1) {
            dotValues.replace(1,2, "1");
        }
        if (dot2) {
            dotValues.replace(2,3, "1");
        }
        if (dot3) {
            dotValues.replace(3,4, "1");
        }
        if (dot4) {
            dotValues.replace(4,5, "1");
        }
        if (dot5) {
            dotValues.replace(5,6, "1");
        }
        if (dot6) {
            dotValues.replace(6,7, "1");
        }
        if (dot7) {
            dotValues.replace(7,8, "1");
        }
        if (dot8) {
            dotValues.replace(8,9, "1");
        }

        Integer i = Integer.parseInt(dotValues.toString());

        switch (i) {
            case 900000000:
                System.out.printf("\u2800");
                break;
            case 910000000:
                System.out.printf("\u2801");
                break;
            case 901000000:
                System.out.printf("\u2802");
                break;
            case 911000000:
                System.out.printf("\u2803");
                break;
            case 900100000:
                System.out.printf("\u2804");
                break;
            case 910100000:
                System.out.printf("\u2805");
                break;
            case 901100000:
                System.out.printf("\u2806");
                break;
            case 911100000:
                System.out.printf("\u2807");
                break;
            case 900010000:
                System.out.printf("\u2808");
                break;
            case 910010000:
                System.out.printf("\u2809");
                break;
            case 901010000:
                System.out.printf("\u280A");
                break;
            case 911010000:
                System.out.printf("\u280B");
                break;
            case 900110000:
                System.out.printf("\u280C");
                break;
            case 910110000:
                System.out.printf("\u280D");
                break;
            case 901110000:
                System.out.printf("\u280E");
                break;
            case 911110000:
                System.out.printf("\u280F");
                break;
            case 900001000:
                System.out.printf("\u2810");
                break;
            case 910001000:
                System.out.printf("\u2811");
                break;
            case 901001000:
                System.out.printf("\u2812");
                break;
            case 911001000:
                System.out.printf("\u2813");
                break;
            case 900101000:
                System.out.printf("\u2814");
                break;
            case 910101000:
                System.out.printf("\u2815");
                break;
            case 901101000:
                System.out.printf("\u2816");
                break;
            case 911101000:
                System.out.printf("\u2817");
                break;
            case 900011000:
                System.out.printf("\u2818");
                break;
            case 910011000:
                System.out.printf("\u2819");
                break;
            case 901011000:
                System.out.printf("\u281A");
                break;
            case 911011000:
                System.out.printf("\u281B");
                break;
            case 900111000:
                System.out.printf("\u281C");
                break;
            case 910111000:
                System.out.printf("\u281D");
                break;
            case 901111000:
                System.out.printf("\u281E");
                break;
            case 911111000:
                System.out.printf("\u281F");
                break;
            case 900000100:
                System.out.printf("\u2820");
                break;
            case 910000100:
                System.out.printf("\u2821");
                break;
            case 901000100:
                System.out.printf("\u2822");
                break;
            case 911000100:
                System.out.printf("\u2823");
                break;
            case 900100100:
                System.out.printf("\u2824");
                break;
            case 910100100:
                System.out.printf("\u2825");
                break;
            case 901100100:
                System.out.printf("\u2826");
                break;
            case 911100100:
                System.out.printf("\u2827");
                break;
            case 900010100:
                System.out.printf("\u2828");
                break;
            case 910010100:
                System.out.printf("\u2829");
                break;
            case 901010100:
                System.out.printf("\u282A");
                break;
            case 911010100:
                System.out.printf("\u282B");
                break;
            case 900110100:
                System.out.printf("\u282C");
                break;
            case 910110100:
                System.out.printf("\u282D");
                break;
            case 901110100:
                System.out.printf("\u282E");
                break;
            case 911110100:
                System.out.printf("\u282F");
                break;
            case 900001100:
                System.out.printf("\u2830");
                break;
            case 910001100:
                System.out.printf("\u2831");
                break;
            case 901001100:
                System.out.printf("\u2832");
                break;
            case 911001100:
                System.out.printf("\u2833");
                break;
            case 900101100:
                System.out.printf("\u2834");
                break;
            case 910101100:
                System.out.printf("\u2835");
                break;
            case 901101100:
                System.out.printf("\u2836");
                break;
            case 911101100:
                System.out.printf("\u2837");
                break;
            case 900011100:
                System.out.printf("\u2838");
                break;
            case 910011100:
                System.out.printf("\u2839");
                break;
            case 901011100:
                System.out.printf("\u283A");
                break;
            case 911011100:
                System.out.printf("\u283B");
                break;
            case 900111100:
                System.out.printf("\u283C");
                break;
            case 910111100:
                System.out.printf("\u283D");
                break;
            case 901111100:
                System.out.printf("\u283E");
                break;
            case 911111100:
                System.out.printf("\u283F");
                break;
        }
    }
}
