package ld26.village;

import ld26.ai.VillagerPathfinding;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class Building {

	private Role	role;

	public Role getRole() {
		return role;
	}

	private Image	image;
	private int		x;
	private int		y;
	private float	rotation;

	private boolean	unreachable;

	public boolean isUnreachable() {
		return unreachable;
	}

	public void setUnreachable(boolean unreachable) {
		this.unreachable = unreachable;
	}

	private int		constructionCost;
	private int		materials;
	private int		materialsReserved	= 0;

	private Image[]	incompleteImages;

	public int getDoorX() {
		int oldX = image.getWidth() / 2 + 1;
		int newX = (int)(oldX * Math.cos(rotation));
		return x + newX;

		// int oldY = image.getHeight() / 2 + 1;
		// int newX = (int)(-oldY * Math.sin(rotation));
		// return x + newX;
	}

	public int getDoorY() {
		int oldX = image.getWidth() / 2 + 1;
		int newY = (int)(oldX * Math.sin(rotation));
		return y + newY;
		// int oldY = image.getHeight() / 2 + 1;
		// int newY = (int)(oldY * Math.cos(rotation));
		// return y + newY;
	}

	public void setIncompleteImages(Image[] incompleteImages) {
		this.incompleteImages = incompleteImages;
	}

	public Building(int x, int y, float rotation, Image image) {
		this.x = x;
		this.y = y;
		this.rotation = rotation;
		this.image = image;

		for(int i = 0; i < image.getWidth(); i++) {
			for(int j = 0; j < image.getHeight(); j++) {
				if(image.getColor(i, j).getAlpha() > 0) {
					int oldX = i - image.getWidth() / 2;
					int oldY = j - image.getHeight() / 2;

					double newX = oldX * Math.cos(rotation) - oldY
							* Math.sin(rotation);
					double newY = oldX * Math.sin(rotation) + oldY
							* Math.cos(rotation);

					VillagerPathfinding.getInstance().setBlocked(
							(int)(x + newX), (int)(y + newY));
				}
			}
		}
	}

	public void sendBuilder() {
		materialsReserved++;
	}

	public boolean needsBuilders() {
		return !unreachable && materialsReserved + materials < constructionCost;
	}

	public void draw(Graphics g) {
		Image picture;
		if(isComplete()) {
			picture = image;
		} else {
			int index = materials
					/ (constructionCost / incompleteImages.length);
			if(index >= incompleteImages.length)
				index = incompleteImages.length - 1;
			picture = incompleteImages[index];
		}
		picture.setRotation(rotation);
		picture.draw(x - image.getWidth() / 2, y - image.getHeight() / 2);
		// g.drawLine(getDoorX(), getDoorY(), getDoorX(), getDoorY());
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getConstructionCost() {
		return constructionCost;
	}

	public void setConstructionCost(int constructionCost) {
		this.constructionCost = constructionCost;
	}

	public int getMaterials() {
		return materials;
	}

	public void setMaterials(int materials) {
		this.materials = materials;
	}

	public void addMaterials(int number) {
		materials += number;
		materialsReserved -= number;
		if(isComplete() && role == null) {
			role = Role.BUILDER;
			Village.getInstance().addBuilder();
		}
	}

	public boolean isComplete() {
		return materials >= constructionCost;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}
