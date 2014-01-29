package ia.gui3d;

public enum Algorithm {
	DEPTH_FIRST,
	BREADTH_FIRST,
	ASTAR,
	GREEDY,
	BEAM_SEARCH,
	SIMULATED_ANNEALING,
	GRADIENT_DESCENT,
	GRADIENT_STEEPEST_DESCENT;
	
	private int limit = 0;
	
	private boolean repeat = false;
	
	private Algorithm()
	{
		limit = 0;
		repeat = false;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public boolean isRepeat() {
		return repeat;
	}

	public void setRepeat(boolean repeat) {
		this.repeat = repeat;
	}
}
