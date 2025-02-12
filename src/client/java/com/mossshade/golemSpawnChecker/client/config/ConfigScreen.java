package com.mossshade.golemSpawnChecker.client.config;

import com.mossshade.golemSpawnChecker.client.Constants;
import com.mossshade.golemSpawnChecker.client.GolemSpawnCheckerClient;
import com.mossshade.golemSpawnChecker.client.config.dimensions.CenteredElement;
import com.mossshade.golemSpawnChecker.client.config.dimensions.ElementDimensions;
import com.mossshade.golemSpawnChecker.client.config.dimensions.LeftJustifiedElement;
import com.mossshade.golemSpawnChecker.client.config.dimensions.RightJustifiedElement;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;

import java.util.List;

public class ConfigScreen extends Screen {
    private final Screen parent;
    private String listeningForKey = "";

    private final ElementTracker elementTracker = new ElementTracker();

    private final KeyBinding scanKey;
    private final KeyBinding clearKey;

    public ConfigScreen(Screen parent) {
        super(Text.translatable(Constants.CATEGORY));
        this.parent = parent;

        this.scanKey = GolemSpawnCheckerClient.getScanKey();
        this.clearKey = GolemSpawnCheckerClient.getClearKey();

        elementTracker.addElement(Constants.SCAN_KEY, new RightJustifiedElement(40, 20), true, 0xFFFFFF);
        elementTracker.addElement(Constants.CLEAR_KEY, new RightJustifiedElement(40, 20), true, 0xFFFFFF);

        elementTracker.addSpacer(50);

        elementTracker.addElement(Constants.DONE_BUTTON, new CenteredElement(200, 20));
    }

    @Override
    protected void init() {
        this.addDrawableChild(ButtonWidget.builder(Text.translatable(scanKey.getBoundKeyTranslationKey()),
                        button -> listeningForKey = Constants.SCAN_KEY)
                .dimensions(
                        elementTracker.getDimensions(Constants.SCAN_KEY).getX(width),
                        elementTracker.getYFor(Constants.SCAN_KEY, height),
                        elementTracker.getDimensions(Constants.SCAN_KEY).width,
                        elementTracker.getDimensions(Constants.SCAN_KEY).height
                )
                .build());

        this.addDrawableChild(ButtonWidget.builder(Text.translatable(clearKey.getBoundKeyTranslationKey()),
                        button -> listeningForKey = Constants.CLEAR_KEY)
                .dimensions(
                        elementTracker.getDimensions(Constants.CLEAR_KEY).getX(width),
                        elementTracker.getYFor(Constants.CLEAR_KEY, height),
                        elementTracker.getDimensions(Constants.CLEAR_KEY).width,
                        elementTracker.getDimensions(Constants.CLEAR_KEY).height
                )
                .build());

        this.addDrawableChild(ButtonWidget.builder(Text.translatable(Constants.DONE_BUTTON),
                        button -> {
                            if (this.client != null) {
                                this.client.setScreen(this.parent);
                            }
                        })
                .dimensions(
                        elementTracker.getDimensions(Constants.DONE_BUTTON).getX(width),
                        elementTracker.getYFor(Constants.DONE_BUTTON, height),
                        elementTracker.getDimensions(Constants.DONE_BUTTON).width,
                        elementTracker.getDimensions(Constants.DONE_BUTTON).height
                )
                .build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        final List<Label> labels = elementTracker.getLabels(height, textRenderer.fontHeight);

        for (final Label label : labels) {
            final ElementDimensions labelDimensions = new LeftJustifiedElement(textRenderer.getWidth(label.text), textRenderer.fontHeight);
            context.drawTextWithShadow(textRenderer, label.text, labelDimensions.getX(width), label.y, label.color);
        }
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return switch (listeningForKey) {
            case Constants.SCAN_KEY -> this.setKey(scanKey, keyCode, scanCode);
            case Constants.CLEAR_KEY -> this.setKey(clearKey, keyCode, scanCode);
            default -> super.keyPressed(keyCode, scanCode, modifiers);
        };
    }

    private boolean setKey(final KeyBinding key, int keyCode, int scanCode) {
        key.setBoundKey(InputUtil.fromKeyCode(keyCode, scanCode));
        listeningForKey = "";
        if (this.client != null) {
            this.client.setScreen(new ConfigScreen(parent));
        }

        return true;
    }

    @Override
    public void close() {
        if (this.client != null) {
            this.client.setScreen(this.parent);
        }
    }
}
