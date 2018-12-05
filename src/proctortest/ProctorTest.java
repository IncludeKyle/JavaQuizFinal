package proctortest;

import java.io.File;

/**
 * Date    11-21-18
 *
 * @author (Paul Egbe, Kyle Blaha, Insert group names)
 **/

public class ProctorTest {
    public static void main(String[] args) {
        // Create a QuestionParser object which will parse the designated questionBank.txt
        // file and create a list of Question objects
        QuestionParser parser = new QuestionParser(new File("questionBank.txt"));

        // Starts the test using the Question objects stored in this parser object
        parser.runTest();
    }
}
