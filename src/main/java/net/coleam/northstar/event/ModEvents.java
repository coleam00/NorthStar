package net.coleam.northstar.event;

import net.coleam.northstar.NorthStar;
import net.coleam.northstar.entity.ModEntities;
import net.coleam.northstar.entity.custom.JadeHoarderEntity;
import net.coleam.northstar.entity.custom.LockedTraderEntity;
import net.coleam.northstar.entity.custom.MurphyEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = NorthStar.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {
    @SubscribeEvent
    public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
        event.put(ModEntities.JADEHOARDER.get(), JadeHoarderEntity.setAttributes());
        event.put(ModEntities.MURPHY.get(), MurphyEntity.setAttributes());
        event.put(ModEntities.LOCKEDTRADER.get(), LockedTraderEntity.setAttributes());
    }
}
