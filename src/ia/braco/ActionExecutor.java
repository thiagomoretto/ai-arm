package ia.braco;

import ia.Action;
import ia.State;

public class ActionExecutor implements Action 
{
	public BracoWrapper wrapper;
	
	public ActionExecutor(BracoWrapper wrapper) 
	{
		this.wrapper = wrapper;
	}
	
	public void execute(State next) 
	{
		assert next instanceof BracoState;
		
		BracoState state = (BracoState) next;
		
		if (state.action() != null)
		{
			Movimento movimento = state.action();

			if (movimento.getTipo().equals(TipoDeMovimento.ANTE_BRACO_CIMA)) {
				// Não tem.
			}
			
			if (movimento.getTipo().equals(TipoDeMovimento.ROTACIONA_ANTE_BRACO)) {
				wrapper.anteBracoDireito(movimento.getValue()); 
			}
			
			if (movimento.getTipo().equals(TipoDeMovimento.SOBE_BRACO)) {
				wrapper.cimaBracoDireito(movimento.getValue()); 
			}
			
			if (movimento.getTipo().equals(TipoDeMovimento.LATERAL_BRACO)) {
				wrapper.lateralBracoDireito(movimento.getValue()); 
			}
			
			if (movimento.getTipo().equals(TipoDeMovimento.ROTACIONA_BRACO)) {
				wrapper.rotacionaBracoDireito(movimento.getValue()); 
			}
		}
		
		if( BracoRunner.global_sleep > 0 )
			sleep( BracoRunner.global_sleep );
	}
	
	private void sleep(int milisec) {
		try {
			Thread.sleep(milisec);
		} catch (InterruptedException e) {}
	}
}
