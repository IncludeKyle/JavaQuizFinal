package proctortest;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * date 11-21-18
 *
 * @author (Paul Egbe, Kyle Blaha, Mackenzie Branch, Brandon Jumbeck Insert group names)
 **/

interface QuestionParserMethods {
    boolean checkDuplicateMethod(ArrayList<Integer> arr, int num);
}

public class QuestionParser {
    private ArrayList<Question> questionsBank = new ArrayList<>();
    private ArrayList<Question> pickedQuest = new ArrayList<>();
    private Random random = new Random();

    /**
     * Pulls every value from the list and then checks for duplicates.
     */
    private QuestionParserMethods checkDuplicate = (ArrayList<Integer> arr, int num) -> {
        for (int value : arr) {
            if (value == num) {
                return false;
            }
        }
        return true;
    };

    /**
     * Default constructor for the question parser.
     */
    public QuestionParser(File questionBank) {
        try {
            // TODO Brandon: Create a speed test to compare the computational time between the threaded and non-threaded versions.
            FileManager fileManager = new FileManager();
            questionsBank = fileManager.loadQuestionBankConcurrent(questionBank);
            ArrayList<Question> nonThreadedQuestionBank = fileManager.loadQuestionBank(questionBank);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Sort questionBank list in ascending order
        Collections.sort(questionsBank);

        // Randomly pick 3 questions of each chapter, and build pickedQuest array list
        randomQuizQuest();
    }

    /**
     * Runs the test using the Question objects stored in the pickedQuest array list.
     */
    // TODO Brandon: This is a bit confusing for a starting point of the test for me. When I think of a question parser
    // I don't think that it should be the class that actually is used to start a test. Instead it should only handle
    // parsing the questions for a test.
    void runTest() {
        // Make a test object to run the test with the selected list of questions
        TestAndAnalyze testObject = new TestAndAnalyze(pickedQuest);
    }

    /**
     * Build's up a quiz by randomly picking 3 questions from each chapter.
     */
    private void randomQuizQuest() {
        String currentChapter;
        String nextChapter;
        ArrayList<Question> groupQuests = new ArrayList<>();
        ArrayList<Integer> randomIndexes;

        for (int i = 0; i < questionsBank.size(); i++) {
            currentChapter = questionsBank.get(i).getChapter();

            // If i reaches to the end, set nextChapter to the chapter of the first question in questionsBank array
            if (i == questionsBank.size() - 1) {
                nextChapter = questionsBank.get(0).getChapter();
            } else {
                nextChapter = questionsBank.get(i + 1).getChapter();
            }

            // Check if currentChapter equals nextChapter, add that question to the groupQuest array
            if (currentChapter.equals(nextChapter)) {
                groupQuests.add(questionsBank.get(i));
            } else {
                groupQuests.add(questionsBank.get(i));
                randomIndexes = random(groupQuests.size() - 1); // random three numbers in order to pick 3 random questions of each chapter

                for (Integer index : randomIndexes) {
                    pickedQuest.add(groupQuests.get(index));
                }

                groupQuests.clear(); // clear groupQuests array to prepare for the next group of question
            }

        }
    }

    /**
     * Generates an ArrayList of random integers with no duplicates.
     *
     * @param max The maximum number that can be generated.
     * @return An ArrayList of random integers with no duplicates.
     */
    private ArrayList<Integer> random(int max) {
        ArrayList<Integer> randomNumbers = new ArrayList<>();
        int min = 0;

        while (randomNumbers.size() < 3) {
            int randomNum = random.nextInt((max - min) + 1) + min;

            // Use the lambda to test the randomNum against the list of random numbers
            if (checkDuplicate.checkDuplicateMethod(randomNumbers, randomNum)) { // Check if duplicating number, then will ignore it
                randomNumbers.add(randomNum);
            }
        }
        return randomNumbers;
    }
}
