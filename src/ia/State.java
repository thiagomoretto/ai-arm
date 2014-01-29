package ia;

import java.util.List;

public interface State {

	public boolean equals(State state);
	
	public double depth();
	
	public State parent();

	public void parent(State state);
	
	public double distance();
	
	public void distance(double distance);

	public double cost();
	
	public void cost(double cost);
	
	public Object action();
	
	public List<State> produce( State goal , boolean preserveParent );
}
