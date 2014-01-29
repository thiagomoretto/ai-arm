package ia.searchs;

import java.util.LinkedList;
import java.util.Queue;

import ia.State;
import ia.Strategy;

public abstract class QueueStrategy implements Strategy {

	private Queue<State> queue = new LinkedList<State> ();
	
	public boolean expandable(State state) {
		return ! queue.isEmpty();
	}

	public State next() {
		return queue.poll();
	}
	
	public void add(State state) {
		queue.add(state);
	}

	public abstract void expand(State state);
}
