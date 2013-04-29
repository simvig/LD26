package ld26.map;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Map {
	public static final int AREAS = 34;

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
	public int[] islandAreas = {9, 30, 31, 32};

	private Image mapImage;

	public int[] rightAreas = {12, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29};

	public int[] rivers = {18, 19};

	private int selectedArea = -1;
	// private Image[] selectionCircles;

	private int selectedAreaType = -1;

	private Image selectionMask;

	private Map() {

	}

	public void accessRight() {
		for(int i = 0; i < rightAreas.length; i++) {
			isBlocked[rightAreas[i]] = false;
		}
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

	public int getArea(int x, int y) {
		Color c = selectionMask.getColor(x, y);
		if(c.getAlpha() == 0) {
			return -1;
		} else {
			return c.getRed();
		}
	}

	public boolean getBlocked(int n) {
		return isBlocked[n];
	}

	public int getDistance(int x1, int y1, int x2, int y2) {
		return (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
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
			for(int i = 0; i < islandAreas.length; i++) {
				isBlocked[islandAreas[i]] = true;
			}

			// river
			for(int i = 0; i < rivers.length; i++) {
				isBlocked[rivers[i]] = true;
			}

			// right of continent
			for(int i = 0; i < rightAreas.length; i++) {
				isBlocked[rightAreas[i]] = true;
			}

		} catch(SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean isIsland(int x, int y) {
		int area = getArea(x, y);
		if(area == -1) {
			return false;
		}
		for(int islandArea : islandAreas) {
			if(area == islandArea) {
				return true;
			}
		}
		return false;
	}

	public boolean isLeft(int x, int y) {
		int area = getArea(x, y);
		if(area == -1) {
			return false;
		}
		for(int rightArea : rightAreas) {
			if(area == rightArea) {
				return false;
			}
		}
		return true;
	}

	public boolean isRight(int x, int y) {
		int area = getArea(x, y);
		if(area == -1) {
			return false;
		}
		for(int rightArea : rightAreas) {
			if(area == rightArea) {
				return true;
			}
		}
		return false;
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
