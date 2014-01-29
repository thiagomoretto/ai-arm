package ia.searchs;

import ia.State;
import ia.Strategy;
import mjr.heap.Heap;
import mjr.heap.Heapable;

public abstract class BestFirstStrategy implements Strategy {

	private Heap heap;
	
	public BestFirstStrategy()
	{
		heap = new Heap(false);
	}
	
	public BestFirstStrategy(Heap heap) {
		this.heap = heap;
	}
	
	public boolean expandable(State state) {
		return ! heap.isEmpty();
	}

	public State next() {
		return (State) heap.remove();
	}

	public void add(State state) {
		heap.insert((Heapable) state);
	}

	public abstract void expand(State state);
}
