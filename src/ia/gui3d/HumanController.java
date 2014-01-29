package ia.gui3d;

import ia.braco.BracoWrapper;

public class HumanController {

	public static HumanController controller;
	
	private Human human;

	private BracoWrapper wrapper;
	
	public static HumanController get() {
		if (controller == null) {
			controller = new HumanController();
			controller.human = new Human();
			controller.wrapper = new BracoWrapper(controller.human);
		}
		
		controller.human();
		controller.wrapper();
		
		return controller;
	}

	public Human human() {
		if (human == null) {
			human = new Human();
		}
		return human;
	}

	public BracoWrapper wrapper() {
		if (wrapper == null) {
			wrapper = new BracoWrapper(controller.human);
		}
		return wrapper;
	}
	
	public void reset() {
		human.dispose();
		controller.human = new Human();
		controller.wrapper = new BracoWrapper(controller.human);
	//	human();
	//	wrapper();
	}
}
