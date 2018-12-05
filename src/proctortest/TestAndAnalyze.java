
package proctortest;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * date    11-23-18
 * @author (Paul Egbe, Kyle Blaha, Insert group names)
 **/
public class TestAndAnalyze extends TestUtilities{

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

        // Analyze the test result instance variables
        analyzeFinalResult();
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

            // Display each possible answer
            for (StringBuilder stringBuilder : question.getTailQuestion())
            {
                System.out.println(stringBuilder);
            }
            line(50, '='); // --line--
            System.out.print("--> ");

            // Gets and converts user input to upper-case
            userAnswer = scanner.next().toUpperCase();


            while (!isUserAnswerValid(userAnswer)){
                System.out.println("The answer is not valid! Re-enter:");
                userAnswer = scanner.next().toUpperCase();
            }

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


    // Finds the resulting score value for each chapter and section
    private void analyzeFinalResult()
    {
        double score = (double)totalCorrect / (double)totalQuestions;
        score *= 100; // Make it a percent %

        line(50, '+'); // --line--
        System.out.println("Final score: " + score + "%");
        line(50, '+'); // --line--
    }


    private boolean isUserAnswerValid(String userAnswer){
        return userAnswer.matches("[A]||[B]||[C]||[D][T][F]");
    }

}
