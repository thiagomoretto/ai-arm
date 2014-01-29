package ia.searchs;

import ia.State;
import ia.Strategy;

import java.util.Stack;

public abstract class StackStrategy implements Strategy {

	private Stack<State> stack = new Stack<State>();
	
	public boolean expandable(State state) {
		return ! stack.empty();
	}

	public State next() {
		return stack.pop();
	}
	
	public void add(State state) {
		stack.push(state);
	}
	
	public abstract void expand(State state);
}
