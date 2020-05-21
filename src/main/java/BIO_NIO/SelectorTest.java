package BIO_NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 多路复用器 
 */
public class SelectorTest {
	private ServerSocketChannel ss =null;
	private Selector selector = null;
	int port = 9090;
	
	public void initServer() {
		try {
			ss = ServerSocketChannel.open();
			ss.configureBlocking(false);//
			ss.bind(new InetSocketAddress(9090));//绑定一个端口号
			
			selector = Selector.open();
			
			SelectionKey ssk = ss.register(selector, SelectionKey.OP_ACCEPT); //这个注册可以有返回值；就是一个selectionKey
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public void start() {
		initServer();
		try {
			while(true) {
				
				while(selector.select() > 0) { //select(0) 阻塞  select(200) // 200ms返回是否有连接事件
					Set<SelectionKey> selectKeys = selector.selectedKeys();//从多路复用器取出有效集合
					
					Iterator<SelectionKey> iter = selectKeys.iterator();
					while(iter.hasNext()) { //遍历
						SelectionKey selectKey = iter.next();
						iter.remove();//处理完就要remove掉，否则会一直在里面；下次循环还会见到他
						
						if(selectKey.isAcceptable()) {
							
						} else if(selectKey.isReadable()) {
							
						}
						
						
					}
					
				}
				
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void acceptHandler(SelectionKey key) {
		try {
			ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
			SocketChannel client = ss.accept();
			ss.configureBlocking(false);
			
			ByteBuffer buffer = ByteBuffer.allocate(4096); //测试容量小的情况
			client.register(selector, SelectionKey.OP_READ, buffer);
			
			System.out.println("---------------------------");
			System.out.println("新客户端："  + client.getRemoteAddress());
			System.out.println("---------------------------");
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
}
