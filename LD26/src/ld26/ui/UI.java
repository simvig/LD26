package ld26.ui;

import ld26.village.Village;

import org.newdawn.slick.Graphics;

public class UI {
	public static void draw(Graphics g) {
		g.drawString("Builders: " + Village.getInstance().getBuilders(), 400,
				670);
	}
}
