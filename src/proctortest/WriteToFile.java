
package proctortest;

import java.util.Date;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.text.SimpleDateFormat;

/**
 * @date    11-21-18
 * @authors (Paul Egbe, Kyle Blaha, Charles Vang, Brady Olson)
 **/

public class WriteToFile {
    
    Scanner scanner=new Scanner(System.in);
    String userName="";
    String fileName="";
    
    BufferedWriter bw=null;
    
    // Save user results to text file
    public void fileWrite(  int count, StringBuilder question, ArrayList<StringBuilder> tailQuestions, 
                            String userAnswer, StringBuilder correctAnswer, StringBuilder chapter, 
                            StringBuilder section)// double percent)
    {
       
         try
            {
            bw=new BufferedWriter(new FileWriter(fileName,true));
            bw.newLine();
            bw.write("Question:"+count);
            bw.newLine();
            bw.write(question.toString());
            bw.newLine();
            bw.write(tailQuestions.toString());
            bw.newLine();
            bw.write("Your answer is: "+userAnswer); 
            bw.newLine();
            bw.write("The correct answer is: "+correctAnswer.toString());
            bw.newLine();
            bw.write("Chapter: "+chapter.toString());
            bw.newLine();
            bw.write("Section: "+section.toString());
            bw.newLine();
            bw.flush();
            bw.close();
            }catch(IOException ioe)
            {
                ioe.printStackTrace();
            }
        
    }
    
   public void fileWrite(String s)
    {
           try
            {
            bw=new BufferedWriter(new FileWriter(fileName,true));
            bw.write(s);
            
            bw.newLine();
            bw.flush();
            bw.close();
            }catch(IOException ioe)
            {
                ioe.printStackTrace();
            }
    
    }
   
   // Write percent correct to text file
    public void fileWrite(double percent)
    {
           try
            {
            bw=new BufferedWriter(new FileWriter(fileName,true));
            bw.write("Percent correct: "+String.valueOf(percent));
            
            bw.newLine();
            bw.flush();
            bw.close();
            }catch(IOException ioe)
            {
                ioe.printStackTrace();
            }
    
    }
   
    // Scan userName for text file name
    public void fileWrite(int count)
    {
           if(count==1)
        {
    
            do
            {
                System.out.println("Please enter your first and last name with a space in bewteen:");
                userName=scanner.nextLine();
            }while(!userName.matches("[a-zA-Z]*[\\s]{1}[a-zA-Z].*")||userName.isEmpty());
                
            fileName = userName+"_"+new SimpleDateFormat("yyyy-MM-dd HHmm'.txt'").format(new Date());
        }
    }
}