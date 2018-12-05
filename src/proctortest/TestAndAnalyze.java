package proctortest;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * date    11-23-18
 *
 * @author (Paul Egbe, Kyle Blaha, Mackenzie Branch, Brandon Jumbeck, Insert group names)
 **/
public class TestAndAnalyze extends TestUtilities {
    private ArrayList<Question> questionBank;
    private int totalQuestionsAsked = 0;
    private int totalCorrect = 0;
    private double percentScore = 100;

    public TestAndAnalyze(ArrayList<Question> questionBank) {
        this.questionBank = questionBank;

        runTest();

        analyzeFinalResult();
    }

    /**
     * Runs a test using the questions stored in question bank.
     */
    public void runTest() {
        // TODO Anyone: Do we need this counter here? We also have the totalQuestionsAsked counter which does basically the same thing.
        int questionsAsked = 1;
        String userAnswer;
        Scanner scanner = new Scanner(System.in);

        displayTestBanner();

        for (Question question : questionBank) {
            // This string contains the question #, chapter, and section to display
            // to the user
            String questionStats =
                    "[Question #" + questionsAsked + " in " + question.getChapter() +
                            " - " + question.getSection() + " | Score: (" + totalCorrect + "/" +
                            totalQuestionsAsked + ")=" + percentScore + "%]";


            line(questionStats.length(), '='); // --line--
            System.out.println(questionStats); // Display origin of question to user
            line(questionStats.length(), '='); // --line--

            // Display the question
            System.out.println(question.getQuestion());
            line(50, '='); // --line--

            // Display each possible answer 
            for (String stringBuilder : question.getWrongAnswers()) {
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
            percentScore = 100 * ((double) totalCorrect / (double) totalQuestionsAsked);
            percentScore = Math.floor(percentScore * 100) / 100;

            // Display results after user answers a question in terminal 
            System.out.println("Answer is " + question.getCorrectAnswer());
            line(50, '-'); // --line--

            // Increment the counter
            questionsAsked++;
        }
    }

    /**
     * Tests the user's answer against the correct answer and then record the results based
     * on the question's chapter and section that it's content originated from.
     *
     * @param answer        The answer given for the question.
     * @param correctAnswer The correct answer for the question.
     * @param chapter       The chapter the question's content originated from.
     * @param section       The section of the chapter the question's content originated from.
     */
    private void analyzeAnswer(String answer, String correctAnswer, String chapter, String section) {
        // Compare answer and correct answer
        if (correctAnswer.charAt(1) == answer.charAt(0)) {
            System.out.println("< Correct! >");
            totalCorrect++; // Update the score instance variable
        } else
            System.out.println("< Wrong! >");

        // Increment +1 every time this method is called to keep track of how many
        // questions were asked
        totalQuestionsAsked++;
    }

    /**
     * Finds the resulting score value for each chapter and section
     *
     * Create an itemized score by chapter
     * S7 (3/3)
     * S14 (2/3)
     * S23 (3/3)
     * ---------
     * Final: 92.5%
     */
    private void analyzeFinalResult() {
        double score = (double) totalCorrect / (double) totalQuestionsAsked;
        score *= 100; // Make it a percent %
        score = Math.floor(score * 100) / 100; // Round to 2 decimal places


        line(50, '+'); // --line--
        System.out.println("Final score: " + score + "%");
        line(50, '+'); // --line--
    }
}

