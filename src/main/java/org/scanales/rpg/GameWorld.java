package org.scanales.rpg;

import java.util.List;

public class GameWorld {
    private List<Location> locations;

    // Constructors, getters, and setters

    public void movePlayerTo(Location location) {
        // Implementation of player movement logic
    }

	public List<Location> getLocations() {
		return locations;
	}

	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}

    // Other methods
}
