package ld26.ui;

import ld26.Main;
import ld26.MenuState;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Message {
	public Image image;

	public boolean twoButtons = false;

	public Message(Image image) {
		this.image = image;
	}

	public Message(Image image, boolean twoButtons) {
		this.image = image;
		this.twoButtons = twoButtons;
	}

	public void draw() {
		image.draw(1024 / 2 - image.getWidth() / 2,
				768 / 2 - image.getHeight() / 2);
	}

	public void handleInput(GameContainer container, Input input,
			StateBasedGame sbg) {
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

		if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			if(!twoButtons) {
				if(input.getMouseX() > 1024 / 2 - image.getWidth() / 2
						&& input.getMouseX() < 1024 / 2 + image.getWidth() / 2
						&& input.getMouseY() > 768 / 2 - image.getHeight() / 2
								+ 130
						&& input.getMouseY() < 768 / 2 + image.getHeight() / 2) {
					Main.message = null;
				}
			} else {
				if(input.getMouseX() > 1024 / 2 - image.getWidth() / 2
						&& input.getMouseX() < 1024 / 2
						&& input.getMouseY() > 768 / 2 - image.getHeight() / 2
								+ 130
						&& input.getMouseY() < 768 / 2 + image.getHeight() / 2) {
					Main.message = null;
				} else if(input.getMouseX() > 1024 / 2
						&& input.getMouseX() < 1024 / 2 + image.getWidth() / 2
						&& input.getMouseY() > 768 / 2 - image.getHeight() / 2
								+ 130
						&& input.getMouseY() < 768 / 2 + image.getHeight() / 2) {
					MenuState.inputDelay = 500;
					sbg.enterState(0);
				}
			}
		}
	}
}
