package fuzs.magnumtorch.mixin;

import fuzs.magnumtorch.api.event.player.LivingSpawnCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Raid.class)
public abstract class RaidMixin {
    @Shadow
    @Final
    private ServerLevel level;

    @Inject(method = "joinRaid", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/raid/Raider;finalizeSpawn(Lnet/minecraft/world/level/ServerLevelAccessor;Lnet/minecraft/world/DifficultyInstance;Lnet/minecraft/world/entity/MobSpawnType;Lnet/minecraft/world/entity/SpawnGroupData;Lnet/minecraft/nbt/CompoundTag;)Lnet/minecraft/world/entity/SpawnGroupData;"), cancellable = true)
    public void joinRaid$invokeFinalizeSpawn(int wave, Raider raider, @Nullable BlockPos blockPos, boolean hasBeenAdded, CallbackInfo callbackInfo) {
        if (!LivingSpawnCallback.EVENT.invoker().onLivingSpawn(raider, this.level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), MobSpawnType.EVENT)) {
            raider.discard();
            callbackInfo.cancel();
        }
    }
}
