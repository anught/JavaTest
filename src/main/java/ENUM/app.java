package ENUM;

import ENUM.ENUM.Signal2;

public class app {	
	public static void main(String [] a) {
		Signal2 color = Signal2.RED;
		change(color);
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
