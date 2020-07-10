package ThreadTest.BaseThreadTest;

public class RunnableDemo implements Runnable {
	@Override
	public void run() {
		for (int i = 0; i < 2; i++) {
			System.out.println(Thread.currentThread().getName() + " " + name + "--" + i);

			this.name = "wangwu ";
		}
	}

	public String name = "";

	public static void main(String[] args) {
		RunnableDemo runnableDemo = new RunnableDemo();
		runnableDemo.name = "lisi";
		Thread thread = new Thread(runnableDemo);
		Thread thread2 = new Thread(runnableDemo);
		thread.start();
		thread2.start();

		for (int i = 0; i < 5; i++) {
			System.out.println(Thread.currentThread().getName() + " " + runnableDemo.name + "++" + i);
		}

	}
}