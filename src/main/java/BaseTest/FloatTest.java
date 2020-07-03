package BaseTest;

import java.math.BigDecimal;

public class FloatTest {
	public static void main(String[] args) {
		// BigDecimalTest();
	}

	public static void BigDecimalTest() {
		BigDecimal a = new BigDecimal("1.0");
		BigDecimal b = new BigDecimal("0.9");
		BigDecimal c = new BigDecimal("0.8");
		BigDecimal x = a.subtract(b);
		BigDecimal y = b.subtract(c);
		if (x.equals(y)) {
			System.out.println("true");
		}

		BigDecimal g1 = new BigDecimal("0.1");// 推荐方式
		BigDecimal g2 = new BigDecimal(0.1f);// 会丢失精度
		System.out.println(g1);// 0.1
		System.out.println(g2);// 0.100000001490116119384765625

	}

}
