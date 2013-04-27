package ld26.ai;

import ld26.village.Building;

public class BuildJob implements Job {
	private Building	building;

	public BuildJob(Building building) {
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
