package CollectionDemo;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class SetDemo {

	public static void hashSetTest() {
		Set s = new HashSet();
		s.add(1);
		s.add(2);
		s.contains(3);
		System.out.println(s.add(3));
		for (Iterator iter = s.iterator(); iter.hasNext();) {
			System.out.println(iter.next());
		}

	}

	public static void treeSetTest() {
		TreeSet<Integer> s = new TreeSet<Integer>();
		s.add(19);
		s.add(2);
		s.add(3);
		s.add(5);
		s.add(100);

		for (Iterator iter = s.iterator(); iter.hasNext();) {
			System.out.println(iter.next());
		}
		System.out.println(s.ceiling(4));

	}

	public static void main(String[] args) {
		treeSetTest();

	}
}
