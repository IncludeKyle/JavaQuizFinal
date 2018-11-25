
package proctortest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

/**
 * @date    11-21-18
 * @authors (Paul Egbe, Kyle Blaha, Insert group names)
 **/

interface QuestionParserMethods{

     boolean checkDuplicateMethod(ArrayList<Integer> arr, int num );
}


//This was originally class: public class StructureTest
public class QuestionParser {
    
    // ===============================
    // ====== Instance Variables =====
    // ===============================
    private ArrayList<Question> questionsBank = new ArrayList<>();
    private ArrayList<Question> pickedQuest = new ArrayList<>();
    private Random random = new Random();

    
    // ========================
    // ====== Constructor =====
    // ========================
    public QuestionParser() {
        
        // Reads questionBank.txt to create objects of type Question,
        // adds the Question objects to the questionsBank array list instance
        loadQuestionBank();
        
        // Sort questionBank list in ascending order
        Collections.sort(questionsBank); 
        
        // Randomly pick 3 questions of each chapter, and build pickedQuest array list
        randomQuizQuest(); 
    }

    
    // ==========================
    // ===== Public Methods =====
    // ==========================
    
    // Runs the test using the Question objects stored in the pickedQuest array list
    public void runTest() { 
        
        // Make a test object to run the test with the selected list of questions
        TestAndAnalyze testObject = new TestAndAnalyze(pickedQuest);
    }
    
    
    // ============================
    // ====== Private Methods =====
    // ============================
    
    // Load questionBank.txt to questionsBank arrayList
    private void loadQuestionBank(){

        // Try to read from the question bank text file
        try {
            File questionBank = new File("questionBank.txt");
            Scanner inputFile = new Scanner(questionBank);

            ArrayList<String> components = new ArrayList<>();
            ArrayList<String> tailQuestion;
            String correctAnswer;

            // Loop through the entire file
            while (inputFile.hasNext()) {
                
                // Pull out each line as a string
                String lineFromFile = inputFile.nextLine();

                // Uses the length to test for '$'
                // If line from file is not $ -> add line to components array list, prepare for building each question
                if (lineFromFile.length() > 1) {
                    components.add(lineFromFile); // Add line to the components array list
                }

                // Once the scanner has reached the 1 char length '$' symbol it knows it has reached
                // the end of a question and answer block.
                // The loop stops and builds a completed Question object with with data stored in 
                // the components array list.
                if (lineFromFile.length() == 1) {
                    tailQuestion = buildTailQuest(components);      // Build question's tail
                    correctAnswer = buildCorrectAnswer(components); // Build correct answer of the question
                    Question question = new Question(components.get(0), tailQuestion, correctAnswer, components.get(1), components.get(2)); // create a question object after has all of its components
                    questionsBank.add(question); // Add that question object to the questionBank array
                    components.clear(); // Clear the component array to prepare for the next building of question
                }
            }

        } catch (IOException e) {
            
            // Display error stack trace
            e.printStackTrace();
        }
    }

    // Uses the received components array list and a Regex to test for all answers in a 
    // question block
    private ArrayList<String> buildTailQuest(ArrayList<String> component) {
        ArrayList<String> tailQuestion = new ArrayList<>();
        for (String tail : component) {
            if (tail.matches("[(].[)].*")) { // Regex to define a tail of a question
                tailQuestion.add(tail);
            }
        }

        return tailQuestion;
    }

    // Uses the received components array list and a Regex to test for the one correct 
    // answer in a question block
    private String buildCorrectAnswer(ArrayList<String> component) {
        String correctAnswer = "";
        for (String string : component) {
            if (string.matches("[(].[)].*[<]")) { // Regex to define correct answer of a question
                correctAnswer = string;
            }
        }

        return correctAnswer;
    }

    // Adds randomly selected questions to the pickedQuest array
    private void buildPickedQuest(ArrayList<Integer> ranums, ArrayList<Question> groupQuest) {
        for (int c = 0; c < ranums.size(); c++) {
            pickedQuest.add(groupQuest.get(ranums.get(c)));
        }
    }

    // Randomly pick 3 questions of each chapter, and build pickedQuest array list
    private void randomQuizQuest() {

        String currentChapter ;
        String nextChapter;
        ArrayList<Question> groupQuests = new ArrayList<>();
        ArrayList<Integer> ranums ;

        for (int i = 0; i < questionsBank.size(); i++) {
            currentChapter = questionsBank.get(i).getChapter().toString();

            // If i reaches to the end, set nextChapter to the chapter of the first question in questionsBank array
            if (i == questionsBank.size() - 1) {
                nextChapter = questionsBank.get(0).getChapter().toString();
            } else {
                nextChapter = questionsBank.get(i + 1).getChapter().toString();
            }

            // Check if currentChapter equals nextChapter, add that question to the groupQuest array
            if (currentChapter.equals(nextChapter)) {
                groupQuests.add(questionsBank.get(i));
            } else {
                groupQuests.add(questionsBank.get(i));
                ranums = random(groupQuests.size() - 1); // random three numbers in order to pick 3 random questions of each chapter
                buildPickedQuest(ranums, groupQuests); // build pickedQuest array, which will be used for the test
                groupQuests.clear(); // clear groupQuests array to prepare for the next group of question
            }

        }
    }

    // Generate a unique list of random numbers, no duplicates
    private ArrayList<Integer> random(int max) {
        ArrayList<Integer> ranums = new ArrayList<>();
        int min = 0;

        while (ranums.size() < 3) {
            int randomNum = random.nextInt((max - min) + 1) + min;
            if (checkDuplicate.checkDuplicateMethod(ranums, randomNum)) { // Check if duplicating number, then will ignore it
                ranums.add(randomNum);
            }
        }
        return ranums;
    }

    // Checks an integer against a list for duplicate numbers, return false if a duplicate
    QuestionParserMethods checkDuplicate =  (ArrayList<Integer> arr, int num)  -> { for (int value : arr) {
            if (value == num) {
                return false;
            }
        }return true; };


}
