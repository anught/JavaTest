package StringTest;

public class builerbuffer {
	public static void main(String[] args) {
		testStringbuffer();
	}

	public static void testStringbuffer() {
		StringBuffer sb = new StringBuffer(); // 线程安全的
		sb.append("2");
		sb.append("2");
		sb.append("2");
		sb.append("3");

		System.out.println(sb.capacity());
		String m = sb.toString();
		System.out.println(sb.length());
		System.out.println(m.length());
	}
}
