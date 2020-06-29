package Map;

public class TestMap {
	public static void main(String[] args) {
		printBit(3 * 16);
		printBit(3);
	}

	static final int MAXIMUM_CAPACITY = 1 << 30;

	// 假如我们要得到一个数的下一个2的n次幂，那么需要将这个数转换成2进制之后，
	// 把它的所有位上的数值都变成1，最后加1，就能够得到这个数的下一个2的n次幂了
	static final int tableSizeFor(int cap) {
		int n = cap - 1;
		// 把数无符号右移一位后，与原数按位或，这样可以保证这个数的最高位与次高位是1
		n |= n >>> 1;
		// 由于这个数的最高位与次高位已经是1了，所以这次向右移动两位并按位或，使前四位都是1
		n |= n >>> 2;
		// 使从最高位数，前八位变成1
		n |= n >>> 4;
		// 同理，使前十六位变成1
		n |= n >>> 8;
		// 因为int最高只有32位 ，所以到这里就可以确定这个数的所有位都是1了，
		n |= n >>> 16;
		// n+1 加一则所有1位向前进一位变成0，即是2的n次幂
		return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
	}

	public static void testBit() {
		int a = 10;

		a = a >> 1;
		printBit(a);

		int b = -2;
		System.out.println(b >> 1);
		printBit(b >> 1);

		System.out.println(b << 1);
		printBit(b << 1);
	}

	public static void printBit(int p) {
		System.out.println(Integer.toBinaryString(p));
	}

	public static void testFloatIsNaN() {
		Float f1 = new Float(-1.0 / 0.0);
		Float f11 = new Float(1.0 / 0.0);
		Float f2 = new Float(0.0 / 0.0);
		Float f3 = new Float(0.0 * 0.0);
		Float f4 = Float.POSITIVE_INFINITY - Float.NEGATIVE_INFINITY;
		Float f5 = Float.POSITIVE_INFINITY + Float.NEGATIVE_INFINITY;
		System.out.println(f1 + " = " + f1.isNaN());
		System.out.println(f11 + " = " + f11.isNaN());
		System.out.println(f2 + " = " + f2.isNaN());
		System.out.println(f3 + " = " + f3.isNaN());

		System.out.println(f4 + " = " + f4.isNaN());
		System.out.println(f5 + " = " + f5.isNaN());
	}
}
