package ld26.ai;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Path;

public class VillagerPathfinding {
	private static VillagerPathfinding	instance;

	private PathfindingMap				map;
	private AStarPathFinder				finder;

	private VillagerPathfinding() {

	}

	public static VillagerPathfinding getInstance() {
		if(instance == null) {
			instance = new VillagerPathfinding();
		}
		return instance;
	}

	public void init() {
		map = new PathfindingMap();
		finder = new AStarPathFinder(map, 10000, true);
	}

	public Path findPath(int startX, int startY, int endX, int endY) {
		return finder.findPath(null, startX, startY, endX, endY);
	}

	public void setBlocked(int x, int y) {
		map.setBlocked(x, y);
	}

	public void drawBlockMap(Graphics g) {
		map.draw(g);
	}
}
