package ia.gui3d;

public class SystemOutputter implements MessageOutputter 
{
	public void print(Object msg) {
		System.out.print(msg);
	}

	public void printf(String fmt, Object... args) {
		System.out.printf(fmt, args);
	}

	public void println(Object msg) {
		System.out.println(msg);		
	}
}
