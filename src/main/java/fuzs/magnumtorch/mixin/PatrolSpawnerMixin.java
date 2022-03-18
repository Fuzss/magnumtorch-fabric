package fuzs.magnumtorch.mixin;

import fuzs.magnumtorch.api.event.player.LivingSpawnCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.PatrollingMonster;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.PatrolSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Random;

@Mixin(PatrolSpawner.class)
public abstract class PatrolSpawnerMixin {
    @Inject(method = "spawnPatrolMember", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/monster/PatrollingMonster;finalizeSpawn(Lnet/minecraft/world/level/ServerLevelAccessor;Lnet/minecraft/world/DifficultyInstance;Lnet/minecraft/world/entity/MobSpawnType;Lnet/minecraft/world/entity/SpawnGroupData;Lnet/minecraft/nbt/CompoundTag;)Lnet/minecraft/world/entity/SpawnGroupData;"), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    private void spawnPatrolMember$invokeFinalizeSpawn(ServerLevel serverLevel, BlockPos blockPos, Random random, boolean bl, CallbackInfoReturnable<Boolean> callbackInfo, BlockState blockState, PatrollingMonster patrollingMonster) {
        if (!LivingSpawnCallback.EVENT.invoker().onLivingSpawn(patrollingMonster, serverLevel, blockPos.getX(), blockPos.getY(), blockPos.getZ(), MobSpawnType.PATROL)) {
            patrollingMonster.discard();
            callbackInfo.setReturnValue(false);
        }
    }
}
