package ThreadTest.SyncTest;

public class syncTest3 {

	public  void m1() {
		System.out.println("m1 start");
		
		try {
			synchronized(this) {
				System.out.println("m1 execute");
				Thread.sleep(2000);	
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
			synchronized(this) {
				System.out.println("m2 execute");
				Thread.sleep(2000);	
			}
			System.out.println("m2 execute end");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public static void main(String[] args) {
		syncTest3 t1 = new syncTest3();
		
		new Thread(()->{
			t1.m1();			
		}).start();
		
		new Thread(()->{
			t1.m1();			
		}).start();
		
	}
	

}
