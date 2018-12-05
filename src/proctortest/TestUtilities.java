
package proctortest;

public abstract class TestUtilities {
    /**
     * Creates a custom line of the specified character on the standard output.
     *
     * @param length        The number of characters you want the line to be.
     * @param lineCharacter The character you want the line to be made up of.
     * @final.requirement Satisfies the recursion and generic requirements for the final.
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

    // A generic and recursive method to satisfy the generics and recursion requirement in the project
    // Create a custom output line of a generic value-- in this case, a line of chars
    public static <T> void barGraphLine(int length, T lineCharacter, int maxLength) {

        // Inflate the scale
        length *= 10;
        maxLength *= 10;

        // Find the difference between the max and received lengths
        int difference = maxLength - length;

        System.out.print("[");

        // Make a line of symbols
        while (length > 0)
        {
            System.out.print(lineCharacter);
            length--;
        }

        // Make the rest of the line with blank spaces as filler
        while (difference > 0)
        {
            System.out.print(' ');
            difference--;
        }

        System.out.println("]");
    }

    /**
     * Displays a banner for the test.
     */
    static void displayTestBanner()
    {
        // Make a title for the quiz when this method is called before looping
        // through all of the questions
        line(50, '#');
        System.out.println("#                 < Final Quiz >                 #");
        line(50, '#');
    }
}

