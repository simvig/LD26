package ld26.village;

import java.util.ArrayList;
import java.util.List;

import ld26.ai.Job;
import ld26.ai.VillagerPathfinding;
import ld26.ai.Waypoint;
import ld26.map.Roads;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.util.pathfinding.Path;

public class Person {

	private Building destination;

	private boolean done;

	private Building home;

	int index = 0;

	private Job job;
	private boolean moving = false;

	private Path path;

	private Role role;

	private int routeIndex = -1;
	private Building source;

	private List<Waypoint> waypoints;

	private int x = -1;

	private int y = -1;

	public Person(Building home) {
		this.home = home;
		waypoints = new ArrayList<>();
	}

	public void addWaypoint(Waypoint wp) {
		waypoints.add(wp);
	}

	public void draw(Graphics g) {
		g.drawLine(x, y, x, y - 1);
	}

	public Building getHome() {
		return home;
	}

	public Job getJob() {
		return job;
	}

	public Path getPath() {
		return path;
	}

	public Role getRole() {
		return role;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	private void setNextPath() {
		routeIndex++;
		source = destination;
		destination = waypoints.get(routeIndex).getDestination();
		path = VillagerPathfinding.getInstance().findPath(source.getDoorX(),
				source.getDoorY(), destination.getDoorX(),
				destination.getDoorY());
		if(path == null) {
			done = true;
		} else {
			moving = true;
		}
	}

	public void setPath(Path path) {
		this.path = path;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public void startRoute() {
		destination = home;
		setNextPath();
	}

	public void update() {
		if(moving) {
			if(index < path.getLength()) {
				x = path.getX(index);
				y = path.getY(index);
				Roads.advanceRoad(x, y);
				index++;
			} else {
				waypoints.get(routeIndex).getJob().doJob();
				index = 0;
				if(routeIndex < waypoints.size() - 1) {
					setNextPath();
				} else {
					moving = false;
				}
			}
		}
	}
}
