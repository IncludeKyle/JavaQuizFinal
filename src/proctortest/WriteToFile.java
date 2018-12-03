
package proctortest;


import java.io.*;
import java.time.LocalDateTime;

public class WriteToFile {
    
    BufferedWriter bw=null;
    
    public void fileWrite(String s)
    {
         try
            {
            bw=new BufferedWriter(new FileWriter("TestResult.txt",true));
            bw.write(s);
            
            bw.newLine();
            bw.flush();
            bw.close();
            }catch(IOException ioe)
            {
                ioe.printStackTrace();
            }
    
    }
    
    public void fileWrite(StringBuilder s)
    {
           try
            {
            bw=new BufferedWriter(new FileWriter("TestResult.txt",true));
            bw.write(s.toString());
            
            bw.newLine();
            bw.flush();
            bw.close();
            }catch(IOException ioe)
            {
                ioe.printStackTrace();
            }
    
    }
    
}




