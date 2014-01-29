package ia.braco;

public class Movimento {

	private TipoDeMovimento tipo;
	
	private int value;

	public Movimento(TipoDeMovimento tipo, int value) {
		this.tipo = tipo;
		this.value = value;
	}

	public TipoDeMovimento getTipo() {
		return tipo;
	}

	public void setTipo(TipoDeMovimento tipo) {
		this.tipo = tipo;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
