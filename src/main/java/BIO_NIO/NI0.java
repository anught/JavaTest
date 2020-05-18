package BIO_NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;

public class NI0 {
	//内核支持NIO 体现在两个方面  再accept 和 read两个地方都不阻塞
	//None blocking
	//new IO
	
	public static void main(String[] args) throws IOException, InterruptedException {
		socketNIO();
	}
	
	public static void socketNIO() throws IOException, InterruptedException{  //
		//NIO 存在的缺点： accept需要一直去循环 而且 要对client 循环 存在一个资源浪费的问题，所以需要多路复用器 selector
		
		LinkedList<SocketChannel> listCli = new LinkedList<SocketChannel>();
		
		ServerSocketChannel ss = ServerSocketChannel.open();//打开一个通道
		ss.bind(new InetSocketAddress(9090));//绑定一个端口号
		ss.configureBlocking(false);//非阻塞
		
		int num = 0;
		ByteBuffer buffer = ByteBuffer.allocateDirect(4096);//堆外缓存 也可以堆内
		while(true) {
			Thread.sleep(1000);
			SocketChannel cli = ss.accept();//此处不阻塞
			
			if(cli == null) {
				System.out.println("... ... ");				
			} else {
				cli.configureBlocking(false);//客户端也非阻塞
				listCli.add(cli);
				System.out.println("cli port"+cli.socket().getPort());
			}
			
			for(SocketChannel c: listCli) {
				num = c.read(buffer);
				if(num > 0) {
					buffer.flip();//将指针指向开始；并标记读到数据的最后的位置
					byte[] aaa = new byte[buffer.limit()];
					
					buffer.get(aaa);
					String in = new String(aaa);
					
					System.out.println(in);
					buffer.clear();
				}
				else {
					//System.out.println(num);
				}
				
			
				
			}
			
		}
		
	}
	
	
}
