package ld26.ai;

import ld26.village.Person;

public class GoHomeJob extends Job {

	public GoHomeJob(Person owner) {
		super(owner);
	}

	/*
	 * (non-Javadoc)
	 * @see ld26.ai.Job#doJob()
	 */
	@Override
	public void doJob() {
		owner.setDone(true);
	}

}
