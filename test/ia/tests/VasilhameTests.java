package ia.tests;

import static org.junit.Assert.assertTrue;
import ia.Search;
import ia.State;
import ia.Strategy;
import ia.searchs.GeneralSearch;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import org.junit.Before;
import org.junit.Test;

public class VasilhameTests {

	public enum Move {
		ENCHE_A,
		ENCHE_B,
		ESVAZIA_A,
		ESVAZIA_B,
		ENCHE_A_USANDO_B,
		ENCHE_B_USANDO_A
	}

	public class VasilhameState implements State
	{
		private int vasilhamea = 0, vasilhameb = 0;

		private State parent;

		private Move move;

		public double depth = 0;
		
		public VasilhameState(int vasilhamea, int vasilhameb, State parent, Move move) {
			this.vasilhamea = vasilhamea;
			this.vasilhameb = vasilhameb;
			this.parent = parent;
			this.move = move;
			if (parent != null) {
				depth = parent.depth() + 1;
			}
		}

		public int getVasilhamea() {
			return vasilhamea;
		}

		public int getVasilhameb() {
			return vasilhameb;
		}

		public boolean equals(State state) {
			VasilhameState local = (VasilhameState) state;
			return (local.getVasilhamea() == getVasilhamea());
		}

		public Move getMove() {
			return move;
		}

		public State parent() {
			return parent;
		}

		public String toString() {
			return String.format("(%d,%d,%s) parent:{%s}", getVasilhamea(), getVasilhameb(), getMove(), parent());
		}

		public double depth() {
			return depth;
		}
	}

	// Busca em largura
	public class VasilhameStrategyQueue implements Strategy
	{
		private Queue<State> queue = new LinkedList<State> ();

		public VasilhameStrategyQueue() {
		}

		public State next() {
			return queue.poll();
		}

		public void clean() {
			queue.clear();
		}

		public void expand(State state)
		{
			VasilhameState local = (VasilhameState) state;

			for (Move move : Move.values())
			{
				switch(move) {
					case ENCHE_A:
						if (local.getMove() != Move.ENCHE_A  && local.getVasilhamea() != 4 )
							queue.add(enchea(local)); break;
					case ENCHE_B:
						if (local.getMove() != Move.ENCHE_B  && local.getVasilhameb() != 3 )
							queue.add(encheb(local)); break;
					case ESVAZIA_A:
						if (local.getMove() != Move.ESVAZIA_A && local.getVasilhamea() > 0)
							queue.add(esvaziaa(local));
						break;
					case ESVAZIA_B:
						if (local.getMove() != Move.ESVAZIA_B && local.getVasilhameb() > 0)
							queue.add(esvaziab(local)); break;
					case ENCHE_A_USANDO_B:
						if (local.getMove() != Move.ENCHE_A_USANDO_B  && local.getVasilhameb() > 0)
							queue.add(encheausandob(local)); break;
					case ENCHE_B_USANDO_A:
						if (local.getMove() != Move.ENCHE_B_USANDO_A  && local.getVasilhamea() > 0)
							queue.add(enchebusandoa(local)); break;
				}
			}
		}

		public boolean expandable(State state) {
			return !queue.isEmpty();
		}

		public State enchea(VasilhameState state) {
			return new VasilhameState(4, state.getVasilhameb(), state, Move.ENCHE_A);
		}

		public State encheb(VasilhameState state) {
			return new VasilhameState(state.getVasilhamea(), 3, state, Move.ENCHE_B);
		}

		public State esvaziaa(VasilhameState state) {
			return new VasilhameState(0, state.getVasilhameb(), state, Move.ESVAZIA_A);
		}

		public State esvaziab(VasilhameState state) {
			return new VasilhameState(state.getVasilhamea(), 0, state, Move.ESVAZIA_B);
		}

		public State encheausandob(VasilhameState state) {
			int a,b;
			b = normalize(state.getVasilhameb() - (4 - state.getVasilhamea()));
			a = normalize(state.getVasilhamea() + (state.getVasilhameb() - b));
			return new VasilhameState(a,b, state, Move.ENCHE_A_USANDO_B);
		}

		public State enchebusandoa(VasilhameState state) {
			int a,b;
			a = normalize(state.getVasilhamea() - (3 - state.getVasilhameb()));
			b = normalize(state.getVasilhameb() + (state.getVasilhamea() - a));
			return new VasilhameState(a,b, state, Move.ENCHE_B_USANDO_A);
		}

		public int normalize(int x) {
			return (x < 0) ? 0 : x;
		}

		public void add(State state) {
			// TODO Auto-generated method stub
			
		}

	}

	// Busca em profundidade
	public class VasilhameStrategyStack implements Strategy
	{
		private Stack<State> stack = new Stack<State> ();

		public VasilhameStrategyStack() {
		}

		public State next() {
			return stack.pop();
		}

		public void clean() {
			stack.clear();
		}

		public void expand(State state)
		{
			VasilhameState local = (VasilhameState) state;

			for (Move move : Move.values())
			{
				switch(move) {
					case ENCHE_A:
						if (local.getMove() != Move.ENCHE_A  && local.getVasilhamea() != 4 )
							stack.push(enchea(local)); break;
					case ENCHE_B:
						if (local.getMove() != Move.ENCHE_B  && local.getVasilhameb() != 3 )
							stack.push(encheb(local)); break;
					case ESVAZIA_A:
						if (local.getMove() != Move.ESVAZIA_A && local.getVasilhamea() > 0)
							stack.push(esvaziaa(local));
						break;
					case ESVAZIA_B:
						if (local.getMove() != Move.ESVAZIA_B && local.getVasilhameb() > 0)
							stack.push(esvaziab(local)); break;
					case ENCHE_A_USANDO_B:
						if (local.getMove() != Move.ENCHE_A_USANDO_B  && local.getVasilhameb() > 0)
							stack.push(encheausandob(local)); break;
					case ENCHE_B_USANDO_A:
						if (local.getMove() != Move.ENCHE_B_USANDO_A  && local.getVasilhamea() > 0)
							stack.push(enchebusandoa(local)); break;
				}
			}
		}

		public boolean expandable(State state) {
			return !stack.isEmpty();
		}

		public State enchea(VasilhameState state) {
			return new VasilhameState(4, state.getVasilhameb(), state, Move.ENCHE_A);
		}

		public State encheb(VasilhameState state) {
			return new VasilhameState(state.getVasilhamea(), 3, state, Move.ENCHE_B);
		}

		public State esvaziaa(VasilhameState state) {
			return new VasilhameState(0, state.getVasilhameb(), state, Move.ESVAZIA_A);
		}

		public State esvaziab(VasilhameState state) {
			return new VasilhameState(state.getVasilhamea(), 0, state, Move.ESVAZIA_B);
		}

		public State encheausandob(VasilhameState state) {
			int a,b;
			b = normalize(state.getVasilhameb() - (4 - state.getVasilhamea()));
			a = normalize(state.getVasilhamea() + (state.getVasilhameb() - b));
			return new VasilhameState(a,b, state, Move.ENCHE_A_USANDO_B);
		}

		public State enchebusandoa(VasilhameState state) {
			int a,b;
			a = normalize(state.getVasilhamea() - (3 - state.getVasilhameb()));
			b = normalize(state.getVasilhameb() + (state.getVasilhamea() - a));
			return new VasilhameState(a,b, state, Move.ENCHE_B_USANDO_A);
		}

		public int normalize(int x) {
			return (x < 0) ? 0 : x;
		}

		public void add(State state) {
			// TODO Auto-generated method stub
			
		}

	}

	VasilhameStrategyQueue strategyQueue;
	VasilhameStrategyStack strategyStack;

	long start , end;

	@Before
    public void setUp() {
		strategyQueue = new VasilhameStrategyQueue();
		strategyStack = new VasilhameStrategyStack();
    }

	// Busca em largura com aprofundamento iterativo 5
	@Test
	public void testIterativeDeepeningSearch() {
		VasilhameState startstate = new VasilhameState(0, 0, null, null);
		VasilhameState goalstate = new VasilhameState(2, 0, null, null);

		strategyQueue.clean();
		Search search = new GeneralSearch(startstate, goalstate, strategyQueue, 15, true);

		start = System.currentTimeMillis();
		State f = search.search();
		end	= System.currentTimeMillis();
		System.out.printf("### testIterativeDeepeningSearch: %d ms\n",end-start);

		assertTrue(f != null);
		System.out.println("-- Solucao encontrada pelo IterativeDeepeningSearch");
		System.out.println(f);
	}

	// Busca em largura limitada 9
	@Test
	public void testDepthLimitedSearchSuccessAlways() {
		VasilhameState startstate = new VasilhameState(0, 0, null, null);
		VasilhameState goalstate = new VasilhameState(2, 0, null, null);

		strategyQueue.clean();
		Search search = new GeneralSearch(startstate, goalstate, strategyQueue, 9, false);

		start = System.currentTimeMillis();
		State f = search.search();
		end	= System.currentTimeMillis();
		System.out.printf("###testDepthLimitedSearchSuccessAlways: %d ms\n",end-start);

		assertTrue(f != null);
		System.out.println("-- Solucao encontrada pelo DepthLimitedSearch");
		System.out.println(f);
	}

	// Busca em largura limitada 5
	@Test
	public void testDepthLimitedSearchFailsAlways() {
		VasilhameState startstate = new VasilhameState(0, 0, null, null);
		VasilhameState goalstate = new VasilhameState(2, 0, null, null);

		strategyQueue.clean();
		Search search = new GeneralSearch(startstate, goalstate, strategyQueue, 5, false);

		start = System.currentTimeMillis();
		State f = search.search();
		end	= System.currentTimeMillis();
		System.out.printf("### testDepthLimitedSearchFailsAlways: %d ms\n",end-start);

		assertTrue(f == null);
	}

	// Busca em profundidade limitada 7000
	@Test
	public void testDepthLimitedSearch() {
		VasilhameState startstate = new VasilhameState(0, 0, null, null);
		VasilhameState goalstate = new VasilhameState(2, 0, null, null);

		strategyStack.clean();
		Search search = new GeneralSearch(startstate, goalstate, strategyStack, 7000, false);

		start = System.currentTimeMillis();
		State f = search.search();
		end	= System.currentTimeMillis();
		System.out.printf("### testDepthLimitedSearch: %d ms\n",end-start);

		assertTrue(f == null);
	}

	// Busca em largura
	@Test
	public void testGeneralSearch() {
		VasilhameState startstate = new VasilhameState(0, 0, null, null);
		VasilhameState goalstate = new VasilhameState(2, 0, null, null);

		strategyQueue.clean();
		Search search = new GeneralSearch(startstate, goalstate, strategyQueue);

		start = System.currentTimeMillis();
		State f = search.search();
		end	= System.currentTimeMillis();
		System.out.printf("### testGeneralSearch: %d ms\n",end-start);

		assertTrue(f != null);
		System.out.println("-- Solucao encontrada pelo GeneralSearch");
		System.out.println(f);
	}

	public static void main(String args[]) {
		org.junit.runner.JUnitCore.main(
				"ia.tests.SimulatedAnnealingTests"
				);
	}
}

