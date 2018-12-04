
package proctortest;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @date    11-23-18
 * @authors (Paul Egbe, Kyle Blaha, Mackenzie Branch, Insert group names)
 **/
public class TestAndAnalyze extends TestUtilities{

    // ===============================
    // ====== Instance Variables =====
    // ===============================
    private ArrayList<Question> questionList = new ArrayList<>();
    private ArrayList<String> listOfChapters = new ArrayList<>(); // Stores each unique test chapter 
    private int chapterIndex = 0;
    private int[] chapterScore; // Parallel int array stores each chapter's score
    private String currentChapter;

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
        boolean setFirstChapter = false;
        Scanner scanner = new Scanner(System.in);

        // Display test banner
        displayTestBanner();
        
        // Pull out each question object to see how many chapters there will be
        for (Question question : questionList)
        {
            // Convert the StringBuilder chapter to a string
            String convertedStringBuilder = question.getChapter().toString();
            
            // If chapter is not in list, add it to the list
            if ( !listOfChapters.contains(convertedStringBuilder) )
            {
                // Add that chapter to the chapter name list
                listOfChapters.add(convertedStringBuilder);
            }
            
            // Also set the starting chapter
            if (!setFirstChapter)
            {
                currentChapter = convertedStringBuilder;
                setFirstChapter = true;
            }
        }
        // Set size of the chapter score array
        chapterScore = new int[listOfChapters.size()];

        // Pull out each question object from the list to give the test
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

            // Test user answer against the correct answer, record the results based on
            // chapter and section
            analyzeAnswer(userAnswer, question.getCorrectAnswer(), question.getChapter());

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
    private void analyzeAnswer(String answer, StringBuilder correctAnswer, StringBuilder chapter)
    {

        // Compare answer and correct answer
        if (correctAnswer.charAt(1) == answer.charAt(0))
        {
            System.out.println("< Correct!>");
            totalCorrect++; // Update the score instance variable
            
            // If this detects that chapter is != currentChapter 
            // it knows the chapter has changed and it will update accordingly
            if ( chapter.toString().equals(currentChapter) )
            {
                chapterScore[chapterIndex]++; // Add +1 point to the respective chapter score
            }
            else
            {
                currentChapter = chapter.toString(); // Update current chapter 
                chapterIndex++; // Update chapter score index +1 to move to next chapter
                chapterScore[chapterIndex]++; // Add +1 point to the respective chapter score
            }
        
        }
        else
        {
            System.out.println("< Wrong!>");
            
            if ( !chapter.toString().equals(currentChapter) )
            {
                currentChapter = chapter.toString(); // Update current chapter 
                chapterIndex++; // Update chapter score index +1 to move to next chapter
            }
        }

        // Increment +1 every time this method is called to keep track of how many
        // questions were asked
        totalQuestions++;
    }

    // Finds the resulting score value for each chapter and section
    private void analyzeFinalResult()
    {
        double score = (double)totalCorrect / (double)totalQuestions;
        score *= 100; // Make it a percent %
        score = Math.floor(score * 100) / 100; // Round to 2 decimal places
        
        line(50, '#'); // --line--
        System.out.println("Final score: " + score + "%");
        line(50, '#'); // --line--
        
        // Make the list of chapters into a string array
        for (int x = 0; x < listOfChapters.size(); x++)
        {
            // Print chapter name
            System.out.print(listOfChapters.get(x));
            
            // Bar graph of chapter score
            barGraphLine(chapterScore[x], '=', 3);
        }
        
        line(50, '#'); // --line--
    }
}

