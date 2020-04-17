package Parms;

public class parms {
	public static void main(String[] args) {
		ParmsClass a = new ParmsClass();
		a.setAge(10);
		a.setName("as");
		testParms(a);
	}
	
	
	
	public static void testParms(ParmsClass a) {
		System.out.println(a.toString());
	}
	
}
