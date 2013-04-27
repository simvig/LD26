package ld26.input;

import ld26.map.Map;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

public class InputHandler {
	private static InputHandler	instance;

	private InputHandler() {

	}

	public static InputHandler getInstance() {
		if(instance == null) {
			instance = new InputHandler();
		}
		return instance;
	}

	public void handleInput(GameContainer container) {
		showSelections(container);
	}

	public void showSelections(GameContainer container) {
		Input input = container.getInput();
		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();

		Map.getInstance().checkMouseOverArea(mouseX, mouseY);
	}
}
