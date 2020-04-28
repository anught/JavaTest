package ClassTest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ClassTest {
	public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		
		Class tc = Class.forName("ClassTest.Test",true, null);
		
		Method m1 = tc.getMethod("print");
		m1.invoke(tc.newInstance());
		
		Method m2 = tc.getMethod("printParms",String.class);
		m2.invoke(tc.newInstance(),"ceshi input");
//		
		Method m3 = tc.getMethod("returnIn",String.class);
		Object kk = m3.invoke(tc.newInstance(),"haha input");
		System.out.println(kk.toString());
		
//		Test t = new Test();
//		t.print();
	}
}
