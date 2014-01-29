package ia.braco;

import ia.Action;
import ia.State;
import ia.searchs.BestFirstStrategy;

import java.util.List;

import mjr.heap.Heap;
import mjr.heap.Heapable;

public class SearchBeam extends BestFirstStrategy {
	
	private State goal;

	private Action action;
	
	private int beamWidth;
	
	public SearchBeam(State goal, Action action, int beamWidth)
	{
		this.goal = goal;
		this.action = action;
		this.beamWidth = beamWidth;
	}
	
	public void expand(State incoming) 
	{
		BracoState current = (BracoState ) incoming;
		
		if (action != null) {
			action.execute(current);
		}
		
		List<State> newStates = current.produce(goal, true);
		
		Heap tempHeap = new Heap(false);
		
		// TODO: Otimizar.
		for (State state : newStates) 
		{
			state.cost(state.distance());
			tempHeap.insert((Heapable) state);
		}
		
		for(int i=0; i<beamWidth && !tempHeap.isEmpty(); i++) 
		{
			add((State) tempHeap.remove());
		}
	}
}