/**
 * Created by Mefew on 13/02/2019.
 */

import java.util.ArrayList;

public class Decoder {

    private static final int LOWERCASE_LETTER = 1, UPPERCASE_LETTER = 2, NUMBER = 3, WORD_DELIMITER = 0;

    public static BrailleCell[] decodeLineToBraille (String line) {
        ArrayList<BrailleCell> brailleLetters = new ArrayList<>();
        boolean longStringOfCapitalLetters = false, aFewWordsInCapitalLetters = false, aFewCapitalLetters = false;
        int numberOfUpperCaseWords = 0;
        char ch;

        for (int pos=0; pos<line.length(); pos++) {
            ch = line.charAt(pos);

            if (isLetterOrNumber(line.charAt(pos)) == UPPERCASE_LETTER) {
                if (!longStringOfCapitalLetters && !aFewWordsInCapitalLetters && !aFewCapitalLetters) {
                    for (int i = pos+1; i < line.length(); i++) {
                        if (i == pos+1 && pos >= 1 && isLetterOrNumber(line.charAt(i-2)) != WORD_DELIMITER)
                            break;
                        if (isLetterOrNumber(line.charAt(i)) == NUMBER || isLetterOrNumber(line.charAt(i)) == LOWERCASE_LETTER) {
                            break;
                        }
                        else if (i == line.length()-1 || isLetterOrNumber(line.charAt(i)) == WORD_DELIMITER && isLetterOrNumber(line.charAt(i-1)) == UPPERCASE_LETTER) {
                            numberOfUpperCaseWords++;
                        }
                    }

                    if (numberOfUpperCaseWords > 3) {
                        longStringOfCapitalLetters = true;
                        brailleLetters.add(new BrailleCell(new int[]{2,5}));
                        brailleLetters.add(new BrailleCell(new int[]{4,6}));
                        brailleLetters.add(new BrailleCell(new int[]{4,6}));
                    }
                    else if (numberOfUpperCaseWords < 1)
                        aFewCapitalLetters = true;
                    else {
                        aFewWordsInCapitalLetters = true;
                    }
                }
                if (longStringOfCapitalLetters) {
                    if (numberOfUpperCaseWords <= 1 && isLetterOrNumber(line.charAt(pos-1)) == WORD_DELIMITER) {
                        brailleLetters.add(new BrailleCell(new int[]{4,6}));
                        brailleLetters.add(new BrailleCell(new int[]{4,6}));
                    }

                    if (pos == line.length()-1 || isLetterOrNumber(line.charAt(pos+1)) == WORD_DELIMITER) {
                        numberOfUpperCaseWords--;
                        if (numberOfUpperCaseWords == 0) {
                            longStringOfCapitalLetters = false;
                        }
                    }
                }
                else if (aFewWordsInCapitalLetters) {
                    if (pos == 0 || isLetterOrNumber(line.charAt(pos-1)) != UPPERCASE_LETTER) {
                        if (pos == (line.length()-1) || isLetterOrNumber(line.charAt(pos+1)) != UPPERCASE_LETTER)
                            brailleLetters.add(new BrailleCell(new int[]{4, 6}));
                        else {
                            brailleLetters.add(new BrailleCell(new int[]{4, 6}));
                            brailleLetters.add(new BrailleCell(new int[]{4, 6}));
                        }
                    }
                    if (pos == line.length()-1 || isLetterOrNumber(line.charAt(pos+1)) == WORD_DELIMITER) {
                        numberOfUpperCaseWords--;
                    }

                    if (numberOfUpperCaseWords <= 0)
                        aFewWordsInCapitalLetters = false;
                }
                else if (aFewCapitalLetters) {
                    brailleLetters.add(new BrailleCell(new int[]{4, 6}));
                    if (pos == (line.length()-1) || isLetterOrNumber(line.charAt(pos+1)) != UPPERCASE_LETTER)
                        aFewCapitalLetters = false;
                }

                ch = Character.toLowerCase(line.charAt(pos));
            }

            if (isLetterOrNumber(line.charAt(pos)) == NUMBER) {
                if (pos >= 2 && (line.charAt(pos-1) == '.' || line.charAt(pos-1) == ',') && isLetterOrNumber(line.charAt(pos-2)) == NUMBER)
                    ;
                else if (pos == 0 || isLetterOrNumber(line.charAt(pos-1)) != NUMBER)
                    brailleLetters.add(new BrailleCell(new int[]{3,4,5,6}));
            }

            switch (ch) {
                case ' ':
                    brailleLetters.add(new BrailleCell(new int[]{}));
                    break;
                case 'a':
                case '1':
                    brailleLetters.add(new BrailleCell(new int[]{1}));
                    break;
                case 'b':
                case '2':
                    brailleLetters.add(new BrailleCell(new int[]{1,2}));
                    break;
                case 'c':
                case '3':
                    brailleLetters.add(new BrailleCell(new int[]{1,4}));
                    break;
                case 'd':
                case '4':
                    brailleLetters.add(new BrailleCell(new int[]{1,4,5}));
                    break;
                case 'e':
                case '5':
                    brailleLetters.add(new BrailleCell(new int[]{1,5}));
                    break;
                case 'f':
                case '6':
                    brailleLetters.add(new BrailleCell(new int[]{1,2,4}));
                    break;
                case 'g':
                case '7':
                    brailleLetters.add(new BrailleCell(new int[]{1,2,4,5}));
                    break;
                case 'h':
                case '8':
                    brailleLetters.add(new BrailleCell(new int[]{1,2,5}));
                    break;
                case 'i':
                case '9':
                    brailleLetters.add(new BrailleCell(new int[]{2,4}));
                    break;
                case 'j':
                case '0':
                    brailleLetters.add(new BrailleCell(new int[]{2,4,5}));
                    break;
                case 'k':
                    brailleLetters.add(new BrailleCell(new int[]{1,3}));
                    break;
                case 'l':
                    brailleLetters.add(new BrailleCell(new int[]{1,2,3}));
                    break;
                case 'm':
                    brailleLetters.add(new BrailleCell(new int[]{1,3,4}));
                    break;
                case 'n':
                    brailleLetters.add(new BrailleCell(new int[]{1,3,4,5}));
                    break;
                case 'o':
                    brailleLetters.add(new BrailleCell(new int[]{1,3,5}));
                    break;
                case 'p':
                    brailleLetters.add(new BrailleCell(new int[]{1,2,3,4}));
                    break;
                case 'q':
                    brailleLetters.add(new BrailleCell(new int[]{1,2,3,4,5}));
                    break;
                case 'r':
                    brailleLetters.add(new BrailleCell(new int[]{1,2,3,5}));
                    break;
                case 's':
                    brailleLetters.add(new BrailleCell(new int[]{2,3,4}));
                    break;
                case 't':
                    brailleLetters.add(new BrailleCell(new int[]{2,3,4,5}));
                    break;
                case 'u':
                    brailleLetters.add(new BrailleCell(new int[]{1,3,6}));
                    break;
                case 'v':
                    brailleLetters.add(new BrailleCell(new int[]{1,2,3,6}));
                    break;
                case 'w':
                    brailleLetters.add(new BrailleCell(new int[]{2,4,5,6}));
                    break;
                case 'x':
                    if (isInNumberContext(line, pos))
                        brailleLetters.add(new BrailleCell(new int[]{2,3,6}));
                    else
                        brailleLetters.add(new BrailleCell(new int[]{1,3,4,6}));
                    break;
                case 'y':
                    brailleLetters.add(new BrailleCell(new int[]{1,3,4,5,6}));
                    break;
                case 'z':
                    brailleLetters.add(new BrailleCell(new int[]{1,3,5,6}));
                    break;

                case 'á':
                    brailleLetters.add(new BrailleCell(new int[]{1,2,3,5,6}));
                    break;
                case 'é':
                    brailleLetters.add(new BrailleCell(new int[]{1,2,3,4,5,6}));
                    break;
                case 'í':
                    brailleLetters.add(new BrailleCell(new int[]{3,4}));
                    break;
                case 'ó':
                    brailleLetters.add(new BrailleCell(new int[]{3,4,6}));
                    break;
                case 'ú':
                    brailleLetters.add(new BrailleCell(new int[]{2,3,4,5,6}));
                    break;
                case 'à':
                    brailleLetters.add(new BrailleCell(new int[]{1,2,4,6}));
                    break;
                case 'â':
                    brailleLetters.add(new BrailleCell(new int[]{1,6}));
                    break;
                case 'ê':
                    brailleLetters.add(new BrailleCell(new int[]{1,2,6}));
                    break;
                case 'ô':
                    brailleLetters.add(new BrailleCell(new int[]{1,4,5,6}));
                    break;
                case 'ã':
                    brailleLetters.add(new BrailleCell(new int[]{3,4,5}));
                    break;
                case 'õ':
                    brailleLetters.add(new BrailleCell(new int[]{2,4,6}));
                    break;
                case 'ç':
                    brailleLetters.add(new BrailleCell(new int[]{1,2,3,4,6}));
                    break;

                case ',':
                    brailleLetters.add(new BrailleCell(new int[]{2}));
                    break;
                case ';':
                    brailleLetters.add(new BrailleCell(new int[]{2,3}));
                    break;
                case '.':
                    brailleLetters.add(new BrailleCell(new int[]{3}));
                    break;
                case '?':
                    brailleLetters.add(new BrailleCell(new int[]{2,6}));
                    break;
                case '!':
                    brailleLetters.add(new BrailleCell(new int[]{2,3,5}));
                    break;
                case '*':
                    brailleLetters.add(new BrailleCell(new int[]{3,5}));
                    break;
                case '(':
                    brailleLetters.add(new BrailleCell(new int[]{1,2,6}));
                    break;
                case ')':
                    brailleLetters.add(new BrailleCell(new int[]{3,4,5}));
                    break;
                case '[':
                    brailleLetters.add(new BrailleCell(new int[]{1,2,3,5,6}));
                    break;
                case ']':
                    brailleLetters.add(new BrailleCell(new int[]{2,3,4,5,6}));
                    break;
                case '"':
                case '“':
                case '”':
                    brailleLetters.add(new BrailleCell(new int[]{2,3,6}));
                    break;
                case '\'':
                case '’':
                    if (apostrophe(line, pos))
                        brailleLetters.add(new BrailleCell(new int[]{3}));
                    else {
                        brailleLetters.add(new BrailleCell(new int[]{6}));
                        brailleLetters.add(new BrailleCell(new int[]{2,3,6}));
                    }
                    break;
                case '‘':
                case '″':
                    brailleLetters.add(new BrailleCell(new int[]{6}));
                    brailleLetters.add(new BrailleCell(new int[]{2,3,6}));
                    break;
                case '«':
                case '»':
                case '‹':
                case '›':
                    brailleLetters.add(new BrailleCell(new int[]{5,6}));
                    brailleLetters.add(new BrailleCell(new int[]{2,3,6}));
                    break;
                case '●':
                    brailleLetters.add(new BrailleCell(new int[]{2,4,6}));
                    brailleLetters.add(new BrailleCell(new int[]{1,3,5}));
                    break;
                case '■':
                    brailleLetters.add(new BrailleCell(new int[]{4,5,6}));
                    brailleLetters.add(new BrailleCell(new int[]{1,3,4,5,6}));
                    break;
                case '&':
                    brailleLetters.add(new BrailleCell(new int[]{1,2,3,4,6}));
                    break;
                case '/':
                    if (isInNumberContext(line, pos))
                        brailleLetters.add(new BrailleCell(new int[]{2,5,6}));
                    else {
                        brailleLetters.add(new BrailleCell(new int[]{6}));
                        brailleLetters.add(new BrailleCell(new int[]{2}));
                    }
                    break;
                case '|':
                    brailleLetters.add(new BrailleCell(new int[]{4,5,6}));
                    break;
                case '×':
                    brailleLetters.add(new BrailleCell(new int[]{4,6}));
                    brailleLetters.add(new BrailleCell(new int[]{2,3,6}));
                    break;
                case '→':
                    brailleLetters.add(new BrailleCell(new int[]{2,5}));
                    brailleLetters.add(new BrailleCell(new int[]{1,3,5}));
                    break;
                case '←':
                    brailleLetters.add(new BrailleCell(new int[]{2,4,6}));
                    brailleLetters.add(new BrailleCell(new int[]{2,5}));
                    break;
                case '↔':
                    brailleLetters.add(new BrailleCell(new int[]{2,4,6}));
                    brailleLetters.add(new BrailleCell(new int[]{2,5}));
                    brailleLetters.add(new BrailleCell(new int[]{1,3,5}));
                    break;
                case '#':
                    brailleLetters.add(new BrailleCell(new int[]{3,4,5,6}));
                    brailleLetters.add(new BrailleCell(new int[]{1,3}));
                    break;
                case '★':
                    brailleLetters.add(new BrailleCell(new int[]{4,6}));
                    brailleLetters.add(new BrailleCell(new int[]{2,4,6}));
                    break;
                case '†':
                    brailleLetters.add(new BrailleCell(new int[]{2,4,5,6}));
                    brailleLetters.add(new BrailleCell(new int[]{2}));
                    break;
                case '♀':
                    brailleLetters.add(new BrailleCell(new int[]{2,4,6}));
                    brailleLetters.add(new BrailleCell(new int[]{1,3,5}));
                    brailleLetters.add(new BrailleCell(new int[]{2,4,5,6}));
                    brailleLetters.add(new BrailleCell(new int[]{2}));
                    break;
                case '♂':
                    brailleLetters.add(new BrailleCell(new int[]{2,4,6}));
                    brailleLetters.add(new BrailleCell(new int[]{1,3,5}));
                    brailleLetters.add(new BrailleCell(new int[]{2,5}));
                    brailleLetters.add(new BrailleCell(new int[]{1,3,5}));
                    break;
                case '⚧':
                    brailleLetters.add(new BrailleCell(new int[]{2,4,6}));
                    brailleLetters.add(new BrailleCell(new int[]{1,3,5}));
                    brailleLetters.add(new BrailleCell(new int[]{2,5,6}));
                    brailleLetters.add(new BrailleCell(new int[]{1,2,5}));
                    break;
                case '©':
                    brailleLetters.add(new BrailleCell(new int[]{1,2,6}));
                    brailleLetters.add(new BrailleCell(new int[]{4,6}));
                    brailleLetters.add(new BrailleCell(new int[]{1,4}));
                    brailleLetters.add(new BrailleCell(new int[]{3,4,5}));
                    break;
                case '®':
                    brailleLetters.add(new BrailleCell(new int[]{1,2,6}));
                    brailleLetters.add(new BrailleCell(new int[]{4,6}));
                    brailleLetters.add(new BrailleCell(new int[]{1,2,3,5}));
                    brailleLetters.add(new BrailleCell(new int[]{3,4,5}));
                    break;
                case '$':
                    brailleLetters.add(new BrailleCell(new int[]{5,6}));
                    break;
                case '€':
                    brailleLetters.add(new BrailleCell(new int[]{4}));
                    brailleLetters.add(new BrailleCell(new int[]{1,5}));
                    break;
                case '£':
                    brailleLetters.add(new BrailleCell(new int[]{4}));
                    brailleLetters.add(new BrailleCell(new int[]{1,2,3}));
                    break;
                case '¥':
                    brailleLetters.add(new BrailleCell(new int[]{4}));
                    brailleLetters.add(new BrailleCell(new int[]{1,3,4,5,6}));
                    break;
                case '%':
                    brailleLetters.add(new BrailleCell(new int[]{4,5,6}));
                    brailleLetters.add(new BrailleCell(new int[]{3,5,6}));
                    break;
                case '‰':
                    brailleLetters.add(new BrailleCell(new int[]{1,2,3}));
                    brailleLetters.add(new BrailleCell(new int[]{3,5,6}));
                    brailleLetters.add(new BrailleCell(new int[]{3,5,6}));
                    break;
                case '§':
                    brailleLetters.add(new BrailleCell(new int[]{2,3,4}));
                    brailleLetters.add(new BrailleCell(new int[]{2,3,4}));
                    break;
                case '+':
                    brailleLetters.add(new BrailleCell(new int[]{2,3,5}));
                    break;
                case '-':
                    brailleLetters.add(new BrailleCell(new int[]{3,6}));
                    break;
                case '—':
                    if (isInNumberContext(line, pos)) {
                        brailleLetters.add(new BrailleCell(new int[]{5}));
                        brailleLetters.add(new BrailleCell(new int[]{2,5,6}));
                    }
                    else{
                        brailleLetters.add(new BrailleCell(new int[]{3, 6}));
                        brailleLetters.add(new BrailleCell(new int[]{3, 6}));
                    }
                    break;
                case ':':
                    if (isInNumberContext(line, pos)) {
                        brailleLetters.add(new BrailleCell(new int[]{2,5,6}));
                    }
                    else
                        brailleLetters.add(new BrailleCell(new int[]{2,5}));
                    break;
                case '÷':
                    brailleLetters.add(new BrailleCell(new int[]{2,5,6}));
                    break;
                case '=':
                    brailleLetters.add(new BrailleCell(new int[]{2,3,5,6}));
                    break;
                case '>':
                    brailleLetters.add(new BrailleCell(new int[]{1,3,5}));
                    break;
                case '<':
                    brailleLetters.add(new BrailleCell(new int[]{2,4,6}));
                    break;
                case '°':
                    brailleLetters.add(new BrailleCell(new int[]{3,5,6}));
                    break;

                default:
                    break;


            }
        }

        BrailleCell[] brailleLine = brailleLetters.toArray(new BrailleCell[brailleLetters.size()]);
        return brailleLine;
    }

    //Extremely simplified. For useful purposes, this method should be fully fledged
    private static boolean isInNumberContext (String line, int pos) {
        //Left Context
        int leftPos = pos-1;
        while (true)
        {
            if (leftPos < 0)
                break;
            if (isLetterOrNumber(line.charAt(leftPos)) == NUMBER)
                return true;
            else if (isLetterOrNumber(line.charAt(leftPos)) == UPPERCASE_LETTER || isLetterOrNumber(line.charAt(leftPos)) == LOWERCASE_LETTER)
                break;
            leftPos--;
        }
        //Right Context
        int rightPos = pos+1;
        while (true)
        {
            if (rightPos >= line.length())
                break;
            if (isLetterOrNumber(line.charAt(rightPos)) == NUMBER)
                return true;
            else if (isLetterOrNumber(line.charAt(rightPos)) == UPPERCASE_LETTER || isLetterOrNumber(line.charAt(rightPos)) == LOWERCASE_LETTER)
                break;
            rightPos++;
        }
        return false;
    }

    private static int isLetterOrNumber (char c) {
        if (Character.toString(c).matches("[a-zsáéíóúàâêôãõç]"))
            return LOWERCASE_LETTER;
        if (Character.toString(c).matches("[A-ZÁÉÍÓÚÀÂÊÔÃÕÇ]"))
            return UPPERCASE_LETTER;
        else if (Character.toString(c).matches("[0-9]"))
            return NUMBER;
        else
            return WORD_DELIMITER;
    }

    private static boolean apostrophe(String line, int pos)
    {
        if (pos == 0 || pos == line.length()-1)
            return false;
        else if ((isLetterOrNumber(line.charAt(pos-1)) == UPPERCASE_LETTER || isLetterOrNumber(line.charAt(pos-1)) == LOWERCASE_LETTER) &&
                (isLetterOrNumber(line.charAt(pos+1)) == UPPERCASE_LETTER || isLetterOrNumber(line.charAt(pos+1)) == LOWERCASE_LETTER))
            return true;
        else
            return false;
    }
}
