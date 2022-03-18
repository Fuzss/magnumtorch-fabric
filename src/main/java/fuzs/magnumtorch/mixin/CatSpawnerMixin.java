package fuzs.magnumtorch.mixin;

import fuzs.magnumtorch.api.event.player.LivingSpawnCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.npc.CatSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(CatSpawner.class)
public abstract class CatSpawnerMixin {
    @Inject(method = "spawnCat", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/Cat;finalizeSpawn(Lnet/minecraft/world/level/ServerLevelAccessor;Lnet/minecraft/world/DifficultyInstance;Lnet/minecraft/world/entity/MobSpawnType;Lnet/minecraft/world/entity/SpawnGroupData;Lnet/minecraft/nbt/CompoundTag;)Lnet/minecraft/world/entity/SpawnGroupData;"), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    private void spawnCat$invokeFinalizeSpawn(BlockPos blockPos, ServerLevel serverLevel, CallbackInfoReturnable<Integer> callbackInfo, Cat cat) {
        if (!LivingSpawnCallback.EVENT.invoker().onLivingSpawn(cat, serverLevel, blockPos.getX(), blockPos.getY(), blockPos.getZ(), MobSpawnType.NATURAL)) {
            cat.discard();
            callbackInfo.setReturnValue(0);
        }
    }
}
