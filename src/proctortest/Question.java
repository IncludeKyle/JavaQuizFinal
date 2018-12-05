package proctortest;

import java.util.ArrayList;

/**
 * date    11-21-18
 *
 * @author (Paul Egbe, Kyle Blaha, Mackenzie Branch, Insert group names)
 **/

public class Question implements Comparable {
    private String question;
    private ArrayList<String> wrongAnswers;
    private String correctAnswer;
    private String chapter;
    private String section;
    private int sort;

    /**
     * @param question      The question that is asked.
     * @param wrongAnswers  An ArrayList of String that represent the wrong answers from the multiple choice questions.
     * @param correctAnswer A string that represents the correct answer from the multiple choice questions.
     * @param chapter       The chapter that the question came from.
     * @param section       The section that the question came from.
     */
    public Question(String question, ArrayList<String> wrongAnswers, String correctAnswer, String chapter, String section) {
        this.question = question;
        this.wrongAnswers = wrongAnswers;
        this.correctAnswer = correctAnswer;
        this.chapter = chapter;
        this.section = section;

        setCh();        // Adds up ASCII values for use in the overriden compareTo()
        cleanUpQuestion(); // Invokes deleteSymbol() with different chars to remove
    }

    /**
     * @return The correct answer for the multiple choice question.
     */
    String getCorrectAnswer() {
        return correctAnswer;
    }

    /**
     * @return The multiple choice question itself.
     */
    String getQuestion() {
        return question;
    }

    /**
     * @return The wrong answers from the multiple choice question.
     */
    ArrayList<String> getWrongAnswers() {
        return wrongAnswers;
    }

    /**
     * @return The chapter the question relates to.
     */
    String getChapter() {
        return chapter;
    }

    /**
     * @return The section of the chapter the question relates to.
     */
    String getSection() {
        return section;
    }

    /**
     * Sort instance variable is added up with the ASCII value of each char.
     * in the StringBuilder chapter instance.
     */
    private void setCh() {
        for (int i = 0; i < chapter.length(); i++) {
            sort += (int) chapter.charAt(i);
        }
    }

    /**
     * Override method in Comparable interface to sort the question in ascending order.
     *
     * @param o Object to be compared to.
     * @return 0 if equal, -1 if less than, 1 if greater than.
     */
    @Override
    public int compareTo(Object o) {
        int compareage = ((Question) o).sort;
        return this.sort - compareage;
    }

    /**
     * Deletes '?' in the question, '<' and also deletes '<' in the correctAnswer.
     */
    private void cleanUpQuestion() {
        question = deleteSymbol(question, '?');
        correctAnswer = deleteSymbol(correctAnswer, '<');
    }

    /**
     * Seeks out and removes a specified symbol in a StringBuilder object.
     *
     * @param string The string you would like to delete symbols from.
     * @param symbol The symbol you would like to remove from the StringBuilder.
     * @return A new string with all the characters specified by the symbol parameter removed.
     */
    private String deleteSymbol(String string, char symbol) {
        StringBuilder temp = new StringBuilder(string);

        for (int i = 0; i < temp.length(); i++) {
            if ((int) temp.charAt(i) == (int) symbol) {
                temp.deleteCharAt(i);
                break;
            }
        }

        return temp.toString();
    }
}