package StringTest;

public class app {
	public static void main(String[] args) {
		testCLC();
	}

	public static void testCLC() {
		String s = "aaa";
		String str3 = new String("aaa");
		String str4 = new String("aaa");
		System.out.println(str3 == str4);

	}
}
