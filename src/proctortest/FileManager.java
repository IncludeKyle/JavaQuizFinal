package proctortest;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Date    12-03-18
 * @author (Paul Egbe, Brandon Jumbeck, Insert group names)
 **/

public class FileManager {

    BufferedWriter bufferedWriter = null;

    public void fileWrite(String s) {
        try {
            bufferedWriter = new BufferedWriter(new FileWriter("TestResult.txt", true));
            bufferedWriter.write(s);

            bufferedWriter.newLine();
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

    public void fileWrite(StringBuilder s) {
        try {
            bufferedWriter = new BufferedWriter(new FileWriter("TestResult.txt", true));
            bufferedWriter.write(s.toString());

            bufferedWriter.newLine();
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

}




