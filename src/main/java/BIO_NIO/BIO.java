package BIO_NIO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class BIO {
	public static void main(String[] args) throws IOException {
		socketIO();
		System.in.read();
	}
	
	public static void socketIO() throws IOException {
		//会有连个阻塞    一个时accept 一个是 read 	
		
		ServerSocket server = new ServerSocket(9090);
		//相当于 内核中的三个操作 
		//socket() 6fd    创建套接字 
		//bind(6fd,9090)  套接字与端口绑定	 
		//listen(6fd)     监听该套接字		
		System.out.println("step 1:将端口和socket 套接字绑定，建立监听");//将套接字与端口绑定	
		
		Socket client = server.accept();//阻塞；直到收到一个连接，然后返回一个client，通过这个client与客户端通信
		//相当于 内核中的accept 返回 7fd
		//后面对client的操作相当于操作7fd的读和写 
		
		System.out.println("step 2:接收到客户端连接：" + client.getPort());
		
		InputStream in = client.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		
		System.out.println("read:" + reader.readLine());
	}
}
