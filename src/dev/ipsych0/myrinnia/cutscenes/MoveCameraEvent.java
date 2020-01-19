package dev.ipsych0.myrinnia.cutscenes;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.gfx.GameCamera;
import dev.ipsych0.myrinnia.input.KeyManager;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.Serializable;

public class MoveCameraEvent implements CutsceneEvent, Serializable {

    private static final long serialVersionUID = 4734735791005699168L;
    private double xStart, yStart, xEnd, yEnd;
    private double xDiff, yDiff;
    private double xMove, yMove;
    private double xMoved, yMoved;
    private boolean xFinished, yFinished;
    private GameCamera camera;
    private boolean reverse;
    private boolean hasReturned;
    private boolean hasSwapped;

    /*
     * TODO: Commented out for now, fix later
     */
//    public MoveCameraEvent(double xStart, double yStart, double xEnd, double yEnd, boolean reverse) {
//        this.xStart = xStart - Handler.get().getWidth() / 2d;
//        this.yStart = yStart - Handler.get().getHeight() / 2d;
//        this.xEnd = xEnd - Handler.get().getWidth() / 2d;
//        this.yEnd = yEnd - Handler.get().getHeight() / 2d;
//        this.reverse = reverse;
//
//        correctCoordinates();
//        getDirections();
//
//        camera = Handler.get().getGameCamera();
//    }
//
//    public MoveCameraEvent(double xStart, double yStart, double xEnd, double yEnd) {
//        this(xStart, yStart, xEnd, yEnd, true);
//    }

    public MoveCameraEvent(Rectangle start, Rectangle end, boolean reverse) {
        this.xStart = start.getX() - Handler.get().getWidth() / 2d - start.getWidth() / 2d;
        this.yStart = start.getY() - Handler.get().getHeight() / 2d - start.getHeight() / 2d;
        this.xEnd = end.getX() - Handler.get().getWidth() / 2d + end.getWidth() / 2d;
        this.yEnd = end.getY() - Handler.get().getHeight() / 2d + end.getHeight() / 2d;
        this.reverse = reverse;

        correctCoordinates();
        getDirections();

        camera = Handler.get().getGameCamera();
    }

    public MoveCameraEvent(Rectangle start, Rectangle end) {
        this(start, end, true);
    }

    private void correctCoordinates() {
        if (xEnd < 0) {
            xEnd = 0;
        } else if (xEnd > Handler.get().getWidth()) {
            xEnd = Handler.get().getWidth();
        }

        if (yEnd < 0) {
            yEnd = 0;
        } else if (yEnd > Handler.get().getHeight()) {
            yEnd = Handler.get().getHeight();
        }
    }

    private void getDirections() {
        xDiff = xEnd - xStart;
        yDiff = yEnd - yStart;
        double cameraSpeed = getCameraSpeed();

        if (xDiff < 0) {
            xMove = -1 * cameraSpeed;
        } else if (xDiff == 0) {
            xMove = 0;
        } else {
            xMove = cameraSpeed;
        }

        if (yDiff < 0) {
            yMove = -1 * cameraSpeed;
        } else if (yDiff == 0) {
            yMove = 0;
        } else {
            yMove = cameraSpeed;
        }
    }

    private double getCameraSpeed() {
        // Default speed, increase depending on distance
        double speed = 2.0;

        double xDiff = Math.abs(this.xDiff);
        double yDiff = Math.abs(this.yDiff);

        speed += (int) ((xDiff + yDiff) / 400);

        return speed;
    }

    public void tick() {
        if (!xFinished) {
            camera.moveX(xMove);
            xMoved += xMove;
            if (Math.abs(xMoved) >= Math.abs(xDiff)) {
                xFinished = true;
            }
            if (xMove < 0 && camera.isAtLeftBound() || xMove > 0 && camera.isAtRightBound()) {
                xFinished = true;
            }
        }

        if (!yFinished) {
            camera.moveY(yMove);
            yMoved += yMove;
            if (Math.abs(yMoved) >= Math.abs(yDiff)) {
                yFinished = true;
            }
            if (yMove < 0 && camera.isAtTopBound() || yMove > 0 && camera.isAtBottomBound()) {
                yFinished = true;
            }
        }

        if (xFinished && yFinished && reverse) {
            if (!hasSwapped && Handler.get().getKeyManager().talk) {
                swapCoords();
                hasSwapped = true;
            } else if (hasSwapped) {
                hasReturned = true;
            }
        }
    }

    private void swapCoords() {
        double xStart = this.xStart, xEnd = this.xEnd;
        double yStart = this.yStart, yEnd = this.yEnd;

        double tempX = xStart;
        this.xStart = xEnd;
        this.xEnd = tempX;

        double tempY = yStart;
        this.yStart = yEnd;
        this.yEnd = tempY;

        getDirections();
        xMoved = 0;
        yMoved = 0;

        xFinished = false;
        yFinished = false;
    }

    public void render(Graphics2D g) {
        if (reverse) {
            if (xFinished && yFinished) {
                if (!hasSwapped) {
                    Text.drawString(g, "Press '" + KeyEvent.getKeyText(KeyManager.interactKey) + "' to continue.", Handler.get().getWidth() / 2, Handler.get().getHeight() / 2, true, Color.YELLOW, Assets.font20);
                }
            }
        } else {
            if (xFinished && yFinished) {
                Text.drawString(g, "Press '" + KeyEvent.getKeyText(KeyManager.interactKey) + "' to continue.", Handler.get().getWidth() / 2, Handler.get().getHeight() / 2, true, Color.YELLOW, Assets.font20);
            }
        }
    }

    @Override
    public boolean isFinished() {
        if (reverse) {
            return hasReturned;
        } else {
            return xFinished && yFinished && Handler.get().getKeyManager().talk;
        }
    }
}
