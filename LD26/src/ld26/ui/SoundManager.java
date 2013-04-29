package ld26.ui;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class SoundManager {
	private static Sound[] buildSound;
	private static Sound[] chopSound;
	private static Sound[] foodSound;
	private static Sound[] goldSound;
	private static Sound[] mineSound;

	private static Sound[] wineSound;

	public static void init() throws SlickException {
		buildSound = new Sound[3];
		buildSound[0] = new Sound("Data/Sounds/build0.wav");
		buildSound[1] = new Sound("Data/Sounds/build1.wav");
		buildSound[2] = new Sound("Data/Sounds/build2.wav");

		chopSound = new Sound[3];
		chopSound[0] = new Sound("Data/Sounds/chop0.wav");
		chopSound[1] = new Sound("Data/Sounds/chop1.wav");
		chopSound[2] = new Sound("Data/Sounds/chop2.wav");

		mineSound = new Sound[3];
		mineSound[0] = new Sound("Data/Sounds/mine0.wav");
		mineSound[1] = new Sound("Data/Sounds/mine1.wav");
		mineSound[2] = new Sound("Data/Sounds/mine2.wav");

		goldSound = new Sound[3];
		goldSound[0] = new Sound("Data/Sounds/gold0.wav");
		goldSound[1] = new Sound("Data/Sounds/gold1.wav");
		goldSound[2] = new Sound("Data/Sounds/gold2.wav");

		wineSound = new Sound[3];
		wineSound[0] = new Sound("Data/Sounds/wine0.wav");
		wineSound[1] = new Sound("Data/Sounds/wine1.wav");
		wineSound[2] = new Sound("Data/Sounds/wine2.wav");

		foodSound = new Sound[3];
		foodSound[0] = new Sound("Data/Sounds/food0.wav");
		foodSound[1] = new Sound("Data/Sounds/food1.wav");
		foodSound[2] = new Sound("Data/Sounds/food2.wav");
	}

	public static void playBuild() {
		if(Math.random() < 0.05) {
			buildSound[(int)(Math.random() * 3)].play();
		}
	}

	public static void playChop() {
		if(Math.random() < 0.05) {
			chopSound[(int)(Math.random() * 3)].play();
		}
	}

	public static void playFood() {
		if(Math.random() < 0.05) {
			foodSound[(int)(Math.random() * 3)].play();
		}
	}

	public static void playGold() {
		if(Math.random() < 0.05) {
			goldSound[(int)(Math.random() * 3)].play();
		}
	}

	public static void playMine() {
		if(Math.random() < 0.05) {
			mineSound[(int)(Math.random() * 3)].play();
		}
	}

	public static void playWine() {
		if(Math.random() < 0.05) {
			wineSound[(int)(Math.random() * 3)].play();
		}
	}
}
