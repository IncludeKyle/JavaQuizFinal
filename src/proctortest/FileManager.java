package proctortest;


import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.*;

/**
 * A class that manages everything the project needs to handle when it comes to writing, reading and parsing information
 * from/to files.
 *
 * Date    12-03-18
 *
 * @final.requirement Concurrency, Exceptions, Regex
 *
 * @author (Paul Egbe, Brandon Jumbeck, Insert group names)
 **/

// TODO Brandon: Cleanup and document this file more.
// TODO Brandon: Refactor the non-concurrent question bank loading a bit and implement speed testing between the two.

public class FileManager {
    private Scanner scanner = new Scanner(System.in);
    private String userOutputFile;

    /**
     * Multi-threaded solution which parses questions from the file that is passed in to the questionBankFile parameter.
     * It does this by using 2 threads and splitting the file in roughly half to concurrently read each portion's questions
     * and store them in a container. While at this point we don't see much if any speedup from using multiple threads
     * to parse our questions from the text file, if the file was let's say full of 100k or more question then it would
     * definitely increase the programs efficiency.
     *
     * @final.requirement Concurrency, Exceptions
     *
     * @param questionBankFile The question bank you would like to parse. Must be formatted a certain way to work see questionBank.txt for example.
     * @return An ArrayList full of Question objects which represent a single question for the test.
     * @throws ExecutionException   Uses threading, holds whatever exception that thread threw. The threads do read from file so IOException can be common.
     * @throws InterruptedException Uses threading, if interrupt is called before it is done this will throw.
     * @throws IOException          This is parsing a file so any IOException associated with that is common.
     */
    ArrayList<Question> loadQuestionBankConcurrent(File questionBankFile) throws Exception {
        long secondThreadStartOffset = 0;

        // Need to figure out some things about the file first like where is the halfway point in the file
        // and then using that to determine a good starting point for our second thread to start at.
        RandomAccessFile statsFile = new RandomAccessFile(questionBankFile, "r");
        long halfSize = statsFile.length() / 2;
        statsFile.seek(halfSize);

        // Advance down one line since we could be in the middle of a line right now, or even worse in the middle of a
        // a char (The next two bytes could be one from one character and one from another character).
        statsFile.readLine();

        // Look for the first $ which denotes the end of a question block then we have a good starting point for the second
        // thread.
        String line;
        do {
            line = statsFile.readLine();
        } while (!line.contains("$"));
        secondThreadStartOffset = statsFile.getFilePointer();

        // Build a thread pool and execute the parsing of questionBank.txt
        ExecutorService threadPool = Executors.newFixedThreadPool(2);

        Callable<ArrayList<Question>> questBankCallable1 = new ParseQuestionFileCallable(questionBankFile, 0, secondThreadStartOffset);
        Future<ArrayList<Question>> questBankFuture1 = threadPool.submit(questBankCallable1);

        Callable<ArrayList<Question>> questBankCallable2 = new ParseQuestionFileCallable(questionBankFile, secondThreadStartOffset);
        Future<ArrayList<Question>> questBankFuture2 = threadPool.submit(questBankCallable2);

        // We call get to pause execution of the main thread (This one) until both of our threads in the thread pool
        // have completed their parsing, then combine their results into a single collection.
        ArrayList<Question> fullQuestionBank = new ArrayList<>(questBankFuture1.get());
        fullQuestionBank.addAll(questBankFuture2.get());

        // End the threads
        threadPool.shutdown();

        return fullQuestionBank;
    }

    /**
     * Non-threaded solution which parses questions from the file that is passed in to the questionBankFile parameter.
     *
     * @final.requirement Exceptions
     *
     * @param questionBankFile The question bank you would like to parse. Must be formatted a certain way to work see questionBank.txt for example.
     * @return An ArrayList full of Question objects which represent a single question for the test.
     * @throws FileNotFoundException Throws FileNotFoundException when unable to locate the questionBankFile specified.
     */
    ArrayList<Question> loadQuestionBank(File questionBankFile) throws FileNotFoundException {
        // Will hold all questions after they are loaded from the text file.
        ArrayList<Question> questionBank = new ArrayList<>();

        // Try to read from the question bank text file
        Scanner inputFile = new Scanner(questionBankFile);

        ArrayList<String> components = new ArrayList<>();
        ArrayList<String> tailQuestion;
        String correctAnswer;

        // Loop through the entire file
        while (inputFile.hasNextLine()) {
            // Pull out each line as a string
            String lineFromFile = inputFile.nextLine();

            if (!lineFromFile.contains("$")) {
                components.add(lineFromFile); // Add line to the components array list
            } else {
                tailQuestion = findWrongAnswers(components);      // Build question's tail
                correctAnswer = findCorrectAnswer(components); // Build correct answer of the question
                Question question = new Question(components.get(0), tailQuestion, correctAnswer, components.get(1), components.get(2)); // create a question object after has all of its components
                questionBank.add(question); // Add that question object to the questionBank array
                components.clear(); // Clear the component array to prepare for the next building of question
            }
        }

        return questionBank;
    }

    // TODO: If time refactor this method so that it doesn't write to a file after every question and instead once per test
    /**
     * Save all the user's results from his/her test to a file.
     *
     * @final.requirement Exceptions
     *
     * @param questionNum The current question number.
     * @param question A Question object that holds information about the question taken.
     * @param userAnswer The answer the user chose.
     */
    // Save user results to text file
    void appendUserStatsToFile(int questionNum, Question question, String userAnswer, double percent, boolean isAnswerCorrect) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(userOutputFile, true));
        bw.newLine();
        bw.write("Question:" + questionNum);
        bw.newLine();
        bw.write(question.getQuestion());
        bw.newLine();
        bw.write(question.getAllAnswers().toString());
        bw.newLine();
        bw.write("Your answer is: " + userAnswer);
        bw.newLine();
        bw.write("The correct answer is: " + question.getCorrectAnswer());
        bw.newLine();
        bw.write("Chapter: " + question.getChapter());
        bw.newLine();
        bw.write("Section: " + question.getSection());
        bw.newLine();
        bw.write("Current total percent correct: " + String.valueOf(percent));
        bw.newLine();
        bw.write(isAnswerCorrect ? "< Correct! >" : "< Wrong! >");
        bw.newLine();
        bw.flush();
        bw.close();
    }

    /**
     * Converts the user name passed in to a format that we can use to save that users test info into a file.
     *
     * @param userName The full name of the user taking the test
     * @final.requirement Regex
     */
    void setUsernameForFile(String userName) {
        userOutputFile = userName + "_" + new SimpleDateFormat("yyyy-MM-dd HHmm'.txt'").format(new Date());
    }

    // TODO Brandon: These two methods below are also inside of ParseQuestionFileCallable, would like to figure out how to make them in 1 place.

    /**
     * Uses the regex "[(].[)].*" to search through the strings that were passed to the method
     * and pull out all incorrect answers to the question.
     *
     * @final.requirement Regex
     *
     * @param questionComponents An ArrayList of strings that contains lines to be searched for the wrong answers.
     * @return An ArrayList of Strings that contains every line that matches the "[(].[)].*" regex (Is a wrong answer).
     */
    private ArrayList<String> findWrongAnswers(ArrayList<String> questionComponents) {
        ArrayList<String> tailQuestion = new ArrayList<>();
        for (String tail : questionComponents) {
            // Satisfies the regex requirement of the project. Though we use regex elsewhere in the project also.
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
     * @final.requirement Regex
     *
     * @param questionComponents An ArrayList of strings that contains lines to be searched for the wrong answers.
     * @return An ArrayList of Strings that contains every line that matches the "[(].[)].*" regex (Is a wrong answer).
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




