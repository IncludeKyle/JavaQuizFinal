
package proctortest;

public abstract class TestUtilities {

    // Recursive method to satisfy the recursion requirement in the project
    // Create a custom output line of chars
    public static void line(int length, char lineCharacter) {
        if (length <= 0) // Base case
        {
            // Drop to a new line after printing out a line
            System.out.print("\n");
        } else // Recursive case
        {
            System.out.print(lineCharacter);
            line(length - 1, lineCharacter); // Call method again
        }
    }

    // Display the test banner
    public static void displayTestBanner()
    {
        // Make a title for the quiz when this method is called before looping
        // through all of the questions
        line(50, '#');
        System.out.println("#                 < Final Quiz >                 #");
        line(50, '#');
    }
}
