
package com.misaka.endermiteprotection;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

@Mod(EndermiteProtection.MODID)
public final class EndermiteProtection {
    public static final String MODID = "endermiteprotection";

    public EndermiteProtection() {
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.GAME)
    public static final class Events {
        @SubscribeEvent
        public static void onLivingDamage(LivingDamageEvent event) {
            LivingEntity entity = event.getEntity();

            if (!(entity instanceof Endermite)) {
                return;
            }

            if (!entity.getTags().contains("enderman_tower_bait")) {
                return;
            }

            ResourceLocation id = entity.level()
                    .registryAccess()
                    .registryOrThrow(Registries.DAMAGE_TYPE)
                    .getKey(event.getSource().type());

            if (id != null && id.equals(new ResourceLocation("l2hostility", "killer_aura"))) {
                event.setAmount(0.0F);
            }
        }
    }
}
