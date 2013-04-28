package ld26.ui;

import ld26.Main;

import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

public class Message {
	public Image image;

	public Message(Image image) {
		this.image = image;
	}

	public void draw() {
		image.draw(1024 / 2 - image.getWidth() / 2,
				768 / 2 - image.getHeight() / 2);
	}

	public void handleInput(Input input, StateBasedGame sbg) {
		if(input.isKeyPressed(Input.KEY_ESCAPE)) {
			sbg.enterState(0);
		}

		if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			if(input.getMouseX() > 1024 / 2 - image.getWidth() / 2
					&& input.getMouseX() < 1024 / 2 + image.getWidth() / 2
					&& input.getMouseY() > 768 / 2 - image.getHeight() / 2
							+ 130
					&& input.getMouseY() < 768 / 2 + image.getHeight() / 2) {
				Main.message = null;
			}
		}
	}
}
