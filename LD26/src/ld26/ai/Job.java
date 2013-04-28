package ld26.ai;

import ld26.village.Person;

public abstract class Job {
	protected Person owner;

	public Job(Person owner) {
		this.owner = owner;
	}

	public abstract void doJob();
}
