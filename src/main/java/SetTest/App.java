package SetTest;

import java.util.TreeSet;

public class App {
	public static void main(String[] args) {
		TreeSet a = new TreeSet(new comClass());

		a.add(new Person("qwe", 1));
		a.add(new Person("qwee", 12));
		a.add(new Person("qweee", 123));
		a.add(new Person("qweeeee", 124));

		System.out.println(a);

		TreeSet ab = new TreeSet();
		ab.add("a");
		ab.add("ab");
		ab.add("ac");
		System.out.println(ab);
	}
}
