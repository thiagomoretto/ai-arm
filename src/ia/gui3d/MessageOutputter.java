package ia.gui3d;

public interface MessageOutputter 
{
	public void printf(String fmt, Object ... args);
	
	public void print(Object msg);
	
	public void println(Object msg);
}
