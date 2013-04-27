package ld26.village;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ld26.BuildingSpot;
import ld26.ai.BuildJob;
import ld26.ai.VillagerPathfinding;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.pathfinding.Path;

public class Village {
	private List<BuildingSpot>	buildingSpots;
	private List<Building>		buildings;

	private Image				smallHut;
	private Image[]				smallHutIncomplete;
	private Image				villageCenter;

	private Building			center;

	private List<Person>		population;

	private int					builders	= 0;

	private Village() {

	}

	private static Village	instance;

	public static Village getInstance() {
		if(instance == null) {
			instance = new Village();
		}
		return instance;
	}

	public void draw(Graphics g) {
		for(Person p : population) {
			p.draw(g);
		}
		for(Building b : buildings) {
			b.draw(g);
		}
	}

	public void init() throws SlickException {
		loadBuildingSpots();
		buildings = new ArrayList<>();

		smallHut = new Image("Data/smallHut.png");
		smallHutIncomplete = new Image[2];
		smallHutIncomplete[0] = new Image("Data/Images/smallHut/smallHut0.png");
		smallHutIncomplete[1] = new Image("Data/Images/smallHut/smallHut1.png");

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

	private int					totalBuilders;

	private static final int	SPAWN_DELAY		= 100;
	private static final int	VILLAGER_SPEED	= 50;			// milliseconds
																// per pixel

	private int					lastUpdate		= 0;

	private int					spawnCounter	= SPAWN_DELAY;

	public int getPopulation() {
		return totalBuilders;
	}

	public int getBuilders() {
		return totalBuilders;
	}

	private void spawnBuilder() {
		Building b = unfinishedBuilding();
		if(spawnCounter <= 0 && b != null) {
			List<Building> eligibleBuildings = new ArrayList<>();
			for(Building build : buildings) {
				if(build == center
						|| (build.isComplete() && build.getRole() == Role.BUILDER)) {
					eligibleBuildings.add(build);
				}
			}
			Building home = eligibleBuildings
					.get((int)(Math.random() * eligibleBuildings.size()));

			Path path = VillagerPathfinding.getInstance().findPath(
					home.getDoorX(), home.getDoorY(), b.getDoorX(),
					b.getDoorY());
			if(path == null) {
				b.setUnreachable(true);
				System.out.println("Building unreachable");
			} else if(builders > 0) {
				spawnCounter = SPAWN_DELAY;
				builders--;
				b.sendBuilder();
				Person p = new Person(home.getX(), home.getY());
				p.setRole(Role.BUILDER);
				p.setPath(path);
				p.setJob(new BuildJob(b));
				population.add(p);
			}
		}
	}

	public void update(int delta) {
		spawnCounter -= delta;

		spawnBuilder();

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
					builders++;
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

	public boolean constructBuilding(int area, int size) {
		if(area == -1) {
			return false;
		}
		if(buildings.size() > 0) {
			return constructHut(area, size);
		} else {
			return constructVillageCenter(area);
		}
	}

	private boolean constructVillageCenter(int area) {
		BuildingSpot spot = findFreeSpot(area, 2);
		if(spot == null) {
			return false;
		}
		center = new Building(spot.x, spot.y, spot.rotation, villageCenter);
		center.setConstructionCost(0);
		center.setMaterials(0);
		buildings.add(center);
		buildingSpots.remove(spot);

		builders += 10;
		totalBuilders += 10;

		return true;
	}

	private Building unfinishedBuilding() {
		for(Building b : buildings) {
			if(b.needsBuilders()) {
				return b;
			}
		}
		return null;
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

		buildings.add(b); // TODO
							// change//
							// images

		buildingSpots.remove(spot);
		return true;
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

	public void addBuilder() {
		builders++;
		totalBuilders++;
	}
}
