package ld26;

import ld26.input.InputHandler;
import ld26.map.Map;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Main extends BasicGame {

	public Main() {
		super("LD26");
	}

	public static void main(String[] args) {
		try {
			AppGameContainer container = new AppGameContainer(new Main());
			container.setDisplayMode(1024, 768, false);
			container.start();
		} catch(SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.newdawn.slick.Game#render(org.newdawn.slick.GameContainer,
	 * org.newdawn.slick.Graphics)
	 */
	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		Map.getInstance().draw(container, g);
	}

	/*
	 * (non-Javadoc)
	 * @see org.newdawn.slick.BasicGame#init(org.newdawn.slick.GameContainer)
	 */
	@Override
	public void init(GameContainer arg0) throws SlickException {
		Map.getInstance().init();
	}

	/*
	 * (non-Javadoc)
	 * @see org.newdawn.slick.BasicGame#update(org.newdawn.slick.GameContainer,
	 * int)
	 */
	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		InputHandler.getInstance().handleInput(container);
	}

}
