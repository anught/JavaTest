package ThreadTest.ThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/*
 * 相当于：
 * new ThreadPoolExecutor(1, 1,0L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>())
 * */

public class SingleThreadPoolDemo {
	public static void main(String[] args) {
		ExecutorService executorService = Executors.newSingleThreadExecutor();

		for (int i = 0; i < 20; i++) {
			executorService.execute(new Runnable() {
				@Override
				public void run() {
					System.out.println("延迟三秒执行:" + i);
					System.out.println(System.currentTimeMillis());
				}

				public int i = 0;

				public Runnable setIdx(int i) {
					this.i = i;
					return this;
				}
			}.setIdx(i)

			);
		}
		executorService.shutdown();
	}
}
