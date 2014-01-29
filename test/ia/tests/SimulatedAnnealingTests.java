package ia.tests;

import static org.junit.Assert.assertTrue;
import ia.State;
import ia.searchs.SimulatedAnnealing;
import ia.searchs.basic.TestSAState;
import ia.searchs.basic.TestSATemperature;

import org.junit.Test;

public class SimulatedAnnealingTests {

	@Test
	public void testSimulatedAnnealing() {
		TestSAState start;
		start = new TestSAState(20, null);

		SimulatedAnnealing search = new SimulatedAnnealing(start, new TestSATemperature());
		State f;

		long startt, end;
		startt = System.currentTimeMillis();
		f = search.search();
		end	= System.currentTimeMillis();
		System.out.printf("### testSimulatedAnnealing: %d ms\n",end-startt);

		assertTrue(f!=null);

		System.out.printf("-- Melhor encontrado pelo SA\n(x,y) = %s\n",f);
	}
}
