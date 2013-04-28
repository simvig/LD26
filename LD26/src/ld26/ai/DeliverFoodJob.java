package ld26.ai;

import ld26.village.Person;
import ld26.village.Village;

public class DeliverFoodJob extends Job {

	public DeliverFoodJob(Person owner) {
		super(owner);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * @see ld26.ai.Job#doJob()
	 */
	@Override
	public void doJob() {
		Village.getInstance().addFood(1);
	}

}
