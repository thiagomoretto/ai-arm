package ia.braco;

import ia.Heuristic;
import ia.State;

import java.util.LinkedList;
import java.util.List;

import javax.vecmath.Vector3f;

import mjr.heap.Heapable;

public class BracoState implements State, Heapable {

	private double x, y, z;
	
	private double euclideanDistance;
	
	private double cost;
	
	private BracoState parent;
	
	private Movimento movimento;
	
	private BracoWrapper wrapper = null;
	
	private double depth = 0;

	private Heuristic heuristic;
	
	public BracoState(BracoWrapper wrapper, Heuristic heuristic, State parent)
	{
		if (parent != null) {
			depth = parent.depth() + 0.1;
		}
		this.parent = (BracoState) parent;
		this.wrapper = wrapper;
		this.heuristic = heuristic;
	}

	public BracoState(BracoWrapper wrapper, Heuristic heuristic, State parent, double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		
		if (parent != null) {
			depth = parent.depth() + 0.1;
		}
		this.parent = (BracoState) parent;
		this.wrapper = wrapper;
		this.heuristic = heuristic;
	}
	
	public Movimento action() {
		return movimento;
	}

	public void setMovimento(Movimento movimento) {
		this.movimento = movimento;
	}

	public boolean equals(State state) 
	{
		BracoState current = (BracoState) state;
		return current.distance() < BracoRunner.global_threshold;
	}
	
	public void parent(State state)
	{
		parent = (BracoState ) state;
	}

	public State parent() {
		return parent;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public boolean equalTo(Object other) {
		BracoState b = (BracoState) other;
		return b.cost() == cost(); 
	}

	public boolean greaterThan(Object other) {
		BracoState b = (BracoState) other;
		return b.cost() < cost();
	}

	public boolean lessThan(Object other) {
		BracoState b = (BracoState) other;
		return b.cost() > cost();
	}

	public double depth() {
		return depth;
	}

	public double distance()
	{
		return euclideanDistance;
	}
	
	public void distance(double distance) 
	{
		this.euclideanDistance = distance;
	}
	
	public void cost(double cost)
	{
		this.cost = cost;
	}
	
	public double cost()
	{
		return cost;
	}

	public List<State> produce( State goal , boolean preserveParent )
	{
		assert wrapper != null;
		
		if (wrapper == null) 
			throw new IllegalArgumentException("assert: mover != null");

		// Atualizo a posicao do alvo.
		Vector3f alvo3f = wrapper.getPosicaoAlvo();
		BracoState meta = (BracoState) goal;
		meta.setX(alvo3f.x);
		meta.setY(alvo3f.y);
		meta.setZ(alvo3f.z);
		goal = meta;
		
		List<State> states = new LinkedList<State>();
		
		BracoState current = null; 
		
		if (preserveParent)
			current = this;
		BracoState tmp;
		Vector3f tempPos;
		
		// -----------------------------------------------------------------------------
		wrapper.anteBracoDireito((int)BracoRunner.global_angle); 
		tempPos = wrapper.getPosicaoMao();
		tmp = new BracoState(wrapper, heuristic, current,tempPos.x, tempPos.y, tempPos.z);
		tmp.distance(heuristic.cost(tmp, goal));
		tmp.setMovimento(new Movimento(TipoDeMovimento.ROTACIONA_ANTE_BRACO, (int)BracoRunner.global_angle));
		states.add(tmp);
		wrapper.anteBracoDireito(-(int)BracoRunner.global_angle);
		
		//System.out.println("Distancia euclidiana: "+ heuristic.cost(tmp, goal));
		//System.out.println("Movendo p/ nova posicao: "+tempPos.x+"x"+tempPos.y+"x"+tempPos.x);
		// -----------------------------------------------------------------------------

		// -----------------------------------------------------------------------------
		wrapper.anteBracoDireito(-(int)BracoRunner.global_angle); 
		tempPos = wrapper.getPosicaoMao();
		tmp = new BracoState(wrapper, heuristic, current,tempPos.x, tempPos.y, tempPos.z);
		tmp.distance(heuristic.cost(tmp, goal));
		tmp.setMovimento(new Movimento(TipoDeMovimento.ROTACIONA_ANTE_BRACO, -(int)BracoRunner.global_angle));
		states.add(tmp);
		wrapper.anteBracoDireito((int)BracoRunner.global_angle);
		
		//System.out.println("Distancia euclidiana: "+ heuristic.cost(tmp, goal));
		//System.out.println("Movendo p/ nova posicao: "+tempPos.x+"x"+tempPos.y+"x"+tempPos.x);
		// -----------------------------------------------------------------------------
		
		// -----------------------------------------------------------------------------
		wrapper.cimaBracoDireito(-(int)BracoRunner.global_angle); 
		tempPos = wrapper.getPosicaoMao();
		tmp = new BracoState(wrapper, heuristic, current,tempPos.x, tempPos.y, tempPos.z);
		tmp.distance(heuristic.cost(tmp, goal));
		tmp.setMovimento(new Movimento(TipoDeMovimento.SOBE_BRACO, -(int)BracoRunner.global_angle));
		states.add(tmp);
		wrapper.cimaBracoDireito((int)BracoRunner.global_angle);
		
		//System.out.println("Distancia euclidiana: "+ heuristic.cost(tmp, goal));
		//System.out.println("Movendo p/ nova posicao: "+tempPos.x+"x"+tempPos.y+"x"+tempPos.x);
		// -----------------------------------------------------------------------------
		
		// -----------------------------------------------------------------------------
		wrapper.cimaBracoDireito((int)BracoRunner.global_angle); 
		tempPos = wrapper.getPosicaoMao();
		tmp = new BracoState(wrapper, heuristic, current,tempPos.x, tempPos.y, tempPos.z);
		tmp.distance(heuristic.cost(tmp, goal));
		tmp.setMovimento(new Movimento(TipoDeMovimento.SOBE_BRACO, (int)BracoRunner.global_angle));
		states.add(tmp);
		wrapper.cimaBracoDireito(-(int)BracoRunner.global_angle);
		
		//System.out.println("Distancia euclidiana: "+ heuristic.cost(tmp, goal));
		//System.out.println("Movendo p/ nova posicao: "+tempPos.x+"x"+tempPos.y+"x"+tempPos.x);
		// -----------------------------------------------------------------------------
		
		// -----------------------------------------------------------------------------
		wrapper.lateralBracoDireito((int)BracoRunner.global_angle); 
		tempPos = wrapper.getPosicaoMao();
		tmp = new BracoState(wrapper, heuristic, current,tempPos.x, tempPos.y, tempPos.z);
		tmp.distance(heuristic.cost(tmp, goal));
		tmp.setMovimento(new Movimento(TipoDeMovimento.LATERAL_BRACO, (int)BracoRunner.global_angle));
		states.add(tmp);
		wrapper.lateralBracoDireito(-(int)BracoRunner.global_angle);
		
		//System.out.println("Distancia euclidiana: "+ heuristic.cost(tmp, goal));
		//System.out.println("Movendo p/ nova posicao: "+tempPos.x+"x"+tempPos.y+"x"+tempPos.x);
		// -----------------------------------------------------------------------------
		
		// -----------------------------------------------------------------------------
		wrapper.lateralBracoDireito(-(int)BracoRunner.global_angle); 
		tempPos = wrapper.getPosicaoMao();
		tmp = new BracoState(wrapper, heuristic, current,tempPos.x, tempPos.y, tempPos.z);
		tmp.distance(heuristic.cost(tmp, goal));
		tmp.setMovimento(new Movimento(TipoDeMovimento.LATERAL_BRACO, -(int)BracoRunner.global_angle));
		states.add(tmp);
		wrapper.lateralBracoDireito((int)BracoRunner.global_angle);
		
		//System.out.println("Distancia euclidiana: "+ heuristic.cost(tmp, goal));
		//System.out.println("Movendo p/ nova posicao: "+tempPos.x+"x"+tempPos.y+"x"+tempPos.x);
		// -----------------------------------------------------------------------------
		
		// -----------------------------------------------------------------------------
		wrapper.rotacionaBracoDireito(-(int)BracoRunner.global_angle); 
		tempPos = wrapper.getPosicaoMao();
		tmp = new BracoState(wrapper, heuristic, current,tempPos.x, tempPos.y, tempPos.z);
		tmp.distance(heuristic.cost(tmp, goal));
		tmp.setMovimento(new Movimento(TipoDeMovimento.ROTACIONA_BRACO, -(int)BracoRunner.global_angle));
		states.add(tmp);
		wrapper.rotacionaBracoDireito((int)BracoRunner.global_angle);
		
		//System.out.println("Distancia euclidiana: "+ heuristic.cost(tmp, goal));
		//System.out.println("Movendo p/ nova posicao: "+tempPos.x+"x"+tempPos.y+"x"+tempPos.x);
		// -----------------------------------------------------------------------------
		
		// -----------------------------------------------------------------------------
		wrapper.rotacionaBracoDireito((int)BracoRunner.global_angle); 
		tempPos = wrapper.getPosicaoMao();
		tmp = new BracoState(wrapper, heuristic, current,tempPos.x, tempPos.y, tempPos.z);
		tmp.distance(heuristic.cost(tmp, goal));
		tmp.setMovimento(new Movimento(TipoDeMovimento.ROTACIONA_BRACO, (int)BracoRunner.global_angle));
		states.add(tmp);
		wrapper.rotacionaBracoDireito(-(int)BracoRunner.global_angle);
		
		return states;
	}
	
	public String toString()
	{
		return String.format("{ depth: %f ; distance: %f ; cost: %f }", depth()*10, distance(), cost());
	}
}
