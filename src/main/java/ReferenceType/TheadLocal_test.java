package ReferenceType;

import java.util.concurrent.TimeUnit;

public class TheadLocal_test {
	static ThreadLocal<Person> tl = new ThreadLocal<>();
	
	public static void main(String[] args) {
		new Thread(()->{
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//	System.out.println(p.name);
		}).start();
		
		new Thread(()->{
			tl.set(new Person());			
		}).start();
		
	}
	
}

class Person{
	String name = "3";
}
