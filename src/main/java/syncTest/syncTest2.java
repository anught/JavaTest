package syncTest;

public class syncTest2 {

	public static synchronized void m1() {
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
	
	public static synchronized void m2() {
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
		syncTest2 t1 = new syncTest2();
		syncTest2 t2 = new syncTest2();
		new Thread(()->{
			t1.m1();			
		}).start();
		
		new Thread(()->{
			t2.m2();			
		}).start();
		
	}
	

}
