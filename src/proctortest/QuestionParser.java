
package proctortest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

/**
 * @date    11-18-18
 * @authors (Add group names)
 **/

// This is class: public class StructureTest
public class QuestionParser {
    
    // == instants==
    private ArrayList<Question> questionsBank = new ArrayList<Question>();
    private ArrayList<Question> pickedQuest = new ArrayList<Question>();
    private Random random = new Random();


    // == constructor ==
    public QuestionParser() throws IOException {
        loadQuestionBank(); // load questionBank.txt to questionBank arrayList
        Collections.sort(questionsBank); // sorting ascending order for questionBank list
        randomQuizQuest(); // random pick 3 questions of each chapter, and build pickedQuest list

    }

    // == public methods ==
    private void loadQuestionBank() throws IOException { // load questionBank.txt to questionBank arrayList


        try {
            File questionBank = new File("questionBank.txt");
            Scanner inputFile = new Scanner(questionBank);

            ArrayList<String> components = new ArrayList<>();
            ArrayList<String> tailQuestion = new ArrayList<>();
            String correctAnswer = "";

            while (inputFile.hasNext()) {
                String str = inputFile.nextLine();

                // if hasNext() is not $ -> build component array, prepare for building each question
                if (str.length() > 1) {
                    components.add(str);
                }

                // if hasNext() is $ -> build each component for each question
                if (str.length() == 1) {
                    tailQuestion = buildTailQuest(components); // build question's tail
                    correctAnswer = buildCorrectAnswer(components); // build correct answer of the question
                    Question question = new Question(components.get(0), tailQuestion, correctAnswer, components.get(1), components.get(2)); // create a question object after has all of its components
                    questionsBank.add(question); // add that question object to the questionBank array
                    components.clear(); // clear the component array to prepare for the next building of question
                }
            }

        } catch (IOException e) {
            throw new IllegalArgumentException("File path does't exist.");
        }
    }

    private ArrayList<String> buildTailQuest(ArrayList<String> component) {
        ArrayList<String> tailQuestion = new ArrayList<>();
        for (String tail : component) {
            if (tail.matches("[(].[)].*")) { // regex to define a tail of a question
                tailQuestion.add(tail);
            }
        }

        return tailQuestion;
    }

    private String buildCorrectAnswer(ArrayList<String> component) {
        String correctAnswer = "";
        for (String string : component) {
            if (string.matches("[(].[)].*[<]")) { // regex to define correct answer of a question
                correctAnswer = string;
            }
        }

        return correctAnswer;
    }

    private void buildPickedQuest(ArrayList<Integer> ranums, ArrayList<Question> groupQuest) {
        for (int c = 0; c < ranums.size(); c++) {
            pickedQuest.add(groupQuest.get(ranums.get(c)));
        }
    }

    private void randomQuizQuest() {

        String currentChapter = "";
        String nextChapter = "";
        ArrayList<Question> groupQuests = new ArrayList<>();
        ArrayList<Integer> ranums = new ArrayList<>();

        for (int i = 0; i < questionsBank.size(); i++) {
            currentChapter = questionsBank.get(i).getChapter().toString();

            // if i reaches to the end, set nextChapter to the chapter of the first question in questionsBank array
            if (i == questionsBank.size() - 1) {
                nextChapter = questionsBank.get(0).getChapter().toString();
            } else {
                nextChapter = questionsBank.get(i + 1).getChapter().toString();
            }

            // check if currentChapter equals nextChapter, add that question to the groupQuest array
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

    private ArrayList<Integer> random(int max) {
        ArrayList<Integer> ranums = new ArrayList<>();
        int min = 0;

        while (ranums.size() < 3) {
            int randomNum = random.nextInt((max - min) + 1) + min;
            if (checkDuplicate(ranums, randomNum)) { // check if duplicating number, then will ignore it
                ranums.add(randomNum);
            }
        }
        return ranums;
    }

    private boolean checkDuplicate(ArrayList<Integer> arr, int num) {
        for (int value : arr) {
            if (value == num) {
                return false;
            }
        }
        return true;
    }

    // == public methods ==
    public void runTest() { // run test on command line
        String userAnswer = "";
        Scanner scanner = new Scanner(System.in);
        System.out.printf("%5s%s%5s%n", "*****", "FINAL QUIZ", "*****");

        int count = 1;
        for (Question question : pickedQuest) {
            System.out.printf("%d%s%s%n", count, ". ", question.getHeadQuestion());
            for (StringBuilder stringBuilder : question.getTailQuestion()) {
                System.out.println(stringBuilder);
            }

            userAnswer = scanner.next().toUpperCase();
            System.out.println("* Your Answer: " + userAnswer);
            System.out.println("* Correct Answer: " + question.getCorrectAnswer());
            System.out.println("* Chapter: " + question.getChapter());
            System.out.println("* Section: " + question.getSection() + "\n\n");

            count++;
        }


    }
    
}
