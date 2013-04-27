package ld26.ai;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

public class PathfindingMap implements TileBasedMap {

	private boolean[][]	map;

	public void setBlocked(int x, int y) {
		map[x][y] = true;
	}

	public void setUnblocked(int x, int y) {
		map[x][y] = false;
	}

	public PathfindingMap() {
		map = new boolean[1024][768];
	}

	public void draw(Graphics g) {
		for(int i = 0; i < 1024; i++) {
			for(int j = 0; j < 768; j++) {
				if(map[i][j]) {
					g.drawLine(i, j, i, j);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.newdawn.slick.util.pathfinding.TileBasedMap#blocked(org.newdawn.slick
	 * .util.pathfinding.PathFindingContext, int, int)
	 */
	@Override
	public boolean blocked(PathFindingContext context, int x, int y) {
		return map[x][y];
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.newdawn.slick.util.pathfinding.TileBasedMap#getCost(org.newdawn.slick
	 * .util.pathfinding.PathFindingContext, int, int)
	 */
	@Override
	public float getCost(PathFindingContext arg0, int arg1, int arg2) {
		return 1;
	}

	/*
	 * (non-Javadoc)
	 * @see org.newdawn.slick.util.pathfinding.TileBasedMap#getHeightInTiles()
	 */
	@Override
	public int getHeightInTiles() {
		return 768;
	}

	/*
	 * (non-Javadoc)
	 * @see org.newdawn.slick.util.pathfinding.TileBasedMap#getWidthInTiles()
	 */
	@Override
	public int getWidthInTiles() {
		return 1024;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.newdawn.slick.util.pathfinding.TileBasedMap#pathFinderVisited(int,
	 * int)
	 */
	@Override
	public void pathFinderVisited(int arg0, int arg1) {
		// TODO Auto-generated method stub

	}

}
