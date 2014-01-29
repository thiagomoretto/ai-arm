package ia.braco;

import ia.Action;
import ia.Heuristic;
import ia.Search;
import ia.State;
import ia.Strategy;
import ia.gui3d.IO;
import ia.searchs.GeneralSearch;
import ia.searchs.Gradient;
import ia.searchs.GradientSteepest;
import ia.searchs.SimulatedAnnealing;
import ia.searchs.SimulatedAnnealingTemperatureInTime;

import javax.vecmath.Vector3f;

public class BracoRunner {

	// TODO: Refazer.
	public static double global_threshold = 0.15;
	
	public static double global_angle = 10.0;
	
	public static int global_sleep = 200;
	
	private Action action;
	
	private Heuristic heuristic;
	
	private State goal, start, end;
	
	private Search searcher;
	
	private long start_time, time_ellapsed;
		
	public BracoRunner(BracoWrapper wrapper)
	{
		// Realiza movimentos para o Java 3D
		// atualizar suas coordernadas globais.
		wrapper.anteBracoDireito(1); 
		wrapper.anteBracoDireito(-1); 
		
		Vector3f mao3f, alvo3f;
		mao3f = wrapper.getPosicaoMao();
		
		action = new ActionExecutor(wrapper);
		
		heuristic = new BracoEuclideanDistance();
		alvo3f = wrapper.getPosicaoAlvo();
		
		// goal = new BracoState(wrapper, heuristic, null, -0.57, 0.28, 0.42);
		goal = new BracoState(wrapper, heuristic, null, alvo3f.x, alvo3f.y, alvo3f.z);
		start = new BracoState(wrapper, heuristic, null, mao3f.x, mao3f.y, mao3f.z);
		
		start.distance(heuristic.cost(start, goal));
		IO.println("Start state: " + start);
	}
	
	private void search() {
		start_time = System.currentTimeMillis();
		end = searcher.search();
		time_ellapsed = System.currentTimeMillis() - start_time;
	}
	
	public void showResults() {
		IO.printf("# Search end. Time ellapsed %dms\n", time_ellapsed);
		IO.printf("# Final state: %s\n", end);
		IO.printf("# Total steps (visited states): %d\n", searcher.steps());
	}
	
	public void searchAstar(int limit, boolean repeat) {
		Strategy strategy = new SearchAStar(goal, action);
		searcher = new GeneralSearch(start, goal, strategy, limit, repeat);
		search();
	}
	
	public void searchBreadth(int limit, boolean repeat) {
		Strategy strategy = new SearchBreadth(goal, action);
		searcher = new GeneralSearch(start, goal, strategy, limit, repeat);
		search();
	}
	
	public void searchDepth(int limit, boolean repeat) {
		Strategy strategy = new SearchDepth(goal, action);
		searcher = new GeneralSearch(start, goal, strategy, limit, repeat);
		end = searcher.search();
	}
	
	public void searchGreedy(int limit, boolean repeat) {
		Strategy strategy = new SearchGreedy(goal, action);
		searcher = new GeneralSearch(start, goal, strategy, limit, repeat);
		search();
	}
	
	public void searchBeam(int limit, boolean repeat, int beamWidth) {
		Strategy strategy = new SearchBeam(goal, action, beamWidth);
		searcher = new GeneralSearch(start, goal, strategy, limit, repeat);
		search();
	}
	
	public void searchSimulatedAnnealing(SimulatedAnnealingTemperatureInTime tFunction, double initTemp, double bolztman) {
		searcher = new SimulatedAnnealing(start, goal, tFunction, action, bolztman, initTemp);
		search();
	}
	
	public void searchGradient(int maxSteps) {
		searcher  = new Gradient(start, goal, heuristic, action, maxSteps);
		search();
	}
	
	public void searchGradientSteepest(int maxSteps) {
		searcher  = new GradientSteepest(start, goal, heuristic, action, maxSteps);
		search();
	}
	
	public State getEndState() {
		return end;
	}
}
