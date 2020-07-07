package Filetest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ByteStreamTest {

	public static void main(String args[]) throws IOException {

		ByteArrayOutputStream bOutput = new ByteArrayOutputStream(12);

		while (bOutput.size() != 10) {
			// 获取用户输入值
			bOutput.write(System.in.read());
		}

		byte b[] = bOutput.toByteArray();
		System.out.println("Print the content");
		for (int x = 0; x < b.length; x++) {
			// 打印字符
			System.out.print((char) b[x] + " ");
		}
		System.out.println("   ");

		FileOutputStream fos = new FileOutputStream("bst");
		bOutput.writeTo(fos);// 将缓存数据写入本地文件
		fos.close();

		int c;

		ByteArrayInputStream bInput = new ByteArrayInputStream(b);

		System.out.println("Converting characters to Upper case ");
		for (int y = 0; y < 1; y++) {
			while ((c = bInput.read()) != -1) {
				System.out.print(Character.toUpperCase((char) c) + " ");
			}
			bInput.reset();
		}
	}
}