package LambdaTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * @author: 马士兵教育
 * @create: 2019-10-19 15:11
 */
public class LambdaTest {

	public static void testHandInterface() {
		FunctionMutiParms fmp = (str1, str2) -> {
			return false;
		};

		System.out.println("fmp:" + fmp.isStrVaild("", ""));

	}

	public static void m1() {
		List<String> l = Arrays.asList("asdf111", "zfw2e", "22");
		Collections.sort(l, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				// TODO Auto-generated method stub
				return o1.length() - o2.length();
			}
		});
		l.forEach(System.out::println);

		Collections.sort(l, (a, b) -> b.length() - a.length());

		l.forEach(System.out::println);
	}

	public static void m2() {
		/*
		 * 在Java中提供了一系列的函数式接口，用来接受后续传入的逻辑，但是对输入和输出有要求
		 */

		// String 输入参数的类型 Integer 返回参数的类型 只支持一个输入一个输出
		Function<String, Integer> f1 = (str) -> {
			return str.length();
		};
		System.out.println(f1.apply("abcdefg"));

		Function<String, Boolean> f2 = (str) -> {
			return (str != null && str.length() > 0) ? true : false;
		};

		System.out.println("f2:" + f2.apply(""));

		// 只有输出
		Supplier<String> s1 = () -> {
			return "mashibing";
		};
		Supplier<String> s2 = () -> "mashibing2";
		System.out.println(s1.get());
		System.out.println(s2.get());

		// 只有输入
		Consumer<String> c11 = (str) -> System.out.println(str);
		c11.accept("beijing");

		System.out.println("---------------");
		BiConsumer<String, Integer> bc = (str, idx) -> System.out.println(str.length() + idx);
		bc.accept("a", 5);
		BiFunction<String, String, Integer> bf = (a, b) -> a.length() + b.length();
		System.out.println(bf.apply("a", "b"));

		Predicate<Integer> predicate = p -> p % 2 == 0;
		predicate.test(1000);

		Predicate<String> predicate1 = p -> p.length() > 0;
		try {
			System.out.println(predicate1.test("f"));
		} catch (Exception e) {
			// e.printStackTrace();
		}

		UnaryOperator<Integer> uf2 = (idx) -> idx += 1;
		uf2.apply(3);

		BiFunction<String, Integer, Boolean> vf = (str, idx) -> {
			if (str.length() == idx)
				return true;
			else
				return false;
		};

		BinaryOperator<Integer> bot = (A, B) -> {
			return A + B;
		};

	}

	public static void main(String[] args) throws Exception {
		Function<String, Boolean> f2 = (str) -> {
			return (str != null && str.length() > 0) ? true : false;
		};
		System.out.println("f2:" + f2.apply(null));
		// function();
		// testHandInterface();
		// m1();
		// System.in.read();
//		Runnable runnable = new Runnable() {
//			@Override
//			public void run() {
//				System.out.println("running1 .....");
//			}
//		};
//		runnable.run();
//
//        Runnable runnable2 = ()->{
//            System.out.println("running2....");
//        };
//        runnable2.run();
//
//        Runnable runnable3 = ()-> System.out.println("running3....");
//        runnable3.run();
//
//		Callable<String> c1 = new Callable() {
//			@Override
//			public String call() throws Exception {
//				return "mashibing";
//			}
//		};
//		System.out.println(c1.call());
//
//        Callable<String> c2 = ()->{return "mashibing2";};
//        System.out.println(c2.call());
//
//        Callable<String> c3 = ()->"mashibing3";
//        System.out.println(c3.call());
//
		StudentDao sd1 = new StudentDao() {
			@Override
			public void insert(Student student) {
				System.out.println("插入学生1");
			}
		};
		sd1.insert(new Student());

		StudentDao sd2 = (student) -> {
			System.out.println("student: " + student);
		};
		sd2.insert(new Student());

		StudentDao sd3 = (Student student) -> System.out.println("student3:" + student);
		sd3.insert(new Student());
//
//        TeacherDao td1 = new TeacherDao() {
//            @Override
//            public int get(Teacher teacher) {
//                return 1;
//            }
//        };
//        TeacherDao td2 = (teacher)->{return 2;};
//        TeacherDao td3 = (Teacher teacher)->{return 3;};
//        TeacherDao td4 = (teacher)->4;
//        TeacherDao td5 = (Teacher teacher)->5;
//
//        System.out.println(td1.get(new Teacher()));
//        System.out.println(td2.get(new Teacher()));
//        System.out.println(td3.get(new Teacher()));
//        System.out.println(td4.get(new Teacher()));
//        System.out.println(td5.get(new Teacher()));

//        Runnable runnable1 = ()->get();
//        Runnable runnable2 = ()->exec();
//        Runnable runnable3 = ()->100;
//        Runnable runnable4 = ()->"";
//        runnable1.run();

		LamabdInterface li1 = () -> get();
//        LamabdInterface li2 = ()->find();
		LamabdInterface li3 = () -> 100;
//        LamabdInterface li4 = ()->"abc";
		LamabdInterface li5 = () -> true ? 1 : 0;
		System.out.println(li1.get());

		List<String> list = Arrays.asList("a", "b", "c");
//        for (String s : list) {
//            System.out.println(s);
//        }
		list.forEach(System.out::println);
	}

	static int get() {
		return 1;
	}

	static String find() {
		return "find";
	}

	static void exec() {
		find();
	}
}