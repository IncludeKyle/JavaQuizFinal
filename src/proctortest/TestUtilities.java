package proctortest;

/**
 * A utilities class which helps with various little things required in the test taking process
 * <p>
 * Date    12-03-18
 *
 * @author (Kyle Blaha)
 * @final.requirement Generics, Recursion
 **/

public abstract class TestUtilities {
    /**
     * Creates a custom line of the specified character on the standard output.
     *
     * @final.requirement Generics, Recursion
     *
     * @param length        The number of characters you want the line to be.
     * @param lineCharacter The character you want the line to be made up of.
     */
    public static <T> void line(int length, T lineCharacter) {
        if (length <= 0) {
            // Drop to a new line after printing out a line
            System.out.print("\n");
        } else {
            System.out.print(lineCharacter);
            line(length - 1, lineCharacter);
        }
    }

    /**
     * Create a custom output line of a generic values for a bar graph.
     *
     * @final.requirement Generics
     *
     * @param length        The length of the line.
     * @param lineCharacter The character you want the line to be made up of.
     * @param maxLength     The maximum length that the bar graph line can be.
     */
    public static <T> void barGraphLine(int length, T lineCharacter, int maxLength) {

        // Inflate the scale
        length *= 10;
        maxLength *= 10;

        // Find the difference between the max and received lengths
        int difference = maxLength - length;

        System.out.print("[");

        // Make a line of symbols
        while (length > 0) {
            System.out.print(lineCharacter);
            length--;
        }

        // Make the rest of the line with blank spaces as filler
        while (difference > 0) {
            System.out.print(' ');
            difference--;
        }

        System.out.println("]");
    }

    /**
     * Displays a banner for the test.
     */
    static void displayTestBanner() {
        // Make a title for the quiz when this method is called before looping
        // through all of the questions
        line(50, '#');
        System.out.println("#                 < Final Quiz >                 #");
        line(50, '#');
    }
}

