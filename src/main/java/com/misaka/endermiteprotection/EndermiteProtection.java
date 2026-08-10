
package com.misaka.endermiteprotection;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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

            if (!entity.getCommandTags().contains("enderman_tower_bait")) {
                return;
            }

            ResourceLocation id = entity.getWorld()
                    .getRegistryManager()
                    .get(Registries.DAMAGE_TYPE)
                    .getKey(event.getSource().getType());

            if (id != null && id.equals(new ResourceLocation("l2hostility", "killer_aura"))) {
                event.setAmount(0.0F);
            }
        }
    }
}
