package SerializeDemo;

import java.io.Serializable;

public class FlyPig implements Serializable{
	//private static final long serialVersionUID = -2236182038380256247L; 
	
	private static String Age = "18";
	private String name = "";
	transient private String car;
	public static String getAge() {
		return Age;
	}
	public static void setAge(String age) {
		Age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCar() {
		return car;
	}

	
	
}
