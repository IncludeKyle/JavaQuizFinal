
package proctortest;

import java.util.ArrayList;

/**
 * @date    11-21-18
 * @authors (Paul Egbe, Kyle Blaha, Mackenzie Branch, Insert group names)
 **/

public class Question implements Comparable {
    
    // ===============================
    // ====== Instance Variables =====
    // ===============================
    private StringBuilder headQuestion;
    private ArrayList<StringBuilder> tailQuestion = new ArrayList<>();
    private StringBuilder correctAnswer;
    private StringBuilder chapter;
    private StringBuilder section;
    private int sort;

    
    // =======================
    // ===== Constructor =====
    // =======================
    public Question(
            String headQuestion, ArrayList<String> tailQuestionInput, 
            String correctAnswer, String chapter, String section) {

        // Converts strings received in the constructor to string 
        // builders and stores them in the instance variables
        this.headQuestion = convertToStringBuilder(headQuestion);
        buildTailQuest(tailQuestionInput);
        this.correctAnswer = convertToStringBuilder(correctAnswer);
        this.chapter = convertToStringBuilder(chapter);
        this.section = convertToStringBuilder(section);
        
        setCh();        // Adds up ASCII values for use in the overriden compareTo()
        modifyString(); // Invokes deleteSymbol() with different chars to remove
    }

    
    // ==========================
    // ===== Public Methods =====
    // ==========================
    
    // Setters and getters for each Question object
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

    // Sort instance variable is added up with the ASCII value of each char 
    // in the StringBuilder chapter instance
    public void setCh() {
        for (int i = 0; i < chapter.length(); i++) {
            sort += (int) chapter.charAt(i);
        }
    }

    // Override method in Comparable interface to sort the question
    @Override
    public int compareTo(Object o) { 
        int compareage = ((Question) o).sort;
        // For ascending order
        return this.sort - compareage;
        
        // For descending order do this
        // return compareage-this.sort;
    }

    
    // ============================
    // ====== Private Methods =====
    // ============================
    
    // Convert each string in a list to a StringBuilder and add it 
    // to the tailQuestion instance list
    private void buildTailQuest(ArrayList<String> tailQuest) {
        
        // For each string in the tailQuest list
        for (String string : tailQuest) {
            
            // Convert the string to a StringBuilder and add it 
            // to the tailQuestion instance list
            tailQuestion.add(convertToStringBuilder(string));
        }
    }

    // Convert a String to a StringBuilder
    private StringBuilder convertToStringBuilder(String str) { 
        StringBuilder stringBuilder = new StringBuilder(str);
        return stringBuilder;
    }

    // Deletes '?' in the headQuestion and deletes '<' in the tailQuestion
    // Also deletes '<' in the correctAnswer
    private void modifyString() {
        
        // Delete ? in the headQuestion
        deleteSymbol(headQuestion, '?'); 
        
        // Delete < in the tailQuestion
        for (StringBuilder stringBuilder : tailQuestion) {
            deleteSymbol(stringBuilder, '<');
        }
        
        // Delete < in the correctAnswer
        deleteSymbol(correctAnswer, '<');
    }

    // Seeks out and removes a specified symbol in a StringBuilder object
    private void deleteSymbol(StringBuilder stringBuilder, char symbol) {
        for (int i = 0; i < stringBuilder.length(); i++) {
            if ((int) stringBuilder.charAt(i) == (int) symbol) {
                stringBuilder.deleteCharAt(i);
                break;
            }
        }
    }
}