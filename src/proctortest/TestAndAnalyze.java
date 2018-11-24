
package proctortest;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @date    11-23-18
 * @authors (Paul Egbe, Kyle Blaha, Insert group names)
 **/
public class TestAndAnalyze {
    
    // ===============================
    // ====== Instance Variables =====
    // ===============================
    private ArrayList<Question> questionList = new ArrayList<>();
    
    // ========================
    // ====== Constructor =====
    // ========================
    // Receives and sets the Question object list
    public TestAndAnalyze(ArrayList<Question> questionList)
    {   
        // Set the instance list with the constructor parameter list
        this.questionList = questionList;
        
        // Start the test
        runTest();
    }
    
    
    // ==========================
    // ===== Public Methods =====
    // ==========================
    
    // Runs the test using the Question objects stored in the questionList
    public void runTest() { 
        
        int count = 1;
        String userAnswer;
        Scanner scanner = new Scanner(System.in);
        
        // Make a title for the quiz when this method is called before looping
        // through all of the questions
        System.out.println("--------------------------------------------------");
        System.out.println("-                  < Final Quiz >                -");
        System.out.println("--------------------------------------------------");
        
        // Pull out each question object from the list
        for (Question question : questionList) 
        {
            // Display question to user
            System.out.printf("%d%s%s%n", count, ". ", question.getHeadQuestion());
            
            // Display each possible answer to user
            for (StringBuilder stringBuilder : question.getTailQuestion())
            {
                System.out.println(stringBuilder);
            }

            // Converts user input to upper-case
            userAnswer = scanner.next().toUpperCase(); 
            
            // Display results after user answers a question in terminal 
            System.out.println("* Your Answer: " + userAnswer);
            System.out.println("* Correct Answer: " + question.getCorrectAnswer());
            System.out.println("* Chapter: " + question.getChapter());
            System.out.println("* Section: " + question.getSection() + "\n\n");

            count++;
        }
    }
    
}
