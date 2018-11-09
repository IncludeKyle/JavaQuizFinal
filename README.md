# JavaQuizFinal
Final group project: Generates quiz of 40 questions randomly selected from a text file of 100 questions.


Class: BuildQuiz
   This class will use a text parser to read the first line in the file. If the text parser detects the question
mark symbol '?' as the first character in the line it will load that corresponing line into the questionBankArray[].
   The parser continues to read each line and load them into a 2 dimensional answerBankArray[Q][A] until a '$' symbol is encountered. The 'Q' dimension of the array is parallel with the index of the questionBankArray[].
[Q] is indexing the question number.
[A] is indexing the answer number.

For example:
Accessing answerBankArray[0][0] would give you the string contents for Question #1, answer (A)
Accessing answerBankArray[5][3] would give you the string contents for Question #6, answer (D)
