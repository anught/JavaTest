package JedisTest;
import java.util.HashMap;

import io.rebloom.client.Client;

public class Jrebloom {
	public static void main(String[] args) {
		 testBloom();
	}
	
	public static void testBloom() {
		Client client = new Client("localhost", 6379);
		client.add("card1", "189");
		System.out.println(client.exists("card", "999189"));
		System.out.println(client.exists("card", "184"));
		
//		for (int i = 0;i<1000000;i++) {
//			if(false == client.exists("card", i + "189")) {
//				System.out.println(	);
//			}
//		}		
		client.close();
		
		
		System.out.println(client.exists("card", "189"));

	}
}
