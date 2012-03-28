package no.ntnu.fp.util;

public class Log {

	public static void out(Object... msg) {
		StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];
		
		String className = stackTraceElement.getClassName();
		String methodName = stackTraceElement.getMethodName();
		int linenumber = stackTraceElement.getLineNumber();
		
		
		System.out.print(className + " " + methodName + "(" + linenumber + ")" + ": ");
		
		for(Object obj : msg) {
			System.out.print(obj.toString() + ", ");
		}
		System.out.println();
		
	}
	
	public static void main(String[] args) {
		
		Log.out("hei");
		
	}
	
}
