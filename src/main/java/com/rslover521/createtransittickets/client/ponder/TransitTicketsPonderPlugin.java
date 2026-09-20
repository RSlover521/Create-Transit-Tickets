package com.rslover521.createtransittickets.client.ponder;

import com.rslover521.createtransittickets.CreateTransitTickets;
import com.rslover521.createtransittickets.registry.ModItems;
import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

public final class TransitTicketsPonderPlugin implements PonderPlugin {
    @Override
    public String getModId() {
        return CreateTransitTickets.MOD_ID;
    }

    @Override
    public void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        PonderSceneRegistrationHelper<Item> itemHelper = helper.withKeyFunction(ForgeRegistries.ITEMS::getKey);
        itemHelper.forComponents(ModItems.BLANK_TICKET.get())
                .addStoryBoard(ResourceLocation.fromNamespaceAndPath("create", "deployer/processing"), BlankTicketPonderScenes::usingBlankTickets);
        itemHelper.forComponents(ModItems.TICKET_GATE.get())
                .addStoryBoard(ResourceLocation.fromNamespaceAndPath("create", "deployer/processing"), TicketGatePonderScenes::usingTicketGates)
                .addStoryBoard(ResourceLocation.fromNamespaceAndPath("create", "deployer/processing"), TicketGatePonderScenes::configuringTicketGates);
    }
}
