package ld26;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MenuState extends BasicGameState {

	public static int inputDelay = 0;
	public static boolean newGameStarted = false;
	private Image background;
	private Image cont;
	private Image contSelected;
	private Image exit;
	private boolean exitOver = false;

	private Image exitSelected;

	private Image newGame;

	private boolean newGameOver = false;

	private Image newGameSelected;

	/*
	 * (non-Javadoc)
	 * @see org.newdawn.slick.state.BasicGameState#getID()
	 */
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.newdawn.slick.state.GameState#init(org.newdawn.slick.GameContainer,
	 * org.newdawn.slick.state.StateBasedGame)
	 */
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		background = new Image("Data/Images/menu.jpg");
		newGame = new Image("Data/Images/newGame.png");
		newGameSelected = new Image("Data/Images/newGameSelected.png");
		exit = new Image("Data/Images/exit.png");
		exitSelected = new Image("Data/Images/exitSelected.png");
		cont = new Image("Data/Images/continue.png");
		contSelected = new Image("Data/Images/continueSelected.png");
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.newdawn.slick.state.GameState#render(org.newdawn.slick.GameContainer,
	 * org.newdawn.slick.state.StateBasedGame, org.newdawn.slick.Graphics)
	 */
	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		background.draw();
		if(!newGameOver) {
			if(!newGameStarted) {
				newGame.draw(1024 / 2 - 250, 250);
			} else {
				cont.draw(1024 / 2 - 250, 250);
			}
		} else {
			if(!newGameStarted) {
				newGameSelected.draw(1024 / 2 - 250, 250);
			} else {
				contSelected.draw(1024 / 2 - 250, 250);
			}
		}
		if(!exitOver) {
			exit.draw(1024 / 2 - 250, 400);
		} else {
			exitSelected.draw(1024 / 2 - 250, 400);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.newdawn.slick.state.GameState#update(org.newdawn.slick.GameContainer,
	 * org.newdawn.slick.state.StateBasedGame, int)
	 */
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		inputDelay -= delta;
		Input input = gc.getInput();
		int x = input.getMouseX();
		int y = input.getMouseY();

		if(x > 1024 / 2 - 250 && x < 1024 / 2 + 250 && y > 250 && y < 350) {
			newGameOver = true;
		} else {
			newGameOver = false;
		}

		if(x > 1024 / 2 - 250 && x < 1024 / 2 + 250 && y > 400 && y < 500) {
			exitOver = true;
		} else {
			exitOver = false;
		}

		if(inputDelay < 0 && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
			if(exitOver) {
				gc.exit();
			} else if(newGameOver) {
				input.pause();
				sbg.enterState(1);
				input.resume();
			}
		}
	}

}
