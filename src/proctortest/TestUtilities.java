
package proctortest;

public abstract class TestUtilities {

    // A generic and recursive method to satisfy the generics and recursion requirement in the project
    // Create a custom output line of a generic value-- in this case, a line of chars
    public static <T> void line(int length, T lineCharacter) {
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

