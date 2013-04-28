package ld26.ui;

import ld26.input.InputHandler;
import ld26.map.Map;
import ld26.village.Village;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class UI {
	private static Image tooltip;

	public static void draw(Graphics g) {
		drawCounts(g);
		drawTooltip(g);
	}

	private static void drawCounts(Graphics g) {
		g.drawString("Builders: " + Village.getInstance().getBuilders(), 400,
				670);
		g.drawString("Woodcutters: " + Village.getInstance().getWoodcutters(),
				400, 690);
		g.drawString("Farmers: " + Village.getInstance().getFarmers(), 400, 710);
		g.drawString("Population: " + Village.getInstance().getPopulation(),
				400, 730);
		g.drawString("Wood: " + Village.getInstance().getWood(), 650, 670);
		g.drawString("Food: " + Village.getInstance().getFood(), 650, 690);
	}

	private static void drawTooltip(Graphics g) {

		String string = "";
		Color color = Color.white;
		switch(Map.getInstance().getType()) {
			case 0:
				string = "Build Hut 5 Wood";
				if(Village.getInstance().getWood() < 5) {
					color = Color.red;
				}
				break;
			case 1:
				string = "Build Woodcutter 10 Wood";
				if(Village.getInstance().getWood() < 10) {
					color = Color.red;
				}
				break;
			case 2:
				string = "Build Farm 20 Wood";
				if(Village.getInstance().getWood() < 20) {
					color = Color.red;
				}
				break;
			default:
				return;
		}

		tooltip.draw(InputHandler.getInstance().mouseX,
				InputHandler.getInstance().mouseY - 20, 10 * string.length(),
				20);

		g.setColor(color);
		g.drawString(string, InputHandler.getInstance().mouseX + 5,
				InputHandler.getInstance().mouseY - 20);

		g.setColor(Color.white);
	}

	public static void init() {
		try {
			tooltip = new Image("Data/Images/tooltip.png");
		} catch(SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
