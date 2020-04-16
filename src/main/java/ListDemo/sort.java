package ListDemo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class sort {
	public static void main(String[] args)
	{
		
		class User{
			String name = null;
			int age=0;
			User(String name,int age){
				this.name = name;
				this.age =age;
			}
			@Override
			public String  toString() {
				return String.format("%s,%d", name,age);
			}
			
		}
		
		List<User> list = new ArrayList<User>();
		list.add(new User("20200405", 15));
		list.add(new User("20200402", 30));
		list.add(new User("20200407", 19));
		list.add(new User("20200401", 27));
//		Collections.sort(list, new Comparator<User>() {
//			@Override
//			public int compare(User u1, User u2) {
//				int diff = u1.age - u2.age;
//				if (diff > 0) {
//					return 1;
//				}
//				else if (diff < 0) 
//				{
//					return -1;
//				}
//				else {
//					return 0;//相等为0　}　　}); // 按年龄排序　　		System.out.println(list.toString());　　}}
//				}
//			}
//		});
		
		System.out.println(list.toString());
		
		Collections.sort(list, (v1, v2) -> (v1.name.compareTo(v2.name)));
		
		System.out.println(list.toString());
	}
}