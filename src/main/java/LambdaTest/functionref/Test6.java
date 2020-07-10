package LambdaTest.functionref;

import java.util.function.Consumer;

public class Test6 {
	int size = 10;

	public Test6(int size) {
		this.size = size;
	}

	public void cs(Integer size) {
		this.size = size;
		System.out.println(this.size);
	}

	public static void main(String[] args) {
		Test6 ttt = new Test6(19);
		Consumer<Integer> cs = ttt::cs;
		cs.accept(3);

		System.out.println(ttt.size);
	}
}
