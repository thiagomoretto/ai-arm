package ia.braco;

import ia.Action;
import ia.State;
import ia.searchs.BestFirstStrategy;

import java.util.List;

public class SearchGreedy extends BestFirstStrategy {
	
	private State goal;

	private Action action;
	
	public SearchGreedy(State goal, Action action)
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
			// Meu custo é a distancia.
			state.cost(state.distance());
			add(state);
		}
	}
}