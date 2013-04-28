package ld26.ui;

import ld26.Main;
import ld26.input.InputHandler;
import ld26.map.Map;
import ld26.village.Village;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class UI {
	private static Image builder;
	private static Image chopper;
	private static Image farmer;
	private static Image food;
	private static Image miner;
	private static Image paused;
	private static Image person;
	private static Image stone;
	private static Image tooltip;
	private static Image wine;
	private static Image winemaker;

	private static Image wood;

	public static void draw(Graphics g) {
		drawCounts(g);
		if(!Main.statePaused && Main.message == null) {
			drawTooltip(g);
		} else {
			paused.draw(1024 / 2 - 50, 768 / 2 - 10);
		}
		if(Main.message != null) {
			Main.message.draw();
		}
	}

	private static void drawCounts(Graphics g) {
		builder.draw(402, 672);
		chopper.draw(402, 692);
		farmer.draw(402, 712);
		person.draw(402, 732);

		miner.draw(502, 672);
		winemaker.draw(502, 692);

		wood.draw(652, 672);
		food.draw(652, 692);
		stone.draw(652, 712);
		wine.draw(652, 732);

		g.drawString("" + Village.getInstance().getBuilders(), 420, 670);
		g.drawString("" + Village.getInstance().getWoodcutters(), 420, 690);
		g.drawString("" + Village.getInstance().getFarmers(), 420, 710);
		g.drawString("" + Village.getInstance().getPopulation(), 420, 730);

		g.drawString("" + Village.getInstance().getMiners(), 520, 670);
		g.drawString("" + Village.getInstance().getWinemakers(), 520, 690);

		g.drawString("" + Village.getInstance().getWood(), 670, 670);
		g.drawString("" + Village.getInstance().getFood(), 670, 690);
		g.drawString("" + Village.getInstance().getStone(), 670, 710);
		g.drawString("" + Village.getInstance().getWine(), 670, 730);
	}

	private static void drawTooltip(Graphics g) {

		String string = "";
		Color color = Color.white;

		boolean drawWood = false;

		int area = Map.getInstance().getSelectedArea();
		if(area != -1) {
			if(Map.getInstance().getFull(area)) {
				string = "Area Full";
				color = Color.red;
				// System.out.println("Area " + area + " full");
			} else if(!Map.getInstance().getBlocked(area)) {
				switch(Map.getInstance().getType()) {
					case 0:
						string = "Build Hut 5";
						if(Village.getInstance().getWood() < 5) {
							color = Color.red;
						}
						break;
					case 1:
						string = "Build Woodcutter 10";
						if(Village.getInstance().getWood() < 10) {
							color = Color.red;
						}
						break;
					case 2:
						string = "Build Farm 20";
						if(Village.getInstance().getWood() < 20) {
							color = Color.red;
						}
						break;
					case 3:
						string = "Build Mine 10";
						if(Village.getInstance().getWood() < 10) {
							color = Color.red;
						}
						break;
					case 4:
						string = "Build Winery 20";
						if(Village.getInstance().getWood() < 20) {
							color = Color.red;
						}
						break;
					case 5:
						string = "Build Bridge 20";
						if(Village.getInstance().getWood() < 20) {
							color = Color.red;
						}
						break;
					default:
						return;
				}
				drawWood = true;
			} else {
				color = Color.yellow;
				string = "Area Inaccessible";
			}
			tooltip.draw(InputHandler.getInstance().mouseX,
					InputHandler.getInstance().mouseY - 20,
					10 * string.length() + 20, 20);
			if(drawWood) {
				wood.draw(
						InputHandler.getInstance().mouseX + 9.7f
								* string.length(),
						InputHandler.getInstance().mouseY - 18);
			}

			g.setColor(color);
			g.drawString(string, InputHandler.getInstance().mouseX + 5,
					InputHandler.getInstance().mouseY - 20);

			g.setColor(Color.white);
		}
	}

	public static void init() {
		try {
			tooltip = new Image("Data/Images/tooltip.png");
			wood = new Image("Data/Images/wood.png");
			food = new Image("Data/Images/food.png");
			builder = new Image("Data/Images/builder.png");
			chopper = new Image("Data/Images/chopper.png");
			farmer = new Image("Data/Images/farmer.png");
			person = new Image("Data/Images/person.png");
			stone = new Image("Data/Images/stone.png");
			miner = new Image("Data/Images/miner.png");
			wine = new Image("Data/Images/wine.png");
			winemaker = new Image("Data/Images/winemaker.png");

			paused = new Image("Data/images/paused.jpg");
		} catch(SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
