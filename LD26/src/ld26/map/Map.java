package ld26.map;

import java.io.File;
import java.io.FilenameFilter;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Map {
	private static Map	instance;

	private Image		selectionMask;
	private Image		mapImage;
	private Image[]		selectionCircles;

	private int			numAreas;
	private int			selectedArea	= -1;

	public static Map getInstance() {
		if(instance == null) {
			instance = new Map();
		}
		return instance;
	}

	private Map() {

	}

	public void init() {
		try {
			mapImage = new Image("Data/map.jpg");
			selectionMask = new Image("Data/selectionMask.png");

			File dataDir = new File("Data");
			String[] files = dataDir.list(new FilenameFilter() {

				@Override
				public boolean accept(File dir, String name) {
					return name.matches("selection\\d\\.png");
				}
			});
			numAreas = files.length;
			selectionCircles = new Image[numAreas];

			for(int i = 0; i < numAreas; i++) {
				String filename = "Data/" + files[i];
				selectionCircles[i] = new Image(filename);
			}

		} catch(SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void draw(GameContainer container, Graphics g) {
		mapImage.draw();
		if(selectedArea >= 0 && selectedArea < selectionCircles.length) {
			selectionCircles[selectedArea].draw();
		}
	}

	public void checkMouseOverArea(int mouseX, int mouseY) {
		Color c = selectionMask.getColor(mouseX, mouseY);
		if(c.getAlpha() == 0) {
			selectedArea = -1;
		} else {
			selectedArea = c.getRed();
		}
	}

	public void drawAllSelections(GameContainer container, Graphics g) {
		for(int i = 0; i < numAreas; i++) {
			selectionCircles[i].draw();
		}
	}

	public int getSelectedArea() {
		return selectedArea;
	}
}
