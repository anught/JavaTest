package ENUM;

import ENUM.ENUM.Signal2;

public class app {
	public static void main(String[] a) {
		// Signal2 color = Signal2.RED;
		// change(color);

		testEnum2();
	}

	public static void testEnum2() {
		ENUM2 E2 = ENUM2.RED;
		System.out.println(E2);
		System.out.println(E2.getName());
	}

	public static void change(Signal2 color) {

		switch (color) {
		case RED:
			color = Signal2.GREEN;
			break;
		case GREEN:
			color = Signal2.RED;
			break;
		case YELLOW:
			color = Signal2.YELLOW;
			break;
		}
	}

}
