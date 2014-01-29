package ia.braco;

import javax.vecmath.Vector3f;

import ia.gui3d.Human;

public class BracoWrapper {
	
	private Human human;

	public BracoWrapper(Human human)
	{
		this.human = human;
	}

	public Vector3f getPosicaoAlvo() {
		return human.getPosicaoAlvo();
	}

	public Vector3f getPosicaoMao() {
		return human.getPosicaoMao();
	}

	public void lateralBracoDireito(int rotation) {
		human.lateralBracoDireito(rotation);
	}

	public void rotacionaBracoDireito(int rotation) {
		human.rotacionaBracoDireito(rotation);
	}
	
	public void cimaBracoDireito(int rotation) {
		human.cimaBracoDireito(rotation);
	}
	
	public void anteBracoDireito(int rotation) {
		human.anteBracoDireito(rotation);
	}
	
	public void alvoY(float value) {
		human.alvoY(value);
	}
	
	public void alvoX(float value) {
		human.alvoX(value);
	}
	
	public void alvoZ(float value) {
		human.alvoZ(value);
	}
}
