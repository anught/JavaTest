package Filetest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class filetest {
	public static void main(String[] args) throws IOException {
		
		writeFile();
	}
	public static void readFile() throws IOException {	
		String filePath = "hello.txt";
		File file1 = new File (filePath);
		FileInputStream in=new FileInputStream (file1);
		 
		
		File file2 = new File (filePath);
		FileInputStream in1=new FileInputStream (file2);
		InputStreamReader inReader=new InputStreamReader (in1,StandardCharsets.UTF_8);
		BufferedReader bufReader=new BufferedReader(inReader);
		bufReader.readLine();		
		
		File file3 = new File (filePath);
		FileReader fileReader=new FileReader(file3);
		BufferedReader bufReader1=new BufferedReader(fileReader);

	}
	
	public static void writeFile() {
		
		BufferedWriter out = null;     
        try {     
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("append_file", true),"UTF-8"));     
            out.append("conent\n");     
        } catch (Exception e) {     
            e.printStackTrace();     
        } finally {     
            try {     
                if(out != null){  
                    out.close();     
                }  
            } catch (IOException e) {     
                e.printStackTrace();     
            }     
        }     
	}
	
}
