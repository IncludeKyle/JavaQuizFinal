
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
    
    // Recursive method to satisfy the recursion requirement in the project
    // Create a custom output line of chars
    public static void barGraphLine(int length, char lineCharacter, int maxLength) {
        
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

