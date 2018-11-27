
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
    
    private int totalQuestions = 0; // Keeps track of how many questions were asked during test
    private int totalCorrect = 0;
    private double percentScore = 100;
    
    
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
        
        // Display test banner
        displayTestBanner();
        
        // Pull out each question object from the list
        for (Question question : questionList) 
        {            
            // This string contains the question #, chapter, and section to display
            // to the user
            String questionStats = 
                        "[Question #" + count + " in " + question.getChapter() +
                        " - " + question.getSection() + " | Score: (" + totalCorrect + "/" + 
                        totalQuestions + ")=" + percentScore + "%]";
            
            
            line(questionStats.length(), '='); // --line--
            System.out.println(questionStats); // Display origin of question to user
            line(questionStats.length(), '='); // --line--
            
            // Display the question
            System.out.println(question.getHeadQuestion());
            line(50, '='); // --line--
            
            // Display each possible answer to user
            for (StringBuilder stringBuilder : question.getTailQuestion())
            {
                System.out.println(stringBuilder);
            }
            line(50, '='); // --line--
            System.out.print("--> ");

            // Gets and converts user input to upper-case
            userAnswer = scanner.next().toUpperCase(); 
            
            // Test user answer against the correct answer, record the results based on
            // chapter and section
            analyzeAnswer(userAnswer, question.getCorrectAnswer(), question.getChapter(), question.getSection());
            
            // Update the score
            percentScore = 100*( (double)totalCorrect / (double)totalQuestions );
            percentScore = Math.floor(percentScore * 100) / 100;
            
            // Display results after user answers a question in terminal 
            System.out.println("Answer is " + question.getCorrectAnswer());
            line(50, '-'); // --line--
            
            // Increment the counter
            count++;
        }
    }
    
    
    // ===========================
    // ===== Private Methods =====
    // ===========================
    
    // Test user answer against the correct answer, record the results based on
    // chapter and section
    private void analyzeAnswer(String answer, StringBuilder correctAnswer, StringBuilder chapter, StringBuilder section)
    {
        // Compare answer and correct answer
        if (correctAnswer.charAt(1) == answer.charAt(0))
        {
            System.out.println("< Correct! >");
            totalCorrect++; // Update the score instance variable
        }
        else
            System.out.println("< Wrong! >");
        
        // Increment +1 every time this method is called to keep track of how many
        // questions were asked
        totalQuestions++; 
    }
    
    // Recursive method to satisfy the recursion requirement in the project
    // Create a custom output line of chars
    private void line(int length, char lineCharacter)
    {
        if (length <= 0) // Base case
        {
            // Drop to a new line after printing out a line
            System.out.print("\n");
        }
        else // Recursive case
        {
            System.out.print(lineCharacter);
            line(length-1, lineCharacter); // Call method again
        }
    }
    
    // Display the test banner
    private void displayTestBanner()
    {
        // Make a title for the quiz when this method is called before looping
        // through all of the questions
        line(50, '#');
        System.out.println("#                 < Final Quiz >                 #");
        line(50, '#');
    }
}
