package fuzs.magnumtorch.mixin;

import fuzs.magnumtorch.api.event.player.LivingSpawnCallback;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.village.VillageSiege;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(VillageSiege.class)
public abstract class VillageSiegeMixin {
    @Inject(method = "trySpawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;addFreshEntityWithPassengers(Lnet/minecraft/world/entity/Entity;)V"), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    private void trySpawn$invokeAddFreshEntityWithPassengers(ServerLevel serverLevel, CallbackInfo callbackInfo, Vec3 vec3, Zombie zombie) {
        if (!LivingSpawnCallback.EVENT.invoker().onLivingSpawn(zombie, serverLevel, vec3.x(), vec3.y(), vec3.z(), MobSpawnType.EVENT)) {
            zombie.discard();
            callbackInfo.cancel();
        }
    }
}
