package fuzs.magnumtorch.mixin;

import fuzs.magnumtorch.api.event.player.LivingSpawnCallback;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(Zombie.class)
public abstract class ZombieMixin extends Monster {
    protected ZombieMixin(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "hurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/monster/Zombie;setTarget(Lnet/minecraft/world/entity/LivingEntity;)V"), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    public void hurt$invokeSetTarget(DamageSource damageSource, float damageAmount, CallbackInfoReturnable<Boolean> callbackInfo, ServerLevel serverLevel, LivingEntity livingEntity, int myPosX, int myPosY, int myPosZ, Zombie zombie, int index, int posX, int posY, int posZ) {
        if (!LivingSpawnCallback.EVENT.invoker().onLivingSpawn(zombie, serverLevel, posX, posY, posZ, MobSpawnType.REINFORCEMENT)) {
            zombie.discard();
            this.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE).addPermanentModifier(new AttributeModifier("Zombie reinforcement caller charge", -0.05000000074505806D, AttributeModifier.Operation.ADDITION));
            // true means zombie was damaged, has nothing to do with reinforcement spawning
            // only needed to prematurely cancel method
            callbackInfo.setReturnValue(true);
        }
    }
}
