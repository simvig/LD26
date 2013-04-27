package ld26.village;

import org.newdawn.slick.Image;

public class Building {

	private Image	image;
	private int		x;
	private int		y;
	private float	rotation;

	private int		constructionCost;
	private int		materials;

	private Image[]	incompleteImages;

	public void setIncompleteImages(Image[] incompleteImages) {
		this.incompleteImages = incompleteImages;
	}

	public Building(int x, int y, float rotation, Image image) {
		this.x = x;
		this.y = y;
		this.rotation = rotation;
		this.image = image;
	}

	public void draw() {
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
	}

	public boolean isComplete() {
		return materials >= constructionCost;
	}
}
