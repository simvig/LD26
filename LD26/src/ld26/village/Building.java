package ld26.village;

import ld26.ai.BuildJob;
import ld26.ai.DeliverFoodJob;
import ld26.ai.DeliverWoodJob;
import ld26.ai.GetMaterialsJob;
import ld26.ai.GoHomeJob;
import ld26.ai.Waypoint;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class Building {

	private int builders = 0;

	private int constructionCost;

	private int farmers = 0;
	private Image image;
	private Image[] incompleteImages;
	private int materials;

	private int materialsReserved = 0;

	private Role role;

	private float rotation;

	private int spawnCounter = Village.SPAWN_DELAY;

	private boolean unreachable;

	private int woodcutters = 0;

	private int x;

	private int y;

	public Building(int x, int y, float rotation, Image image) {
		this.x = x;
		this.y = y;
		this.rotation = rotation;
		this.image = image;

		// for(int i = 0; i < image.getWidth(); i++) {
		// for(int j = 0; j < image.getHeight(); j++) {
		// if(image.getColor(i, j).getAlpha() > 0) {
		// int oldX = i - image.getWidth() / 2;
		// int oldY = j - image.getHeight() / 2;
		//
		// double newX = oldX * Math.cos(rotation) - oldY
		// * Math.sin(rotation);
		// double newY = oldX * Math.sin(rotation) + oldY
		// * Math.cos(rotation);
		//
		// VillagerPathfinding.getInstance().setBlocked(
		// (int)(x + newX), (int)(y + newY));
		// }
		// }
		// }
	}

	public void addBuilder(int n) {
		builders += n;
	}

	public void addFarmer(int i) {
		farmers += i;
	}

	public void addMaterials(int number) {
		materials += number;
		materialsReserved -= number;
		if(isComplete()) { // this should not trigger more than once
			switch(role) {
				case BUILDER:
					builders++;
					Village.getInstance().addBuilder();
					break;
				case WOODCUTTER:
					woodcutters += 3;
					Village.getInstance().addWoodcutter(3);
					break;
				case FARMER:
					farmers += 5;
					Village.getInstance().addFarmer(5);
					break;
			}
		}
	}

	public void addWoodcutter(int i) {
		woodcutters += i;
	}

	public void draw(Graphics g) {
		Image picture;
		if(isComplete()) {
			picture = image;
		} else {
			int index = materials
					/ (constructionCost / incompleteImages.length);
			if(index >= incompleteImages.length) {
				index = incompleteImages.length - 1;
			}
			picture = incompleteImages[index];
		}
		picture.setRotation(rotation);
		picture.draw(x - image.getWidth() / 2, y - image.getHeight() / 2);
		// g.drawLine(getDoorX(), getDoorY(), getDoorX(), getDoorY());
	}

	public int getConstructionCost() {
		return constructionCost;
	}

	public int getDoorX() {
		return x;
		// int oldX = image.getWidth() / 2 + 1;
		// int newX = (int)(oldX * Math.cos(rotation));
		// return x + newX;

		// int oldY = image.getHeight() / 2 + 1;
		// int newX = (int)(-oldY * Math.sin(rotation));
		// return x + newX;
	}

	public int getDoorY() {
		return y;
		// int oldX = image.getWidth() / 2 + 1;
		// int newY = (int)(oldX * Math.sin(rotation));
		// return y + newY;
		// int oldY = image.getHeight() / 2 + 1;
		// int newY = (int)(oldY * Math.cos(rotation));
		// return y + newY;
	}

	public int getMaterials() {
		return materials;
	}

	public Role getRole() {
		return role;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isComplete() {
		return materials >= constructionCost;
	}

	public boolean isUnreachable() {
		return unreachable;
	}

	public boolean needsBuilders() {
		return !unreachable && materialsReserved + materials < constructionCost;
	}

	public void sendBuilder() {
		materialsReserved++;
	}

	public void setConstructionCost(int constructionCost) {
		this.constructionCost = constructionCost;
	}

	public void setIncompleteImages(Image[] incompleteImages) {
		this.incompleteImages = incompleteImages;
	}

	public void setMaterials(int materials) {
		this.materials = materials;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public void setUnreachable(boolean unreachable) {
		this.unreachable = unreachable;
	}

	private void spawnBuilder() {
		Building b = Village.getInstance().unfinishedBuilding();
		if(spawnCounter <= 0 && builders > 0 && b != null
				&& Village.getInstance().getWood() > 0
				&& Village.getInstance().getFood() > 0) {
			Village.getInstance().addWood(-1);
			Village.getInstance().addFood(-1);
			spawnCounter = Village.SPAWN_DELAY;
			builders--;
			b.sendBuilder();
			Person p = new Person(this);

			if(this != Village.getInstance().getCenter()) {
				p.addWaypoint(new Waypoint(Village.getInstance().getCenter(),
						new GetMaterialsJob(p)));
			}
			p.addWaypoint(new Waypoint(b, new BuildJob(p, b)));
			p.addWaypoint(new Waypoint(this, new GoHomeJob(p)));

			p.setRole(Role.BUILDER);
			// p.setPath(path);
			// p.setJob(new BuildJob(p, b));
			Village.getInstance().addPerson(p);

			p.startRoute();
		}
	}

	private void spawnFarmer() {
		if(spawnCounter <= 0 && farmers > 0) {
			spawnCounter = Village.SPAWN_DELAY;
			farmers--;
			Person p = new Person(this);
			p.addWaypoint(new Waypoint(Village.getInstance().getCenter(),
					new DeliverFoodJob(p)));
			p.addWaypoint(new Waypoint(this, new GoHomeJob(p)));
			p.setRole(Role.FARMER);
			Village.getInstance().addPerson(p);
			p.startRoute();
		}
	}

	private void spawnWoodcutter() {
		if(spawnCounter <= 0 && woodcutters > 0
				&& Village.getInstance().getFood() > 0) {
			Village.getInstance().addFood(-1);
			spawnCounter = Village.SPAWN_DELAY;
			woodcutters--;
			Person p = new Person(this);
			p.addWaypoint(new Waypoint(Village.getInstance().getCenter(),
					new DeliverWoodJob(p)));
			p.addWaypoint(new Waypoint(this, new GoHomeJob(p)));
			p.setRole(Role.WOODCUTTER);
			Village.getInstance().addPerson(p);
			p.startRoute();
		}
	}

	public void update(int delta) {
		if(isComplete()) {
			spawnCounter -= delta;

			switch(role) {
				case BUILDER:
					spawnBuilder();
					break;
				case WOODCUTTER:
					spawnWoodcutter();
					break;
				case FARMER:
					spawnFarmer();
					break;
			}
		}
	}
}
