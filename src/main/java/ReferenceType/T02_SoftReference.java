package ReferenceType;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

//软引用
public class T02_SoftReference {
	public static void main(String[] args) {
		test2();
	}
	public static void test1() {
		SoftReference<byte[]> sr = new SoftReference<>(new byte[1024*1024*10]);		
		System.out.println(sr.get());		
		System.gc();//此处即使GC也不会回收		
		System.out.println(sr.get());
		
		byte[] A = sr.get();
		
		//强引用申请15MB内存 ；如果heap内存不够用，先回收一次；如果还是不够用会把上面软引用申请的10MB回收
		byte[] k = new byte[1024*1024*10]; 
		System.out.println(sr.get());	
		
	}
	
	public static void test2() {
		//可以将回收后的软引用中指向的对象放入如下队列
		ReferenceQueue queue = new  ReferenceQueue();  
		SoftReference<byte[]> sr1 = new SoftReference<>(new byte[1024*1024*10],queue);
		SoftReference<byte[]> sr2 = new SoftReference<>(new byte[1024*1024*10],queue);
		SoftReference<byte[]> sr3 = new SoftReference<>(new byte[1024*1024*10],queue);
		SoftReference<byte[]> sr4 = new SoftReference<>(new byte[1024*1024*10],queue);		
		
		System.out.println(sr1);
		System.out.println(sr2);
		System.out.println(sr3);
		System.out.println(sr4);
		byte[] k = new byte[1024*1024*10]; 
		
		Reference sr;
		while((sr = queue.poll()) != null) {
			System.out.println(sr);//此处是已经被回收的对象
			System.out.println(sr.get());
		}
	}
	
	
	
	
	
	
}
