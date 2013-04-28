package ld26;

import ld26.ai.VillagerPathfinding;
import ld26.input.InputHandler;
import ld26.map.Map;
import ld26.ui.SoundManager;
import ld26.ui.UI;
import ld26.village.Village;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameState extends BasicGameState {
	@Override
	public int getID() {
		return 1;
	}

	/*
	 * (non-Javadoc)
	 * @see org.newdawn.slick.BasicGame#init(org.newdawn.slick.GameContainer)
	 */
	@Override
	public void init(GameContainer arg0, StateBasedGame sbg)
			throws SlickException {
		Map.getInstance().init();
		VillagerPathfinding.getInstance().init();
		Village.getInstance().init();
		UI.init();
		SoundManager.init();
	}

	/*
	 * (non-Javadoc)
	 * @see org.newdawn.slick.Game#render(org.newdawn.slick.GameContainer,
	 * org.newdawn.slick.Graphics)
	 */
	@Override
	public void render(GameContainer container, StateBasedGame sbg, Graphics g)
			throws SlickException {
		Map.getInstance().draw(container, g);
		Village.getInstance().draw(g);
		// VillagerPathfinding.getInstance().drawBlockMap(g);

		UI.draw(g);
	}

	/*
	 * (non-Javadoc)
	 * @see org.newdawn.slick.BasicGame#update(org.newdawn.slick.GameContainer,
	 * int)
	 */
	@Override
	public void update(GameContainer container, StateBasedGame sbg, int delta)
			throws SlickException {
		if(Main.message == null) {
			InputHandler.getInstance().handleInput(container, sbg);
		} else {
			Main.message.handleInput(container.getInput(), sbg);
		}

		if(!Main.statePaused && Main.message == null) {
			Village.getInstance().update(delta);
		}
	}
}
