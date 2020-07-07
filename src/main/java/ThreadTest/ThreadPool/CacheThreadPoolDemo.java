package ThreadTest.ThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CacheThreadPoolDemo {
	public static void main(String[] args) {
		ExecutorService executorService = Executors.newCachedThreadPool();
		for (int i = 0; i < 20; i++) {
			executorService.execute(() -> {
				try {
					Thread.sleep(1000); // 加了sleep就会创建20个线程
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName() + " running");
			});
		}
		executorService.shutdown();
	}

}
