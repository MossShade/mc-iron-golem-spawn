package com.mossshade.golemSpawnChecker.client.config;

import com.mossshade.golemSpawnChecker.client.config.dimensions.ElementDimensions;

public class Element {
    public boolean isLabeled;
    public int color;
    public ElementDimensions dimensions;

    public Element(final boolean isLabeled, final int color, final ElementDimensions dimensions) {
        this.isLabeled = isLabeled;
        this.color = color;
        this.dimensions = dimensions;
    }
}
