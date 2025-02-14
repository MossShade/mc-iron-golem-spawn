package com.mossshade.golemSpawnChecker.mixin.client;

import com.mossshade.golemSpawnChecker.client.GolemSpawnCheckerOverride;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(IronGolemEntity.class)
public class GolemSpawnCheckerMixin {
    @Inject(method = "canSpawn", at = @At("HEAD"), cancellable = true)
    private void modifyCanSpawn(WorldView world, CallbackInfoReturnable<Boolean> cir) {
        if (GolemSpawnCheckerOverride.isOverrideEnabled()) {
            IronGolemEntity golem = (IronGolemEntity) (Object) this;
            BlockPos blockPos = golem.getBlockPos();
            BlockPos blockPos2 = blockPos.down();
            BlockState blockState = world.getBlockState(blockPos2);
            if (!blockState.hasSolidTopSurface(world, blockPos2, golem)) {
                cir.setReturnValue(false);
            } else {
                for(int i = 1; i < 3; ++i) {
                    BlockPos blockPos3 = blockPos.up(i);
                    BlockState blockState2 = world.getBlockState(blockPos3);
                    if (!SpawnHelper.isClearForSpawn(world, blockPos3, blockState2, blockState2.getFluidState(), EntityType.IRON_GOLEM)) {
                        cir.setReturnValue(false);
                        return;
                    }
                }
                boolean result = SpawnHelper.isClearForSpawn(world, blockPos, world.getBlockState(blockPos), Fluids.EMPTY.getDefaultState(), EntityType.IRON_GOLEM);
                cir.setReturnValue(result);
            }
        }
    }

}
