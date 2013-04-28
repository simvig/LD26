package ld26.ai;

import ld26.ui.SoundManager;
import ld26.village.Person;
import ld26.village.Village;

public class DeliverWineJob extends Job {

	public DeliverWineJob(Person owner) {
		super(owner);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * @see ld26.ai.Job#doJob()
	 */
	@Override
	public void doJob() {
		Village.getInstance().addWine(1);
		SoundManager.playWine();
	}

}
