package me.hysong.sgl;

import lombok.Getter;
import me.hysong.sgl.interfaces.SGLEntityInterface;
import me.hysong.sgl.interfaces.SGLEntityRunnable;
import me.hysong.sgl.panels.SGLPanel;

import javax.swing.*;
import java.util.HashMap;

@Getter
public abstract class SGLEntity implements SGLEntityInterface {
    private final JPanel panel;
    private final String entityCode;
    private final String entityName;

    private final SGLPanel involvedPanel;

    public static final String GENERAL_ENTITY = ".generic";

    private final HashMap<String, SGLEntityRunnable> overlapEvents = new HashMap<>();

    public SGLEntity(String entityName, String entityCode, JPanel panel, SGLPanel involvedPanel) {
        if (entityCode.startsWith(".")) throw new RuntimeException("Entity code cannot start with a dot.");
        this.panel = panel;
        this.entityCode = entityCode;
        this.entityName = entityName;
        this.involvedPanel = involvedPanel;
    }

    public SGLEntity(JPanel panel, SGLPanel involvedPanel) {
        this.panel = panel;
        this.entityCode = GENERAL_ENTITY;
        this.entityName = "General Entity";
        this.involvedPanel = involvedPanel;
    }

    public int getX() {
        return panel.getX();
    }
    public void setX(int x) {
        panel.setLocation(x, panel.getY());
    }

    public int getY() {
        return panel.getY();
    }
    public void setY(int y) {
        panel.setLocation(panel.getX(), y);
    }

    public int getWidth() {
        return panel.getWidth();
    }
    public void setWidth(int width) {
        panel.setSize(width, panel.getHeight());
    }

    public int getHeight() {
        return panel.getHeight();
    }
    public void setHeight(int height) {
        panel.setSize(panel.getWidth(), height);
    }

    public int[][] getVertices(JPanel panel) {
        int[][] vertices = new int[4][2];
        vertices[0][0] = panel.getX();
        vertices[0][1] = panel.getY();
        vertices[1][0] = panel.getX() + panel.getWidth();
        vertices[1][1] = panel.getY();
        vertices[2][0] = panel.getX();
        vertices[2][1] = panel.getY() + panel.getHeight();
        vertices[3][0] = panel.getX() + panel.getWidth();
        vertices[3][1] = panel.getY() + panel.getHeight();
        return vertices;
    }

    public int[][] getVertices() {
        return getVertices(this.panel);
    }

    public boolean checkOverlapping(JPanel panel) {
        int[][] vertOther = getVertices(panel);
        int[][] vertSelf = getVertices();

        // Check if one rectangle is on the left side of the other
        boolean condition1 = vertSelf[1][0] < vertOther[0][0] || vertOther[1][0] < vertSelf[0][0];

        // Check if one rectangle is above the other
        boolean condition2 = vertSelf[2][1] < vertOther[0][1] || vertOther[2][1] < vertSelf[0][1];

        // If neither condition is true, rectangles are overlapping
        return !(condition1 || condition2);
    }


    public boolean checkOverlapping(SGLEntity entity) {
        return checkOverlapping(entity.panel);
    }

    public void runOverlappingEvent(SGLEntity entity) {
        SGLEntityRunnable event = overlapEvents.get(entity.entityCode);
        if (event == null) return;
        new Thread(() -> event.run(entity, involvedPanel)).start();
    }

    public void addOverlappingEvent(String entityCode, SGLEntityRunnable runnable) {
        overlapEvents.put(entityCode, runnable);
    }

    public void removeOverlappingEvent(String entityCode) {
        overlapEvents.remove(entityCode);
    }

    public double getRelativeDeviation(SGLEntity entity) {
        return getRelativeDeviation(entity.panel);
    }

    /**
     * Returns the relative deviation of the entity from the center of the panel.
     * @param panel The panel to compare with.
     * @return The relative deviation. 0.0 means the entity is at the center of the panel.
     */
    public double getRelativeDeviation(JPanel panel) {
        int otherXCenter = panel.getX() + panel.getWidth() / 2;
        int otherYCenter = panel.getY() + panel.getHeight() / 2;
        int selfXCenter = this.panel.getX() + this.panel.getWidth() / 2;
        int selfYCenter = this.panel.getY() + this.panel.getHeight() / 2;

        int xDiff = Math.abs(otherXCenter - selfXCenter);
        int yDiff = Math.abs(otherYCenter - selfYCenter);

        int xMax = panel.getWidth() / 2;
        int yMax = panel.getHeight() / 2;

        double xDeviation = (double) xDiff / xMax;
        double yDeviation = (double) yDiff / yMax;

        return Math.max(xDeviation, yDeviation);
    }

    public double[] getRelativeDeviationInVector(SGLEntity entity) {
        return getRelativeDeviationInVector(entity.panel);
    }

    public double[] getRelativeDeviationInVector(JPanel panel) {
        int otherXCenter = panel.getX() + panel.getWidth() / 2;
        int otherYCenter = panel.getY() + panel.getHeight() / 2;
        int selfXCenter = this.panel.getX() + this.panel.getWidth() / 2;
        int selfYCenter = this.panel.getY() + this.panel.getHeight() / 2;

        int xDiff = otherXCenter - selfXCenter;
        int yDiff = otherYCenter - selfYCenter;

        double magnitude = Math.sqrt(xDiff * xDiff + yDiff * yDiff);
        double angle = Math.atan2(-yDiff, xDiff); // Invert yDiff since screen coordinates have y increasing downwards

        // Convert angle to degrees and adjust for coordinate system
        angle = Math.toDegrees(angle);
        if (angle < 0) {
            angle += 360;
        }

        return new double[]{magnitude, angle};
    }


    public double getOverlapPercentage(SGLEntity entity) {
        return getOverlapPercentage(entity.panel);
    }

    public double getOverlapPercentage(JPanel panel) {
        int[][] vertOther = getVertices(panel);
        int[][] vertSelf = getVertices();

        // Find the coordinates of the overlapping rectangle
        int left = Math.max(vertSelf[0][0], vertOther[0][0]);
        int right = Math.min(vertSelf[1][0], vertOther[1][0]);
        int top = Math.max(vertSelf[0][1], vertOther[0][1]);
        int bottom = Math.min(vertSelf[2][1], vertOther[2][1]);

        // Check if there is no overlap
        if (left >= right || top >= bottom) {
            return 0.0;
        }

        // Calculate area of overlapping rectangle
        int overlapArea = (right - left) * (bottom - top);

        // Calculate area of the smaller panel
        int areaSelf = panel.getWidth() * panel.getHeight();
        int areaOther = this.panel.getWidth() * this.panel.getHeight();
        int smallerArea = Math.min(areaSelf, areaOther);

        // Calculate and return the percentage
        return (double) overlapArea / smallerArea * 100;
    }

    public void kill() {
        involvedPanel.getWindow().dropEntity(entityName);
    }
}
