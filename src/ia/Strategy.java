package ia;


public interface Strategy {

	public boolean expandable(State state);

	public void expand(State state);

	public void add(State state);
	
	public State next();

}
