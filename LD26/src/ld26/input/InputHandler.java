package ld26.input;

import ld26.map.Map;
import ld26.village.Village;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

public class InputHandler {
	private static InputHandler instance;

	public static InputHandler getInstance() {
		if(instance == null) {
			instance = new InputHandler();
		}
		return instance;
	}

	private Input input;
	public int mouseX = 0;

	public int mouseY = 0;

	private InputHandler() {

	}

	public void handleInput(GameContainer container) {
		input = container.getInput();
		showSelections(container);
		mouseClick();
		keyboard();
	}

	private void keyboard() {
		if(input.isKeyPressed(Input.KEY_SPACE)) { // remove for release
			if(Village.SPAWN_DELAY > 0) {
				Village.SPAWN_DELAY = 0;
				Village.VILLAGER_SPEED = 10;
			} else {
				Village.SPAWN_DELAY = 500;
				Village.VILLAGER_SPEED = 50;
			}
		}
	}

	private void mouseClick() {
		if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			boolean success = Village.getInstance().constructBuilding(
					Map.getInstance().getSelectedArea(), 0);
			if(!success) {
				Map.getInstance().setFull(Map.getInstance().getSelectedArea());
			}
		}
	}

	public void showSelections(GameContainer container) {
		Input input = container.getInput(); // needed for building spot
											// placement
		mouseX = input.getMouseX();
		mouseY = input.getMouseY();

		Map.getInstance().checkMouseOverArea(mouseX, mouseY);
	}
}
