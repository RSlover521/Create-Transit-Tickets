package com.rslover521.createtransittickets.client.ponder;

import com.rslover521.createtransittickets.CreateTransitTickets;
import net.createmod.ponder.foundation.PonderIndex;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = CreateTransitTickets.MOD_ID, value = Dist.CLIENT)
public final class PonderClientEvents {
    private PonderClientEvents() {
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        PonderIndex.addPlugin(new TransitTicketsPonderPlugin());
    }
}
