package ld26;

import ld26.ui.Message;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Main extends StateBasedGame {

	public static Message message = null;
	public static boolean statePaused = false;

	public static void main(String[] args) {
		try {
			AppGameContainer container = new AppGameContainer(new Main());
			container.setDisplayMode(1024, 768, true);
			container.setShowFPS(false);
			container.setIcon("Data/Images/icon.png");
			container.start();
		} catch(SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Main() {
		super("Ville");

		addState(new MenuState());
		addState(new GameState());

		enterState(0);
	}

	// @Override
	// public void enterState(int i) {
	// super.enterState(i);
	//
	// if(i == 1 && !MenuState.newGameStarted) {
	// MenuState.newGameStarted = true;
	//
	// }
	// }

	/*
	 * (non-Javadoc)
	 * @see
	 * org.newdawn.slick.state.StateBasedGame#initStatesList(org.newdawn.slick
	 * .GameContainer)
	 */
	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		getState(0).init(container, this);
		getState(1).init(container, this);
	}

}
