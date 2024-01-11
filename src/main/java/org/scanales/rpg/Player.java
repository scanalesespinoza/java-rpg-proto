package org.scanales.rpg;

public class Player {
    private int health;
    private int attack;
    private int defense;

    // Constructors, getters, and setters

    public void attack(Character target) {
        // Implementation of attack logic
    }

    public void takeDamage(int damage) {
        // Implementation of damage calculation
    }

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

    // Other methods
}
