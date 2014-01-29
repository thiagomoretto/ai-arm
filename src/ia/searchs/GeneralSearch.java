package ia.searchs;

import ia.Search;
import ia.State;
import ia.Strategy;
import ia.gui3d.IO;

public class GeneralSearch implements Search {

	public static final boolean SUCCESS = true, FAIL = true;

	private Strategy strategy;

	private State goalstate, startstate;

	private int depthlimit = -1;
	
	private boolean restart;
	
	private int steps = 0;
	
	public GeneralSearch(
			State startstate, 
			State goalstate, 
			Strategy strategy)
	{
		this.strategy = strategy;
		this.startstate = startstate;
		this.goalstate = goalstate;
	}
	
	public GeneralSearch(
			State startstate, 
			State goalstate, 
			Strategy strategy,
			int depthlimit,
			boolean restart)
	{
		this(startstate, goalstate, strategy);
		this.depthlimit = depthlimit;
		this.restart = restart;
	}
	
	public State search()
	{
		State result = doSearch();
		if (result == null && restart && depthlimit > 0) {
			do {
				depthlimit ++;
				result = doSearch();
			} while (result != null);
		}
		return result;
	}

	private State doSearch()
	{
		State current;
		current = startstate;

		strategy.expand(current);

		for(;;) {
			steps++;
			
			IO.println(current);
			
			if (current.depth() > depthlimit*10 && depthlimit > 0) {
				return null;
			}
			
			if(!strategy.expandable(current))
				return null;

			current = strategy.next();
			if (goalstate.equals(current))
				return current;

			strategy.expand(current);
		}
	}
	
	public int steps()
	{
		return steps;
	}
}
