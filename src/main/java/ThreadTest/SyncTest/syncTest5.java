package ThreadTest.SyncTest;

public class syncTest5 {
	private Object lock1 = new Object();

	public void m1() {

		synchronized (lock1) {
			try {
				System.out.println("m1");
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void m2() {
		synchronized (lock1) {
			try {
				System.out.println("m2");
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		syncTest5 t1 = new syncTest5();

		new Thread(() -> {
			t1.m1();
		}).start();

		new Thread(() -> {
			t1.m2();
		}).start();

	}
}
