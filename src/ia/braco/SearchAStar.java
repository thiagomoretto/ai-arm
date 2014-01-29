package ia.braco;

import ia.Action;
import ia.State;
import ia.searchs.BestFirstStrategy;

import java.util.List;

public class SearchAStar extends BestFirstStrategy {
	
	private State goal;

	private Action action;
	
	public SearchAStar(State goal, Action action)
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
			// Heaping
			// No caso do A*, considero o custo já percorrido.
			// (Profundidade)
			state.cost(state.depth() + state.distance());
			add(state);
		}
	}
}