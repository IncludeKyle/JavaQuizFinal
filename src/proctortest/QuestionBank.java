package proctortest;

import java.util.*;

/**
 * Holds a large question bank of possible test questions which it can use to
 * generate questions lists that test's can use for their tests.
 *
 * @final.requirement Exceptions
 *
 * date 11-21-18
 *
 * @author (Paul Egbe, Kyle Blaha, Mackenzie Branch, Brandon Jumbeck)
 **/


public class QuestionBank {
    private ArrayList<Question> questionsBank;

    /**
     * Creates a questionBank
     *
     * @param allQuestions All possible question's available.
     */
    public QuestionBank(ArrayList<Question> allQuestions) {
        // Sort questionBank list in ascending order
        this.questionsBank = allQuestions;
        Collections.sort(this.questionsBank);
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
