package syncTest;

public class syncTest1 {
	public synchronized void m1() {
		System.out.println("m1 start");
		
		try {
			System.out.println("m1 execute");
			Thread.sleep(2000);
			System.out.println("m1 execute end");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public synchronized void m2() {
		System.out.println("m2 start");
		
		try {
			System.out.println("m2 execute");
			Thread.sleep(2000);
			System.out.println("m2 execute end");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public static void main(String[] args) {
		syncTest1 t1 = new syncTest1();
		
		new Thread(()->{
			t1.m1();			
		}).start();
		
		new Thread(()->{
			t1.m2();			
		}).start();
		
	}
	
}
