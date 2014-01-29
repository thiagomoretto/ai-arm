package ia.gui3d;

public class IO {

	private static boolean enabled = true;
	
	private static MessageOutputter defaultOutputter = new SystemOutputter();

	public static void enabled(boolean toggled) {
		enabled = toggled;
	}

	public static void setDefaultMessageOutput( MessageOutputter outputter ) {
		if(enabled)
			defaultOutputter = outputter;
	}
	
	public static void printf(String fmt, Object ... args) {
		if(enabled)
			defaultOutputter.printf(fmt, args);
	}
	
	public static void print(Object msg) {
		if(enabled)
			defaultOutputter.print(msg);
	}
	
	public static void println(Object msg) {
		if(enabled)
			defaultOutputter.println(msg);
	}
}
