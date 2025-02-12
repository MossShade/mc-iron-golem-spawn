package com.mossshade.golemSpawnChecker.client.config.dimensions;

public class RightJustifiedElement extends ElementDimensions {

    public RightJustifiedElement(int width, int height) {
        super(width, height);
    }

    @Override
    public int getX(int screenWidth) {
        return (screenWidth) / 2;
    }

}
