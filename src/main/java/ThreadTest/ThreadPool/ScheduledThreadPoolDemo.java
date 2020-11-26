package ThreadTest.ThreadPool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolDemo {
	public static void main(String[] args) {
		ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);
		System.out.println(System.currentTimeMillis());

		scheduledExecutorService.schedule(new Runnable() {
			@Override
			public void run() {
				while (true) {
					System.out.println("延迟1秒执行");
					System.out.println(Thread.currentThread().getId());

					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		}, 1, TimeUnit.SECONDS);// runable 延时 单位 //延时1秒执行

		scheduledExecutorService.shutdown();
	}
}
