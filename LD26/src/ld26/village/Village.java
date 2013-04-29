package ld26.village;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ld26.BuildingSpot;
import ld26.Main;
import ld26.ai.VillagerPathfinding;
import ld26.map.Map;
import ld26.ui.Message;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Village {
	private static Village instance;
	public static int SPAWN_DELAY = 500;

	public static int VILLAGER_SPEED = 10; // milliseconds
											// per pixel

	public static Village getInstance() {
		if(instance == null) {
			instance = new Village();
		}
		return instance;
	}

	private Image bigBridge;

	public boolean bigBridgeBuilt = false;

	private Image bridge;

	private Image[] bridgeIncomplete;

	private int builders;

	private List<Building> buildings;

	private List<BuildingSpot> buildingSpots;
	private Building center;

	private Image farm;
	private int farmers;

	private Image[] farmIncomplete;
	private int food = 100;

	private int gold;
	private Image goldMine;
	private Image[] goldMineIncomplete;

	private int goldMiners = 0;

	private Image largeHut;

	private Image[] largeHutIncomplete;
	private int lastUpdate = 0;

	private Image mediumHut;
	private Image[] mediumHutIncomplete;

	private Image mine;
	private Image[] mineIncomplete;

	private int miners = 0;
	public Building nearestBridge = null;

	private List<Person> population;
	public boolean secretEndingUnlocked = false;

	private Image smallHut;

	private Image[] smallHutIncomplete;

	private int stone = 0;
	private Image villageCenter;

	private int wine = 0;

	private int winemakers = 0;

	private Image winery;
	private Image[] wineryIncomplete;

	private int wood = 100;

	private Image woodcutter;

	private Image[] woodcutterIncomplete;

	private int woodcutters = 0;

	private Village() {

	}

	public void addBuilder(int n) {
		builders += n;
	}

	public void addFarmer(int n) {
		farmers += n;
	}

	public void addFood(int n) {
		food += n;
	}

	public void addGold(int i) {
		gold += i;
	}

	public void addGoldMiners(int i) {
		goldMiners += i;
	}

	public void addMiner(int i) {
		miners += i;
	}

	public void addPerson(Person p) {
		population.add(p);
	}

	public void addStone(int n) {
		stone += n;
	}

	public void addWine(int n) {
		wine += n;
	}

	public void addWinemaker(int n) {
		winemakers += n;
	}

	public void addWood(int n) {
		wood += n;
	}

	public void addWoodcutter(int n) {
		woodcutters += n;
	}

	private boolean constructBridge(int area) {
		BuildingSpot spot = findFreeSpot(area, 2);
		if(spot == null) {
			return false;
		}

		Building b = new Building(spot.x, spot.y, spot.rotation, bridge);
		b.setIncompleteImages(bridgeIncomplete);
		b.setConstructionCost(20);
		b.setMaterials(0);
		b.setRole(Role.BRIDGE);

		buildings.add(b);

		buildingSpots.remove(spot);
		findFreeSpot(area, 2);
		return true;
	}

	public boolean constructBuilding(int area, int size) {
		if(area == -1) {
			return false;
		}
		if(Map.getInstance().getBlocked(area)) {
			return false;
		}
		/*
		 * 0 - plains; 1 - forest; 2 - field; 3 - mountain; 4 - hill; 5 - river;
		 * 6 - gold
		 */
		switch(Map.getInstance().getType()) {
			case 0:
				return constructHut(area, size);
			case 1:
				return constructWoodcutter(area);
			case 2:
				return constructFarm(area);
			case 3:
				return constructMine(area);
			case 4:
				return constructWinery(area);
			case 5:
				return constructBridge(area);
			case 6:
				return constructGoldMine(area);
			case 7:
				if(secretEndingUnlocked) {
					try {
						Main.message = new Message(new Image(
								"Data/Images/secretEnd.jpg"), true);
					} catch(SlickException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				return false;
			default:
				return false;
		}
	}

	private boolean constructFarm(int area) {
		BuildingSpot spot = findFreeSpot(area, 2);
		if(spot == null) {
			return false;
		}

		Building b = new Building(spot.x, spot.y, spot.rotation, farm);
		b.setIncompleteImages(farmIncomplete);
		b.setConstructionCost(20);
		b.setMaterials(0);
		b.setRole(Role.FARMER);

		buildings.add(b);

		buildingSpots.remove(spot);
		findFreeSpot(area, 2);
		return true;
	}

	private boolean constructGoldMine(int area) {
		BuildingSpot spot = findFreeSpot(area, 2);
		if(spot == null) {
			return false;
		}

		Building b = new Building(spot.x, spot.y, spot.rotation, goldMine);
		b.setIncompleteImages(goldMineIncomplete);
		b.setConstructionCost(50);
		b.setMaterials(0);
		b.setRole(Role.DIGGER);

		buildings.add(b);

		buildingSpots.remove(spot);
		findFreeSpot(area, 2);
		return true;
	}

	private boolean constructHut(int area, int size) {
		BuildingSpot spot = findFreeSpot(area, size);
		if(spot == null) {
			return false;
		}

		Building b = new Building(spot.x, spot.y, spot.rotation, smallHut);
		b.size = 0;
		b.spotSize = spot.size;
		b.setIncompleteImages(smallHutIncomplete);
		b.setConstructionCost(5);
		b.setMaterials(0);
		b.setRole(Role.BUILDER);

		buildings.add(b);

		buildingSpots.remove(spot);
		findFreeSpot(area, size);
		return true;
	}

	private boolean constructMine(int area) {
		BuildingSpot spot = findFreeSpot(area, 1);
		if(spot == null) {
			return false;
		}

		Building b = new Building(spot.x, spot.y, spot.rotation, mine);
		b.setIncompleteImages(mineIncomplete);
		b.setConstructionCost(10);
		b.setMaterials(0);
		b.setRole(Role.MINER);
		b.spotSize = spot.size;

		buildings.add(b);

		buildingSpots.remove(spot);
		findFreeSpot(area, 1);
		return true;
	}

	private boolean constructVillageCenter(int area) {
		BuildingSpot spot = findFreeSpot(area, 2);
		if(spot == null) {
			return false;
		}
		center = new Building(spot.x, spot.y, spot.rotation, villageCenter);
		center.setConstructionCost(0);
		center.setMaterials(0);
		center.setRole(Role.BUILDER);
		buildings.add(center);
		buildingSpots.remove(spot);

		center.addBuilder(10);
		builders += 10;

		return true;
	}

	private boolean constructWinery(int area) {
		BuildingSpot spot = findFreeSpot(area, 2);
		if(spot == null) {
			return false;
		}

		Building b = new Building(spot.x, spot.y, spot.rotation, winery);
		b.setIncompleteImages(wineryIncomplete);
		b.setConstructionCost(20);
		b.setMaterials(0);
		b.setRole(Role.WINEMAKER);

		buildings.add(b);

		buildingSpots.remove(spot);
		findFreeSpot(area, 2);
		return true;
	}

	private boolean constructWoodcutter(int area) {
		BuildingSpot spot = findFreeSpot(area, 1);
		if(spot == null) {
			return false;
		}

		Building b = new Building(spot.x, spot.y, spot.rotation, woodcutter);
		b.setIncompleteImages(woodcutterIncomplete);
		b.setConstructionCost(10);
		b.setMaterials(0);
		b.setRole(Role.WOODCUTTER);

		buildings.add(b);

		buildingSpots.remove(spot);
		findFreeSpot(area, 1);
		return true;
	}

	public void draw(Graphics g) {
		if(bigBridgeBuilt) {
			bigBridge.draw();
		}
		for(Person p : population) {
			p.draw(g);
		}
		for(Building b : buildings) {
			b.draw(g);
		}
	}

	private BuildingSpot findFreeSpot(int area, int size) {
		List<BuildingSpot> spots = new ArrayList<>();
		for(BuildingSpot spot : buildingSpots) {
			if(spot.area == area && spot.size >= size) {
				spots.add(spot);
			}
		}
		if(spots.size() > 0) {
			return spots.get((int)(Math.random() * spots.size()));
		}
		Map.getInstance().setFull(area); // assumes some things about the
											// size
		return null;
	}

	public int getBuilders() {
		return builders;
	}

	public Building getCenter() {
		return center;
	}

	public int getFarmers() {
		return farmers;
	}

	public int getFood() {
		return food;
	}

	public int getGold() {
		return gold;
	}

	public int getGoldMiners() {
		return goldMiners;
	}

	public int getMiners() {
		return miners;
	}

	public int getPopulation() {
		return builders + woodcutters + farmers + miners + winemakers
				+ goldMiners;
	}

	public int getStone() {
		return stone;
	}

	public int getWine() {
		return wine;
	}

	public int getWinemakers() {
		return winemakers;
	}

	public int getWood() {
		return wood;
	}

	public int getWoodcutters() {
		return woodcutters;
	}

	public void init() throws SlickException {
		loadBuildingSpots();
		buildings = new ArrayList<>();

		smallHut = new Image("Data/Images/smallHut.png");
		smallHutIncomplete = new Image[2];
		smallHutIncomplete[0] = new Image("Data/Images/smallHut/smallHut0.png");
		smallHutIncomplete[1] = new Image("Data/Images/smallHut/smallHut1.png");

		woodcutter = new Image("Data/Images/woodcutter.png");
		woodcutterIncomplete = new Image[2];
		woodcutterIncomplete[0] = new Image(
				"Data/Images/woodcutter/woodcutter0.png");
		woodcutterIncomplete[1] = new Image(
				"Data/Images/woodcutter/woodcutter1.png");

		farm = new Image("Data/Images/farm.png");
		farmIncomplete = new Image[5];
		farmIncomplete[0] = new Image("Data/Images/farm/farm0.png");
		farmIncomplete[1] = new Image("Data/Images/farm/farm1.png");
		farmIncomplete[2] = new Image("Data/Images/farm/farm2.png");
		farmIncomplete[3] = new Image("Data/Images/farm/farm3.png");
		farmIncomplete[4] = new Image("Data/Images/farm/farm4.png");

		largeHut = new Image("Data/Images/largeHut.png");
		largeHutIncomplete = new Image[5];
		largeHutIncomplete[0] = new Image("Data/Images/largeHut/largeHut0.png");
		largeHutIncomplete[1] = new Image("Data/Images/largeHut/largeHut1.png");
		largeHutIncomplete[2] = new Image("Data/Images/largeHut/largeHut2.png");
		largeHutIncomplete[3] = new Image("Data/Images/largeHut/largeHut3.png");
		largeHutIncomplete[4] = new Image("Data/Images/largeHut/largeHut4.png");

		mine = new Image("Data/Images/mine.png");
		mineIncomplete = new Image[3];
		mineIncomplete[0] = new Image("Data/Images/mine/mine0.png");
		mineIncomplete[1] = new Image("Data/Images/mine/mine1.png");
		mineIncomplete[2] = new Image("Data/Images/mine/mine2.png");

		bridge = new Image("Data/Images/bridge.png");
		bridgeIncomplete = new Image[4];
		bridgeIncomplete[0] = new Image("Data/Images/bridge/bridge0.png");
		bridgeIncomplete[1] = new Image("Data/Images/bridge/bridge1.png");
		bridgeIncomplete[2] = new Image("Data/Images/bridge/bridge2.png");
		bridgeIncomplete[3] = new Image("Data/Images/bridge/bridge3.png");

		winery = new Image("Data/Images/winery.png");
		wineryIncomplete = new Image[2];
		wineryIncomplete[0] = new Image("Data/Images/winery/winery0.png");
		wineryIncomplete[1] = new Image("Data/Images/winery/winery1.png");

		mediumHut = new Image("Data/Images/mediumHut.png");
		mediumHutIncomplete = new Image[4];
		mediumHutIncomplete[0] = new Image(
				"Data/Images/mediumHut/mediumHut0.png");
		mediumHutIncomplete[1] = new Image(
				"Data/Images/mediumHut/mediumHut1.png");
		mediumHutIncomplete[2] = new Image(
				"Data/Images/mediumHut/mediumHut2.png");
		mediumHutIncomplete[3] = new Image(
				"Data/Images/mediumHut/mediumHut3.png");

		goldMine = new Image("Data/Images/goldMine.png");
		goldMineIncomplete = new Image[3];
		goldMineIncomplete[0] = new Image("Data/Images/goldMine/goldMine0.png");
		goldMineIncomplete[1] = new Image("Data/Images/goldMine/goldMine1.png");
		goldMineIncomplete[2] = new Image("Data/Images/goldMine/goldMine2.png");

		villageCenter = new Image("Data/Images/villageCenter.png");

		bigBridge = new Image("Data/Images/bigBridge.png");

		population = new ArrayList<>();

		constructVillageCenter(1);
	}

	private void loadBuildingSpots() {
		buildingSpots = new ArrayList<>();
		try {
			BufferedReader in = new BufferedReader(new FileReader(new File(
					"Data/Spots/spots.txt")));
			// in.useDelimiter("\\s*,\\s*");
			String line;
			line = in.readLine();
			int num = Integer.parseInt(line);
			for(int i = 0; i < num; i++) {
				line = in.readLine();
				String[] parts = line.split(", ");

				buildingSpots
						.add(new BuildingSpot(Integer.parseInt(parts[1]),
								Integer.parseInt(parts[2]), Integer
										.parseInt(parts[4]), Float
										.parseFloat(parts[3]), Integer
										.parseInt(parts[0])));
			}
			// System.out.println(buildingSpots.size());
		} catch(FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Building unfinishedBuilding() { // optimize here
		for(Building b : buildings) {
			if(b.needsBuilders()) {
				return b;
			}
		}
		return null;
	}

	public void update(int delta) {
		if(stone >= 20 && wine >= 20) {
			upgradeToMedium();
		}
		if(stone >= 20 && wine >= 20 && gold >= 10) {
			upgradeToLarge();
		}

		for(Building b : buildings) {
			b.update(delta);
		}

		// spawnBuilder();

		lastUpdate += delta;
		Person personToRemove = null;
		for(Person p : population) {
			if(p.isDone()) {
				personToRemove = p;
			}
		}
		if(personToRemove != null) {
			switch(personToRemove.getRole()) {
				case BUILDER:
					personToRemove.getHome().addBuilder(1);
					break;
				case WOODCUTTER:
					personToRemove.getHome().addWoodcutter(1);
					break;
				case FARMER:
					personToRemove.getHome().addFarmer(1);
					break;
				case MINER:
					personToRemove.getHome().addMiner(1);
					break;
				case WINEMAKER:
					personToRemove.getHome().addWinemaker(1);
					break;
				case DIGGER:
					personToRemove.getHome().addGoldMiner(1);
					break;
			}
			population.remove(personToRemove);
		}
		if(lastUpdate > VILLAGER_SPEED) {
			lastUpdate = 0;
			for(Person p : population) {
				p.update();
			}
		}
	}

	public void updatePathfinding() {
		for(int i = 0; i < bigBridge.getWidth(); i++) {
			for(int j = 0; j < bigBridge.getHeight(); j++) {
				org.newdawn.slick.Color c = bigBridge.getColor(i, j);
				if(c.getAlpha() > 0) {
					VillagerPathfinding.getInstance().setUnblocked(i, j);
				}
			}
		}
	}

	private void upgradeToLarge() {
		for(Building b : buildings) {
			if(b.getRole() == Role.BUILDER && b.spotSize > 1 && b.size == 1
					&& b.isComplete()) {
				b.setMaterials(0);
				b.setConstructionCost(30);
				b.setIncompleteImages(largeHutIncomplete);
				b.setImage(largeHut);
				b.setReserved(0);
				stone -= 20;
				wine -= 20;
				gold -= 10;
				b.size = 2;
				return;
			}
		}
	}

	private void upgradeToMedium() {
		for(Building b : buildings) {
			if(b.getRole() == Role.BUILDER && b.spotSize > 0 && b.size == 0
					&& b.isComplete()) {
				b.setMaterials(0);
				b.setConstructionCost(20);
				b.setIncompleteImages(mediumHutIncomplete);
				b.setImage(mediumHut);
				b.setReserved(0);
				stone -= 20;
				wine -= 20;
				b.size = 1;
				return;
			}
		}
	}
}
