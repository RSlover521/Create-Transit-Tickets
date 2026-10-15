package com.rslover521.createtransittickets.registry;

import com.rslover521.createtransittickets.CreateTransitTickets;
import com.rslover521.createtransittickets.item.BlankTicketItem;
import com.rslover521.createtransittickets.item.IncompleteTransitTicketItem;
import com.rslover521.createtransittickets.item.TicketBlueprintItem;
import com.rslover521.createtransittickets.item.TicketGateItem;
import com.rslover521.createtransittickets.item.TransitTicketItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CreateTransitTickets.MOD_ID);

    public static final DeferredHolder<Item, Item> BLANK_TICKET = ITEMS.register("blank_ticket",
            () -> new BlankTicketItem(new Item.Properties()));
    public static final DeferredHolder<Item, Item> TICKET_BLUEPRINT = ITEMS.register("ticket_blueprint",
            () -> new TicketBlueprintItem(new Item.Properties().stacksTo(1)));
    public static final DeferredHolder<Item, Item> INCOMPLETE_TRANSIT_TICKET = ITEMS.register("incomplete_transit_ticket",
            () -> new IncompleteTransitTicketItem(new Item.Properties().stacksTo(1)));
    public static final DeferredHolder<Item, Item> TRANSIT_TICKET = ITEMS.register("transit_ticket",
            () -> new TransitTicketItem(new Item.Properties().stacksTo(16)));
    public static final DeferredHolder<Item, Item> TICKET_GATE = ITEMS.register("ticket_gate",
            () -> new TicketGateItem(ModBlocks.TICKET_GATE.get(), new Item.Properties()));

    private ModItems() {
    }

    public static void register(IEventBus modBus) {
        ITEMS.register(modBus);
    }
}
