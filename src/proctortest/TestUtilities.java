
package proctortest;

abstract class TestUtilities {
    /**
     * Creates a custom line of the specified character on the standard output.
     *
     * @param length        The number of characters you want the line to be.
     * @param lineCharacter The character you want the line to be made up of.
     * @final.requirement Satisfies the recursion requirement for the final.
     */
    static void line(int length, char lineCharacter) {
        if (length <= 0) // Base case
        {
            // Drop to a new line after printing out a line
            System.out.print("\n");
        } else // Recursive case
        {
            System.out.print(lineCharacter);
            line(length - 1, lineCharacter);
        }
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

