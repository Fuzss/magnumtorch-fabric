package fuzs.magnumtorch.mixin;

import fuzs.magnumtorch.api.event.player.LivingCheckSpawnCallback;
import fuzs.magnumtorch.api.event.player.LivingSpawnCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NaturalSpawner.class)
public abstract class NaturalSpawnerMixin {
    @Inject(method = "isValidPositionForMob", at = @At("TAIL"), cancellable = true)
    private static void isValidPositionForMob$tail(ServerLevel serverLevel, Mob mob, double distanceToPlayer, CallbackInfoReturnable<Boolean> callbackInfo) {
        if (callbackInfo.getReturnValue()) {
            if (!LivingSpawnCallback.EVENT.invoker().onLivingSpawn(mob, serverLevel, mob.getX(), mob.getY(), mob.getZ(), MobSpawnType.NATURAL)) {
                mob.discard();
                callbackInfo.setReturnValue(false);
            }
        }
    }

    @Inject(method = "isValidEmptySpawnBlock", at = @At("TAIL"), cancellable = true)
    private static void isValidEmptySpawnBlock$tail(BlockGetter blockGetter, BlockPos blockPos, BlockState blockState, FluidState fluidState, EntityType<?> entityType, CallbackInfoReturnable<Boolean> callbackInfo) {
        // this is used for a lot of mobs, but we need this for phantoms, as the main spawner class doesn't have any good point to hook into without capturing way too many locals
        if (callbackInfo.getReturnValue() && blockGetter instanceof ServerLevel level && entityType == EntityType.PHANTOM) {
            if (!LivingCheckSpawnCallback.EVENT.invoker().onLivingSpawn(entityType, level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), MobSpawnType.NATURAL)) {
                callbackInfo.setReturnValue(false);
            }
        }
    }
}
