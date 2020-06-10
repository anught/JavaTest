package WsServer;

import java.util.concurrent.Executors;

import javax.xml.ws.Endpoint;

public class start {
	public static void main(String[] args) {
		String url = "http://127.0.0.1:8080/ws";
		Endpoint.publish(url, new HelloWs()).setExecutor(Executors.newFixedThreadPool(2));
	}
}
