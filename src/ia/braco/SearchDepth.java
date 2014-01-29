package ia.braco;

import java.util.List;

import ia.Action;
import ia.State;
import ia.searchs.StackStrategy;

public class SearchDepth  extends StackStrategy {

	private State goal;

	private Action action;
	
	public SearchDepth(State goal, Action action)
	{
		this.goal = goal;
		this.action = action;
	}
	
	public void expand(State incoming) 
	{
		BracoState current = (BracoState ) incoming;
		
		if (action != null) {
			action.execute(current);
		}
		
		List<State> newStates = current.produce(goal, true);
		
		for (State state : newStates) 
		{
			add(state);
		}
	}
}
