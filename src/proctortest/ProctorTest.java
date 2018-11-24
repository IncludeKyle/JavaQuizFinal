
package proctortest;

import java.io.IOException;

/**
 * @date    11-21-18
 * @authors (Paul Egbe, Insert group names)
 **/

// ========================
// ===== Driver class =====
// ========================
// This was originally public class Driver
public class ProctorTest {

    // Main method
    public static void main(String[] args) throws IOException {
        
        // Create a QuestionParser object which will parse the designated questionBank.txt
        // file and create a list of Question objects
        QuestionParser parser = new QuestionParser();
        
        // Starts the test using the Question objects stored in this parser object
        parser.runTest();  
    } 
}
