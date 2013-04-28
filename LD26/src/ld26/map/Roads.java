package ld26.map;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import slicktests.pixel.PixelData;

public class Roads {
	private static int counter = 0;
	private static PixelData pixels;

	private static Image roadImage;
	private static int threshold = 1000;

	public static void advanceRoad(int x, int y) {
		counter++;
		Color c = pixels.getPixel(x, y);
		c.a += 0.01;
		pixels.setPixel(x, y, c);
		if(counter > threshold) {
			counter = 0;
			pixels.apply(roadImage.getTexture());
		}
	}

	public static void draw() {
		roadImage.draw();
	}

	public static void init() {
		try {
			roadImage = new Image("Data/Images/paths.png");

			pixels = new PixelData(1024, 768);
			for(int i = 0; i < 1024; i++) {
				for(int j = 0; j < 768; j++) {
					Color c = roadImage.getColor(i, j);
					c.a = 0;
					pixels.setPixel(i, j, c);
				}
			}

			pixels.apply(roadImage.getTexture());
		} catch(SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
