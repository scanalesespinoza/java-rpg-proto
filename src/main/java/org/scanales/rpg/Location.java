package org.scanales.rpg;


public class Location {
    private double x;
    private double y;
    private double z;

    public Location(double x, double y, double z) {
        this.setX(x);
        this.setY(y);
        this.setZ(z);
    }

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

    // Getters and setters for x, y, z

    // Other methods as needed
}
