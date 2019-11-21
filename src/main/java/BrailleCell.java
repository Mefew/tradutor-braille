/**
 * Created by Mefew on 22/02/2019.
 */
public class BrailleCell {
    private boolean dot1, dot2, dot3, dot4, dot5, dot6, dot7, dot8;
    /*1   4
      2   5
      3   6
      7   8*/

    public BrailleCell () {
        dot1 = false;
        dot2 = false;
        dot3 = false;
        dot4 = false;
        dot5 = false;
        dot6 = false;
        dot7 = false;
        dot8 = false;
    }

    public BrailleCell (int[] activatedCells) {
        dot1 = false;
        dot2 = false;
        dot3 = false;
        dot4 = false;
        dot5 = false;
        dot6 = false;
        dot7 = false;
        dot8 = false;
        setCell(activatedCells);
    }

    public void setCell (int[] activatedCells) {
        for (int cell : activatedCells) {
            switch (cell) {
                case 1:
                    this.dot1 = true;
                    break;
                case 2:
                    this.dot2 = true;
                    break;
                case 3:
                    this.dot3 = true;
                    break;
                case 4:
                    this.dot4 = true;
                    break;
                case 5:
                    this.dot5 = true;
                    break;
                case 6:
                    this.dot6 = true;
                    break;
                case 7:
                    this.dot7 = true;
                    break;
                case 8:
                    this.dot8 = true;
                    break;
                default:
                    System.out.println("Cell was set with an invalid dot number. Dot numbers should be between 1 and 8, inclusive. Please check code for errors.");
                    System.exit(0);
            }
        }
    }

    public boolean getDot1() {
        return dot1;
    }

    public boolean getDot2() {
        return dot2;
    }

    public boolean getDot3() {
        return dot3;
    }

    public boolean getDot4() {
        return dot4;
    }

    public boolean getDot5() {
        return dot5;
    }

    public boolean getDot6() {
        return dot6;
    }

    public boolean getDot7() {
        return dot7;
    }

    public boolean getDot8() {
        return dot8;
    }

    public int getByteRepresentationOfBrailleSymbol () {
        int answer = 0;

        if (getDot1())
            answer+=(1);
        if (getDot2())
            answer+=(2);
        if (getDot3())
            answer+=(4);
        if (getDot4())
            answer+=(8);
        if (getDot5())
            answer+=(16);
        if (getDot6())
            answer+=(32);
        if (getDot7())
            answer+=(64);
        if (getDot8())
            answer+=(128);

        return answer;
    }
}
