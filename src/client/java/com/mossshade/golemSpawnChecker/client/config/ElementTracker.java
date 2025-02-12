package com.mossshade.golemSpawnChecker.client.config;

import com.mossshade.golemSpawnChecker.client.config.dimensions.ElementDimensions;
import net.minecraft.text.Text;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ElementTracker {

    private final Map<String, Element> elementsMap = new LinkedHashMap<>();

    private int totalHeight = 0;

    public void addElement(final String key, final ElementDimensions dimensions) {
        this.addElement(key, dimensions, false, 0);
    }

    public void addElement(final String key, final ElementDimensions dimensions, final boolean isLabeled, final int color) {
        dimensions.setOffsetY(totalHeight);
        elementsMap.put(key, new Element(isLabeled, color, dimensions));
        totalHeight += dimensions.height;
    }

    public void addSpacer(final int height) {
        totalHeight += height;
    }

    public Element getElement(final String key) {
        return elementsMap.get(key);
    }

    public ElementDimensions getDimensions(final String key) {
        return this.getElement(key).dimensions;
    }

    public int getYFor(final String key, final int screenHeight) {
        return ((screenHeight - totalHeight) / 2) + this.getDimensions(key).getOffsetY();
    }

    public int getYForLabelOf(final String key, final int screenHeight, final int labelHeight) {
        return this.getYFor(key, screenHeight) + ((this.getDimensions(key).height - labelHeight) / 2);
    }

    public List<Label> getLabels(final int screenHeight, final int labelHeight) {
        return elementsMap.entrySet().stream()
                .filter(entry -> entry.getValue().isLabeled)
                .map(entry -> new Label(
                        Text.translatable(entry.getKey()).append(": "),
                        this.getYForLabelOf(entry.getKey(), screenHeight, labelHeight),
                        entry.getValue().color)
                ).collect(Collectors.toList());
    }

}
