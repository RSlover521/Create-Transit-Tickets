package com.rslover521.createtransittickets.registry;

import com.rslover521.createtransittickets.CreateTransitTickets;
import com.rslover521.createtransittickets.item.BlankTicketItem;
import com.rslover521.createtransittickets.item.TicketBlueprintItem;
import com.rslover521.createtransittickets.item.TransitTicketItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, CreateTransitTickets.MOD_ID);

    public static final RegistryObject<Item> BLANK_TICKET = ITEMS.register("blank_ticket",
            () -> new BlankTicketItem(new Item.Properties()));
    public static final RegistryObject<Item> TICKET_BLUEPRINT = ITEMS.register("ticket_blueprint",
            () -> new TicketBlueprintItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> TRANSIT_TICKET = ITEMS.register("transit_ticket",
            () -> new TransitTicketItem(new Item.Properties().stacksTo(16)));

    private ModItems() {
    }

    public static void register(IEventBus modBus) {
        ITEMS.register(modBus);
    }
}
