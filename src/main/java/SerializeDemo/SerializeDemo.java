package SerializeDemo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializeDemo {
	public static void main(String[] args) throws Exception {
		//serialFlyPig();
		deserialFlyPig();
	}
	
	public static void serialFlyPig() throws IOException {
		FlyPig fp = new FlyPig();
		//fp.setCar("qq");
		fp.setName("da");
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("serial")));
		oos.writeObject(fp);
		oos.close();
		System.out.println("序列化成功");		
	}
	public static void deserialFlyPig() throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("serial")));
		FlyPig pig = (FlyPig)ois.readObject();
		System.out.println(pig.getCar());
		System.out.println(pig.getName());
		System.out.println(pig.getAge());
	}
}
