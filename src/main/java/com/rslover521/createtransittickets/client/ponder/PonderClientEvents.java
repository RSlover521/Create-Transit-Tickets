package com.rslover521.createtransittickets.client.ponder;

import com.rslover521.createtransittickets.CreateTransitTickets;
import net.createmod.ponder.foundation.PonderIndex;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = CreateTransitTickets.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class PonderClientEvents {
    private PonderClientEvents() {
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        PonderIndex.addPlugin(new TransitTicketsPonderPlugin());
    }
}
