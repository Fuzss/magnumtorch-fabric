package fuzs.magnumtorch.mixin;

import fuzs.magnumtorch.api.event.player.LivingSpawnCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(Villager.class)
public abstract class VillagerMixin extends AbstractVillager {
    public VillagerMixin(EntityType<? extends AbstractVillager> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "trySpawnGolem", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;addFreshEntityWithPassengers(Lnet/minecraft/world/entity/Entity;)V"), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    private void trySpawnGolem$invokeAddFreshEntityWithPassengers(ServerLevel serverLevel, CallbackInfoReturnable<IronGolem> callbackInfo, BlockPos blockPos, int index, double randomX, double randomZ, BlockPos blockPos2, IronGolem ironGolem) {
        if (!LivingSpawnCallback.EVENT.invoker().onLivingSpawn(ironGolem, serverLevel, blockPos2.getX(), blockPos2.getY(), blockPos2.getZ(), MobSpawnType.MOB_SUMMONED)) {
            ironGolem.discard();
            callbackInfo.setReturnValue(null);
        }
    }
}
