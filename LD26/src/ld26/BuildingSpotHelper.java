package ld26;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import ld26.input.InputHandler;
import ld26.map.Map;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class BuildingSpotHelper extends BasicGame {
	private Image				smallTemplate;
	private Image				mediumTemplate;
	private Image				largeTemplate;

	private List<BuildingSpot>	buildingSpots;

	public BuildingSpotHelper() {
		super("Building Spot Placement");
	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer container = new AppGameContainer(
				new BuildingSpotHelper());
		container.setDisplayMode(1024, 768, false);
		container.start();
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
		for(BuildingSpot spot : buildingSpots) {
			Image image = sizeToImage(spot.size);
			image.setRotation(spot.rotation);
			image.draw(spot.x - image.getWidth() / 2,
					spot.y - image.getHeight() / 2);
		}
		if(drawing) {
			Image template = sizeToImage(size);
			template.setRotation((float)Math.toDegrees(Math.atan2(mouseY
					- startY, mouseX - startX)));
			template.draw(startX - template.getWidth() / 2,
					startY - template.getHeight() / 2);
			g.drawLine(startX, startY, mouseX, mouseY);
		}
	}

	private Image sizeToImage(int size) {
		switch(size) {
			case 0:
				return smallTemplate;
			case 1:
				return mediumTemplate;
			default:
				return largeTemplate;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.newdawn.slick.BasicGame#init(org.newdawn.slick.GameContainer)
	 */
	@Override
	public void init(GameContainer arg0) throws SlickException {
		buildingSpots = new ArrayList<>();
		Map.getInstance().init();
		smallTemplate = new Image("Data/smallTemplate.png");
		mediumTemplate = new Image("Data/mediumTemplate.png");
		largeTemplate = new Image("Data/largeTemplate.png");
		smallTemplate.setCenterOfRotation(5, 5);
		mediumTemplate.setCenterOfRotation(7, 7);
		largeTemplate.setCenterOfRotation(10, 10);
	}

	/*
	 * (non-Javadoc)
	 * @see org.newdawn.slick.BasicGame#update(org.newdawn.slick.GameContainer,
	 * int)
	 */
	@Override
	public void update(GameContainer container, int arg1) throws SlickException {
		Input input = container.getInput();
		InputHandler.getInstance().showSelections(container);

		mouseX = input.getMouseX();
		mouseY = input.getMouseY();

		if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
			if(!drawing) {
				drawing = true;
				startX = mouseX;
				startY = mouseY;
				area = Map.getInstance().getSelectedArea();
			}
		} else {
			if(drawing) {
				drawing = false;
				endX = mouseX;
				endY = mouseY;

				if(area != -1) {
					buildingSpots.add(new BuildingSpot(startX, startY, size,
							(float)Math.toDegrees(Math.atan2(mouseY - startY,
									mouseX - startX)), area));
				}
			}
		}

		if(input.isKeyPressed(Input.KEY_Z)) {
			buildingSpots.remove(buildingSpots.size() - 1);
		}

		if(input.isKeyPressed(Input.KEY_1)) {
			size = 0;
		}

		if(input.isKeyPressed(Input.KEY_2)) {
			size = 1;
		}

		if(input.isKeyPressed(Input.KEY_2)) {
			size = 2;
		}

		if(input.isKeyPressed(Input.KEY_SPACE)) {
			saveSpots();
		}
	}

	private File	output;

	private void saveSpots() {
		File dataDir = new File("Data/Spots");
		if(output == null) {
			String[] files = dataDir.list(new FilenameFilter() {

				@Override
				public boolean accept(File dir, String name) {
					return name.matches("spots\\d\\.txt");
				}
			});
			output = new File(dataDir, "spots" + files.length + ".txt");
		}
		try {
			PrintWriter out = new PrintWriter(output);
			for(BuildingSpot spot : buildingSpots) {
				out.println(spot.toString());
			}
			out.close();
			System.out.println("Wrote " + buildingSpots.size() + " spots to "
					+ output.getPath());
		} catch(FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private int		area	= -1;
	private int		mouseX	= 0;
	private int		mouseY	= 0;
	private int		size	= 0;
	private int		startX	= 0;
	private int		startY	= 0;
	private int		endX	= 0;
	private int		endY	= 0;

	private boolean	drawing	= false;

}
