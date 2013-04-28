package ld26.map;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Map {
	public static final int AREAS = 20;

	private static Map instance;

	public static Map getInstance() {
		if(instance == null) {
			instance = new Map();
		}
		return instance;
	}

	// private int numAreas;

	private boolean[] isBlocked = new boolean[AREAS];
	private boolean[] isFull = new boolean[AREAS];
	private Image mapImage;

	private int selectedArea = -1;
	// private Image[] selectionCircles;

	private int selectedAreaType = -1;

	private Image selectionMask;

	private Map() {

	}

	public void accessRight() {
		// TODO Auto-generated method stub

	}

	public void checkMouseOverArea(int mouseX, int mouseY) {
		Color c = selectionMask.getColor(mouseX, mouseY);
		if(c.getAlpha() == 0) {
			selectedArea = -1;
			selectedAreaType = -1;
		} else {
			selectedArea = c.getRed();
			selectedAreaType = c.getGreen();
		}
	}

	public void draw(GameContainer container, Graphics g) {
		mapImage.draw();
		Roads.draw();
		// if(selectedArea >= 0 && selectedArea < selectionCircles.length) {
		// //selectionCircles[selectedArea].draw();
		// }
	}

	public void drawAllSelections(GameContainer container, Graphics g) {
		// for(int i = 0; i < numAreas; i++) {
		// //selectionCircles[i].draw();
		// }
	}

	public boolean getBlocked(int n) {
		return isBlocked[n];
	}

	public boolean getFull(int area) {
		return isFull[area];
	}

	public int getSelectedArea() {
		return selectedArea;
	}

	public int getType() {
		return selectedAreaType;
	}

	public void init() {
		try {
			mapImage = new Image("Data/Images/map.jpg");
			selectionMask = new Image("Data/Images/selectionMask.png");
			Roads.init();

			// File dataDir = new File("Data");
			// String[] files = dataDir.list(new FilenameFilter() {
			//
			// @Override
			// public boolean accept(File dir, String name) {
			// return name.matches("selection\\d\\.png");
			// }
			// });
			// numAreas = files.length;
			// selectionCircles = new Image[numAreas];
			//
			// for(int i = 0; i < numAreas; i++) {
			// String filename = "Data/" + files[i];
			// selectionCircles[i] = new Image(filename);
			// }

			// on the island
			isBlocked[9] = true;

			// river
			isBlocked[18] = true;
			isBlocked[19] = true;

		} catch(SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setBlocked(int n) {
		isBlocked[n] = true;
	}

	public void setFull(int area) {
		isFull[area] = true;
	}

	public void setImage(Image image) {
		mapImage = image;
	}

	public void setUnblocked(int n) {
		isBlocked[n] = false;
	}
}
