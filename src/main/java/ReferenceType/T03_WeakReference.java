package ReferenceType;

import java.lang.ref.WeakReference;
//弱引用 用在Threadlocal 解决某些地方的内存泄露
public class T03_WeakReference {
	
	public static void main(String[] args) {
		WeakReference <M> m = new WeakReference<> (new M());
		
		System.out.println(m.get());
		System.gc();
		
		System.out.println(m.get());
		
		ThreadLocal <M> tl =new ThreadLocal<>();
		tl.set(new M());
		tl.remove();
		
	}
	
}
