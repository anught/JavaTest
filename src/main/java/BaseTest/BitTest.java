package BaseTest;

public class BitTest {

	static int MAXIMUM_CAPACITY = 1 << 30;

	// 假如我们要得到一个数的下一个2的n次幂，那么需要将这个数转换成2进制之后，
	// 把它的所有位上的数值都变成1，最后加1，就能够得到这个数的下一个2的n次幂了
	static int tableSizeFor8(int cap) {// 在使用 hashmap 初始化长度时使用
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

	static int tableSizeFor7(int cap) {
		int res = 2;
		while (res < cap) {
			res = res << 1;
		}
		return res;
	}

	// 取余
	static void testQy() {
		int a = 100;
		int b = 8;

		// 当b为2的n次幂时 可以用 a & (b - 1) 来取余 可以用来程序中取余 建议配合tableSizeFor 使用

		System.out.println(100 % 16);
		System.out.println(100 & 15);

	}

	public static void main(String[] args) {
		System.out.println(tableSizeFor7(1000));
//		testQy();
	}
}
