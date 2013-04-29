package ld26.ai;

import java.util.HashMap;
import java.util.Map;

import ld26.village.Village;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Path;

public class VillagerPathfinding {
	private static VillagerPathfinding instance;

	public static VillagerPathfinding getInstance() {
		if(instance == null) {
			instance = new VillagerPathfinding();
		}
		return instance;
	}

	private AStarPathFinder finder;

	private PathfindingMap map;

	private Map<String, Path> pathCache;

	private VillagerPathfinding() {

	}

	public void drawBlockMap(Graphics g) {
		map.draw(g);
	}

	// Biggest hack I've ever written
	public Path findPath(int startX, int startY, int endX, int endY) {
		String pathString = startX + ", " + startY + ", " + endX + ", " + endY;
		Path path = pathCache.get(pathString);
		if(path != null) {
			return path;
		}
		boolean startLeft = ld26.map.Map.getInstance().isLeft(startX, startY);
		boolean endLeft = ld26.map.Map.getInstance().isLeft(endX, endY);

		boolean startIsland = ld26.map.Map.getInstance().isIsland(startX,
				startY);
		boolean endIsland = ld26.map.Map.getInstance().isIsland(endX, endY);

		boolean startRight = ld26.map.Map.getInstance().isRight(startX, startY);

		boolean endRight = ld26.map.Map.getInstance().isRight(endX, endY);

		if((startLeft && endRight) || (startRight && endLeft)) { // need to
																	// go
																	// across
																	// the
																	// river
			Path part1 = finder.findPath(null, startX, startY,
					Village.getInstance().nearestBridge.getX(),
					Village.getInstance().nearestBridge.getY());
			Path part2 = finder.findPath(null,
					Village.getInstance().nearestBridge.getX(),
					Village.getInstance().nearestBridge.getY(), endX, endY);
			path = part1;
			for(int i = 0; i < part2.getLength(); i++) {
				path.appendStep(part2.getX(i), part2.getY(i));
			}
		} else if(startLeft && endIsland) {
			Path part1 = finder.findPath(null, startX, startY, 282, 499);
			Path part2 = finder.findPath(null, 282, 499, 202, 609);
			Path part3 = finder.findPath(null, 202, 609, endX, endY);
			path = part1;
			for(int i = 0; i < part2.getLength(); i++) {
				path.appendStep(part2.getX(i), part2.getY(i));
			}
			for(int i = 0; i < part3.getLength(); i++) {
				path.appendStep(part3.getX(i), part3.getY(i));
			}
		} else if(startIsland && endLeft) {
			Path part1 = finder.findPath(null, startX, startY, 202, 609);
			Path part2 = finder.findPath(null, 202, 609, 282, 499);
			Path part3 = finder.findPath(null, 282, 499, endX, endY);
			path = part1;
			for(int i = 0; i < part2.getLength(); i++) {
				path.appendStep(part2.getX(i), part2.getY(i));
			}
			for(int i = 0; i < part3.getLength(); i++) {
				path.appendStep(part3.getX(i), part3.getY(i));
			}
		} else if(startRight && endIsland) {
			Path part1 = finder.findPath(null, startX, startY,
					Village.getInstance().nearestBridge.getX(),
					Village.getInstance().nearestBridge.getY());
			Path part2 = finder.findPath(null,
					Village.getInstance().nearestBridge.getX(),
					Village.getInstance().nearestBridge.getY(), 282, 499);
			Path part3 = finder.findPath(null, 282, 499, 202, 609);
			Path part4 = finder.findPath(null, 202, 609, endX, endY);
			path = part1;
			for(int i = 0; i < part2.getLength(); i++) {
				path.appendStep(part2.getX(i), part2.getY(i));
			}
			for(int i = 0; i < part3.getLength(); i++) {
				path.appendStep(part3.getX(i), part3.getY(i));
			}
			for(int i = 0; i < part4.getLength(); i++) {
				path.appendStep(part4.getX(i), part4.getY(i));
			}
		} else if(startIsland && endRight) {
			Path part1 = finder.findPath(null, startX, startY, 202, 609);
			Path part2 = finder.findPath(null, 202, 609, 282, 499);
			Path part3 = finder.findPath(null, 282, 499,
					Village.getInstance().nearestBridge.getX(),
					Village.getInstance().nearestBridge.getY());
			Path part4 = finder.findPath(null,
					Village.getInstance().nearestBridge.getX(),
					Village.getInstance().nearestBridge.getY(), endX, endY);
			path = part1;
			for(int i = 0; i < part2.getLength(); i++) {
				path.appendStep(part2.getX(i), part2.getY(i));
			}
			for(int i = 0; i < part3.getLength(); i++) {
				path.appendStep(part3.getX(i), part3.getY(i));
			}
			for(int i = 0; i < part4.getLength(); i++) {
				path.appendStep(part4.getX(i), part4.getY(i));
			}
		} else {
			path = finder.findPath(null, startX, startY, endX, endY);
		}
		pathCache.put(pathString, path);
		return path;
	}

	public void init() {
		map = new PathfindingMap();
		finder = new AStarPathFinder(map, 10000, true);
		pathCache = new HashMap<String, Path>();
	}

	public void setBlocked(int x, int y) {
		map.setBlocked(x, y);
	}

	public void setUnblocked(int x, int y) {
		map.setUnblocked(x, y);
	}
}
