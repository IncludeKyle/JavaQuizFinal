package proctortest;

import java.io.File;
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
    private File workFile;

    ParseQuestionFileCallable(File file, long startOffsetBytes) {
        this.startOffset = startOffsetBytes;
        this.endOffset = -1;
        workFile = file;
    }

    ParseQuestionFileCallable(File file, long startOffsetBtyes, long endOffsetBytes) {
        this.startOffset = startOffsetBtyes;
        this.endOffset = endOffsetBytes;
        workFile = file;
    }

    // A callable object to parse a portion of whatever file is given for our questions from a start offset
    // to an end offset (Both in bytes). We can use this with an executor to offload parsing to
    // multiple threads at once.
    public ArrayList<Question> call() throws Exception {
        ArrayList<Question> questionBank = new ArrayList<>();

        try (RandomAccessFile questionFile = new RandomAccessFile(workFile, "r")) {
            questionFile.seek(startOffset);

            String line;
            String correctAnswer;
            ArrayList<String> tailQuestions;
            ArrayList<String> questionComponents = new ArrayList<>();
            while (questionFile.getFilePointer() < endOffset && (line = questionFile.readLine()) != null) {

                // Search for $ to determine if the question block is done
                if (!line.contains("$")) {
                    questionComponents.add(line);
                } else {
                    tailQuestions = buildTailQuestion(questionComponents);
                    correctAnswer = buildCorrectAnswer(questionComponents);

                    Question question = new Question(questionComponents.get(0), tailQuestions, correctAnswer,
                            questionComponents.get(1), questionComponents.get(2));
                    questionBank.add(question);
                    questionComponents.clear();
                }
            }
        }

        return questionBank;
    }

    // Uses the received components array list and a Regex to test for all answers in a
    // question block
    private ArrayList<String> buildTailQuestion(ArrayList<String> component) {
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