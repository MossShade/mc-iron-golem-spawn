package com.mossshade.golemSpawnChecker.client.config;

import net.minecraft.text.Text;

public class Label {

    public Text text;
    public int x;
    public int y;
    public int color;

    public Label(Text text, int y, int color) {
        this.text = text;
        this.y = y;
        this.color = color;
    }

}
