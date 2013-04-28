package ld26.ai;

import ld26.village.Building;
import ld26.village.Person;

public class BuildJob extends Job {
	private Building building;

	public BuildJob(Person owner, Building building) {
		super(owner);
		this.building = building;
	}

	/*
	 * (non-Javadoc)
	 * @see ld26.ai.Job#doJob()
	 */
	@Override
	public void doJob() {
		building.addMaterials(1);
	}

}
