package ld26.input;

import ld26.map.Map;
import ld26.village.Village;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

public class InputHandler {
	private static InputHandler	instance;

	private Input				input;

	private InputHandler() {

	}

	public static InputHandler getInstance() {
		if(instance == null) {
			instance = new InputHandler();
		}
		return instance;
	}

	public void handleInput(GameContainer container) {
		input = container.getInput();
		showSelections(container);
		mouseClick();
	}

	public void showSelections(GameContainer container) {
		Input input = container.getInput(); // needed for building spot
											// placement
		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();

		Map.getInstance().checkMouseOverArea(mouseX, mouseY);
	}

	private void mouseClick() {
		if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			Village.getInstance().constructBuilding(
					Map.getInstance().getSelectedArea(), 0);
		}
	}
}
