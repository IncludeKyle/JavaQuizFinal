package proctortest;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @date 11-21-18
 * @author (Paul Egbe, Kyle Blaha, Thanh Nguyen, Mackenzie Branch, Insert group names)
 **/
public class ProctorTest {
    public static void main(String[] args) {
        boolean retakeSameTest = false;
        boolean takeANewTest = true; // True just to enter the test at least once.

        // Load the questions from the text file into an array concurrently.
        FileManager fileManager = new FileManager();
        ArrayList<Question> allQuestions;
        try {
            allQuestions = fileManager.loadQuestionBankConcurrent(new File("questionBank.txt"));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        // Create a QuestionBank object which holds all the questions possible.
        QuestionBank questionBank = new QuestionBank(allQuestions);

        // Create a brand new test with 3 randomly generated questions per chapter stored in our QuestionBank
        ArrayList<Question> questionList = questionBank.generateQuestionList(3);
        Test test = new Test(questionList, fileManager);

        do {
            if (takeANewTest) {

                // Create a brand new test with 3 randomly generated questions per chapter stored in our QuestionBank
                // There is some inefficiency here with creating another new test member.
                questionList = questionBank.generateQuestionList(3);
                test = new Test(questionList, fileManager);

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

                // TODO: If we have time, refactor this menu into something that isn't copy and pasted in both conditions
                int menuChoice;
                do {
                    System.out.println("\n\n\n\n\n\n");
                    TestUtilities.displayTestBanner();
                    System.out.println("\n1 - Retake the same test.\n2 - Take a new test.\n3 - Exit Program.");

                    System.out.print("\nChoice: ");
                    Scanner consoleInput = new Scanner(System.in);
                    menuChoice = consoleInput.nextInt();
                } while (menuChoice > 3 || menuChoice < 1);

                switch (menuChoice) {
                    case 1:
                        retakeSameTest = true;
                        takeANewTest = false;
                        break;

                    case 2:
                        takeANewTest = true;
                        retakeSameTest = false;
                        break;

                    case 3:
                    default:
                        retakeSameTest = false;
                        takeANewTest = false;
                }
            } else {
                // Retake the same test that they just took, so no need to generate new random questions.
                test.runTest();
                test.analyzeFinalResult();

                // Get user information so that we can save the test results
                String userName;
                do {
                    System.out.print("Please enter your first and last name with a space in between: ");
                    Scanner scanner = new Scanner(System.in);
                    userName = scanner.nextLine();
                } while (!userName.matches("[a-zA-Z]*[\\s]{1}[a-zA-Z].*") || userName.isEmpty());

                // TODO: If we have time, refactor this menu into something that isn't copy and pasted in both conditions
                int menuChoice;
                do {
                    System.out.println("\n\n\n");
                    TestUtilities.displayTestBanner();
                    System.out.println("\n1 - Retake the same test.\n2 - Take a new test.\n3 - Exit Program.");

                    System.out.print("\nChoice: ");
                    Scanner consoleInput = new Scanner(System.in);
                    menuChoice = consoleInput.nextInt();
                } while (menuChoice > 3 || menuChoice < 1);

                switch (menuChoice) {
                    case 1:
                        retakeSameTest = true;
                        takeANewTest = false;
                        break;

                    case 2:
                        takeANewTest = true;
                        retakeSameTest = false;
                        break;

                    case 3:
                    default:
                        retakeSameTest = false;
                        takeANewTest = false;
                }
            }
        } while (retakeSameTest || takeANewTest);
    }
}
