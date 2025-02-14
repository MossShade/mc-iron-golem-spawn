package com.mossshade.golemSpawnChecker.client;

public class GolemSpawnCheckerOverride {
    private static final ThreadLocal<Boolean> OVERRIDE_SPAWN_CHECK = ThreadLocal.withInitial(() -> false);

    public static void enableOverride() {
        OVERRIDE_SPAWN_CHECK.set(true);
    }

    public static void disableOverride() {
        OVERRIDE_SPAWN_CHECK.set(false);
    }

    public static boolean isOverrideEnabled() {
        return OVERRIDE_SPAWN_CHECK.get();
    }
}
