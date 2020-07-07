package ThreadTest.BaseThreadTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

public class Shunxuahizing {

	public static void main(String[] args) throws InterruptedException {
		m2();
	}

	public static void m1() {
		Thread t1 = new Thread(() -> {
			System.out.println(Thread.currentThread().getName());
		});

		Thread t2 = new Thread(() -> {
			try {
				t1.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName());
		});

		Thread t3 = new Thread(() -> {
			try {
				t2.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName());
		});

		t3.start();
		t2.start();
		t1.start();

	}

	public static void m2() {
		final Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName() + " run 1");
			}
		}, "T1");
		final Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName() + " run 2");
			}
		}, "T2");
		final Thread t3 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName() + " run 3");
			}
		}, "T3");

		Thread t4 = new Thread(() -> {
			System.out.println(Thread.currentThread().getName() + " run 4");

			Function<String, Integer> f1 = (str) -> {
				return str.length();
			};
			System.out.println(f1.apply("abcdefg"));
		});

//        method 2 使用 单个任务的线程池来实现。保证线程的依次执行
		ExecutorService executor = Executors.newSingleThreadExecutor();
		executor.submit(t1);
		executor.submit(t2);
		executor.submit(t3);
		executor.submit(t4);

		executor.shutdown();
	}

}
