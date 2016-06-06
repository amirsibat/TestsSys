package testsys.utils;

public class L {
	public static void log(String text){
		System.out.println(text);
	}
	
	public static void err(Exception e){
		e.printStackTrace();
	}
	
	public static void err(String text){
		System.err.println(text);
	}
}
