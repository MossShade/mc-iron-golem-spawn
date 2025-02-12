package com.mossshade.golemSpawnChecker.client.config.dimensions;

public class CenteredElement extends ElementDimensions {

    public CenteredElement(int width, int height) {
        super(width, height);
    }

    @Override
    public int getX(int screenWidth) {
        return (screenWidth - this.width) / 2;
    }

}
