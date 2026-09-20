package com.rslover521.createtransittickets.client.ponder;

import com.rslover521.createtransittickets.CreateTransitTickets;
import com.rslover521.createtransittickets.registry.ModItems;
import com.simibubi.create.foundation.ponder.PonderRegistrationHelper;
import com.simibubi.create.foundation.ponder.PonderRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = CreateTransitTickets.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class PonderClientEvents {
    private PonderClientEvents() {
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        PonderRegistrationHelper helper = new PonderRegistrationHelper(CreateTransitTickets.MOD_ID);
        PonderRegistry.addStoryBoard(helper.createStoryBoardEntry(
                BlankTicketPonderScenes::usingBlankTickets,
                ResourceLocation.fromNamespaceAndPath("create", "deployer/processing"),
                ForgeRegistries.ITEMS.getKey(ModItems.BLANK_TICKET.get())
        ));
        PonderRegistry.addStoryBoard(helper.createStoryBoardEntry(
                TicketGatePonderScenes::usingTicketGates,
                ResourceLocation.fromNamespaceAndPath("create", "deployer/processing"),
                ForgeRegistries.ITEMS.getKey(ModItems.TICKET_GATE.get())
        ));
        PonderRegistry.addStoryBoard(helper.createStoryBoardEntry(
                TicketGatePonderScenes::configuringTicketGates,
                ResourceLocation.fromNamespaceAndPath("create", "deployer/processing"),
                ForgeRegistries.ITEMS.getKey(ModItems.TICKET_GATE.get())
        ));
    }
}
