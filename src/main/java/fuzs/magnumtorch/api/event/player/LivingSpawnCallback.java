package fuzs.magnumtorch.api.event.player;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.LevelAccessor;

@FunctionalInterface
public interface LivingSpawnCallback {
    Event<LivingSpawnCallback> EVENT = EventFactory.createArrayBacked(LivingSpawnCallback.class, listeners -> (Mob mob, LevelAccessor levelAccessor, double posX, double posY, double posZ, MobSpawnType spawnType) -> {
        for (LivingSpawnCallback event : listeners) {
            if (!event.onLivingSpawn(mob, levelAccessor, posX, posY, posZ, spawnType)) {
                return false;
            }
        }
        return true;
    });

    boolean onLivingSpawn(Mob mob, LevelAccessor levelAccessor, double posX, double posY, double posZ, MobSpawnType spawnType);
}
