package proctortest;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ExecutionException;

/**
 * date 11-21-18
 * @author (Paul Egbe, Kyle Blaha, Mackenzie Branch, Brandon Jumbeck Insert group names)
 **/

// Functional interface added for a lambda
interface QuestionParserMethods {
    boolean checkDuplicateMethod(ArrayList<Integer> arr, int num);
}


// This was originally class: public class StructureTest
public class QuestionParser {

    // ===============================
    // ====== Instance Variables =====
    // ===============================
    private FileManager fileManager = new FileManager();
    private ArrayList<Question> questionsBank = new ArrayList<>();
    private ArrayList<Question> pickedQuest = new ArrayList<>();
    private Random random = new Random();

    // Uses a lambda to pull every value from the list and check for duplicates
    // Checks an integer against a list for duplicate numbers, return false if a duplicate
    private QuestionParserMethods checkDuplicate = (ArrayList<Integer> arr, int num) -> {
        for (int value : arr) {
            if (value == num) {
                return false;
            }
        }
        return true;
    };


    // ========================
    // ====== Constructor =====
    // ========================
    public QuestionParser() {
        // Reads questionBank.txt to create objects of type Question,
        // adds the Question objects to the questionsBank array list instance
        try {
            questionsBank = fileManager.loadQuestionBankConcurrent(new File("questionBank.txt"));
            ArrayList<Question> test = fileManager.loadQuestionBank(new File("questionBank.txt"));
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        // Sort questionBank list in ascending order
        Collections.sort(questionsBank);

        // Randomly pick 3 questions of each chapter, and build pickedQuest array list
        randomQuizQuest();
    }


    // ==========================
    // ===== Public Methods =====
    // ==========================

    // Runs the test using the Question objects stored in the pickedQuest array list
    void runTest() {
        // Make a test object to run the test with the selected list of questions
        TestAndAnalyze testObject = new TestAndAnalyze(pickedQuest);
    }


    // ============================
    // ====== Private Methods =====
    // ============================

    // Adds randomly selected questions to the pickedQuest array
    private void buildPickedQuest(ArrayList<Integer> ranums, ArrayList<Question> groupQuest) {
        for (Integer ranum : ranums) {
            pickedQuest.add(groupQuest.get(ranum));
        }
    }

    // Randomly pick 3 questions of each chapter, and build pickedQuest array list
    private void randomQuizQuest() {

        String currentChapter;
        String nextChapter;
        ArrayList<Question> groupQuests = new ArrayList<>();
        ArrayList<Integer> ranums;

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

            // Use the lambda to test the randomNum against the list of random numbers
            if (checkDuplicate.checkDuplicateMethod(ranums, randomNum)) { // Check if duplicating number, then will ignore it
                ranums.add(randomNum);
            }
        }
        return ranums;
    }

}
