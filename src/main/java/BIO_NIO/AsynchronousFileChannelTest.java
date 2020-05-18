package BIO_NIO;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Future;

/*Java NIO中 :使用AsynchronousFileChannel可以实现异步地读取和写入文件数据。
 * 
 * 
 * */
public class AsynchronousFileChannelTest {
	public static void main(String[] args) throws IOException {
		write3();
		System.in.read();

	}

	public static void read() throws IOException {
		Path path = Paths.get("pom.xml");
		AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		long position = 0;
		Future<Integer> operation = fileChannel.read(buffer, position);// read会立即返回，不管是否读完

		while (!operation.isDone()) {
			System.out.println("unDone");// 读取的时候不阻塞
		}
		buffer.flip();// 将指针指向开始，并把limit指向读取到的文件的结尾获取limit位置
		byte[] data = new byte[buffer.limit()];// limit为文件的结尾（读出来的，在buffer中的位置）
		buffer.get(data);
		System.out.println(new String(data));
		buffer.clear();

	}

	public static void read2() throws IOException {
		Path path = Paths.get("pom.xml");
		AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);
		ByteBuffer buffer = ByteBuffer.allocate(1024);

		long position = 0;
		fileChannel.read(buffer, position, buffer, new CompletionHandler<Integer, ByteBuffer>() {
			@Override
			public void completed(Integer result, ByteBuffer attachment) {
				System.out.println("result = " + result);

				attachment.flip();
				byte[] data = new byte[attachment.limit()];
				attachment.get(data);
				System.out.println(new String(data));
				attachment.clear();
			}

			@Override
			public void failed(Throwable exc, ByteBuffer attachment) {
				System.out.println("failed");
			}
		});

	}

	public static void write() throws IOException {
		Path path = Paths.get("test-write.txt");
		if (!Files.exists(path)) {
			Files.createFile(path);
		}

		// AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path,
		// StandardOpenOption.APPEND);

		AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE);

		ByteBuffer buffer = ByteBuffer.allocate(1024);
		long position = 0;

		buffer.put("test data a ".getBytes());
		buffer.flip();
		Future<Integer> operation = fileChannel.write(buffer, position);
		buffer.clear();
//		while (!operation.isDone())
//			;

		System.out.println("Write done");
	}

	public static void write2() throws IOException {
		Path path = Paths.get("test-write2.txt");
		if (!Files.exists(path)) {
			Files.createFile(path);
		}
		AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE);

		ByteBuffer buffer = ByteBuffer.allocate(1024);
		long position = 0;

		buffer.put("test data".getBytes());
		buffer.flip();

		fileChannel.write(buffer, position, buffer, new CompletionHandler<Integer, ByteBuffer>() {

			@Override
			public void completed(Integer result, ByteBuffer attachment) {
				System.out.println("bytes written: " + result);
			}

			@Override
			public void failed(Throwable exc, ByteBuffer attachment) {
				System.out.println("Write failed");
				exc.printStackTrace();
			}
		});
	}

	public static void write3() {

		String filename = "w3";
		File file = new File(filename);

		FileOutputStream fos = null;
		int rtn = 0;
		try {
			fos = new FileOutputStream(file, true);
			FileChannel fc = fos.getChannel();

			byte[] bytes = "by4r4tes".getBytes();
			ByteBuffer bbf = ByteBuffer.wrap(bytes);
			bbf.put(bytes);
			bbf.flip();

			bytes = "by44tes".getBytes();
			ByteBuffer bbq = ByteBuffer.wrap(bytes);
			bbq.put(bytes);
			bbq.flip();

			fc.write(bbf);
			fc.write(bbq);
			fc.close();
			fos.flush();
			fos.close();
		} catch (IOException e) {
			rtn = 1;
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public static void write4() {
	}

}
