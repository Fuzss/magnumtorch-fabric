package fuzs.magnumtorch.mixin;

import fuzs.magnumtorch.api.event.player.LivingCheckSpawnCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Villager.class)
public abstract class VillagerMixin extends AbstractVillager {
    public VillagerMixin(EntityType<? extends AbstractVillager> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "findSpawnPositionForGolemInColumn", at = @At(value = "RETURN", ordinal = 0), cancellable = true)
    private void findSpawnPositionForGolemInColumn(BlockPos blockPos, double d, double e, CallbackInfoReturnable<BlockPos> callbackInfo) {
        BlockPos blockPos2 = callbackInfo.getReturnValue();
        // handles iron golems spawned by a villager
        if (!LivingCheckSpawnCallback.EVENT.invoker().onLivingCheckSpawn(EntityType.IRON_GOLEM, this.level, blockPos2.getX(), blockPos2.getY(), blockPos2.getZ(), MobSpawnType.MOB_SUMMONED)) {
            callbackInfo.setReturnValue(null);
        }
    }
}
