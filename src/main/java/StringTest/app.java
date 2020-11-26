package StringTest;

import java.util.concurrent.atomic.AtomicInteger;

public class app {
	public static void main(String[] args) {
		testBit();
	}

	public static void testCLC() {
		String s = "aaa";
		String str3 = new String("aaa");
		String str4 = new String("aaa");
		System.out.println(str3 == str4);

	}

	private static final int COUNT_BITS = Integer.SIZE - 3;
	private static final int CAPACITY = (1 << COUNT_BITS) - 1;

	// runState is stored in the high-order bits
	private static final int RUNNING = -1 << COUNT_BITS;
	private static final int SHUTDOWN = 0 << COUNT_BITS;
	private static final int STOP = 1 << COUNT_BITS;
	private static final int TIDYING = 2 << COUNT_BITS;
	private static final int TERMINATED = 3 << COUNT_BITS;
	private static AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));

	// Packing and unpacking ctl
	private static int runStateOf(int c) {
		return c & ~CAPACITY;
	}

	private static int workerCountOf(int c) {
		return c & CAPACITY;
	}

	private static int ctlOf(int rs, int wc) {
		return rs | wc;
	}

	public static void testBit() {
//		System.out.println(Integer.toBinaryString(CAPACITY));
//		System.out.println(Integer.toBinaryString(RUNNING));
//		System.out.println(Integer.toBinaryString(SHUTDOWN));
//		System.out.println(Integer.toBinaryString(STOP));
//		System.out.println(Integer.toBinaryString(TIDYING));
//		System.out.println(Integer.toBinaryString(TERMINATED));
//
//		System.out.println(Integer.toBinaryString(ctl.get()));

		System.out.println(Integer.toBinaryString(9));
		System.out.println(Integer.toBinaryString(~9));
		System.out.println(Integer.toBinaryString(~9 + 1));
	}

}
