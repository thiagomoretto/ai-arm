package ia.searchs;

import ia.Action;
import ia.Search;
import ia.State;
import ia.gui3d.IO;

import java.util.List;
import java.util.Random;

public class SimulatedAnnealing implements Search {

	private State start;

	private SimulatedAnnealingTemperatureInTime temperatureInTime;

	private double boltzman = 200f;
	
	private State goal;

	private Action action;

	private int steps = 0, badsteps = 0;
	
	public SimulatedAnnealing(
			State start, State goal, SimulatedAnnealingTemperatureInTime temperatureInTime,Action action)
	{
		this.start = start;
		this.goal = goal;
		this.temperatureInTime = temperatureInTime;
		this.action = action;
		temperatureInTime.initialTemperature(100);
	}
	
	public SimulatedAnnealing(
			State start, State goal, SimulatedAnnealingTemperatureInTime temperatureInTime, Action action,
			double boltzman, double ti)
	{
		this(start, goal, temperatureInTime, action);
		this.boltzman = boltzman;
		temperatureInTime.initialTemperature(ti);
	}

	public State search()
	{
		Random generator = new Random();

		State current = start;
		State next, best;
		double temperature = 0.0d;
		int time = 0;
		double dE = 0, probality;

		List<State> states;

		best = current;
		int i = 1;

		while (true) 
		{
			
			temperature = temperatureInTime.getTemperature(time);
			time++;
						
			if(temperature == 0) {
				IO.println("# SA: Temperature = 0");
				break;
			}
			
			steps ++;
				
			states = current.produce(goal, false);
			next = (State) states.toArray()[generator.nextInt(states.size())];
			
			dE = current.distance() - next.distance();
			
			if(dE > 0) {
				current = next;
				if (action != null) {
					action.execute(current);
				}
				i++;
			} else {
				// De acordo com uma probalidade
				// eu escolho ou nao o proximo.
				// t = T(i)
				// p = exp(-((abs(dX)/k*t)))
				// if(rand(1)<=p)
				//	 estadoAtual = proximoEstado
				// end
				probality = Math.exp(-(Math.abs(dE)/boltzman*temperature));
				double gen = generator.nextDouble() ;

				if(gen > probality) {
					badsteps++;
					current = next;
					if (action != null) {
						action.execute(current);
					}
					i++;
				}
			}
			
			// Atualiza o melhor
			if (best.distance() < current.distance()) {
				best = current;
			}
			
			// Coloquei isto já que em algumas
			// funções de temperatura nunca 
			// chegam a zero.
			// E também, para utilizar o limiar
			// já que o mesmo já satisfaz.
			// Caso nao queria utilizar este,
			// posso utilizar um limiar negativo.
			if (goal.equals(current)) {
				break;
			}
		}

		IO.println("# SA: Bad step count: "+ badsteps);
		IO.println("# SA: Best state: "+ best);
		return current;
	}
	
	public int steps()
	{
		return steps;
	}
}
