package com.mossshade.golemSpawnChecker.client.config.dimensions;

public abstract class ElementDimensions {

    public int width;
    public int height;

    protected int offsetY = 0;

    public ElementDimensions(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public abstract int getX(int screenWidth);

    public int getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }

}
