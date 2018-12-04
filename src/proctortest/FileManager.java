package proctortest;


import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.*;

/**
 * Date    12-03-18
 * @author (Paul Egbe, Brandon Jumbeck, Insert group names)
 **/

// TODO Brandon: Cleanup and document this file more.
// TODO Brandon: Refactor the non-concurrent question bank loading a bit and implement speed testing between the two.

public class FileManager {

    private BufferedWriter bufferedWriter = null;

    // ==========================
    // ===== Public Methods =====
    // ==========================

    ArrayList<Question> loadQuestionBankConcurrent(File questionBankFile) throws ExecutionException, InterruptedException {
        // Threading solution that uses two threads threads to read from the questionBankFile by
        // splitting the file's questions in about half and then having each thread take care of parsing
        // the information from both halves of the file.

        // I was going to go with what was listed in the assignment that we separate the threading on
        // the question and answer but this way provided a better speed then the other way overall and was easier to implement
        // with the way we set up the question bank and storing of questions which was implemented already.

        ArrayList<Question> allQuestions = new ArrayList<>();
        long secondThreadStartOffset = 0;

        // First get some simple file details needed for the threading
        try (RandomAccessFile statsFile = new RandomAccessFile(questionBankFile, "r")) {
            // Halfway points in bytes
            long halfSize = statsFile.length() / 2;
            statsFile.seek(halfSize);

            // Advance it by one line just in case we ended up inbetween two bytes of two different chars
            // Doesn't matter to much if we skip over the first $ character by doing so since one more question
            // isn't going to hurt anything either way.
            statsFile.readLine();

            // Keep advancing the filepointer until the next '$' character which denotes the end of a question block
            String line;
            do {
                line = statsFile.readLine();
            } while (!line.contains("$"));

            // Now we can get the offset for the second thread to start at
            secondThreadStartOffset = statsFile.getFilePointer();

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Build a thread pool and execute the parsing of questionBank.txt
        ExecutorService threadPool = Executors.newFixedThreadPool(2);

        Callable<ArrayList<Question>> questBankCallable1 = new ParseQuestionFileCallable(questionBankFile, 0, secondThreadStartOffset);
        Future<ArrayList<Question>> questBankFuture1 = threadPool.submit(questBankCallable1);

        Callable<ArrayList<Question>> questBankCallable2 = new ParseQuestionFileCallable(questionBankFile, secondThreadStartOffset);
        Future<ArrayList<Question>> questBankFuture2 = threadPool.submit(questBankCallable2);

        // We call get to pause execution of the main thread (This one) until both of our threads in the thread pool
        // have completed their parsing.
        ArrayList<Question> fullQuestionBank = new ArrayList<>(questBankFuture1.get());
        fullQuestionBank.addAll(questBankFuture2.get());

        return fullQuestionBank;
    }

    // Read's from questionBank.txt without threading and builds up an array of
    // all the different questions possible.
    ArrayList<Question> loadQuestionBank(File questionBankFile) {
        // Will hold all questions after they are loaded from the text file.
        ArrayList<Question> questionBank = new ArrayList<>();

        // Try to read from the question bank text file
        try {
            Scanner inputFile = new Scanner(questionBankFile);

            ArrayList<String> components = new ArrayList<>();
            ArrayList<String> tailQuestion;
            String correctAnswer;


            // Loop through the entire file
            while (inputFile.hasNext()) {

                // Pull out each line as a string
                String lineFromFile = inputFile.nextLine();

                // Uses the length to test for '$'
                // If line from file is not $ -> add line to components array list, prepare for building each question

                // TODO Brandon: This could cause some hard to debug runtime errors (Like the infinite that was caused just now) if the questionBank.txt isn't
                // formatted exactly how it should be. For example if there is a space after $ or really any type of
                // character it can fail. Would suggest changing this to looking for a $ in the string.
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
                    questionBank.add(question); // Add that question object to the questionBank array
                    components.clear(); // Clear the component array to prepare for the next building of question
                }
            }

        } catch (IOException e) {

            // Display error stack trace
            e.printStackTrace();
        }

        return questionBank;
    }

    public void appendToFile(String filePath, String stringToWrite) {
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(filePath, true));
            bufferedWriter.write(stringToWrite);

            bufferedWriter.newLine();
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    // Helper Methods

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
}




