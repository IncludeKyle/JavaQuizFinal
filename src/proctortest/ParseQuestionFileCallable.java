package proctortest;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.concurrent.Callable;

/**
 * Date    12-03-18
 *
 * @author (Brandon Jumbeck, Insert group names)
 **/

// TODO Brandon: Clean up and document this file more.
class ParseQuestionFileCallable implements Callable {
    private long startOffset;
    private long endOffset;
    private RandomAccessFile questionFile;

    /**
     * @param file The file to work off of.
     * @throws IOException Opens up a file to be read from so can throw IOExceptions related to that.
     */
    ParseQuestionFileCallable(File file) throws IOException {
        questionFile = new RandomAccessFile(file, "r");

        this.startOffset = 0;
        this.endOffset = questionFile.length();
    }

    /**
     * @param file             The file to work off of
     * @param startOffsetBytes How many bytes to offset the starting point of the file
     * @throws IOException Opens up a file to be read from so can throw IOExceptions related to that.
     */
    ParseQuestionFileCallable(File file, long startOffsetBytes) throws IOException {
        questionFile = new RandomAccessFile(file, "r");

        this.startOffset = startOffsetBytes;
        this.endOffset = questionFile.length();
    }

    /**
     * @param file             The file to work off of
     * @param startOffsetBtyes How many bytes to offset the starting point of the file
     * @param endOffsetBytes   Determines where the end point is where we should stop parsing. This is the number of bytes from the start of the file
     * @throws IOException Opens up a file to be read from so can throw IOExceptions related to that.
     */
    ParseQuestionFileCallable(File file, long startOffsetBtyes, long endOffsetBytes) throws IOException {
        questionFile = new RandomAccessFile(file, "r");

        this.startOffset = startOffsetBtyes;
        this.endOffset = endOffsetBytes;
    }

    /**
     * Parses the file it has been given from the starting point to the ending point (EOF if none was given) and takes
     * all the question information within that file and stores it in an ArrayList full of Question objects.
     *
     * @return An ArrayList full of Question objects that represent all the questions that have been parsed by this callable
     * @throws Exception Can throw exceptions related to file IO.
     */
    public ArrayList<Question> call() throws Exception {
        ArrayList<Question> questionBank = new ArrayList<>();
        questionFile.seek(startOffset);

        String line;
        ArrayList<String> questionComponents = new ArrayList<>();
        while (questionFile.getFilePointer() < endOffset && (line = questionFile.readLine()) != null) {

            // The $ symbol denotes the end of a question block in our text file. So we keep on
            // storing all lines in an ArrayList until we hit that symbol then we use them to build
            // up the Question object.
            if (!line.contains("$")) {
                questionComponents.add(line);
            } else {
                ArrayList<String> wrongAnswers = findWrongAnswers(questionComponents);
                String correctAnswer = findCorrectAnswer(questionComponents);

                Question question = new Question(questionComponents.get(0), wrongAnswers, correctAnswer,
                        questionComponents.get(1), questionComponents.get(2));
                questionBank.add(question);

                questionComponents.clear();
            }
        }

        questionFile.close();

        return questionBank;
    }

    /**
     * Uses the regex "[(].[)].*" to search through the strings that were passed to the method
     * and pull out all incorrect answers to the question.
     *
     * @param questionComponents An ArrayList of strings that contains lines to be searched for the wrong answers
     * @return An ArrayList of Strings that contains every line that matches the "[(].[)].*" regex (Is a wrong answer)
     */
    private ArrayList<String> findWrongAnswers(ArrayList<String> questionComponents) {
        ArrayList<String> tailQuestion = new ArrayList<>();
        for (String tail : questionComponents) {
            if (tail.matches("[(].[)].*")) { // Regex to define a tail of a question
                tailQuestion.add(tail);
            }
        }

        return tailQuestion;
    }

    /**
     * Uses the regex "[(].[)].*" to search through the strings that were passed to the method
     * and pull out all incorrect answers to the question.
     *
     * @param questionComponents An ArrayList of strings that contains lines to be searched for the wrong answers
     * @return An ArrayList of Strings that contains every line that matches the "[(].[)].*" regex (Is a wrong answer)
     */
    private String findCorrectAnswer(ArrayList<String> questionComponents) {
        String correctAnswer = "";
        for (String string : questionComponents) {
            if (string.matches("[(].[)].*[<]")) { // Regex to define correct answer of a question
                correctAnswer = string;
            }
        }

        return correctAnswer;
    }
}