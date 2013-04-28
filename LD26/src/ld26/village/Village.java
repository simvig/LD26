package ld26.village;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ld26.BuildingSpot;
import ld26.map.Map;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Village {
	private static Village instance;
	public static int SPAWN_DELAY = 500;

	public static int VILLAGER_SPEED = 50; // milliseconds
											// per pixel

	public static Village getInstance() {
		if(instance == null) {
			instance = new Village();
		}
		return instance;
	}

	private int builders;

	private List<Building> buildings;

	private List<BuildingSpot> buildingSpots;

	private Building center;

	private Image farm;

	private int farmers;

	private Image[] farmIncomplete;
	private int food = 100;

	private int lastUpdate = 0;
	private List<Person> population;

	private Image smallHut;
	private Image[] smallHutIncomplete;

	private Image villageCenter;
	private int wood = 100;
	private Image woodcutter;

	private Image[] woodcutterIncomplete;
	private int woodcutters = 0;

	private Village() {

	}

	public void addBuilder() {
		builders++;
	}

	public void addFarmer(int n) {
		farmers += n;
	}

	public void addFood(int n) {
		food += n;
	}

	public void addPerson(Person p) {
		population.add(p);
	}

	public void addWood(int n) {
		wood += n;
	}

	public void addWoodcutter(int n) {
		woodcutters += n;
	}

	public boolean constructBuilding(int area, int size) {
		if(area == -1) {
			return false;
		}
		switch(Map.getInstance().getType()) {
			case 0:
				return constructHut(area, size);
			case 1:
				return constructWoodcutter(area);
			case 2:
				return constructFarm(area);
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
		return true;
	}

	private boolean constructHut(int area, int size) {
		BuildingSpot spot = findFreeSpot(area, size);
		if(spot == null) {
			return false;
		}

		Building b = new Building(spot.x, spot.y, spot.rotation, smallHut);
		b.setIncompleteImages(smallHutIncomplete);
		b.setConstructionCost(5);
		b.setMaterials(0);
		b.setRole(Role.BUILDER);

		buildings.add(b);

		buildingSpots.remove(spot);
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
		return true;
	}

	public void draw(Graphics g) {
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

	public int getPopulation() {
		return builders + woodcutters + farmers;
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

		smallHut = new Image("Data/smallHut.png");
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

		villageCenter = new Image("Data/villageCenter.png");

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
}
