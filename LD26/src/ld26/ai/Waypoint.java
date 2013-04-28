package ld26.ai;

import ld26.village.Building;

public class Waypoint {
	private Building destination;
	private Job job;

	public Waypoint(Building destination, Job job) {
		this.destination = destination;
		this.job = job;
	}

	public Building getDestination() {
		return destination;
	}

	public Job getJob() {
		return job;
	}
}
