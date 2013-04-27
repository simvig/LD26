package ld26.village;

import org.newdawn.slick.Graphics;

public class Person {
	private int	x;
	private int	y;

	public Person(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void draw(Graphics g) {
		g.drawLine(x, y, x, y - 1);
	}

	public void update() {
		x += Math.random() * 3 - 1;
		y += Math.random() * 3 - 1;
	}
}
