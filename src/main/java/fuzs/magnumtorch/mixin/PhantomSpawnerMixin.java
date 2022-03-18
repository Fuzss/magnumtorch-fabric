package fuzs.magnumtorch.mixin;

import fuzs.magnumtorch.api.event.player.LivingSpawnCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.ServerStatsCounter;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.PhantomSpawner;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Random;

@Mixin(PhantomSpawner.class)
public abstract class PhantomSpawnerMixin {
    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;addFreshEntityWithPassengers(Lnet/minecraft/world/entity/Entity;)V", shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILHARD)
    public void tick$invokeAddFreshEntityWithPassengers(ServerLevel serverLevel, boolean bl, boolean bl2, CallbackInfoReturnable<Integer> callbackInfo, Random random, int i, Player player, BlockPos blockPos, DifficultyInstance difficultyInstance, ServerStatsCounter serverStatsCounter, int j, int k, BlockPos blockPos2, BlockState blockState, FluidState fluidState, SpawnGroupData spawnGroupData, int l, int m, Phantom phantom) {
        if (!LivingSpawnCallback.EVENT.invoker().onLivingSpawn(phantom, serverLevel, blockPos2.getX(), blockPos2.getY(), blockPos2.getZ(), MobSpawnType.NATURAL)) {
            // no way for cancelling entity being added to world (without disrupting the whole spawning logic for all players as forge does), so all we can do is discard it immediately after being added
            phantom.discard();
        }
    }
}
