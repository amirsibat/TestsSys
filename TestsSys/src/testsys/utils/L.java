package testsys.utils;

public class L {
	public static void log(String text){
		System.out.println("LOG: " + text);
	}
	
	public static void err(Exception e){
		System.err.println("---------------------------------------------");
		e.printStackTrace();
		System.err.println("---------------------------------------------");
	}
	
	public static void err(String text){
		System.err.println("---------------------------------------------");
		System.err.println("ERROR: " + text);
		System.err.println("---------------------------------------------");
	}
}
