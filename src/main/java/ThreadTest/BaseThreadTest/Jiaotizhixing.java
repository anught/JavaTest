package ThreadTest.BaseThreadTest;

public class Jiaotizhixing implements Runnable {
	@Override
	public void run() {
		for (int idx = 0; idx < 10; idx++) {
			try {
				Thread.sleep(950);
				System.out.println(Thread.currentThread().getName() + " " + idx);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		Jiaotizhixing jt = new Jiaotizhixing();
		Thread jtt = new Thread(jt);
		jtt.start();

		for (int idx = 10; idx >= 0; idx--) {
			System.out.println(Thread.currentThread().getName() + " " + idx);
			Thread.sleep(1000);
		}

	}
}
