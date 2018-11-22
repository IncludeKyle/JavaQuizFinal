
package proctortest;

import java.util.ArrayList;

public class Question implements Comparable {
    // == instants ==
    private StringBuilder headQuestion;
    private ArrayList<StringBuilder> tailQuestion = new ArrayList<StringBuilder>();
    private StringBuilder correctAnswer;
    private StringBuilder chapter;
    private StringBuilder section;
    private int sort;

    // == constructor ==
    public Question(String headQuestion, ArrayList<String> tailQuestionInput, String correctAnswer, String chapter, String section) {

        this.headQuestion = convertToStringBuilder(headQuestion);
        buildTailQuest(tailQuestionInput);
        this.correctAnswer = convertToStringBuilder(correctAnswer);
        this.chapter = convertToStringBuilder(chapter);
        this.section = convertToStringBuilder(section);
        setCh();
        modifyString();

    }

    // == public methods ==


    public StringBuilder getCorrectAnswer() {
        return correctAnswer;
    }

    public StringBuilder getHeadQuestion() {
        return headQuestion;
    }

    public ArrayList<StringBuilder> getTailQuestion() {
        return tailQuestion;
    }

    public StringBuilder getChapter() {
        return chapter;
    }

    public StringBuilder getSection() {
        return section;
    }

    public void setCh() {
        for (int i = 0; i < chapter.length(); i++) {
            sort += (int) chapter.charAt(i);
        }
    }

    @Override
    public int compareTo(Object o) { // override method in Comparable interface to sort the question
        int compareage = ((Question) o).sort;
//         For Ascending order
        return this.sort - compareage;
//         For Descending order do like this
//        return compareage-this.sort;
    }

    // == private methods ==
    private void buildTailQuest(ArrayList<String> tailQuest) {
        for (String string : tailQuest) {
            tailQuestion.add(convertToStringBuilder(string));
        }
    }

    private StringBuilder convertToStringBuilder(String str) { // convert a String to a StringBuilder
        StringBuilder stringBuilder = new StringBuilder(str);
        return stringBuilder;
    }

    private void modifyString() {
        deleteSymbol(headQuestion, '?'); // delete ? in the headQuestion
        for (StringBuilder stringBuilder : tailQuestion) {
            deleteSymbol(stringBuilder, '<'); // delete < in the tailQuestion
        }
        deleteSymbol(correctAnswer, '<');// delete < in the correctAnswer

    }

    private void deleteSymbol(StringBuilder stringBuilder, char symbol) {
        for (int i = 0; i < stringBuilder.length(); i++) {
            if ((int) stringBuilder.charAt(i) == (int) symbol) {
                stringBuilder.deleteCharAt(i);
                break;
            }
        }
    }
}