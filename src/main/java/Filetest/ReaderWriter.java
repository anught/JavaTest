package Filetest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReaderWriter {
	public static void main(String[] args) throws IOException {
		readin();
	}

	public static void readin() throws IOException {
		BufferedReader bfr = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new FileWriter("fw.txt", true));
		String line;
		while ((line = bfr.readLine()) != null) {
			bw.write(line);
			bw.newLine();
			bw.flush();
		}
		// 关闭流
		bw.close();
	}

	public static void readinTest() throws IOException {
		try (FileInputStream fin = new FileInputStream("README");
				InputStreamReader isr = new InputStreamReader(fin, "utf-8"); // 处理流；将字节输入流转换为字符输入流来处理
				BufferedReader br = new BufferedReader(isr)) {// 将字符输入流更进一步；可以readline 方便处理
			String brLine;
			while ((brLine = br.readLine()) != null) {
				System.out.println(brLine);
			}
		}

	}
}
