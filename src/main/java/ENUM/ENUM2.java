package ENUM;

public enum ENUM2 {
	RED("red"), BLUE("blue"), YELLOW("yellow");
	private final String name;

	// 将构造器私有化，使构造器不能在类的外部被使用
	private ENUM2(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
}
