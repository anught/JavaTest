package ThreadTest.ThreadPool;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolDemo2 {
	public static void main(String[] args) throws IOException {
		ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
		System.out.println(System.currentTimeMillis());
		scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				System.out.println("1------延迟一秒执行，每三秒执行一次");
				System.out.println(System.currentTimeMillis() / 1000);

				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, 1, 3, TimeUnit.SECONDS); // 延迟一秒执行 ：加入队列后停一秒开始执行； 每3秒执行一次
		scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				System.out.println("2------延迟一秒执行，每三秒执行一次");
				System.out.println(System.currentTimeMillis());
			}
		}, 1, 3, TimeUnit.SECONDS);
//		scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
//			@Override
//			public void run() {
//				System.out.println("3-------延迟一秒执行，每三秒执行一次");
//				System.out.println(System.currentTimeMillis());
//			}
//		}, 1, 3, TimeUnit.SECONDS);
//		scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
//			@Override
//			public void run() {
//				System.out.println("4--------延迟一秒执行，每三秒执行一次");
//				System.out.println(System.currentTimeMillis());
//			}
//		}, 1, 3, TimeUnit.SECONDS);
		System.in.read();
		scheduledExecutorService.shutdown();
		System.in.read();
	}
}
