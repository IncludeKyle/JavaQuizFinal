package proctortest;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main entry point for the application, handles showing a simple menu to the user and also
 * starting tests.
 *
 * @final.requirement Exceptions, Regex
 *
 * @date 11-21-18
 * @author (Paul Egbe, Kyle Blaha, Thanh Nguyen, Mackenzie Branch)
 **/
public class ProctorTest {
    public static void main(String[] args) {
        // Load the questions from the text file into an array concurrently.
        FileManager fileManager = new FileManager();
        ArrayList<Question> allQuestions;

        // Exception handling requirement fulfilled here. Though we use exceptions quite a bit throughout
        // the program most are propagated back to around this main method.
        try {
            allQuestions = fileManager.loadQuestionBankConcurrent(new File("questionBank.txt"));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        // Create a QuestionBank object which holds all the questions possible.
        QuestionBank questionBank = new QuestionBank(allQuestions);

        boolean takeANewTest = true;
        do {
            // Create a brand new test with 3 randomly generated questions per chapter stored in our QuestionBank
            ArrayList<Question> questionList = questionBank.generateQuestionList(3);
            Test test = new Test(questionList, fileManager);

            // Get user information so that we can save the test results
            String userName;
            do {
                System.out.print("Please enter your first and last name with a space in between: ");
                Scanner scanner = new Scanner(System.in);
                userName = scanner.nextLine();
            } while (!userName.matches("[a-zA-Z]*[\\s]{1}[a-zA-Z].*") || userName.isEmpty());

            fileManager.setUsernameForFile(userName);
            System.out.println("\n\n\n");

            test.runTest();
            test.analyzeFinalResult();

            int menuChoice;
            do {
                System.out.println("\n\n\n\n\n\n");
                TestUtilities.displayTestBanner();
                System.out.println("\n1 - Take a new test.\n2 - Exit Program.");

                System.out.print("\nChoice: ");
                Scanner consoleInput = new Scanner(System.in);
                menuChoice = consoleInput.nextInt();
            } while (menuChoice > 3 || menuChoice < 1);

            switch (menuChoice) {
                case 1:
                    takeANewTest = true;
                    break;

                case 2:
                default:
                    takeANewTest = false;
            }
        } while (takeANewTest);
    }
}
