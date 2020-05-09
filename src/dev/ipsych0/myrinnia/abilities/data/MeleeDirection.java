package dev.ipsych0.myrinnia.abilities.data;

public class MeleeDirection {

    private double rotation, xPos, yPos;

    public MeleeDirection(double rotation, double xPos, double yPos) {
        this.rotation = rotation;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public double getxPos() {
        return xPos;
    }

    public void setxPos(double xPos) {
        this.xPos = xPos;
    }

    public double getyPos() {
        return yPos;
    }

    public void setyPos(double yPos) {
        this.yPos = yPos;
    }
}
