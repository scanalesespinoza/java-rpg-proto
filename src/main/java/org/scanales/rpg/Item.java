package org.scanales.rpg;

public class Item {
    private String name;
    private int power;

    // Constructors, getters, and setters

    public void use(Player player) {
        // Implementation of item usage logic
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}
}
