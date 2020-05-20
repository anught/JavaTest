package syncTest;

import java.io.IOException;

public class syncTest4 {


	public  void m1() {
		System.out.println("m1 start");
		
		try {
			synchronized(syncTest4.class) {
				System.out.println("m1 execute");
				Thread.sleep(20000);	
			}
			System.out.println("m1 execute end");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public synchronized void m2() {
		System.out.println("m2 start");
		
		try {
			synchronized(syncTest4.class) {
				System.out.println("m2 execute");
				Thread.sleep(2000);	
			}
			System.out.println("m2 execute end");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public synchronized void m3() {
		System.out.println("m3 start");
		
		try {
			System.out.println("m3 execute");
			Thread.sleep(2000);	
			System.out.println("m3 execute end");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	
	public static void main(String[] args) throws IOException {
		syncTest4 t1 = new syncTest4();
		
		syncTest4 t2 = new syncTest4();
		
		new Thread(()->{
			t1.m1();			
		}).start();
		
		new Thread(()->{
			t2.m2();			
		}).start();
		
		System.in.read();
	}
	


}
