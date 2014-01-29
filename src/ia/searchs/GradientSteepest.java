package ia.searchs;

import ia.Action;
import ia.Heuristic;
import ia.Search;
import ia.State;
import ia.gui3d.IO;

import java.util.List;

public class GradientSteepest implements Search {

	private State start;

	private State goal;

	private Heuristic heuristic;

	private Action action;

	private int maxSteps;
	
	public GradientSteepest(
				State start,
				State goal,
				Heuristic heuristic, 
				Action action,
				int maxSteps)
	{
		this.start = start;
		this.goal = goal;
		this.heuristic = heuristic;
		this.action = action;
		this.maxSteps = maxSteps;
	}

	public State search()
	{
		State current = start;
		List<State> states;

		current.distance(heuristic.cost(current, goal));
		
		int i = 0;
		
		while (i < maxSteps) 
		{
			i++;

			states = current.produce(goal, false);

			// Ingrime/Steep
			State better = states.get(0);
			for (State state : states ) {
				if (state.distance() < better.distance()) {
					better =  state;
				}
			}

			current = better;
			
			if (action != null) {
				action.execute(current);
			}
		}
		
		IO.println("# GSD: End");
		
		return current;
	}
	
	public int steps()
	{
		return maxSteps;
	}
}
