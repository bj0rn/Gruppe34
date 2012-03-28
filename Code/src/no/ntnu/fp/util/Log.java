package no.ntnu.fp.util;

public class Log {

	public static void out(String msg) {
		StackTraceElement stackTraceElement = new Exception().getStackTrace()[1];
		
		String className = stackTraceElement.getClassName();
		String methodName = stackTraceElement.getMethodName();
		int linenumber = stackTraceElement.getLineNumber();
		
		
		System.out.println(className + " " + methodName + "(" + linenumber + ")" + ": " + msg);
	}
	
	public static void main(String[] args) {
		
		Log.out("hei");
		
	}
	
}
