package ia.braco;

import ia.Heuristic;
import ia.State;

public class BracoEuclideanDistance implements Heuristic {

	public double cost(State state, State goal) {
		BracoState b1, b2;
		b1 = (BracoState)state;
		b2 = (BracoState)goal;
		
		return 	Math.sqrt(	Math.pow(b1.getX() - b2.getX(), 2) +
							Math.pow(b1.getY() - b2.getY(), 2) +
							Math.pow(b1.getZ() - b2.getZ(), 2));
	}

}
