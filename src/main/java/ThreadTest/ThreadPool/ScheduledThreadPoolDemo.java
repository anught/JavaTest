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
				System.out.println("延迟三秒执行");
				System.out.println(System.currentTimeMillis());
			}
		}, 3, TimeUnit.SECONDS);// runable 延时 单位 //延时3秒执行

		scheduledExecutorService.shutdown();
	}
}
