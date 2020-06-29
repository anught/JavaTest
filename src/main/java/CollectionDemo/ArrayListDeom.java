package CollectionDemo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

public class ArrayListDeom {
	public static void main(String[] args) {
		Collection c = new ArrayList();
		c.add(true);
		Object[] k = c.toArray();
		System.out.println((Boolean) k[0] == true);

		ArrayList lc = new ArrayList();
		lc.add("aa");

		System.out.println(lc.indexOf("a"));

		LinkedList ll = new LinkedList();

	}
}
