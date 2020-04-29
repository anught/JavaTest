package ReferenceType;

import java.io.IOException;

public class T01_NomalReference {
	public static void main(String[] args) throws IOException {
		M m = new M();
		//对于强引用　如果想中断强引用和某个对象之间的关联，可以显示地将引用赋值为null，或者退出当前函数，
		//这样一来的话，JVM在合适的时间就会回收该对象。
		//否则永远也不会被回收
		m  = null;		
		System.gc();
		System.in.read();
	}
}
