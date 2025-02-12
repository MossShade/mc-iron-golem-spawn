package com.mossshade.golemSpawnChecker.client.config.dimensions;

public class LeftJustifiedElement extends ElementDimensions {

    public LeftJustifiedElement(int width, int height) {
        super(width, height);
    }

    @Override
    public int getX(int screenWidth) {
        return (screenWidth / 2) - this.width;
    }

}
