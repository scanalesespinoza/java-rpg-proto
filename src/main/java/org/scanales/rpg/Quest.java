package org.scanales.rpg;

import java.util.List;

public class Quest {
    private List<Objective> objectives;

    // Constructors, getters, and setters

    public boolean isComplete() {
		return false;
        // Implementation to check if all objectives are complete
    }

	public List<Objective> getObjectives() {
		return objectives;
	}

	public void setObjectives(List<Objective> objectives) {
		this.objectives = objectives;
	}

    // Other methods
}
