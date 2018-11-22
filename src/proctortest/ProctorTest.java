
package proctortest;

import java.io.IOException;

/**
 * @date    11-18-18
 * @authors (Insert group names)
 **/

// --- Driver class ---
// This was: public class Driver
public class ProctorTest {

    // Main method
    public static void main(String[] args) throws IOException {
        
        // Create a parser object
        QuestionParser parser = new QuestionParser();
        parser.runTest();  
    } 
}
