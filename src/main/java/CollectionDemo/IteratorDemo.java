package CollectionDemo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public class IteratorDemo {
	public static void main(String[] args) {
		ArrayList lc = new ArrayList();
		lc.add("aa");
		lc.add("ab");
		lc.add("ab");
		lc.add("ab");
		lc.add("ac");

//		for (int idx = 0; idx < lc.size(); idx++) {
//			System.out.println(lc.get(idx));
//		}

//		Iterator iterator = lc.iterator();
//		while (iterator.hasNext()) {
//			System.out.println(iterator.next());
//		}

		ListIterator llr = lc.listIterator();
		while (llr.hasNext()) {
			Object o = llr.next();
			if (o.equals("ab")) {
				llr.remove();
			}

		}
		System.out.println(lc);

		Object m = "";
		Object n = "";
		Object it = 1;
		System.out.println(it.getClass() == n.getClass());
		System.out.println(m.getClass() == n.getClass());

		System.out.println();
		for (Iterator iter = lc.iterator(); iter.hasNext();) {
			System.out.println(iter.next());
		}

		// 迭代器
		lc.iterator();
		lc.listIterator();

	}

}
