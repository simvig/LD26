package ld26.input;

import ld26.Main;
import ld26.map.Map;
import ld26.village.Village;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

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

	public void handleInput(GameContainer container, StateBasedGame sbg) {
		input = container.getInput();
		showSelections(container);
		mouseClick();
		keyboard(container, sbg);
	}

	private void keyboard(GameContainer container, StateBasedGame sbg) {
		// if(input.isKeyPressed(Input.KEY_C)) { // remove for release
		// if(Village.SPAWN_DELAY > 0) {
		// Village.SPAWN_DELAY = 0;
		// Village.VILLAGER_SPEED = 10;
		// } else {
		// Village.SPAWN_DELAY = 500;
		// Village.VILLAGER_SPEED = 50;
		// }
		// }
		// if(input.isKeyPressed(Input.KEY_W)) {
		// Village.getInstance().addWood(100);
		// }
		// if(input.isKeyPressed(Input.KEY_F)) {
		// Village.getInstance().addFood(100);
		// }
		// if(input.isKeyPressed(Input.KEY_S)) {
		// Village.getInstance().addStone(100);
		// }
		// if(input.isKeyPressed(Input.KEY_N)) {
		// Village.getInstance().addWine(100);
		// }
		// if(input.isKeyPressed(Input.KEY_G)) {
		// Village.getInstance().addGold(100);
		// }

		if(input.isKeyPressed(Input.KEY_SPACE)) {
			Main.statePaused = !Main.statePaused;
		}
		if(input.isKeyPressed(Input.KEY_ESCAPE)) {
			sbg.enterState(0);
		}

		if(input.isKeyPressed(Input.KEY_F)) {
			boolean fullScreen = container.isFullscreen();
			try {
				container.setFullscreen(!fullScreen);
			} catch(SlickException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void mouseClick() {
		if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			Village.getInstance().constructBuilding(
					Map.getInstance().getSelectedArea(), 0);
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
