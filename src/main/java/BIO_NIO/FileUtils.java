package BIO_NIO;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public abstract class FileUtils {

	/**
	 * append写入
	 * 
	 * 基于FileChannel实现高效大数据量文件写入
	 * 
	 * @param file       文件路径
	 * @param content    待写入的文件内容
	 * @param bufferSize 单次写入缓冲区大小 默认4M 1024 * 1024 * 4
	 */
	public static void write(String file, String content, Integer bufferSize) {

		bufferSize = null == bufferSize ? 4194304 : bufferSize;
		ByteBuffer buf = ByteBuffer.allocate(bufferSize);
		FileChannel channel = null;
		try {
			File fileTemp = new File(file);
			File parent = fileTemp.getParentFile();
			if (!parent.exists())
				parent.mkdirs();
			if (!fileTemp.exists())
				fileTemp.createNewFile();

			// new FileOutputStream(file, append) 第二个参数为true表示追加写入
			channel = new FileOutputStream(file, true).getChannel();

			buf.put(content.getBytes());
			buf.flip(); // 切换为读模式

			while (buf.hasRemaining()) {
				channel.write(buf);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				channel.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
