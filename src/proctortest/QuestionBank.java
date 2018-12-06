package proctortest;

import java.io.File;
import java.util.*;

/**
 * Holds a large question bank of possible test questions which it can use to
 * generate questions lists that test's can use for their tests.
 *
 * date 11-21-18
 *
 * @author (Paul Egbe, Kyle Blaha, Mackenzie Branch, Brandon Jumbeck Insert group names)
 **/

interface QuestionParserMethods {
    boolean checkDuplicateMethod(ArrayList<Integer> arr, int num);
}

public class QuestionBank {
    private ArrayList<Question> questionsBank = new ArrayList<>();
    private Random random = new Random();

    /**
     * Creates a questionBank
     *
     * @param questionBank
     */
    public QuestionBank(File questionBank) {
        try {
            // TODO Brandon: Create a speed test to compare the computational time between the threaded and non-threaded versions.
            FileManager fileManager = new FileManager();
            questionsBank = fileManager.loadQuestionBankConcurrent(questionBank);
            // ArrayList<Question> nonThreadedQuestionBank = fileManager.loadQuestionBank(questionBank);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Sort questionBank list in ascending order
        Collections.sort(questionsBank);
    }

    /**
     * Build's up a quiz that can be used by the Test class by randomly picking X questions from each chapter.
     *
     * @param numQuestionsPerChapter The amount of questions that will be chosen from each chapter.
     * @return An ArrayList<Question> that represents the question list.
     */
    public ArrayList<Question> generateQuestionList(int numQuestionsPerChapter) throws IllegalArgumentException {
        ArrayList<Question> pickedQuestions = new ArrayList<>();
        Random randomNumberGenerator = new Random();

        // Split the question bank into buckets for each chapter using a map that
        // uses the chapter string as a key and a List<Question> as a value.
        HashMap<String, ArrayList<Question>> chapterMap = new HashMap<>();
        for (int i = 0; i < questionsBank.size(); i++) {
            if (chapterMap.containsKey(questionsBank.get(i).getChapter())) {
                chapterMap.get(questionsBank.get(i).getChapter()).add(questionsBank.get(i));
            } else {
                chapterMap.put(questionsBank.get(i).getChapter(), new ArrayList<>());
                chapterMap.get(questionsBank.get(i).getChapter()).add(questionsBank.get(i));
            }
        }

        // Go through each of those chapter buckets and pick X number of questions from them to add
        // to the test. If there isn't enough questions in that chapter bucket to get X questions throw
        // IllegalArgumentException
        for (String chapter : chapterMap.keySet()) {
            ArrayList<Question> currentChapterQuestions = chapterMap.get(chapter);

            if (currentChapterQuestions.size() < numQuestionsPerChapter) {
                throw new IllegalArgumentException("There is not enough questions in " + chapter + " to get "
                        + numQuestionsPerChapter + " random questions");
            }

            // Get X random unique indexes
            Set<Integer> uniqueIndexes = new LinkedHashSet<>();
            int randomIndex;
            for (int i = 0; i != numQuestionsPerChapter; ++i) {

                // Continue generating random indexes until we find a index which is unique
                // we do this by taking advantage of the unique property of Sets and their add method.
                do {
                    // Grab a random index to get a random question for that chapter.
                    randomIndex = randomNumberGenerator.nextInt(currentChapterQuestions.size());
                } while (!uniqueIndexes.add(randomIndex));

                // Found a unique index so add it's related question to the pickedQuestions ArrayList
                pickedQuestions.add(currentChapterQuestions.get(randomIndex));
            }
        }

        return pickedQuestions;
    }
}
