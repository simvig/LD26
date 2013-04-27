package ld26.village;

import ld26.ai.Job;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.util.pathfinding.Path;

public class Person {

	private Role	role;

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	private int		x;
	private int		y;

	int				index	= 0;

	private Path	path;
	private Job		job;

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public Path getPath() {
		return path;
	}

	public void setPath(Path path) {
		this.path = path;
	}

	public Person(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void draw(Graphics g) {
		g.drawLine(x, y, x, y - 1);
	}

	public void update() {
		if(!returning) {
			if(index < path.getLength()) {
				x = path.getX(index);
				y = path.getY(index);
				index++;
			} else {
				job.doJob();
				returning = true;
				index--;
			}
		} else {
			if(index >= 0) {
				x = path.getX(index);
				y = path.getY(index);
				index--;
			} else {
				done = true;
			}
		}
	}

	private boolean	done;

	public boolean isDone() {
		return done;
	}

	private boolean	returning	= false;
}
