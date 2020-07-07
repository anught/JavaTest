package ThreadTest.BaseThreadTest;

public class ThreadDemo extends Thread {

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			System.out.println(Thread.currentThread().getName() + "--------------" + i);
		}
	}

	public static void main(String[] args) throws InterruptedException {
		ThreadDemo threadDemo = new ThreadDemo();
		threadDemo.start();
		for (int i = 0; i < 5; i++) {

			if (i == 2) {
				Thread.yield();
				threadDemo.join();
			}

			System.out.println(Thread.currentThread().getName() + "===========" + i);
		}

	}
}