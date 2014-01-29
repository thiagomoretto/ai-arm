package ia.braco;

import ia.Action;
import ia.State;
import ia.searchs.QueueStrategy;

import java.util.List;

public class SearchBreadth extends QueueStrategy {

	private State goal;

	private Action action;
	
	public SearchBreadth(State goal, Action action)
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