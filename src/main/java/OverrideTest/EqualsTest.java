package OverrideTest;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * test2 ：
 * 如果不重写hashcod函数；在入hashset时会有异常；因为add时先判断hashcode，再判断equals，如果有hashcod不一致默认set中不包含可以直接入
 * test4 ： 如果不重写hashcod函数；在入hashmap时当重定义的类作为可以时会有异常；原因同上
 */
public class EqualsTest {

	public static void main(String[] args) {
		String a = "闫乐乐";

		System.out.println(a.length());
		try {
			System.out.println(a.getBytes("UTF-8").length);// 本工程是UTF8 所以默认可以不指定；但是建议指定
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		test1();
		test2();
		test3();
		test4();
	}

	public static void test1() {
		System.out.println("####:test1");
		List<Model> list = new ArrayList<Model>();

		Model a = new Model("a", "20", "test");
		Model b = new Model("a", "20", "abcdef");
		Model c = new Model("a", "20", "");

		list.add(a);
		System.out.println("a-hashCode:" + a.hashCode());
		System.out.println(list);
		if (!list.contains(b)) {
			list.add(b);
			System.out.println("b-hc:" + b.hashCode());
			System.out.println(list);
		}

	}

	public static void test2() {
		System.out.println("\n####:test2");
		Set<Model> set = new HashSet<Model>();

		Model a = new Model("a", "20", "test");
		Model b = new Model("a", "20", "abcdef");
		Model c = new Model("a", "21", "");

		set.add(a);
		System.out.println("a-hashCode:" + a.hashCode());
		set.add(b);

		set.add(c);

		System.out.println(set);
	}

	public static void test3() {
		System.out.println("\n####:test3");
		HashMap<String, Model> map1 = new HashMap<String, Model>();

		Model a = new Model("a", "20", "test");
		Model b = new Model("a", "20", "abcdef");

		map1.put("a", a);
		map1.put("b", b);

		System.out.println(map1);

		HashMap<String, Model> map2 = new HashMap<String, Model>();

		Model a1 = new Model("a", "20", "test");
		Model b1 = new Model("a", "20", "abcdef");

		map2.put("a", a1);
		map2.put("b", b1);

		System.out.println(map2);

		System.out.println(map1.equals(map2));
	}

	public static void test4() {
		System.out.println("\n####:test4");
		HashMap<Model, String> map1 = new HashMap<Model, String>();

		Model a = new Model("a", "20", "test");
		Model b = new Model("a", "20", "abcdef");
		Model c = new Model("a", "20", "abcdef");

		map1.put(a, "a");
		map1.put(b, "b");
		map1.put(c, "c");

		System.out.println(map1);

	}

}