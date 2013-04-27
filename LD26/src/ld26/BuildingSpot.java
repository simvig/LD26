package ld26;

public class BuildingSpot {
	public int		x;
	public int		y;
	public int		size;
	public float	rotation;
	public int		area;

	// public boolean occupied = false;

	public BuildingSpot(int x, int y, int size, float rotation, int area) {
		super();
		this.x = x;
		this.y = y;
		this.size = size;
		this.rotation = rotation;
		this.area = area;
	}

	public String toString() {
		return area + ", " + x + ", " + y + ", " + rotation + ", " + size;
	}
}
