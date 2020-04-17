package Filetest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class filetest {
	public static void main(String[] args) throws IOException {
		
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
}
